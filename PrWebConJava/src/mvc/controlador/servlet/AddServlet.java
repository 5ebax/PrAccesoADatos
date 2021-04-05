package mvc.controlador.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.modelo.beans.Alumno;
import mvc.controlador.Procesos;

/**
 * Servlet implementation class RespuestaServlet
 */
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Alumno alumno;
	private Procesos pr = new Procesos();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Lanza la página agregar.jsp.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("variableInicio", "Datos del alumno");
		request.getRequestDispatcher("agregar.jsp").forward(request, response);
	}

	/**
	 * En el doPost recibe los datos del formulario y agrega un usuario nuevo para
	 * después mostrar todos junto a él. Guarda los datos en la base de datos.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nombre = (request.getParameter("nombre") != null) ? request.getParameter("nombre") : "";
		String apellidos = (request.getParameter("apellido") != null) ? request.getParameter("apellido") : "";
		String edad = (request.getParameter("edad") != null) ? request.getParameter("edad") : "";
		String asignatura = (request.getParameter("asignatura") != null) ? request.getParameter("asignatura") : "";
		alumno = new Alumno(nombre, apellidos, edad, asignatura);

		pr.crearAlumno(alumno);
		pr.crearBackupXML();
		request.getRequestDispatcher("menuDB.jsp").forward(request, response);

	}
}
