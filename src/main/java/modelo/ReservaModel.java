package modelo;

import Clases.Reserva;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReservaModel {

    private final Conexion conexion;
    private final List<ClienteReservaModel> listaDeClientesQueReservan = new LinkedList<>();
    private final List<ReservaAsientoModel> listaDeAsientosQueSonReservados = new LinkedList<>();

    public ReservaModel() {
        this.conexion = new Conexion();
    }

    public List<Reserva> getListaDeReservas() {
        List<Reserva> listaDeReservas = new LinkedList<>();
        Connection connection = this.conexion.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM public.reserva ORDER BY id ASC;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int destinoId = resultSet.getInt("destino_id");
                int horarioVueloId = resultSet.getInt("horario_vuelo_id");
                Reserva reserva = new Reserva(id, destinoId, horarioVueloId);
                listaDeReservas.add(reserva);
            }
            System.out.println("lista de reservas obtenida exitosamente");
        } catch (Exception exception) {
            System.err.println("Error al leer la base de datos");
            System.err.println(exception.getMessage());
        }
        return listaDeReservas;
    }

    public boolean guardarReserva(Map<String, String[]> mapaDeParametros) {
        if (mapaDeParametros.get("asientos_id").length != mapaDeParametros.get("clientes_id").length) {
            System.err.println("La Cantidad De asientos no coinciden con la de clientes");
            return false;
        }
        Connection connection = this.conexion.getConnection();
        String query = "INSERT INTO public.reserva (destino_id, horario_vuelo_id) VALUES (?, ?);";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Integer.parseInt(mapaDeParametros.get("destino_id")[0]));
                preparedStatement.setInt(2, Integer.parseInt(mapaDeParametros.get("horario_vuelo_id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                int reservaId = 0;
                if (resultSet.next()) {
                    reservaId = resultSet.getInt(1);
                }

                this.GuardarReservaDeClientesYAsientos(mapaDeParametros, reservaId);

                connection.commit();
                this.listaDeAsientosQueSonReservados.clear();
                this.listaDeClientesQueReservan.clear();
                System.out.println("reserva guardada exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al guardar la reserva");
                exception.printStackTrace();
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean editarReserva(Map<String, String[]> mapaDeParametros) {
        if (mapaDeParametros.get("asientos_id").length != mapaDeParametros.get("clientes_id").length) {
            System.err.println("La Cantidad De asientos no coinciden con la de clientes");
            return false;
        }
        Connection connection = this.conexion.getConnection();
        String query = "UPDATE public.reserva SET destino_id=?, horario_vuelo_id=? WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(mapaDeParametros.get("destino_id")[0]));
                preparedStatement.setInt(2, Integer.parseInt(mapaDeParametros.get("horario_vuelo_id")[0]));
                preparedStatement.setInt(3, Integer.parseInt(mapaDeParametros.get("id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }

                ReservaAsientoModel reservaAsientoModel = new ReservaAsientoModel();
                ClienteReservaModel clienteReservaModel = new ClienteReservaModel();
                this.listaDeAsientosQueSonReservados.add(reservaAsientoModel);
                this.listaDeClientesQueReservan.add(clienteReservaModel);

                boolean resultado;
                resultado = reservaAsientoModel.eliminarReservaDeAsiento(Integer.parseInt(mapaDeParametros.get("id")[0]));
                resultado = resultado && clienteReservaModel.eliminarReservaDeCliente(Integer.parseInt(mapaDeParametros.get("id")[0]));

                if (!resultado) {
                    throw new Exception("No se pudieron eliminar los detalles de reserva al editar");
                }

                this.GuardarReservaDeClientesYAsientos(mapaDeParametros, Integer.parseInt(mapaDeParametros.get("id")[0]));

                connection.commit();
                System.out.println("reserva editada exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al editar la reserva");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public void GuardarReservaDeClientesYAsientos(Map<String, String[]> mapaDeParametros, int reservaId) throws Exception {
        boolean hayResultado = true;
        for (int i = 0; i < mapaDeParametros.get("asientos_id").length && hayResultado; i++) {
            ReservaAsientoModel reservaAsientoModel = new ReservaAsientoModel();
            ClienteReservaModel clienteReservaModel = new ClienteReservaModel();
            this.listaDeAsientosQueSonReservados.add(reservaAsientoModel);
            this.listaDeClientesQueReservan.add(clienteReservaModel);
            hayResultado = reservaAsientoModel.guardarReservaAsiento(reservaId, Integer.parseInt(mapaDeParametros.get("asientos_id")[i]));
            hayResultado = hayResultado && clienteReservaModel.guardarClienteReserva(reservaId, Integer.parseInt(mapaDeParametros.get("clientes_id")[i]));
        }
        if (!hayResultado) {
            throw new Exception("ocurrio algun error al guardar los detalles de la reserva");
        }
    }

    public boolean eliminarReserva(int id) {
        Connection connection = this.conexion.getConnection();
        String query = "DELETE FROM public.reserva WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("Reserva eliminada exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al eliminar la reserva");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }
}
