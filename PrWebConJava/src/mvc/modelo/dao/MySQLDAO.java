package mvc.modelo.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mvc.modelo.beans.Alumno;

public class MySQLDAO implements DAOFactory {

	private String driver = "com.mysql.jdbc.Driver";
	private String urlBase = "jdbc:mysql://localhost:3306/";
	private String baseDatos = null;
	private String usuario = null;
	private String clave = null;
	private Connection conexion = null;

	/**
	 * Construye MySQLDAO a partir de driver, url base, base de datos, nombre de
	 * usuario y clave
	 */
	public MySQLDAO(String driver, String urlBase, String baseDatos, String usuario, String clave) {
		this.driver = driver;
		this.urlBase = urlBase;
		this.baseDatos = baseDatos;
		this.usuario = usuario;
		this.clave = clave;
	}

	/**
	 * Construye un bean de DAO a partir de url base, base de datos, nombre de
	 * usuario y clave
	 */
	public MySQLDAO(String urlBase, String baseDatos, String usuario, String clave) {
		this.urlBase = urlBase;
		this.baseDatos = baseDatos;
		this.usuario = usuario;
		this.clave = clave;
	}

	/**
	 * Construye un bean de DAO a partir de base de datos, nombre de usuario y clave
	 */
	public MySQLDAO(String baseDatos, String usuario, String clave) {
		this.baseDatos = baseDatos;
		this.usuario = usuario;
		this.clave = clave;
	}

	/**
	 * Devuelve el objeto Connection asociado. Si no existe previamente, lo crea.
	 * 
	 * @return Devuelve la conexion.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @uml.property name="conexion"
	 */
	public Connection getConexion() throws SQLException, ClassNotFoundException {
		if (conexion == null) {
			Class.forName(driver);
			conexion = DriverManager.getConnection(urlBase + baseDatos+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", usuario, clave);
		}
		return conexion;
	}

	/**
	 * Cierra la conexion actual.
	 * 
	 * @throws SQLException Si no se ha podido cerrar la conexiÃ³n
	 */
	public void close() throws SQLException {
		if (conexion != null) {
			conexion.close();
			conexion = null;
		}
	}

	/**
	 * Recibe el alumno y guarda el alumno en la base de datos.
	 * 
	 * @param nomb
	 * @param ape
	 * @param ed
	 * @param asig
	 */

	@Override
	public void insertarAlumno(Alumno alumno) {
		Statement sentencia = null;
		try {
			sentencia = conexion.createStatement();
			// Se coge la inforamción y se inserta en la tabla de la base de datos.
			sentencia.executeUpdate("INSERT INTO alumnos (nombre, apellido, edad, asignatura) " + "VALUES('"
					+ alumno.getNombre() + "', '" + alumno.getApellido() + "', " + Integer.parseInt(alumno.getEdad())
					+ ", '" + alumno.getAsignatura() + "');");

			sentencia.close();
			
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			try {
				if (sentencia != null)
					sentencia.close();
			} catch (SQLException e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	/**
	 * Metodo de consulta de alumnos devuelve un ResulSet con todos los usuarios si
	 * todo va bien y un ResultSet=null en caso de error
	 * 
	 * @param usuario
	 * @return
	 */
	@Override
	public List<Alumno> dameAlumnos() {
		ResultSet rs = null;
		Statement st = null;
		List<Alumno> lista = null;
		;
		Alumno alumno;
		try {
			st = conexion.createStatement();
			rs = st.executeQuery("SELECT * FROM alumnos.alumnos");
			lista = new ArrayList<Alumno>();

			while (rs.next()) {
				alumno = new Alumno();
				alumno.setNombre(rs.getString("nombre"));
				alumno.setApellido(rs.getString("apellido"));
				alumno.setEdad(Integer.toString(rs.getInt("edad")));
				alumno.setAsignatura(rs.getString("asignatura"));
				lista.add(alumno);
			}

		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
		return lista;
	}

	/**
	 * Si no existe crea la tabla de alumnos en la DB.
	 */
	@Override
	public void noexistecreaDB() {
		Statement sentencia = null;
		ResultSet tables = null;
		try {
			sentencia = conexion.createStatement();

			// Se coge la información de la tabla alumnos para comprobar si existe.
			DatabaseMetaData dbm = conexion.getMetaData();
			tables = dbm.getTables(null, null, "alumnos", null);

			// Si el ResulsSet no es capaz de encontrarla la crea.
			if (!tables.next()) {
				String sqlCreateAlum = "CREATE TABLE alumnos ( " + "alum_no int NOT NULL AUTO_INCREMENT, "
						+ "nombre VARCHAR(20), " + "apellido VARCHAR(20), " + "edad INT, "
						+ "asignatura VARCHAR(50), PRIMARY KEY (alum_no) " + ");";
				// la sentencia SQL crea una tabla con 3 campos
				sentencia.execute(sqlCreateAlum);
			}
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			try {
				if (sentencia != null)
					sentencia.close();
				if (tables != null)
					tables.close();
			} catch (SQLException e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	/**
	 * Elimina los alumnos de la base de datos.
	 */
	@Override
	public void borrarAlumnos(List<Alumno> listaAlu) {
		Statement st = null;
		try {

			st = conexion.createStatement();

			// borro todos los registros de la bd
			st.executeUpdate("DELETE FROM alumnos");
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}
	
	@Override
	public int insertarAlumno(List<Alumno> listaAlu) {
		// TODO Auto-generated method stub
		return 0;
	}
}
