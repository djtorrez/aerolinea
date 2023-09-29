package modelo;

import Clases.Destino;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DestinoModel {

    private Conexion conexion;

    public DestinoModel() {
        this.conexion = new Conexion();
    }

    public List<Destino> getListaDestinos () {
        List<Destino> listaDeDestinos = new LinkedList<>();
        Connection connection = this.conexion.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM public.destino ORDER BY id ASC;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                boolean estado = resultSet.getBoolean("estado");
                Destino destino = new Destino(id, nombre, estado);
                listaDeDestinos.add(destino);
            }
            System.out.println("lista de destinos obtenida exitosamente");
        } catch (Exception exception) {
            System.err.println("Error al leer la base de datos");
            System.err.println(exception.getMessage());
        }
        return listaDeDestinos;
    }

    public boolean guardarDestino(Map<String, String[]> parametros) {
        Connection connection = this.conexion.getConnection();
        String query = "INSERT INTO public.destino(nombre, estado) VALUES (?, ?);";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, parametros.get("nombre")[0]);
                preparedStatement.setBoolean(2, Boolean.parseBoolean(parametros.get("estado")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("destino guardado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al guardar el destino");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean editarDestino(Map<String, String[]> parametros) {
        Connection connection = this.conexion.getConnection();
        String query = "UPDATE public.destino SET nombre=?, estado=? WHERE id = ?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, parametros.get("nombre")[0]);
                preparedStatement.setBoolean(2, Boolean.parseBoolean(parametros.get("estado")[0]));
                preparedStatement.setInt(3, Integer.parseInt(parametros.get("id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("destino editado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al editar el destino");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean eliminarDestino(int id) {
        Connection connection = this.conexion.getConnection();
        String query = "DELETE FROM public.destino WHERE id = ?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("destino eliminado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al eliminar el destino");
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
