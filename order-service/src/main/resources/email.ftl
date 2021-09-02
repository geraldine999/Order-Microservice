<!doctype html>
<html>
<head>
    <meta name="viewport" content="width=device-width" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>GeekShirt Email</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body class="">
    <span class="preheader">This is preheader text. Some clients will show this text as a preview.</span>
    <table role="presentation" border="0" cellpadding="0" cellspacing="0" class="body">
        <tr>
            <td>&nbsp;</td>
            <td class="container">
                <div class="content">

                    <!-- START CENTERED WHITE CONTAINER -->
                    <table role="presentation" class="main">

                        <!-- START MAIN CONTENT AREA -->
                        <tr>
                            <td class="wrapper">
                                <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td>
                                            <p>Hola ${user}.</p>
                                            <p>Por favor revisa el status de la siguiente orden.</p>
                                            <p><b>Order Id:</b> ${orderId} </p>
                                            <p><b>Tracking Id:</b> ${orderTrackingId} </p>
                                            <p><b>Status Orden:</b> ${orderStatus} </p>
                                            <p><b>Dirección de Envio:</b> ${orderAddress} </p>
                                            <p><b>Fecha de Envio:</b> ${orderShippingDate} </p>
                                            <p><b>Fecha de Entrega:</b> ${orderDeliveryDate} </p>
                                            <p><b>Total de Envio:</b> ${orderTotal} </p>
                                            <table role="presentation" border="0" cellpadding="0" cellspacing="0" class="btn btn-primary">
                                                <tbody>
                                                    <tr>
                                                        <td align="left">
                                                            <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                                                                <tbody>
                                                                    <tr>
                                                                        <td> <a href="#" target="_blank">Rastrear Orden</a> </td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <p>           <p>Geekshirt.</p>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>

                        <!-- END MAIN CONTENT AREA -->
                    </table>
                    <!-- END CENTERED WHITE CONTAINER -->

                    <!-- START FOOTER -->
                    <div class="footer">
                        <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="content-block">
                                    <span class="apple-link">GeekShirt Company Inc, 354 Downtown Road, San Francisco CA 94784</span>
                                    <br> No te gustan estos emails? <a href="http://i.imgur.com/CScmqnj.gif">unsuscribite</a>.
                                </td>
                            </tr>
                            <tr>
                                <td class="content-block powered-by">
                                    Powered by <a href="http://htmlemail.io">GeekShirt</a>.
                                </td>
                            </tr>
                        </table>
                    </div>
                    <!-- END FOOTER -->

                </div>
            </td>
            <td>&nbsp;</td>
        </tr>
    </table>
</body>
</html>