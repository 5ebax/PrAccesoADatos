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
        <title>Agregar alumnos</title>
    </head>
    <body>
<div class="loader-page"></div>
    	<h2 class="title"><%=request.getAttribute("variableInicio")%></h2>
		<form action="/ProyectoFinalAD/AddServlet" method="post">
		<div class="login">
		    <p class="a">Nombre:
		    <input class="cuad1" type="text" name="nombre"></p>
		    <p class="a">Apellido:
		    <input class="cuad2" type="text" name="apellido"></p>
		    <p class="a">Edad:
		    <input class="cuad3" type="text" name="edad"></p>
		    <p class="a">Asignatura que quiere cursar:
		    <select class="option" name="asignatura">
		      <option value="Prog. Multimedia">Prog. Multimedia
		      <option value="Acceso a Datos" selected>Acceso a Datos
		      <option value="Empresas">Empresas
		      <option value="Diseño de Interfaces">Diseño de Interfaces
		      <option value="Sistemas de Gesti&oacuten Empresarial">S.G. Empresarial
		    </select></p>
		</div>
		    <p><input class="boton" type="submit" value="Enviar"></p>
        </form>
        <form action="/ProyectoFinalAD/IndexServlet" method="post">
		<p><input class="boton2" type="submit" value="Volver"></p>
	</form>
	<p class="b">Proyecto de Acceso a datos.</p>
    </body>
</html>