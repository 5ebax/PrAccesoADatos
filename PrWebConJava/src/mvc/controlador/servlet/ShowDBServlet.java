package mvc.controlador.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controlador.Procesos;
import mvc.modelo.beans.Alumno;

/**
 * Servlet implementation class ShowDBServlet
 */
public class ShowDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Procesos pr = new Procesos();
	private boolean vacio = true;
	public List<Alumno> alumnos;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowDBServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String boton = request.getParameter("variable");
		pr.crearBackupXML();
		
		alumnos = pr.showDB(boton);
		
		if(alumnos != null) {
		for (int i = 0; i < alumnos.size(); i++) {
			request.setAttribute("resNombre"+i, alumnos.get(i).getNombre());
			request.setAttribute("resApe"+i, alumnos.get(i).getApellido());
			request.setAttribute("resEdad"+i, alumnos.get(i).getEdad());
			request.setAttribute("resAsig"+i, alumnos.get(i).getAsignatura());
		}
		request.setAttribute("alumnos", alumnos);
		request.setAttribute("boton", boton);
		request.getRequestDispatcher("respuesta.jsp").forward(request, response);
		}else {
		request.setAttribute("nousuarios", vacio);
		request.getRequestDispatcher("menuMuestraDB.jsp").forward(request, response);
		}
	}

}
