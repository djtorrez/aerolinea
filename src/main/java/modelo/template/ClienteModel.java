package modelo.template;

import Clases.Cliente;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClienteModel extends Model {

    public ClienteModel() {
        super();
    }

    public List<Cliente> getListaDeClientes() {
        List<Cliente> listaDeClientes = new LinkedList<>();
        Connection connection = super.conexion.getConnection();
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

    @Override
    protected PreparedStatement prepararDeclaracionGuardar(Connection connection, Map<String, String[]> parametros) throws SQLException {
        String query = "INSERT INTO public.cliente (nombre, ci, telefono) VALUES (?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, parametros.get("nombre")[0]);
        preparedStatement.setString(2, parametros.get("ci")[0]);
        preparedStatement.setInt(3, Integer.parseInt(parametros.get("telefono")[0]));
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepararDeclaracionEditar(Connection connection, Map<String, String[]> parametros) throws SQLException {
        String query = "UPDATE public.cliente SET nombre=?, ci=?, telefono=? WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, parametros.get("nombre")[0]);
        preparedStatement.setString(2, parametros.get("ci")[0]);
        preparedStatement.setInt(3, Integer.parseInt(parametros.get("telefono")[0]));
        preparedStatement.setInt(4, Integer.parseInt(parametros.get("id")[0]));
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepararDeclaracionEliminar(Connection connection,int id) throws SQLException {
        String query = "DELETE FROM public.cliente WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }
}
