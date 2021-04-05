package mvc.modelo.dao;

import java.util.List;
import mvc.modelo.beans.Alumno;

public interface DAOFactory {
	public void noexistecreaDB();
    public void insertarAlumno(Alumno alumno);
    public int insertarAlumno(List<Alumno> listaAlu);
    public void borrarAlumnos(List<Alumno> listaAlu);
    public List<Alumno> dameAlumnos();
}
