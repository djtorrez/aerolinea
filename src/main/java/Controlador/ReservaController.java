package Controlador;

import modelo.ReservaModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ReservaController", value = "/reserva")
public class ReservaController extends HttpServlet {

    private ReservaModel reservaModel;

    public void init() {
        this.reservaModel = new ReservaModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/vista/ReservaView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = switch (request.getParameter("accion")) {
            case "guardar" -> this.reservaModel.guardarReserva(request.getParameterMap());
            case "editar" -> this.reservaModel.editarReserva(request.getParameterMap());
            case "eliminar" -> this.reservaModel.eliminarReserva(Integer.parseInt(request.getParameterMap().get("id")[0]));
            default -> false;
        };
        if (!resultado) {
            String error = "Ocurrio un error al " + request.getParameter("accion");
            request.setAttribute("error", error);
        }
        getServletContext().getRequestDispatcher("/vista/ReservaView.jsp").forward(request, response);
    }
}
