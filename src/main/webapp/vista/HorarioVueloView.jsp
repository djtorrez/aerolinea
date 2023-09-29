<%@ page import="modelo.HorarioVueloModel" %>
<%@ page import="Clases.HorarioVuelo" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: aleja
  Date: 10/27/2021
  Time: 7:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestionar Horario Vuelo</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <a href="/"><--Men&uacute;--</a><br>
    <h1>Gestionar Horario Vuelo</h1>
    <form action="horario-vuelo" method="post">
        <label for="fecha">Fecha:</label>
        <input id="fecha" type="date" name="fecha" required><br>
        <label for="hora">Hora:</label>
        <input id="hora" type="time" name="hora" required><br>
        <label for="estado">Estado:</label>
        <select id="estado" name="estado">
            <option value="true" selected>Activo</option>
            <option value="false">No Activo</option>
        </select>
        <input type="text" id="accion" name="accion" value="guardar" hidden><br>
        <input type="number" id="id" name="id" value="0" hidden>
        <button type="reset">Limpiar</button>
        <input type="submit" value="Guardar">
    </form>
    <%
        HorarioVueloModel horarioVueloModel = new HorarioVueloModel();
        List<HorarioVuelo> listaDeHorariosVuelo = horarioVueloModel.getListaHorariosVuelo();
    %>
    <table>
        <tr>
            <th>id</th>
            <th>Fecha</th>
            <th>Hora</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        <%for (HorarioVuelo horarioVuelo: listaDeHorariosVuelo) {%>
        <tr>
            <td><%= horarioVuelo.getId() %></td>
            <td><%= horarioVuelo.getFecha() %></td>
            <td><%= horarioVuelo.getHora() %></td>
            <td><%= horarioVuelo.isEstado()? "Activo" : "No Activo" %></td>
            <td>
                <button onclick="editar(<%= horarioVuelo.getId() %>,'<%= horarioVuelo.getFecha() %>','<%= horarioVuelo.getHora() %>', <%= horarioVuelo.isEstado()%>)">Editar</button>
                <form action="horario-vuelo" method="post">
                    <input type="number" name="id" value="<%= horarioVuelo.getId() %>" hidden>
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
        function editar(id, fecha, hora, estado) {
            let inputId = document.getElementById("id");
            let inputFecha = document.getElementById("fecha");
            let inputHora = document.getElementById("hora");
            let selectEstado = document.getElementById("estado");
            let inputAccion = document.getElementById("accion");
            inputId.value = id;
            inputFecha.value = fecha;
            inputHora.value = hora.substring(0,5); //05:30:00 -> 05:30
            selectEstado.value = estado;
            inputAccion.value = "editar";
        }
    </script>
</body>
</html>
