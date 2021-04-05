package mvc.modelo.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mvc.modelo.beans.Alumno;

public class XMLDAO implements DAOFactory {
	private String NOMBRE_FICHERO = "datosAlumnos.xml";
	private String rutaFich = null; //Ex: C:/Usuarios/
	private Alumno nuevoAlumno = new Alumno();
	private List<Alumno> alumnosOld;
	private File fichero;

	/**
	 * Constructores del XMLDAO.
	 */
	public XMLDAO() {
		if(fichero == null) {
		fichero = new File(NOMBRE_FICHERO);
		}
	}

	public XMLDAO(String NOMBRE_FICHERO) {
		this.NOMBRE_FICHERO = NOMBRE_FICHERO;
		fichero = new File(NOMBRE_FICHERO);
	}

	public XMLDAO(String NOMBRE_FICHERO, String rutaFich) {
		this.NOMBRE_FICHERO = NOMBRE_FICHERO;
		this.rutaFich = rutaFich;
		fichero = new File(rutaFich+NOMBRE_FICHERO);
	}
	
	/**
	 * Comprueba que exista el fichero XML, si no, lo crea.
	 */
	@Override
	public void noexistecreaDB() {
		try {
			if (!fichero.exists()) {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// Elemento Raiz
				Document documento = docBuilder.newDocument();
				Element elementoRaiz = documento.createElement("DatosPersonales");
				documento.appendChild(elementoRaiz);

				// Elementos primarios del documento
				Element nombreDocumento = documento.createElement("nombreDocumento");
				nombreDocumento.appendChild(documento.createTextNode("Datos Personales Clientes"));
				elementoRaiz.appendChild(nombreDocumento);
				Element usuarios = documento.createElement("alumnos");
				elementoRaiz.appendChild(usuarios);

				// Escribimos el contenido generado en un archivo XML
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource fuenteDOM = new DOMSource(documento);
				StreamResult xml = new StreamResult(fichero);

				transformer.transform(fuenteDOM, xml);

			}
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	/**
	 * Inserta el alumno nuevo al fichero XML.
	 */
	@Override
	public void insertarAlumno(Alumno alumno) {
		alumnosOld = new ArrayList<Alumno>();
		String nombre = alumno.getNombre();
		String apellido = alumno.getApellido();
		String edad = alumno.getEdad();
		String asignatura = alumno.getAsignatura();

		int totalUsuarios = 0;

		int contadorUsuarios = 0;

		nuevoAlumno.setNombre(nombre);
		nuevoAlumno.setApellido(apellido);
		nuevoAlumno.setEdad(edad);
		nuevoAlumno.setAsignatura(asignatura);

		// Una vez obtenidos los datos asociados a un alumno, procedemos a crear el
		// fichero XML con los datos de este
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Elemento Raiz
			Document documento = docBuilder.newDocument();
			Element elementoRaiz = documento.createElement("DatosPersonales");
			documento.appendChild(elementoRaiz);

			// Elementos primarios del documento
			Element nombreDocumento = documento.createElement("nombreDocumento");
			nombreDocumento.appendChild(documento.createTextNode("Datos Personales Clientes"));
			elementoRaiz.appendChild(nombreDocumento);
			Element usuarios = documento.createElement("alumnos");
			elementoRaiz.appendChild(usuarios);
			almacenarAlumnosAntiguoFicheroXML(alumnosOld, NOMBRE_FICHERO);
			totalUsuarios = this.alumnosOld.size();
			for (int i = 1; i <= totalUsuarios; i++) {

				Element usuario = documento.createElement("alumno");
				usuarios.appendChild(usuario);
				nombre = this.alumnosOld.get(contadorUsuarios).getNombre();
				apellido = this.alumnosOld.get(contadorUsuarios).getApellido();
				edad = this.alumnosOld.get(contadorUsuarios).getEdad();
				asignatura = this.alumnosOld.get(contadorUsuarios).getAsignatura();

				// Establecemos el atributo id para cada usuario
				Attr identificador = documento.createAttribute("id");
				identificador.setValue(Integer.toString(i));
				usuario.setAttributeNode(identificador);

				// Cargamos el nombre asociado a cada usuario
				Element elementoNombre = documento.createElement("nombre");
				elementoNombre.appendChild(documento.createTextNode(nombre));
				usuario.appendChild(elementoNombre);

				// Cargamos los apellidos asociado a cada usuario
				Element elementoApellido = documento.createElement("apellido");
				elementoApellido.appendChild(documento.createTextNode(apellido));
				usuario.appendChild(elementoApellido);

				// Cargamos la fecha de nacimiento asociado a cada usuario
				Element elementoEdad = documento.createElement("edad");
				elementoEdad.appendChild(documento.createTextNode(edad));
				usuario.appendChild(elementoEdad);

				// Cargamos la asignatura asociado a cada usuario
				Element elementoAsignatura = documento.createElement("asignatura");
				elementoAsignatura.appendChild(documento.createTextNode(asignatura));
				usuario.appendChild(elementoAsignatura);
				contadorUsuarios++;
			}

			// Escribimos el contenido generado en un archivo XML
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource fuenteDOM = new DOMSource(documento);
			StreamResult fichero = new StreamResult(new File(NOMBRE_FICHERO));

			transformer.transform(fuenteDOM, fichero);


		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	/**
	 * Método que recorre el XML que tenemos de antes para almacenar en el array sus
	 * datos y usarlos.
	 * 
	 */
	private void almacenarAlumnosAntiguoFicheroXML(List<Alumno> alumnos, String NOMBRE_FICHERO) {
		try {
			File contenidoFicheroXML = new File(NOMBRE_FICHERO);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(contenidoFicheroXML);
			String nombre = "";
			String apellido = "";
			String edad = "";
			String asignatura = "";
			doc.getDocumentElement().normalize();

			NodeList nodoUsuario = doc.getElementsByTagName("alumno");

			for (int temp = 0; temp < nodoUsuario.getLength(); temp++) {

				Node nNode = nodoUsuario.item(temp);

				Element eElement = (Element) nNode;

				nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
				apellido = eElement.getElementsByTagName("apellido").item(0).getTextContent();
				edad = eElement.getElementsByTagName("edad").item(0).getTextContent();
				asignatura = eElement.getElementsByTagName("asignatura").item(0).getTextContent();
				Alumno alumno = new Alumno(nombre, apellido, edad, asignatura);
				alumnos.add(alumno);
			}
			alumnos.add(nuevoAlumno);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Llama al método almacenarUsuariosAntiguoFicheroXML. Sirve para leer el XML.
	 * 
	 * @return
	 */
	@Override
	public List<Alumno> dameAlumnos() {
		List<Alumno> alumnos = new ArrayList<Alumno>();
		almacenarAlumnosAntiguoFicheroXML(alumnos, NOMBRE_FICHERO);
		return alumnos;
	}

	@Override
	public int insertarAlumno(List<Alumno> listaAlu) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Elimina el fichero XML con los alumnos.
	 */
	@Override
	public void borrarAlumnos(List<Alumno> listaAlu) {
		if(fichero.exists()) {
			fichero.delete();
		}
	}

}
