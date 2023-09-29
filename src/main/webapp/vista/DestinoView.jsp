<%@ page import="java.util.List" %>
<%@ page import="Clases.Destino" %>
<%@ page import="modelo.DestinoModel" %><%--
  Created by IntelliJ IDEA.
  User: Alejandro
  Date: 10/23/2021
  Time: 10:46 AM
  To change this modelo.template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestionar Destino</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <a href="/"><--Men&uacute;--</a><br>
    <!-- <a href="<%= application.getContextPath() %>"><--Men&uacute;69--</a><br> -->
    <h1>Gestionar Destino</h1>
    <form action="destino" method="post">
        <label for="nombre">Nombre:</label>
        <input id="nombre" type="text" name="nombre" required><br>
        <label for="estados">Estado:</label>
        <select id="estados" name="estado">
            <option value="true" selected>Activo</option>
            <option value="false">No Activo</option>
        </select>
        <input type="text" id="accion" name="accion" value="guardar" hidden><br>
        <input type="number" id="id" name="id" hidden>
        <button type="reset">Limpiar</button>
        <input type="submit" value="Guardar">
    </form>
    <%
        DestinoModel destinoModel = new DestinoModel();
        List<Destino> listaDeDestinos = destinoModel.getListaDestinos();
    %>
    <table>
        <tr>
            <th>id</th>
            <th>Nombre</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        <%for (Destino destino: listaDeDestinos) {%>
        <tr>
            <td><%= destino.getId() %></td>
            <td><%= destino.getNombre() %></td>
            <td><%= destino.isEstado()? "Activo" : "No Activo" %></td>
            <td>
                <button onclick="editar(<%= destino.getId() %>,'<%= destino.getNombre() %>',<%= destino.isEstado() %>)">Editar</button>
                <form action="destino" method="post">
                    <input type="number" name="id" value="<%= destino.getId() %>" hidden>
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
    function editar(id, nombreDestino, estado) {
        let inputId = document.getElementById("id");
        let inputNombre = document.getElementById("nombre");
        let selectEstado = document.getElementById("estados");
        let inputAccion = document.getElementById("accion");
        inputId.value = id;
        inputNombre.value = nombreDestino;
        selectEstado.value = estado;
        inputAccion.value = "editar";
    }
</script>
</body>
</html>
