package mvc.modelo.beans;

public class Alumno {
	private String nombre;
	private String apellido;
	private String edad;
	private String asignatura;
	
	//Constructor vacio para la clase UsuarioXML
	public Alumno() {
		
	}
	
	//Constructor para la clase UsuarioXML
	public Alumno(String nombre, String apellido, String edad, String asignatura) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.asignatura = asignatura;
	}
	
	//Metodos getters y setters
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellidos) {
		this.apellido = apellidos;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String fechaNacimiento) {
		this.edad = fechaNacimiento;
	}
	public String getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}
	
}
