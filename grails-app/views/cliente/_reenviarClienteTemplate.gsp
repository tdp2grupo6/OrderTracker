<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Recordatorio como Cliente en Order Tracker</title>
</head>

<body>
    <p>Estimado(a) ${nombreCompleto}:</p>

    <p>Le reenviamos su Código QR de validación como Cliente de Order Tracker. Recuerde que debe presentarlo a nuestros Vendedores cada vez que sea visitado:</p>

    <img src="cid:qrcode" alt="Imagen Código ${validador}" />

    <p>Esperamos que nuestros servicios sean de su utilidad, si tiene alguna consulta, no dude en contactarnos.</p>

    <p>Equipo Order Tracker</p>
</body>
</html>