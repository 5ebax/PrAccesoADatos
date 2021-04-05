<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="java.util.*,
    		java.text.*,mvc.modelo.beans.Alumno"%>
<%
	List<Alumno> alumnos = (List<Alumno>) request.getAttribute("alumnos");
	int j = 0;
	if(request.getAttribute("boton").equals("En XML") || request.getAttribute("boton").equals("Backup XML")){
		j = 1;
	}
%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript">
$(window).on('load', function () {
    setTimeout(function () {
  $(".loader-page").css({visibility:"hidden",opacity:"0"})
}, 2000);
   
});
</script>
<style type="text/css" media="screen">
      /*la directiva include copia el contenido de un archivo y lo incrusta en la pagina*/
     <%@ include file="tabla.css" %>
</style>
	<link rel="shortchut icon" type="image/png" href="/ProyectoFinalAD/favicon.png"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Lista de Alumnos</title>
</head>
<body><div class="loader-page"></div>
<h2 class="title">Lista del Alumnado</h2>
<table id="alumnos">
	<tr>
	    <th>Nombre</th>
	    <th>Apellido</th>
	    <th>Edad</th>
	    <th>Asignatura</th>
  	</tr>
	<%
		for (int i = 0 ; i < (alumnos.size()-j); i++) {
	%>
	<tr>
		<td><%=request.getAttribute("resNombre" + (i))%></td>
		<td><%=request.getAttribute("resApe" + (i))%></td>
		<td><%=request.getAttribute("resEdad" + (i))%></td>
		<td><%=request.getAttribute("resAsig" + (i))%></td>
	</tr>
	<%
		}
	%>
	</table>
	<br>
	<form action="/ProyectoFinalAD/IndexServlet" method="post">
	<p><input class="boton" type="submit" value="Volver"></p>
	</form>
	<p class="b">Proyecto de Acceso a datos.</p>
</body>
</html>