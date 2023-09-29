package Controlador;

import modelo.DestinoModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DestinoController", value = "/destino")
public class DestinoController extends HttpServlet {

    private DestinoModel destinoModel;

    public void init() {
        this.destinoModel = new DestinoModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/vista/DestinoView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = switch (request.getParameter("accion")) {
            case "guardar" -> this.destinoModel.guardarDestino(request.getParameterMap());
            case "editar" -> this.destinoModel.editarDestino(request.getParameterMap());
            case "eliminar" -> this.destinoModel.eliminarDestino(Integer.parseInt(request.getParameterMap().get("id")[0]));
            default -> false;
        };
        if (!resultado) {
            String error = "Ocurrio un error al " + request.getParameter("accion");
            request.setAttribute("error", error);
        }
        getServletContext().getRequestDispatcher("/vista/DestinoView.jsp").forward(request, response);
    }
}
