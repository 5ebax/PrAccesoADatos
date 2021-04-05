package mvc.controlador;

import java.sql.SQLException;
import java.util.List;
import mvc.modelo.beans.Alumno;
import mvc.modelo.dao.MySQLDAO;
import mvc.modelo.dao.NeoDatisDAO;
import mvc.modelo.dao.XMLDAO;

public class Procesos {
	private XMLDAO xml;
	XMLDAO backxml;
	private MySQLDAO my;
	private NeoDatisDAO neo;
	private Alumno alumno;

	public Procesos() {
		backxml = new XMLDAO("datosAlumnosBackup.xml");
		my = new MySQLDAO("alumnos", "root", "root");
		neo = new NeoDatisDAO();
		xml = new XMLDAO();
	}

	/**
	 * Switch para insertar en las bases de datos.
	 * 
	 * @param opc
	 */
	public void chooseDB(String opc) {
		switch (opc) {
		case "En XML":
			insertarXML();
			break;
		case "NeoDatis":
			insertarNeo();
			break;
		case "MySQL":
			insertarSQL();
			break;
		default:
			break;
		}
	}

	/**
	 * Switch para mostrar las bases de datos.
	 * 
	 * @param opc
	 * @throws SQLException
	 */
	public List<Alumno> showDB(String opc) {
		List<Alumno> lista = null;

		try {
			switch (opc) {
			case "En XML":
				lista = mostrarXML();
				break;
			case "NeoDatis":
				lista = mostrarNeo();
				break;
			case "MySQL":
				lista = mostrarSQL();
				break;
			case "Backup XML":
				lista = mostrarBackupXML();
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			System.err.println("Error: "+e.getMessage());
		}
		return lista;
	}

	/**
	 * Métodos del switch.
	 */
	private void insertarSQL() {
		try {
			crearTabla();
			insertarAlumnoSQL(alumno);
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void insertarNeo() {
		insertarAlumnoNeo(alumno);
	}

	public void insertarXML() {
		xml.noexistecreaDB();
		insertarAlumnoXML(alumno);
	}

	/**
	 * Devuelve la lista de alumnos. Sirve para leer el XML.
	 * 
	 * @return
	 */
	public List<Alumno> leerXML() {
		return backxml.dameAlumnos();
	}

	/**
	 * Crea la Tabla de alumnos si no existe.
	 * 
	 * @throws SQLException
	 */
	private void crearTabla() throws SQLException {
		try {
			my.getConexion();
			my.noexistecreaDB();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			my.close();
		}
	}

	/**
	 * Crea nada más empezar el Backup de los alumnos en el XML.
	 */
	public void crearBackupXML() {
		backxml.noexistecreaDB();
		if (alumno != null) {
			backxml.insertarAlumno(alumno);
		}
	}

	/**
	 * Recibe el alumno y guarda el alumno en la base de datos o XML.
	 * 
	 * @throws SQLException
	 */
	private void insertarAlumnoSQL(Alumno alumno) throws SQLException {
		try {
			my.getConexion();
			my.insertarAlumno(alumno);
			System.out.println("Usuario agregado a la Base de Datos de MySQL.\n");
		} catch (ClassNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			my.close();
		}
	}

	private void insertarAlumnoNeo(Alumno alumno) {
		try {
			neo.getConexion();
			neo.insertarAlumno(alumno);
			System.out.println("Usuario agregado a la Base de Datos de NeoDatis.\n");
		} finally {
			neo.close();
		}
	}

	private void insertarAlumnoXML(Alumno alumno) {
		xml.insertarAlumno(alumno);
		System.out.println("Usuario agregado al Fichero XML correctamente!\n");
	}

	/**
	 * Mostrar alumnos de las bases de datos
	 * 
	 * @throws SQLException
	 */
	private List<Alumno> mostrarSQL() throws SQLException {
		List<Alumno> lista = null;
		try {
			my.getConexion();
			lista = my.dameAlumnos();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			my.close();
		}
		if (lista.isEmpty()) {
			lista = null;
		}
		return lista;
	}

	private List<Alumno> mostrarNeo() {
		List<Alumno> lista = null;
		try {
			neo.getConexion();
			lista = neo.dameAlumnos();
		} finally {
			neo.close();
		}
		if (lista.isEmpty()) {
			lista = null;
		}
		return lista;
	}

	private List<Alumno> mostrarXML() {
		List<Alumno> lista = null;
		lista = xml.dameAlumnos();
		if (lista.isEmpty() || lista.get(0).getNombre() == null) {
			lista = null;
		}
		return lista;
	}

	private List<Alumno> mostrarBackupXML() {
		List<Alumno> lista = null;
		lista = backxml.dameAlumnos();
		if (lista.isEmpty() || lista.get(0).getNombre() == null) {
			lista = null;
		}
		return lista;
	}

	/**
	 * Recibe el alumno y lo usa para todos los procesos.
	 * 
	 * @param alumno
	 */
	public void crearAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
}
