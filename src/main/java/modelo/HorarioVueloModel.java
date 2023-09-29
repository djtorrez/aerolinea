package modelo;

import Clases.HorarioVuelo;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HorarioVueloModel {

    private Conexion conexion;

    public HorarioVueloModel() {
        this.conexion = new Conexion();
    }

    public List<HorarioVuelo> getListaHorariosVuelo() {
        List<HorarioVuelo> listaDeHorariosDeVuelo = new LinkedList<>();
        Connection connection = this.conexion.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM public.horario_vuelo ORDER BY id ASC;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                boolean estado = resultSet.getBoolean("estado");
                HorarioVuelo horarioVuelo = new HorarioVuelo(id, fecha, hora, estado);
                listaDeHorariosDeVuelo.add(horarioVuelo);
            }
            System.out.println("lista de horarios de vuelo obtenida exitosamente");
        } catch (Exception exception) {
            System.err.println("Error al leer la base de datos");
            System.err.println(exception.getMessage());
        }
        return listaDeHorariosDeVuelo;
    }

    public boolean guardarHorarioVuelo(Map<String, String[]> parametros) {
        Connection connection = this.conexion.getConnection();
        String query = "INSERT INTO public.horario_vuelo(fecha, hora, estado) VALUES (?, ?, ?);";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDate(1, Date.valueOf(parametros.get("fecha")[0]));
                preparedStatement.setTime(2, Time.valueOf(parametros.get("hora")[0] + ":00"));
                preparedStatement.setBoolean(3, Boolean.parseBoolean(parametros.get("estado")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("horario de vuelo guardado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al guardar el horario de vuelo");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean editarHorarioVuelo(Map<String, String[]> parametros) {
        Connection connection = this.conexion.getConnection();
        String query = "UPDATE public.horario_vuelo SET fecha=?, hora=?, estado=? WHERE id = ?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDate(1, Date.valueOf(parametros.get("fecha")[0]));
                preparedStatement.setTime(2, Time.valueOf(parametros.get("hora")[0] + ":00"));
                preparedStatement.setBoolean(3, Boolean.parseBoolean(parametros.get("estado")[0]));
                preparedStatement.setInt(4, Integer.parseInt(parametros.get("id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("horario de vuelo editado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al editar el horario de vuelo");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean eliminarHorarioVuelo(int id) {
        Connection connection = this.conexion.getConnection();
        String query = "DELETE FROM public.horario_vuelo WHERE id = ?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("horario de vuelo eliminado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al eliminar el horario de vuelo");
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
