package Controlador;

import modelo.AvionModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AvionController", value = "/avion")
public class AvionController extends HttpServlet {

    AvionModel avionModel;

    public void init() {
        this.avionModel = new AvionModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/vista/AvionView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = switch (request.getParameter("accion")) {
            case "guardar" -> this.avionModel.guardarAvion(request.getParameterMap());
            case "editar" -> this.avionModel.editarAvion(request.getParameterMap());
            case "eliminar" -> this.avionModel.eliminarAvion(Integer.parseInt(request.getParameterMap().get("id")[0]));
            default -> false;
        };
        if (!resultado) {
            String error = "Ocurrio un error al " + request.getParameter("accion");
            request.setAttribute("error", error);
        }
        getServletContext().getRequestDispatcher("/vista/AvionView.jsp").forward(request, response);
    }
}
