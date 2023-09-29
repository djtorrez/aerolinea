package modelo;

import Clases.Cliente;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClienteModel {

    private Conexion conexion;

    public ClienteModel() {
        this.conexion = new Conexion();
    }

    public List<Cliente> getListaDeClientes() {
        List<Cliente> listaDeClientes = new LinkedList<>();
        Connection connection = this.conexion.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM public.cliente ORDER BY id ASC;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String ci = resultSet.getString("ci");
                int telefono = resultSet.getInt("telefono");
                Cliente cliente = new Cliente(id, nombre, ci, telefono);
                listaDeClientes.add(cliente);
            }
            System.out.println("lista de clientes obtenida exitosamente");
        } catch (Exception exception) {
            System.err.println("Error al leer la base de datos");
            System.err.println(exception.getMessage());
        }
        return listaDeClientes;
    }

    public boolean guardarCliente(Map<String, String[]> parametros) {
        Connection connection = this.conexion.getConnection();
        String query = "INSERT INTO public.cliente (nombre, ci, telefono) VALUES (?, ?, ?);";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, parametros.get("nombre")[0]);
                preparedStatement.setString(2, parametros.get("ci")[0]);
                preparedStatement.setInt(3, Integer.parseInt(parametros.get("telefono")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("cliente guardado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al guardar el cliente");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean editarCliente(Map<String, String[]> parametros) {
        Connection connection = this.conexion.getConnection();
        String query = "UPDATE public.cliente SET nombre=?, ci=?, telefono=? WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, parametros.get("nombre")[0]);
                preparedStatement.setString(2, parametros.get("ci")[0]);
                preparedStatement.setInt(3, Integer.parseInt(parametros.get("telefono")[0]));
                preparedStatement.setInt(4, Integer.parseInt(parametros.get("id")[0]));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("cliente editado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al editar el cliente");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        Connection connection = this.conexion.getConnection();
        String query = "DELETE FROM public.cliente WHERE id=?;";
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("Al eliminar la declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("cliente eliminado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al eliminar el cliente");
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
