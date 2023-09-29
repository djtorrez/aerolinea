package Controlador;

import modelo.VacunaModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "VacunaController", value = "/vacuna")
public class VacunaController extends HttpServlet {

    private VacunaModel vacunaModel;

    public void init() {
        this.vacunaModel = new VacunaModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/vista/VacunaView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = switch (request.getParameter("accion")) {
            case "guardar" -> this.vacunaModel.guardarVacuna(request.getParameterMap());
            case "editar" -> this.vacunaModel.editarVacuna(request.getParameterMap());
            case "eliminar" -> this.vacunaModel.eliminarVacuna(Integer.parseInt(request.getParameterMap().get("id")[0]));
            default -> false;
        };
        if (!resultado) {
            String error = "Ocurrio un error al " + request.getParameter("accion");
            request.setAttribute("error", error);
        }
        getServletContext().getRequestDispatcher("/vista/VacunaView.jsp").forward(request, response);
    }
}
