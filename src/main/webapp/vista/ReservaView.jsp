<%@ page import="java.util.List" %>
<%@ page import="Clases.*" %>
<%@ page import="modelo.*" %><%--
  Created by IntelliJ IDEA.
  User: aleja
  Date: 11/5/2021
  Time: 6:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestionar Reserva</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <a href="/"><--Men&uacute;--</a><br>
    <h1>Gestionar Reserva</h1>
    <%!
        List<Avion> listaDeAviones;
        List<Destino> listaDeDestinos;
        List<HorarioVuelo> listaDeHorariosDeVuelo;
        List<Cliente> listaDeClientes;
        List<Reserva> listaDeReservas;
        List<Asiento> listaDeAsientos;
        List<ClienteReserva> listaDeClientesQueReservaron;
        List<ReservaAsiento> listaDeAsientosReservados;
        int avionSeleccionadoId;

        String getDestino(int destinoId) {
            String resultado = "";
            for (Destino destinoActual : listaDeDestinos) {
                if (destinoActual.getId() == destinoId)
                    return destinoActual.getNombre();
            }
            return resultado;
        }
        String getHorarioVuelo(int horarioVueloId) {
            String resultado = "";
            for (HorarioVuelo horarioVueloActual : listaDeHorariosDeVuelo) {
                if (horarioVueloActual.getId() == horarioVueloId)
                    return horarioVueloActual.getHora().toString() +" - "+ horarioVueloActual.getFecha().toString();
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

        String getNombreAvion(int avionId) {
            String resultado = "";
            for (Avion avionActual : listaDeAviones) {
                if (avionActual.getId() == avionId) {
                    return avionActual.getModelo();
                }
            }
            return resultado;
        }

        String getAsientos(int reservaId, boolean comoArreglo) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean primerElemento = true;
            for (ReservaAsiento reservaAsientoActual : listaDeAsientosReservados) {
                if (reservaAsientoActual.getReservaId() == reservaId) {
                    for (Asiento asientoActual : listaDeAsientos) {
                        if (asientoActual.getId() == reservaAsientoActual.getAsientoId()) {
                            if (comoArreglo) {
                                if (primerElemento) {
                                    stringBuilder.append("'").append(asientoActual.getId()).append("'");
                                    primerElemento = false;
                                } else {
                                    stringBuilder.append(", '").append(asientoActual.getId()).append("'");
                                }
                            } else {
                                stringBuilder.append(asientoActual.getId())
                                        .append(" - ").append(getTipoAsiento(asientoActual.getTipo()))
                                        .append("<br>");
                            }
                            this.avionSeleccionadoId = asientoActual.getAvionId();
                        }
                    }
                }
            }
            return stringBuilder.toString();
        }
        String getClientes(int reservaId, boolean comoArreglo) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean primerElemento = true;
            for (ClienteReserva clienteReservaActual : listaDeClientesQueReservaron) {
                if (clienteReservaActual.getReservaId() == reservaId) {
                    for (Cliente clienteActual : listaDeClientes) {
                        if (clienteActual.getId() == clienteReservaActual.getClienteId()) {
                            if (comoArreglo) {
                                if (primerElemento) {
                                    stringBuilder.append("'").append(clienteActual.getId()).append("'");
                                    primerElemento = false;
                                } else {
                                    stringBuilder.append(", '").append(clienteActual.getId()).append("'");
                                }
                            } else {
                                stringBuilder.append(clienteActual.getId())
                                        .append(" - ").append(clienteActual.getNombre())
                                        .append("<br>");
                            }
                        }
                    }
                }
            }
            return stringBuilder.toString();
        }
    %>
    <%
        AvionModel avionModel = new AvionModel();
        listaDeAviones = avionModel.getListaDeAviones();
        DestinoModel destinoModel = new DestinoModel();
        listaDeDestinos = destinoModel.getListaDestinos();
        HorarioVueloModel horarioVueloModel = new HorarioVueloModel();
        listaDeHorariosDeVuelo = horarioVueloModel.getListaHorariosVuelo();
        ClienteModel clienteModel = new ClienteModel();
        listaDeClientes = clienteModel.getListaDeClientes();
        ReservaModel reservaModel = new ReservaModel();
        listaDeReservas = reservaModel.getListaDeReservas();
        AsientoModel asientoModel = new AsientoModel();
        listaDeAsientos = asientoModel.getListaDeAsientos();
        ClienteReservaModel clienteReservaModel = new ClienteReservaModel();
        listaDeClientesQueReservaron = clienteReservaModel.getListaDeClientesQueReservaron();
        ReservaAsientoModel reservaAsientoModel = new ReservaAsientoModel();
        listaDeAsientosReservados = reservaAsientoModel.getListaDeAsientosReservados();

    %>
    <form action="reserva" method="post">
        <label for="avion_id">Avion:</label>
        <select id="avion_id" name="avion_id">
            <option value="0">Seleccione un avi&oacute;n</option>
            <%for (Avion avionActual : listaDeAviones) {%>
                <option value="<%= avionActual.getId() %>"><%= avionActual.getModelo() %></option>
            <%}%>
        </select><br>
        <label for="destino_id">Destino:</label>
        <select id="destino_id" name="destino_id">
            <%for (Destino destinoActual : listaDeDestinos) {%>
                <%if (destinoActual.isEstado()) {%>
                    <option value="<%= destinoActual.getId() %>"><%= destinoActual.getNombre() %></option>
                <%}%>
            <%}%>
        </select><br>
        <label for="horario_vuelo_id">Horario Vuelo:</label>
        <select id="horario_vuelo_id" name="horario_vuelo_id">
            <%for (HorarioVuelo horarioVueloActual : listaDeHorariosDeVuelo) {%>
                <%if (horarioVueloActual.isEstado()) {%>
                    <option value="<%= horarioVueloActual.getId() %>"><%= horarioVueloActual.getHora().toString() + " - " + horarioVueloActual.getFecha() %></option>
                <%}%>
            <%}%>
        </select><br>
        <label for="asientos_id">Asientos:</label>
        <select id="asientos_id" name="asientos_id" multiple>
        </select><br>
        <label for="clientes_id">Clientes:</label>
        <select id="clientes_id" name="clientes_id" multiple>
            <%for (Cliente clienteActual : listaDeClientes) {%>
                <option value="<%= clienteActual.getId() %>"><%= clienteActual.getNombre() %></option>
            <%}%>
        </select><br>
        <input type="text" id="accion" name="accion" value="guardar" hidden><br>
        <input type="number" id="id" name="id" hidden>
        <button type="reset">Limpiar</button>
        <input type="submit" value="Guardar">
    </form>
    <table>
        <tr>
            <th>id</th>
            <th>Destino</th>
            <th>Horario Vuelo</th>
            <th>Asientos</th>
            <th>Avi&oacute;n</th>
            <th>Clientes</th>
            <th>Acciones</th>
        </tr>
        <%for (Reserva reservaActual : listaDeReservas) {%>
        <tr>
            <td><%= reservaActual.getId() %></td>
            <td><%= this.getDestino(reservaActual.getDestinoId()) %></td>
            <td><%= this.getHorarioVuelo(reservaActual.getHrarioVueloId()) %></td>
            <td><%= this.getAsientos(reservaActual.getId(), false) %></td>
            <td><%= this.getNombreAvion(this.avionSeleccionadoId) %></td>
            <td><%= this.getClientes(reservaActual.getId(), false) %></td>
            <td>
                <button onclick="editar(<%= reservaActual.getId() %>, <%= reservaActual.getDestinoId() %>, <%= reservaActual.getHrarioVueloId() %>)">Editar</button>
                <form action="reserva" method="post">
                    <input type="number" name="id" value="<%= reservaActual.getId() %>" hidden>
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
        let arrayDeAsientos = [];
        <%for (Asiento asientoActual : listaDeAsientos) {%>
            <%if (asientoActual.getEstado() == 'D') {%>
                arrayDeAsientos.push({
                    id : <%=asientoActual.getId()%>,
                    tipo : "<%= this.getTipoAsiento(asientoActual.getTipo()) %>",
                    estado : "<%= this.getEstadoAsiento(asientoActual.getEstado()) %>",
                    avionId : <%=asientoActual.getAvionId()%>
                });
            <%}%>
        <%}%>
        let arrayDeReservas = [];
        <%for (Reserva reservaActual : listaDeReservas) {%>
            arrayDeReservas.push({
                id : <%= reservaActual.getId() %>,
                asientos : [<%= this.getAsientos(reservaActual.getId(), true) %>],
                avionId: <%= avionSeleccionadoId %>,
                clientes : [<%= this.getClientes(reservaActual.getId(), true) %>]
            });
        <%}%>

        let selectDeAviones = document.getElementById("avion_id");
        let selectDeAsientos = document.getElementById("asientos_id");
        selectDeAviones.addEventListener("change", function (event) {
            // while (selectDeAsientos.target.length > 0) {
            //     selectDeAsientos.target.remove(0);
            // }
            // event.target.length = 0;
            cargarSelectDeAsientos();
            // console.log(event.target.length);
            // console.log(event);
            // console.log(selectDeAviones.options[selectDeAviones.selectedIndex].value);
        });

        function cargarSelectDeAsientos() {
            selectDeAsientos.options.length = 0;
            arrayDeAsientos.forEach(function (value, index, array) {
                if (value.avionId === parseInt(selectDeAviones.value, 10)) {
                    let optionNuevo = new Option(value.id + " - " + value.tipo, value.id);
                    selectDeAsientos.appendChild(optionNuevo);
                }
            });
        }

        function editar(id, destinoId, horarioId) {
            let inputId = document.getElementById("id");
            let selectAvionId = document.getElementById("avion_id");
            let selectDestinoId = document.getElementById("destino_id");
            let selectHorarioVueloId = document.getElementById("horario_vuelo_id");
            let selectDeAsientosId = document.getElementById("asientos_id");
            let selectDeClientesId = document.getElementById("clientes_id");
            let inputAccion = document.getElementById("accion");
            let objetoDeReserva;
            arrayDeReservas.forEach(function (value, index, array) {
                if (value.id === id) {
                    objetoDeReserva = value;
                }
            })
            for (let i = 0; i < arrayDeReservas.length; i++) {
                if (arrayDeReservas[i].id === id) {
                    objetoDeReserva = arrayDeReservas[i];
                    break;
                }
            }
            selectAvionId.value = objetoDeReserva.avionId;
            cargarSelectDeAsientos();
            selectDestinoId.value = destinoId;
            selectHorarioVueloId.value = horarioId;
            for (let j = 0; j < selectDeAsientosId.options.length; j++) {
                selectDeAsientosId.options[j].selected = objetoDeReserva.asientos.indexOf(selectDeAsientosId.options[j].value) >= 0;
            }
            for (let k = 0; k < selectDeClientesId.options.length; k++) {
                selectDeClientesId.options[k].selected = objetoDeReserva.clientes.indexOf(selectDeClientesId.options[k].value) >= 0;
            }
            inputId.value = id;
            inputAccion.value = "editar";
        }
    </script>
</body>
</html>
