<%@ page import="java.util.List" %>
<%@ page import="Clases.Avion" %>
<%@ page import="Clases.Asiento" %>
<%@ page import="modelo.AvionModel" %>
<%@ page import="modelo.AsientoModel" %><%--
  Created by IntelliJ IDEA.
  User: aleja
  Date: 11/4/2021
  Time: 2:48 AM
  To change this modelo.template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestionar Asiento</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <a href="/"><--Men&uacute;--</a><br>
    <h1>Gestionar Asiento</h1>
    <%!
        private List<Avion> listaDeAviones;
        private List<Asiento> listaDeAsientos;
        String getModeloAvion(int avionId) {
            String resultado = "";
            for (Avion avionActual : listaDeAviones) {
                if (avionActual.getId() == avionId)
                    return avionActual.getModelo();
            }
            return resultado;
        }
        String getTipoAsiento(char letra) {
            if (letra == 'P') {
                return "Primera Clase";
            } else if (letra == 'E') {
                return "Clase Ejecutiva";
            }
            return "Clase Econ&oacute;mica";
        }
        String getEstadoAsiento(char letra) {
            if (letra == 'R') {
                return "Reservado";
            } else if (letra == 'D') {
                return "Disponible";
            }
            return "Bloqueado";
        }
    %>
    <%
        AvionModel avionModel = new AvionModel();
        AsientoModel asientoModel = new AsientoModel();
        listaDeAviones = avionModel.getListaDeAviones();
        listaDeAsientos = asientoModel.getListaDeAsientos();
    %>
    <form action="asiento" method="post">
        <label for="avion_id">Avion:</label>
        <select id="avion_id" name="avion_id">
            <%for (Avion avionActual : listaDeAviones) {%>
            <option value="<%= avionActual.getId() %>"><%= avionActual.getModelo() %></option>
            <%}%>
        </select><br>
        <label for="tipo_asiento">Tipo de asiento:</label>
        <select id="tipo_asiento" name="tipo_asiento">
            <option value="P" selected>Primera Clase</option>
            <option value="E">Clase Ejecutiva</option>
            <option value="C">Clase Econ&oacute;mica</option>
        </select><br>
        <label for="estado_asiento">Estado:</label>
        <select id="estado_asiento" name="estado_asiento">
            <option value="R" selected>Reservado</option>
            <option value="D">Disponible</option>
            <option value="B">Bloqueado</option>
        </select><br>
        <input type="text" id="accion" name="accion" value="guardar" hidden><br>
        <input type="number" id="id" name="id" hidden>
        <button type="reset">Limpiar</button>
        <input type="submit" value="Guardar">
    </form>
    <table>
        <tr>
            <th>id</th>
            <th>Modelo Avion</th>
            <th>Tipo Asiento</th>
            <th>Estado Asiento</th>
            <th>Acciones</th>
        </tr>
        <%for (Asiento asientoActual: listaDeAsientos) {%>
        <tr>
            <td><%= asientoActual.getId() %></td>
            <td><%= this.getModeloAvion(asientoActual.getAvionId()) %></td>
            <td><%= this.getTipoAsiento(asientoActual.getTipo()) %></td>
            <td><%= this.getEstadoAsiento(asientoActual.getEstado()) %></td>
            <td>
                <button onclick="editar(<%= asientoActual.getId() %>, <%= asientoActual.getAvionId() %>, '<%= asientoActual.getTipo() %>', '<%= asientoActual.getEstado() %>')">Editar</button>
                <form action="asiento" method="post">
                    <input type="number" name="id" value="<%= asientoActual.getId() %>" hidden>
                    <input type="text" name="accion" value="eliminar" hidden>
                    <input type="submit" value="Eliminar">
                </form>
            </td>
        </tr>
        <%}%>

    </table>
    <script>
        let error = "${error}";
        if (error.length > 0) {
            alert(error);
        }
        function editar(id, avionId, tipo, estado) {
            let inputId = document.getElementById("id");
            let selectIdAvion = document.getElementById("avion_id");
            let selectTipoAsiento = document.getElementById("tipo_asiento");
            let selectEstadoAsiento = document.getElementById("estado_asiento");
            let inputAccion = document.getElementById("accion");
            inputId.value = id;
            selectIdAvion.value = avionId;
            selectTipoAsiento.value = tipo;
            selectEstadoAsiento.value = estado;
            inputAccion.value = "editar";
        }
    </script>
</body>
</html>
