package test_gui_panelAdministrador;

import static org.junit.Assert.*;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Administrador;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloNegocio.Empresa;
import util.Constantes;
import util.CreacionUtil;
import util.FalsoOptionPanel;
import util.IgualdadUtil;
import util.Mensajes;
import util.TestUtils;
import vista.Ventana;

public class Test_Gui_PanelAdmin_CrearChofer {

	private Ventana ventana;
	private Robot robot;
	private FalsoOptionPanel miOptionPanel;
	private Controlador controlador;
	private Empresa empresa;
	private Administrador admin;

	public void irAlPanelDelAdministrador() {
		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		JButton botonLogin = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

		TestUtils.clickComponent(campoNombreUsuario, robot);
		TestUtils.tipeaTexto(admin.getNombreUsuario(), robot);
		TestUtils.clickComponent(campoPassword, robot);
		TestUtils.tipeaTexto(admin.getPass(), robot);

		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonLogin, robot);

	}

	@Before
	public void setUp() throws Exception {
		controlador = new Controlador();
		ventana = (Ventana) controlador.getVista();

		miOptionPanel = new FalsoOptionPanel();
		ventana.setOptionPane(miOptionPanel);

		admin = Administrador.getInstance();
		robot = new Robot();
		empresa = Empresa.getInstance();
		empresa.setUsuarioLogeado(admin);

		this.irAlPanelDelAdministrador();
	}


	@After
	public void tearDown() {
		empresa.setClientes(new HashMap<>());
		empresa.setPedidos(new HashMap<>());
		empresa.setVehiculos(new HashMap<>());
		empresa.setVehiculosDesocupados(new ArrayList<>());
		empresa.setChoferes(new HashMap<>());
		empresa.setChoferesDesocupados(new ArrayList<>());
		empresa.setViajesIniciados(new HashMap<>());
		empresa.setViajesTerminados(new ArrayList<>());
		empresa.setUsuarioLogeado(null);

		if (ventana != null) {
			ventana.dispose();
		}
	}

	@Test
	public void test_panelAdministrador_camposYbotonesChofer_enabledSegunTipoChofer() {

		JTextField campoDNI = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
		JTextField campoNombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
		JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_CANT_HIJOS);
		JTextField campoAnio = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_ANIO);
		JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.PERMANENTE);
		JRadioButton radioTemporario = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.TEMPORARIO);

		// Cuando PERMANENTE esta seleccionado, los campos deben estar habilitados
		TestUtils.clickComponent(radioPermanente, robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(campoDNI.isEnabled());
		assertTrue(campoNombre.isEnabled());
		assertTrue(campoCantHijos.isEnabled());
		assertTrue(campoAnio.isEnabled());

		// Seleccionar TEMPORARIO
		TestUtils.clickComponent(radioTemporario, robot);
		robot.delay(TestUtils.getDelay());

		assertFalse(radioPermanente.isSelected());
		assertTrue(radioTemporario.isSelected());

		// Cuando TEMPORARIO esta seleccionado, el campo cantidad hijos y anio de
		// ingreso deben estar deshabilitados
		assertTrue(campoDNI.isEnabled());
		assertTrue(campoNombre.isEnabled());
		assertFalse(campoCantHijos.isEnabled());
		assertFalse(campoAnio.isEnabled());

		// Volver a PERMANENTE
		TestUtils.clickComponent(radioPermanente, robot);
		robot.delay(TestUtils.getDelay());

		assertTrue(radioPermanente.isSelected());
		assertFalse(radioTemporario.isSelected());

		// Verificar que los campos vuelven a estar habilitados
		assertTrue(campoCantHijos.isEnabled());
		assertTrue(campoAnio.isEnabled());
	}

	@Test
	public void test_panelAdministrador_crearChoferPermanente() {

		String dniChofer = "12345678";
		String nombreChofer = "Juan Perez";
		int anioIngresoChofer = 2010;
		int cantHijosChofer = 2;

		JTextField campoDNI = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
		JTextField campoNombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
		JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_CANT_HIJOS);
		JTextField campoAnio = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_ANIO);
		JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.PERMANENTE);
		JButton botonAceptar = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);

		TestUtils.clickComponent(radioPermanente, robot);
		robot.delay(TestUtils.getDelay());

		TestUtils.clickComponent(campoDNI, robot);
		TestUtils.tipeaTexto(dniChofer, robot);

		TestUtils.clickComponent(campoNombre, robot);
		TestUtils.tipeaTexto(nombreChofer, robot);

		TestUtils.clickComponent(campoCantHijos, robot);
		TestUtils.tipeaTexto(String.valueOf(cantHijosChofer), robot);

		TestUtils.clickComponent(campoAnio, robot);
		TestUtils.tipeaTexto(String.valueOf(anioIngresoChofer), robot);

		robot.delay(TestUtils.getDelay());

		TestUtils.clickComponent(botonAceptar, robot);
		robot.delay(TestUtils.getDelay());

		// Verificar que el chofer se agrego a la empresa
		HashMap<String, Chofer> choferes = empresa.getChoferes();
		assertTrue(choferes.containsKey(dniChofer));

		Chofer choferAgregado = choferes.get(dniChofer);
		ChoferPermanente choferEsperado = new ChoferPermanente(dniChofer, nombreChofer, anioIngresoChofer,
				cantHijosChofer);
		assertTrue(IgualdadUtil.sonIguales(choferEsperado, choferAgregado));
	}

	@Test
	public void test_panelAdministrador_crearChoferTemporario() {

		String dniChofer = "87654321";
		String nombreChofer = "Maria Garcia";

		JTextField campoDNI = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
		JTextField campoNombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
		JRadioButton radioTemporario = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.TEMPORARIO);
		JButton botonAceptar = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);

		TestUtils.clickComponent(radioTemporario, robot);
		robot.delay(TestUtils.getDelay());

		TestUtils.clickComponent(campoDNI, robot);
		TestUtils.tipeaTexto(dniChofer, robot);

		TestUtils.clickComponent(campoNombre, robot);
		TestUtils.tipeaTexto(nombreChofer, robot);

		robot.delay(TestUtils.getDelay());

		TestUtils.clickComponent(botonAceptar, robot);
		robot.delay(TestUtils.getDelay());

		// Verificar que se agrego a la empresa
		HashMap<String, Chofer> choferes = empresa.getChoferes();
		assertTrue(choferes.containsKey(dniChofer));

		Chofer choferAgregado = choferes.get(dniChofer);
		ChoferTemporario choferEsperado = new ChoferTemporario(dniChofer, nombreChofer);
		assertTrue(IgualdadUtil.sonIguales(choferEsperado, choferAgregado));
	}

	@Test
	public void test_panelAdministrador_botonNuevoChofer_habilitacionChoferTemporario() {

		JTextField campoDNI = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
		JTextField campoNombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
		JRadioButton radioTemporario = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.TEMPORARIO);
		JButton botonAceptar = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);

		TestUtils.clickComponent(radioTemporario, robot);
		robot.delay(TestUtils.getDelay());

		// Con los campos vacios el boton debe estar deshabilitado
		assertFalse(botonAceptar.isEnabled());

		// Chofer con solo DNI, el boton debe estar deshabilitado
		TestUtils.clickComponent(campoDNI, robot);
		TestUtils.tipeaTexto("1", robot);
		robot.delay(TestUtils.getDelay());
		assertFalse(botonAceptar.isEnabled());

		// Borrar DNI
		robot.delay(TestUtils.getDelay());
		TestUtils.borraJTextField(campoDNI, robot);

		// Solo con NOMBRE, el boton debe estar deshabilitado
		TestUtils.clickComponent(campoNombre, robot);
		TestUtils.tipeaTexto("A", robot);
		robot.delay(TestUtils.getDelay());
		assertFalse(botonAceptar.isEnabled());

		// Con ambos campos con al menos un caracter, el boton debe estar habilitado
		TestUtils.clickComponent(campoDNI, robot);
		TestUtils.tipeaTexto("1", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(botonAceptar.isEnabled());
	}

	@Test
	public void test_panelAdministrador_botonNuevoChofer_habilitacionChoferPermanente() {

		JTextField campoDNI = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
		JTextField campoNombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
		JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_CANT_HIJOS);
		JTextField campoAnio = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_ANIO);
		JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.PERMANENTE);
		JButton botonAceptar = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);

		TestUtils.clickComponent(radioPermanente, robot);
		robot.delay(TestUtils.getDelay());

		// solamente DNI
		TestUtils.clickComponent(campoDNI, robot);
		TestUtils.tipeaTexto("1", robot);
		robot.delay(TestUtils.getDelay());
		assertFalse(botonAceptar.isEnabled());
		TestUtils.borraJTextField(campoDNI, robot);

		// solamente nombre
		TestUtils.clickComponent(campoNombre, robot);
		TestUtils.tipeaTexto("A", robot);
		robot.delay(TestUtils.getDelay());
		assertFalse(botonAceptar.isEnabled());
		TestUtils.borraJTextField(campoNombre, robot);

		// solamente hijos
		TestUtils.clickComponent(campoCantHijos, robot);
		TestUtils.tipeaTexto("2", robot);
		robot.delay(TestUtils.getDelay());
		assertFalse(botonAceptar.isEnabled());
		TestUtils.borraJTextField(campoCantHijos, robot);

		// solamente año ingreso
		TestUtils.clickComponent(campoAnio, robot);
		TestUtils.tipeaTexto("2010", robot);
		robot.delay(TestUtils.getDelay());
		assertFalse(botonAceptar.isEnabled());
		TestUtils.borraJTextField(campoAnio, robot);

		// DNI, nombre, hijos y año ingreso correctos, pero con hijos negativos
		TestUtils.clickComponent(campoDNI, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(campoNombre, robot);
		TestUtils.tipeaTexto("A", robot);
		TestUtils.clickComponent(campoCantHijos, robot);
		TestUtils.tipeaTexto("-1", robot);
		TestUtils.clickComponent(campoAnio, robot);
		TestUtils.tipeaTexto("2010", robot);
		assertFalse(botonAceptar.isEnabled());

		TestUtils.borraJTextField(campoCantHijos, robot);
		TestUtils.tipeaTexto("2", robot);
		robot.delay(TestUtils.getDelay());

		// fecha fuera de rango
		TestUtils.borraJTextField(campoAnio, robot);
		TestUtils.tipeaTexto("1000", robot);
		robot.delay(TestUtils.getDelay());
		assertFalse(botonAceptar.isEnabled());

		TestUtils.borraJTextField(campoAnio, robot);
		TestUtils.tipeaTexto("2010", robot);
		robot.delay(TestUtils.getDelay());

		// todo correcto
		assertTrue(botonAceptar.isEnabled());
	}

	@Test
	public void test_panelAdministrador_crearChoferExitoso_seAgregaAListaYSeVacianCampos() {
		/*
		 * 
		 * ERROR: Los campos del registro de chofer no se vacian
		 * 
		 */

		JTextField campoDNI = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
		JTextField campoNombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
		JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_CANT_HIJOS);
		JTextField campoAnio = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_ANIO);
		JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.PERMANENTE);
		JButton botonAceptar = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);

		JList<Chofer> listaChoferes = (JList<Chofer>) TestUtils.getComponentForName(ventana,
				Constantes.LISTA_CHOFERES_TOTALES);

		ChoferPermanente choferPermanente = new ChoferPermanente("33333333", "Permanente", 2020, 1);
		ChoferTemporario choferTemporario = new ChoferTemporario("22222222", "Temporario");

		// Crear chofer permanente
		TestUtils.clickComponent(radioPermanente, robot);

		CreacionUtil.creaUnChoferPermanenteYTemporario(ventana, robot, choferTemporario, choferPermanente);

		// Estan en la lista
		ListModel<Chofer> listaChoferesPanelAdmin = (ListModel<Chofer>) listaChoferes.getModel();
		assertEquals("La lista de choferes del panel no contiene dos elementos", 2, listaChoferesPanelAdmin.getSize());
		for (int i = 0; i < listaChoferesPanelAdmin.getSize(); i++) {
			Chofer chofer = listaChoferesPanelAdmin.getElementAt(i);
			if (chofer.getNombre().equals("Temporario"))
				assertTrue(IgualdadUtil.sonIguales(chofer, choferTemporario));
			else
				assertTrue(IgualdadUtil.sonIguales(chofer, choferPermanente));

		}

		// Considerando al anexo como parte de la documentancion, se debe cumplir que:
		// Verificar que los campos se vaciaron
		assertTrue("El campo DNI no fue vaciado", campoDNI.getText().isEmpty());
		assertTrue("El campo Nombre no fue vaciado", campoNombre.getText().isEmpty());
		assertTrue("El campo Cantidad de Hijos no fue vaciado", campoCantHijos.getText().isEmpty());
		assertTrue("El campo Año de Ingreso no fue vaciado", campoAnio.getText().isEmpty());

	}

	 @Test	
	public void test_panelAdministrador_crearChoferConDNIRepetido_debeMostrarMensajeError() {
		/*
		 * 
		 * 
		 * Incorrecto: Se esperaba el mensaje pero se obtuvo null (se crearon los
		 * choferes con mismo DNI)
		 * 
		 */

		String dniChoferRepetido = "22222222";

		ChoferTemporario choferTemporario = new ChoferTemporario(dniChoferRepetido, "choferT");
		ChoferPermanente choferPermanente = new ChoferPermanente(dniChoferRepetido, "choferP", 2000, 1);
		CreacionUtil.creaUnChoferPermanenteYTemporario(ventana, robot, choferTemporario, choferPermanente);

		// Verificar que se muestra el mensaje de error
		assertEquals("El option panel no ejecuto el mensaje correspondiente",
				Mensajes.CHOFER_YA_REGISTRADO.getValor(), miOptionPanel.getMensaje());

		// Verificar que solo existe el chofer temporario original
		HashMap<String, Chofer> choferes = empresa.getChoferes();
		assertEquals(1, choferes.size());
		assertTrue(choferes.containsKey(dniChoferRepetido));
	}

	@Test
	public void test_panelAdmin_verificarSueldoChofer() {

		JTextField campoSueldoChofer = (JTextField) TestUtils.getComponentForName(ventana,
				Constantes.CALIFICACION_CHOFER);
		JList<Chofer> listaChoferes = (JList<Chofer>) TestUtils.getComponentForName(ventana,
				Constantes.LISTA_CHOFERES_TOTALES);

		ChoferTemporario choferTemporario = new ChoferTemporario("11111", "ChoferT");
		ChoferPermanente choferPermanente = new ChoferPermanente("22222", "ChoferP", 2000, 3);

		CreacionUtil.creaUnChoferPermanenteYTemporario(ventana, robot, choferTemporario, choferPermanente);

		for(int i = 0; i < 2; i++) {	
			TestUtils.clickElementoLista(listaChoferes, i, robot);
			robot.delay(TestUtils.getDelay());

			Chofer choferSeleccionado = listaChoferes.getSelectedValue();
			double sueldoChoferCampo = Double.parseDouble(campoSueldoChofer.getText());
			assertTrue("El sueldo del chofer seleccionado no es el correcto",choferSeleccionado.getSueldoNeto() == sueldoChoferCampo);
		}

	}


	@Test
	public void test_panelAdmin_verificarSueldoTotalChoferes() {

		JTextField campoSueldoTotalChoferes = (JTextField) TestUtils.getComponentForName(ventana,
				Constantes.TOTAL_SUELDOS_A_PAGAR);

		ChoferTemporario choferTemporario = new ChoferTemporario("1", "ChoferT");
		ChoferPermanente choferPermanente = new ChoferPermanente("2", "ChoferP", 2000, 3);

		CreacionUtil.creaUnChoferPermanenteYTemporario(ventana, robot, choferTemporario, choferPermanente);

		double sueldoTotalEsperado = choferTemporario.getSueldoNeto() + choferPermanente.getSueldoNeto();
		double sueldoTotalCampo = Double.parseDouble(campoSueldoTotalChoferes.getText());

		assertTrue("Los sueldos totales no son iguales", sueldoTotalEsperado == sueldoTotalCampo);
	}

	
}
