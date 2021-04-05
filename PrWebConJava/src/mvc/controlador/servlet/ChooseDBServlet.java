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
 * Servlet implementation class ChooseDB
 */
public class ChooseDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Procesos pr = new Procesos();
	private List<Alumno> alumnos;
	private Alumno alumno;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChooseDBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i;
		pr.crearBackupXML();
		String boton = request.getParameter("variable");
		
		alumnos = pr.leerXML();
		for (i = 0; i < alumnos.size(); i++) {
			request.setAttribute("resNombre" + i, alumnos.get(i).getNombre());
			request.setAttribute("resApe" + i, alumnos.get(i).getApellido());
			request.setAttribute("resEdad" + i, alumnos.get(i).getEdad());
			request.setAttribute("resAsig" + i, alumnos.get(i).getAsignatura());
		}
		i-=2;
		alumno = new Alumno(alumnos.get(i).getNombre(), alumnos.get(i).getApellido(), alumnos.get(i).getEdad(), alumnos.get(i).getAsignatura());
		pr.crearAlumno(alumno);
		pr.chooseDB(boton);
		request.setAttribute("alumnos", alumnos);
		request.getRequestDispatcher("addMuestra.jsp").forward(request, response);
	}

}
