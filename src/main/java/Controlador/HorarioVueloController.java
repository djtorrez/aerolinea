package Controlador;

import modelo.HorarioVueloModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "HorarioVueloController", value = "/horario-vuelo")
public class HorarioVueloController extends HttpServlet {

    private HorarioVueloModel horarioVueloModel;

    public void init() {
        this.horarioVueloModel = new HorarioVueloModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/vista/HorarioVueloView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = switch (request.getParameter("accion")) {
            case "guardar" -> this.horarioVueloModel.guardarHorarioVuelo(request.getParameterMap());
            case "editar" -> this.horarioVueloModel.editarHorarioVuelo(request.getParameterMap());
            case "eliminar" ->this.horarioVueloModel.eliminarHorarioVuelo(Integer.parseInt(request.getParameterMap().get("id")[0]));
            default -> false;
        };
        if (!resultado) {
            String error = "Ocurrio un error al " + request.getParameter("accion");
            request.setAttribute("error", error);
        }
        getServletContext().getRequestDispatcher("/vista/HorarioVueloView.jsp").forward(request, response);
    }
}
