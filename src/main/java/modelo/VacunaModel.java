package modelo;

import Clases.Vacuna;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VacunaModel {

    private Conexion conexion;

    public VacunaModel() {
        this.conexion = new Conexion();
    }

    public List<Vacuna> getListaDeVacunas() {
        List<Vacuna> listaDevacunas = new LinkedList<>();
        Connection connection = this.conexion.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM public.vacuna ORDER BY id ASC;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                byte nombre = resultSet.getByte("nombre");
                byte numeroDosis = resultSet.getByte("numero_dosis");
                Date fecha = resultSet.getDate("fecha");
                int clienteId = resultSet.getInt("cliente_id");
                Vacuna vacuna = new Vacuna(id, nombre, numeroDosis, fecha, clienteId);
                listaDevacunas.add(vacuna);
            }
            System.out.println("lista de vacunas obtenida exitosamente");
        } catch (Exception exception) {
            System.err.println("Error al leer la base de datos");
            System.err.println(exception.getMessage());
        }
        return listaDevacunas;
    }

    public boolean guardarVacuna(Map<String, String[]> mapaDeParametros) {
        Connection connection = this.conexion.getConnection();
        String query = "INSERT INTO public.vacuna (nombre, numero_dosis, fecha, cliente_id) VALUES (?, ?, ?, ?);";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setByte(1, Byte.parseByte(mapaDeParametros.get("nombre_vacuna")[0]));
                preparedStatement.setInt(2, Integer.parseInt(mapaDeParametros.get("numero_dosis")[0]));
                preparedStatement.setDate(3, Date.valueOf(mapaDeParametros.get("fecha")[0]));
                preparedStatement.setInt(4, Integer.parseInt(mapaDeParametros.get("cliente_id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("vacuna guardada exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al guardar la vacuna");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean editarVacuna(Map<String, String[]> mapaDeParametros) {
        Connection connection = this.conexion.getConnection();
        String query = "UPDATE public.vacuna SET nombre=?, numero_dosis=?, fecha=?, cliente_id=? WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setByte(1, Byte.parseByte(mapaDeParametros.get("nombre_vacuna")[0]));
                preparedStatement.setInt(2, Integer.parseInt(mapaDeParametros.get("numero_dosis")[0]));
                preparedStatement.setDate(3, Date.valueOf(mapaDeParametros.get("fecha")[0]));
                preparedStatement.setInt(4, Integer.parseInt(mapaDeParametros.get("cliente_id")[0]));
                preparedStatement.setInt(5, Integer.parseInt(mapaDeParametros.get("id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("vacuna editada exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al editar la vacuna");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean eliminarVacuna(int id) {
        Connection connection = this.conexion.getConnection();
        String query = "DELETE FROM public.vacuna WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("vacuna eliminada exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al eliminar la vacuna");
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
