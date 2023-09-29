package modelo;

import Clases.ClienteReserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ClienteReservaModel {

    private Conexion conexion;

    public ClienteReservaModel() {
        this.conexion = new Conexion();
    }

    public List<ClienteReserva> getListaDeClientesQueReservaron() {
        List<ClienteReserva> listaDeClientesQueReservaron = new LinkedList<>();
        Connection connection = this.conexion.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM public.cliente_reserva ORDER BY reserva_id ASC;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int reservaId = resultSet.getInt("reserva_id");
                int clienteId = resultSet.getInt("cliente_id");
                ClienteReserva clienteReserva = new ClienteReserva(clienteId, reservaId);
                listaDeClientesQueReservaron.add(clienteReserva);
            }
            System.out.println("lista de clientes que reservaron obtenida exitosamente");
        } catch (Exception exception) {
            System.err.println("Error al leer la base de datos");
            System.err.println(exception.getMessage());
        }
        return listaDeClientesQueReservaron;
    }

    public boolean guardarClienteReserva(int reservaId, int clienteId) {
        Connection connection = this.conexion.getConnection();
        String query = "INSERT INTO public.cliente_reserva (cliente_id, reserva_id) VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, clienteId);
            preparedStatement.setInt(2, reservaId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new Exception("La declaracion no deviolvio ningun resultado");
            }
            System.out.println("cliente reserva guardado exitosamente");
            return true;
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al guardar el cliente que reserva");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean eliminarReservaDeCliente(int reservaId) {
        Connection connection = this.conexion.getConnection();
        String query = "DELETE FROM public.cliente_reserva WHERE reserva_id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reservaId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new Exception("La declaracion no deviolvio ningun resultado");
            }
            System.out.println("Reserva de clientes eliminada exitosamente");
            return true;
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al eliminar la reserva de clientes");
            System.err.println(exception.getMessage());
            return false;
        }
    }
}
