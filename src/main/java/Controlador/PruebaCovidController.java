package Controlador;

import modelo.PruebaCovidModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PruebaCovidController", value = "/prueba-covid")
public class PruebaCovidController extends HttpServlet {

    private PruebaCovidModel pruebaCovidModel;

    public void init() {
        this.pruebaCovidModel = new PruebaCovidModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/vista/PruebaCovidView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = switch (request.getParameter("accion")) {
            case "guardar" -> this.pruebaCovidModel.guardarPruebaCovid(request.getParameterMap());
            case "editar" -> this.pruebaCovidModel.editarPruebaCovid(request.getParameterMap());
            case "eliminar" -> this.pruebaCovidModel.eliminarPruebaCovid(Integer.parseInt(request.getParameterMap().get("id")[0]));
            default -> false;
        };
        if (!resultado) {
            String error = "Ocurrio un error al " + request.getParameter("accion");
            request.setAttribute("error", error);
        }
        getServletContext().getRequestDispatcher("/vista/PruebaCovidView.jsp").forward(request, response);
    }
}
