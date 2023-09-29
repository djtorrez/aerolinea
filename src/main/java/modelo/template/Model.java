package modelo.template;

import modelo.Conexion;

import java.sql.*;
import java.util.Map;

public abstract class Model {

    protected Conexion conexion;

    public Model() {
        this.conexion = new Conexion();
    }

    protected abstract PreparedStatement prepararDeclaracionGuardar(Connection connection, Map<String, String[]> parametros) throws SQLException;

    public boolean guardarCliente(Map<String, String[]> parametros) {
        Connection connection = this.conexion.getConnection();
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = this.prepararDeclaracionGuardar(connection, parametros);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new Exception("La declaracion no deviolvio ningun resultado");
                }
                connection.commit();
                System.out.println("cliente template guardado exitosamente");
                return true;
            } catch (Exception exception) {
                connection.rollback(savepoint);
                System.err.println("ocurrio un problema al guardar el cliente template");
                System.err.println(exception.getMessage());
                return false;
            }
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al hacer BEGINTRANSACTION o al hacer ROLLBACK");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    protected abstract PreparedStatement prepararDeclaracionEditar(Connection connection, Map<String, String[]> parametros) throws SQLException;

    public boolean editarCliente(Map<String, String[]> parametros) {
        Connection connection = this.conexion.getConnection();
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = this.prepararDeclaracionEditar(connection, parametros);
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

    protected abstract PreparedStatement prepararDeclaracionEliminar(Connection connection, int id) throws SQLException;

    public boolean eliminarCliente(int id) {
        Connection connection = this.conexion.getConnection();
        try {
            Savepoint savepoint = connection.setSavepoint();
            try {
                PreparedStatement preparedStatement = this.prepararDeclaracionEliminar(connection, id);
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
