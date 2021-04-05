package mvc.controlador.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controlador.Procesos;
import mvc.modelo.beans.Alumno;

/**
 * Servlet implementation class RespuestaServlet
 */
public class RespuestaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Procesos pr = new Procesos();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RespuestaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Usa el método doPost.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Recibe un array del XML que tenemos leído y muestra la página con los datos.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		pr.crearBackupXML();
		request.getRequestDispatcher("menuMuestraDB.jsp").forward(request, response);

	}
}
