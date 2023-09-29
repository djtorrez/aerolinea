<%@ page import="modelo.ClienteModel" %>
<%@ page import="Clases.Cliente" %>
<%@ page import="modelo.PruebaCovidModel" %>
<%@ page import="Clases.PruebaCovid" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: aleja
  Date: 11/3/2021
  Time: 10:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestionar Prueba Covid</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <a href="/"><--Men&uacute;--</a><br>
    <h1>Gestionar Prueba Covid</h1>
    <%!
        List<PruebaCovid> listaDePuebasDeCovid;
        List<Cliente> listaDeClientes;
        String getNombreCliente(int clienteId) {
            String resultado = "";
            for (Cliente clienteActual : listaDeClientes) {
                if (clienteActual.getId() == clienteId)
                    return clienteActual.getNombre();
            }
            return resultado;
        }
        String getNombrePruebaCovid(byte vacunaId) {
            String nombreVacuna;
            switch (vacunaId)
            {
                case 1:  nombreVacuna = "PCR &oacute; NAAT";
                    break;
                case 2:  nombreVacuna = "Analisis de Sangre";
                    break;
                case 3:  nombreVacuna = "Pueba de antigenos";
                    break;
                case 4:  nombreVacuna = "Prueba de saliba";
                    break;
                default: nombreVacuna = "id invalido";
                    break;
            }
            return nombreVacuna;
        }
    %>
    <%
        PruebaCovidModel pruebaCovidModel = new PruebaCovidModel();
        ClienteModel clienteModel = new ClienteModel();
        listaDePuebasDeCovid = pruebaCovidModel.getListaDePruebasDeCovid();
        listaDeClientes = clienteModel.getListaDeClientes();
    %>
    <form action="prueba-covid" method="post">
        <label for="tipo_prueba">Tipo de prueba:</label>
        <select id="tipo_prueba" name="tipo_prueba">
            <option value="1" selected>PCR &oacute; NAAT</option>
            <option value="2">Analisis de Sangre</option>
            <option value="3">Pueba de antigenos</option>
            <option value="4">Prueba de saliba</option>
        </select><br>
        <label for="resultado">Resultado:</label>
        <select id="resultado" name="resultado">
            <option value="true" selected>Positivo</option>
            <option value="false">Negativo</option>
        </select><br>
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
            <th>Tipo prueba</th>
            <th>Resultado</th>
            <th>Fecha</th>
            <th>Cliente</th>
            <th>Acciones</th>
        </tr>
        <%for (PruebaCovid pruebaDeCovid: listaDePuebasDeCovid) {%>
        <tr>
            <td><%= pruebaDeCovid.getId() %></td>
            <td><%= this.getNombrePruebaCovid(pruebaDeCovid.getTipo()) %></td>
            <td><%= pruebaDeCovid.isResultado() ? "Positivo" : "Negativo" %></td>
            <td><%= pruebaDeCovid.getFecha() %></td>
            <td><%= this.getNombreCliente(pruebaDeCovid.getClienteId()) %></td>
            <td>
                <button onclick="editar(<%= pruebaDeCovid.getId() %>, <%= pruebaDeCovid.getTipo() %>,<%= pruebaDeCovid.isResultado() %>, '<%= pruebaDeCovid.getFecha() %>', <%= pruebaDeCovid.getClienteId() %>)">Editar</button>
                <form action="prueba-covid" method="post">
                    <input type="number" name="id" value="<%= pruebaDeCovid.getId() %>" hidden>
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
        function editar(id, tipoPrueba, resultado, fecha, clienteId) {
            let inputId = document.getElementById("id");
            let selectTipoPrueba = document.getElementById("tipo_prueba");
            let inputResulado = document.getElementById("resultado");
            let inputFecha = document.getElementById("fecha");
            let selectClienteId = document.getElementById("cliente_id");
            let inputAccion = document.getElementById("accion");
            inputId.value = id;
            selectTipoPrueba.value = tipoPrueba;
            inputResulado.value = resultado;
            inputFecha.value = fecha;
            selectClienteId.value = clienteId;
            inputAccion.value = "editar";
        }
    </script>
</body>
</html>
