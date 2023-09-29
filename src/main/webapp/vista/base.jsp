<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Contenedores Docker Modulo 4</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <header>
        <!-- Contenido del encabezado -->
        <h1 class="text-3xl font-bold underline">¡Bienvenido a Mi Aplicación!</h1>
    </header>

    <main>
        <!-- Aquí se incluirá el contenido específico de cada página -->
        <jsp:include page="${contentPage}" />
    </main>

    <footer>
        <!-- Contenido del pie de página -->
        <p>Derechos de autor © 2023 Mi Aplicación</p>
    </footer>
</body>
</html>
