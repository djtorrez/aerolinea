package modelo;

import Clases.Avion;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AvionModel {

    private Conexion conexion;

    public AvionModel() {
        this.conexion = new Conexion();
    }

    public List<Avion> getListaDeAviones() {
        List<Avion> listaDeAviones = new LinkedList<>();
        Connection connection = this.conexion.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM public.avion ORDER BY id ASC;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String modelo = resultSet.getString("modelo");
                String fabricante = resultSet.getString("fabricante");
                String matricula = resultSet.getString("matricula");
                boolean estado = resultSet.getBoolean("estado");
                Avion avion = new Avion(id, modelo, fabricante, estado, matricula);
                listaDeAviones.add(avion);
            }
            System.out.println("lista de aviones obtenida exitosamente");
        } catch (Exception exception) {
            System.err.println("Error al leer la base de datos");
            System.err.println(exception.getMessage());
        }
        return listaDeAviones;
    }

    public boolean guardarAvion(Map<String, String[]> parametros) {
        Connection connection = this.conexion.getConnection();
        String query = "INSERT INTO public.avion(modelo, fabricante, matricula, estado) VALUES (?, ?, ?, ?);";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, parametros.get("modelo")[0]);
                preparedStatement.setString(2, parametros.get("fabricante")[0]);
                preparedStatement.setString(3, parametros.get("matricula")[0]);
                preparedStatement.setBoolean(4, Boolean.parseBoolean(parametros.get("estado")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("avion guardado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al guardar el avion");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean editarAvion(Map<String, String[]> parametros) {
        Connection connection = this.conexion.getConnection();
        String query = "UPDATE public.avion SET modelo=?, fabricante=?, matricula=?, estado=? WHERE id = ?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, parametros.get("modelo")[0]);
                preparedStatement.setString(2, parametros.get("fabricante")[0]);
                preparedStatement.setString(3, parametros.get("matricula")[0]);
                preparedStatement.setBoolean(4, Boolean.parseBoolean(parametros.get("estado")[0]));
                preparedStatement.setInt(5, Integer.parseInt(parametros.get("id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("avion editado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al editar el avion");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean eliminarAvion(int id) {
        Connection connection = this.conexion.getConnection();
        String query = "DELETE FROM public.avion WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("avion eliminado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al eliminar el avion");
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
