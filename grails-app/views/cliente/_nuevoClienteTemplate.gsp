<%--
  Created by IntelliJ IDEA.
  User: dgacitua
  Date: 20-05-16
  Time: 13:16
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Su registro como Cliente en Order Tracker</title>
</head>

<body>
    <p>Estimado(a) ${nombreCompleto}:</p>

    <p>Le informamos que se ha registrado como Cliente en la plataforma Order Tracker para hacer pedidos de productos.</p>

    <p>Se le ha asignado un Código QR de validación, el cual debe mostrar a nuestros Vendedores cuando sea visitado:</p>

    <img src="cid:qrcode" alt="Imagen Código ${validador}" />

    <p>Esperamos que nuestros servicios sean de su utilidad, si tiene alguna consulta, no dude en contactarnos.</p>

    <p>Equipo Order Tracker</p>
</body>
</html>