package Controlador;

import modelo.AsientoModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AsientoController", value = "/asiento")
public class AsientoController extends HttpServlet {

    private AsientoModel asientoModel;

    public void init() {
        this.asientoModel = new AsientoModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/vista/AsientoView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = switch (request.getParameter("accion")) {
            case "guardar" -> this.asientoModel.guardarAsiento(request.getParameterMap());
            case "editar" -> this.asientoModel.editarAsiento(request.getParameterMap());
            case "eliminar" -> this.asientoModel.eliminarAsiento(Integer.parseInt(request.getParameterMap().get("id")[0]));
            default -> false;
        };
        if (!resultado) {
            String error = "Ocurrio un error al " + request.getParameter("accion");
            request.setAttribute("error", error);
        }
        getServletContext().getRequestDispatcher("/vista/AsientoView.jsp").forward(request, response);
    }
}
