package modelo;

import Clases.Asiento;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AsientoModel {

    private Conexion conexion;

    public AsientoModel() {
        this.conexion = new Conexion();
    }

    public List<Asiento> getListaDeAsientos() {
        List<Asiento> listaDeasientos = new LinkedList<>();
        Connection connection = this.conexion.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM public.asiento ORDER BY id ASC;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int AvionId = resultSet.getInt("avion_id");
                int id = resultSet.getInt("id");
                char tipo = resultSet.getString("tipo").charAt(0);
                char estado = resultSet.getString("estado").charAt(0);
                Asiento asiento = new Asiento(id, tipo, estado, AvionId);
                listaDeasientos.add(asiento);
            }
            System.out.println("lista de asientos obtenida exitosamente");
        } catch (Exception exception) {
            System.err.println("Error al leer la base de datos");
            System.err.println(exception.getMessage());
        }
        return listaDeasientos;
    }

    public boolean guardarAsiento(Map<String, String[]> mapaDeParametros) {
        Connection connection = this.conexion.getConnection();
        String query = "INSERT INTO public.asiento (avion_id, tipo, estado) VALUES (?, ?, ?);";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(mapaDeParametros.get("avion_id")[0]));
                preparedStatement.setString(2, mapaDeParametros.get("tipo_asiento")[0]);
                preparedStatement.setString(3, mapaDeParametros.get("estado_asiento")[0]);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("Asiento guardado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al guardar el asiento");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean editarAsiento(Map<String, String[]> mapaDeParametros) {
        Connection connection = this.conexion.getConnection();
        String query = "UPDATE public.asiento SET avion_id=?, tipo=?, estado=? WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(mapaDeParametros.get("avion_id")[0]));
                preparedStatement.setString(2, mapaDeParametros.get("tipo_asiento")[0]);
                preparedStatement.setString(3, mapaDeParametros.get("estado_asiento")[0]);
                preparedStatement.setInt(4, Integer.parseInt(mapaDeParametros.get("id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("Asiento editado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al editar el asiento");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean eliminarAsiento(int id) {
        Connection connection = this.conexion.getConnection();
        String query = "DELETE FROM public.asiento WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("Asiento eliminado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al eliminar el asiento");
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
