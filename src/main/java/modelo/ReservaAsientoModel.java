package modelo;

import Clases.ReservaAsiento;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ReservaAsientoModel {

    private Conexion conexion;

    public ReservaAsientoModel() {
        this.conexion = new Conexion();
    }

    public List<ReservaAsiento> getListaDeAsientosReservados() {
        List<ReservaAsiento> listaDeAsientosReservados = new LinkedList<>();
        Connection connection = this.conexion.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM public.reserva_asiento ORDER BY reserva_id ASC;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int reservaId = resultSet.getInt("reserva_id");
                int asientoId = resultSet.getInt("asiento_id");
                ReservaAsiento reservaAsiento = new ReservaAsiento(reservaId, asientoId);
                listaDeAsientosReservados.add(reservaAsiento);
            }
            System.out.println("lista de asientos reservados obtenida exitosamente");
        } catch (Exception exception) {
            System.err.println("Error al leer la base de datos");
            System.err.println(exception.getMessage());
            exception.printStackTrace();
        }
        return listaDeAsientosReservados;
    }

    public boolean guardarReservaAsiento(int reservaId, int asientoId) {
        Connection connection = this.conexion.getConnection();
        String query = "INSERT INTO public.reserva_asiento (asiento_id, reserva_id) VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, asientoId);
            preparedStatement.setInt(2, reservaId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new Exception("La declaracion no deviolvio ningun resultado");
            }
            System.out.println("asiento reservado guardado exitosamente");
            return true;
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al guardar el asiento reservado");
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public boolean eliminarReservaDeAsiento(int reservaId) {
        Connection connection = this.conexion.getConnection();
        String query = "DELETE FROM public.reserva_asiento WHERE reserva_id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reservaId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new Exception("La declaracion no deviolvio ningun resultado");
            }
            System.out.println("Reserva de asientos eliminada exitosamente");
            return true;
        } catch (Exception exception) {
            System.err.println("ocurrio un problema al eliminar la reserva de asientos");
            System.err.println(exception.getMessage());
            return false;
        }
    }
}
