package modelo;

import Clases.PruebaCovid;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PruebaCovidModel {

    private Conexion conexion;

    public PruebaCovidModel() {
        this.conexion = new Conexion();
    }

    public List<PruebaCovid> getListaDePruebasDeCovid() {
        List<PruebaCovid> listaDePruebasDeCovid = new LinkedList<>();
        Connection connection = this.conexion.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM public.prueba_covid ORDER BY id ASC;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Byte tipo = resultSet.getByte("tipo");
                boolean resultado = resultSet.getBoolean("resultado");
                Date fecha = resultSet.getDate("fecha");
                int cliente_id = resultSet.getInt("cliente_id");
                PruebaCovid pruebaCovid = new PruebaCovid(id, tipo, resultado, fecha, cliente_id);
                listaDePruebasDeCovid.add(pruebaCovid);
            }
            System.out.println("lista de pruebas de covid obtenida exitosamente");
        } catch (Exception exception) {
            System.err.println("Error al leer la base de datos");
            System.err.println(exception.getMessage());
        }
        return listaDePruebasDeCovid;
    }

    public boolean guardarPruebaCovid(Map<String, String[]> mapaDeParametros) {
        Connection connection = this.conexion.getConnection();
        String query = "INSERT INTO public.prueba_covid (tipo, resultado, fecha, cliente_id) VALUES (?, ?, ?, ?);";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setByte(1, Byte.parseByte(mapaDeParametros.get("tipo_prueba")[0]));
                preparedStatement.setBoolean(2, Boolean.parseBoolean(mapaDeParametros.get("resultado")[0]));
                preparedStatement.setDate(3, Date.valueOf(mapaDeParametros.get("fecha")[0]));
                preparedStatement.setInt(4, Integer.parseInt(mapaDeParametros.get("cliente_id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no devolvio ningun resultado");
                }
                connection.commit();
                System.out.println("prueba covid guardada exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al guardar la prueba covid");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean editarPruebaCovid(Map<String, String[]> mapaDeParametros) {
        Connection connection = this.conexion.getConnection();
        String query = "UPDATE public.prueba_covid SET tipo=?, resultado=?, fecha=?, cliente_id=? WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setByte(1, Byte.parseByte(mapaDeParametros.get("tipo_prueba")[0]));
                preparedStatement.setBoolean(2, Boolean.parseBoolean(mapaDeParametros.get("resultado")[0]));
                preparedStatement.setDate(3, Date.valueOf(mapaDeParametros.get("fecha")[0]));
                preparedStatement.setInt(4, Integer.parseInt(mapaDeParametros.get("cliente_id")[0]));
                preparedStatement.setInt(5, Integer.parseInt(mapaDeParametros.get("id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no devolvio ningun resultado");
                }
                connection.commit();
                System.out.println("prueba covid editada exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al editar la prueba covid");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean eliminarPruebaCovid(int id) {
        Connection connection = this.conexion.getConnection();
        String query = "DELETE FROM public.prueba_covid WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no devolvio ningun resultado");
                }
                connection.commit();
                System.out.println("prueba covid eliminada exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al eliminar la prueba covid");
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
