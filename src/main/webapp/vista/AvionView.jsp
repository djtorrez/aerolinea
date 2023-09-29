<%@ page import="modelo.AvionModel" %>
<%@ page import="java.util.List" %>
<%@ page import="Clases.Avion" %><%--
  Created by IntelliJ IDEA.
  User: Alejandro
  Date: 10/30/2021
  Time: 11:08 AM
  To change this modelo.template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestionar Avion</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <a href="/"><--Men&uacute;--</a><br>
    <h1>Gestionar Avion</h1>
    <form action="avion" method="post">
        <label for="modelo">Modelo:</label>
        <input id="modelo" type="text" name="modelo" required><br>
        <label for="fabricante">Fabricante:</label>
        <input id="fabricante" type="text" name="fabricante" required><br>
        <label for="matricula">Matricula:</label>
        <input id="matricula" type="text" name="matricula" required><br>
        <label for="estado">Estado:</label>
        <select id="estado" name="estado">
            <option value="true" selected>Disponible</option>
            <option value="false">No Disponible</option>
        </select>
        <input type="text" id="accion" name="accion" value="guardar" hidden><br>
        <input type="number" id="id" name="id" hidden>
        <button type="reset">Limpiar</button>
        <input type="submit" value="Guardar">
    </form>
    <%
        AvionModel avionModel = new AvionModel();
        List<Avion> listaDeAviones = avionModel.getListaDeAviones();
    %>
    <table>
        <tr>
            <th>id</th>
            <th>Modelo</th>
            <th>Fabricante</th>
            <th>Matricula</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        <%for (Avion avion: listaDeAviones) {%>
        <tr>
            <td><%= avion.getId() %></td>
            <td><%= avion.getModelo() %></td>
            <td><%= avion.getFabricante() %></td>
            <td><%= avion.getMatricula() %></td>
            <td><%= avion.isEstado()? "Activo" : "No Activo" %></td>
            <td>
                <button onclick="editar(<%= avion.getId() %>,'<%= avion.getModelo() %>','<%= avion.getFabricante() %>','<%= avion.getMatricula() %>',<%= avion.isEstado() %>)">Editar</button>
                <form action="avion" method="post">
                    <input type="number" name="id" value="<%= avion.getId() %>" hidden>
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
        function editar(id, modelo, fabricante, matricula, estado) {
            let inputId = document.getElementById("id");
            let inputModelo = document.getElementById("modelo");
            let inputFabricante = document.getElementById("fabricante");
            let inputMatricula = document.getElementById("matricula");
            let selectEstado = document.getElementById("estado");
            let inputAccion = document.getElementById("accion");
            inputId.value = id;
            inputModelo.value = modelo;
            inputFabricante.value = fabricante;
            inputMatricula.value = matricula;
            selectEstado.value = estado;
            inputAccion.value = "editar";
        }
    </script>
</body>
</html>
