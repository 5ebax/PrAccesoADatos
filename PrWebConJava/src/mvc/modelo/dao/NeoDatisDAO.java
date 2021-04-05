package mvc.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.neodatis.odb.*;

import mvc.modelo.beans.Alumno;

public class NeoDatisDAO implements DAOFactory {
	private String baseDatos = null;
	private String rutaConx = null; // Ex:C:\\Users\\
	private ODB odb = null;

	/**
	 * Contructores de NeoDatisDAO.
	 * 
	 * @param baseDatos
	 */
	public NeoDatisDAO() {
		baseDatos = "alumnos.db";
	}

	public NeoDatisDAO(String baseDatos) {
		this.baseDatos = baseDatos;
	}

	public NeoDatisDAO(String baseDatos, String rutaConx) {
		this.baseDatos = baseDatos;
		this.rutaConx = rutaConx;
	}

	/**
	 * Devuelve el objeto Connection asociado. Si no existe previamente, lo crea.
	 * 
	 */
	public ODB getConexion() {
		if (odb == null) {
			if (rutaConx == null) {
				odb = ODBFactory.open(baseDatos);
			} else {
				odb = ODBFactory.open(rutaConx + baseDatos);
			}
		}
		return odb;
	}

	/**
	 * Cierra la conexion actual.
	 * 
	 */
	public void close() {
		if (odb != null) {
			odb.close();
			odb = null;
		}
	}

	/**
	 * Inserta los alumnos en la DB.
	 */
	@Override
	public void insertarAlumno(Alumno alumno) {
		odb.store(alumno);
	}

	@Override
	public int insertarAlumno(List<Alumno> listaAlu) {
		int i;
		for (i = 0; i < listaAlu.size(); i++) {
			odb.store(listaAlu.get(i));
		}
		return i;
	}

	@Override
	public void borrarAlumnos(List<Alumno> listaAlu) {
		for (Alumno alumno : listaAlu) {
			odb.delete(alumno);
		}
	}

	@Override
	public List<Alumno> dameAlumnos() {
		Objects<Alumno> objects = odb.getObjects(Alumno.class);
		List<Alumno> alumnos = new ArrayList<Alumno>();

		// Mientras haya objetos, los capturo y muestro
		while (objects.hasNext()) {
			Alumno alumno = objects.next();
			alumnos.add(alumno);
		}
		return alumnos;
	}

	@Override
	public void noexistecreaDB() {
		// Se crea y mantiene con la propia conexion.
	}
}
