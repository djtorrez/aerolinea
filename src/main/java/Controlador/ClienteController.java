package Controlador;

import modelo.template.ClienteModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ClienteController", value = "/cliente")
public class ClienteController extends HttpServlet {

    private ClienteModel clienteModel;

    public void init() {
        this.clienteModel = new ClienteModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/vista/ClienteView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = switch (request.getParameter("accion")) {
            case "guardar" -> this.clienteModel.guardarCliente(request.getParameterMap());
            case "editar" -> this.clienteModel.editarCliente(request.getParameterMap());
            case "eliminar" ->this.clienteModel.eliminarCliente(Integer.parseInt(request.getParameterMap().get("id")[0]));
            default -> false;
        };
        if (!resultado) {
            String error = "Ocurrio un error al " + request.getParameter("accion");
            request.setAttribute("error", error);
        }
        getServletContext().getRequestDispatcher("/vista/ClienteView.jsp").forward(request, response);
    }
}
