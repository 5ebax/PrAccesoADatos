<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript">
$(window).on('load', function () {
    setTimeout(function () {
  $(".loader-page").css({visibility:"hidden",opacity:"0"})
}, 2000);
   
});
</script>
    <head>
    <style type="text/css" media="screen">
      /*la directiva include copia el contenido de un archivo y lo incrusta en la pagina*/
     <%@ include file="estilo.css" %>
	</style>
		<link rel="shortchut icon" type="image/png" href="/ProyectoFinalAD/favicon.png"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alumnado</title>
    </head>
    <body>
    <div class="loader-page"></div>
    	<h2 class="title">¿Qué base de datos quiere usar?</h2>
		<form action="/ProyectoFinalAD/ChooseDBServlet" method="get">
		    <p><input class="boton" name="variable" type="submit" value="MySQL"></p>
		 </form>
		<form action="/ProyectoFinalAD/ChooseDBServlet" method="post">
		    <p><input class="boton" name="variable" type="submit" value="NeoDatis"></p>
        </form>
		<form action="/ProyectoFinalAD/ChooseDBServlet" method="post">
		    <p><input class="boton" name="variable" type="submit" value="En XML"></p>
        </form>
        <p class="b">Proyecto de Acceso a datos.</p>
    </body>
</html>