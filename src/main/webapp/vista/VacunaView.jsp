<%@ page import="modelo.VacunaModel" %>
<%@ page import="Clases.Vacuna" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.ClienteModel" %>
<%@ page import="Clases.Cliente" %><%--
  Created by IntelliJ IDEA.
  User: aleja
  Date: 11/3/2021
  Time: 10:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestionar Vacuna</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <a href="/"><--Men&uacute;--</a><br>
    <h1>Gestionar Vacuna</h1>
    <%!
        List<Vacuna> listaDeVacunas;
        List<Cliente> listaDeClientes;
        String getNombreCliente(int clienteId) {
            String resultado = "";
            for (Cliente clienteActual : listaDeClientes) {
                if (clienteActual.getId() == clienteId)
                    return clienteActual.getNombre();
            }
            return resultado;
        }
        String getNombreVacuna(byte vacunaId) {
            String nombreVacuna;
            switch (vacunaId)
            {
                case 1:  nombreVacuna = "Pfizer";
                    break;
                case 2:  nombreVacuna = "Sinopharm";
                    break;
                case 3:  nombreVacuna = "Sputnik V";
                    break;
                case 4:  nombreVacuna = "Astrazeneca";
                    break;
                default: nombreVacuna = "id invalido";
                    break;
            }
            return nombreVacuna;
        }
    %>
    <%
        VacunaModel vacunaModel = new VacunaModel();
        ClienteModel clienteModel = new ClienteModel();
        listaDeVacunas = vacunaModel.getListaDeVacunas();
        listaDeClientes = clienteModel.getListaDeClientes();
    %>
    <form action="vacuna" method="post">
        <label for="nombre_vacuna">Nombre de Vacuna:</label>
        <select id="nombre_vacuna" name="nombre_vacuna">
            <option value="1" selected>Pfizer</option>
            <option value="2">Sinopharm</option>
            <option value="3">Sputnik V</option>
            <option value="4">Astrazeneca</option>
        </select><br>
        <label for="numero_dosis">Numero Dosis:</label>
        <input id="numero_dosis" type="number" name="numero_dosis" min="1" max="5" required><br>
        <label for="fecha">Fecha:</label>
        <input id="fecha" type="date" name="fecha" required><br>
        <label for="cliente_id">Cliente:</label>
        <select id="cliente_id" name="cliente_id">
            <%for (Cliente clienteActual : listaDeClientes) {%>
            <option value="<%= clienteActual.getId() %>"><%= clienteActual.getNombre() %></option>
            <%}%>
        </select>
        <input type="text" id="accion" name="accion" value="guardar" hidden><br>
        <input type="number" id="id" name="id" hidden>
        <button type="reset">Limpiar</button>
        <input type="submit" value="Guardar">
    </form>
    <table>
        <tr>
            <th>id</th>
            <th>Nombre Vacuna</th>
            <th>Numero Dosis</th>
            <th>Fecha</th>
            <th>Cliente</th>
            <th>Acciones</th>
        </tr>
        <%for (Vacuna vacuna: listaDeVacunas) {%>
        <tr>
            <td><%= vacuna.getId() %></td>
            <td><%= this.getNombreVacuna(vacuna.getNombre()) %></td>
            <td><%= vacuna.getNumeroDosis() %></td>
            <td><%= vacuna.getFecha() %></td>
            <td><%= this.getNombreCliente(vacuna.getClienteId()) %></td>
            <td>
                <button onclick="editar(<%= vacuna.getId() %>,<%= vacuna.getNombre() %>,<%= vacuna.getNumeroDosis() %>, '<%= vacuna.getFecha() %>', <%= vacuna.getClienteId() %>)">Editar</button>
                <form action="vacuna" method="post">
                    <input type="number" name="id" value="<%= vacuna.getId() %>" hidden>
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
        function editar(id, nombreVacuna, numeroDosis, fecha, clienteId) {
            let inputId = document.getElementById("id");
            let selectNombreVacuna = document.getElementById("nombre_vacuna");
            let inputNumeroDosis = document.getElementById("numero_dosis");
            let inputFecha = document.getElementById("fecha");
            let selectClienteId = document.getElementById("cliente_id");
            let inputAccion = document.getElementById("accion");
            inputId.value = id;
            selectNombreVacuna.value = nombreVacuna;
            inputFecha.value = fecha;
            inputNumeroDosis.value = numeroDosis;
            selectClienteId.value = clienteId;
            inputAccion.value = "editar";
        }
    </script>
</body>
</html>
