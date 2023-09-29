<%@ page import="modelo.ClienteModel" %>
<%@ page import="Clases.Cliente" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: aleja
  Date: 10/31/2021
  Time: 10:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestionar Cliente</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <a href="/"><--Men&uacute;--</a><br>
    <h1>Gestionar Cliente</h1>
    <form action="cliente" method="post">
        <label for="nombre">Nombre:</label>
        <input id="nombre" type="text" name="nombre" required><br>
        <label for="ci">Ci:</label>
        <input id="ci" type="text" name="ci" required><br>
        <label for="telefono">Telefono:</label>
        <input id="telefono" type="number" name="telefono" required><br>
        <input type="text" id="accion" name="accion" value="guardar" hidden><br>
        <input type="number" id="id" name="id" value="0" hidden>
        <button type="reset">Limpiar</button>
        <input type="submit" value="Guardar">
    </form>
    <%
        ClienteModel clienteModel = new ClienteModel();
        List<Cliente> listaDeClientes = clienteModel.getListaDeClientes();
    %>
    <table>
        <tr>
            <th>id</th>
            <th>Nombre</th>
            <th>Ci</th>
            <th>Telefono</th>
            <th>Acciones</th>
        </tr>
        <%for (Cliente cliente : listaDeClientes) {%>
        <tr>
            <td><%= cliente.getId() %></td>
            <td><%= cliente.getNombre() %></td>
            <td><%= cliente.getCi() %></td>
            <td><%= cliente.getTelefono() %></td>
            <td>
                <button onclick="editar(<%= cliente.getId() %>,'<%= cliente.getNombre() %>','<%= cliente.getCi() %>', <%= cliente.getTelefono()%>)">Editar</button>
                <form action="cliente" method="post">
                    <input type="number" name="id" value="<%= cliente.getId() %>" hidden>
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
        function editar(id, nombre, ci, telefono) {
            let inputId = document.getElementById("id");
            let inputNombre = document.getElementById("nombre");
            let inputCi = document.getElementById("ci");
            let inputTelefono = document.getElementById("telefono");
            let inputAccion = document.getElementById("accion");
            inputId.value = id;
            inputNombre.value = nombre;
            inputCi.value = ci;
            inputTelefono.value = telefono;
            inputAccion.value = "editar";
        }
    </script>
</body>
</html>
