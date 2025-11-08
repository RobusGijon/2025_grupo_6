package test_gui_panelAdministrador;

import static org.junit.Assert.*;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
import util.FalsoOptionPanel;
import util.TestUtils;
import vista.Ventana;

public class Test_Gui_PanelAdministrador {

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

		admin = Administrador.getInstance();

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
	public void test_panelAdministrador_camposChofer_habilitacionSegunTipoChofer() {

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

		// Cuando TEMPORARIO esta seleccionado, CH_CANT_HIJOS y CH_ANIO deben estar
		// deshabilitados
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

		// Hacer click en el boton aceptar
		TestUtils.clickComponent(botonAceptar, robot);
		robot.delay(TestUtils.getDelay());

		// Verificar que el chofer se agrego a la empresa
		HashMap<String, Chofer> choferes = empresa.getChoferes();
		assertTrue(choferes.containsKey(dniChofer));

		Chofer choferAgregado = choferes.get(dniChofer);
		assertNotNull(choferAgregado);
		assertTrue(choferAgregado instanceof ChoferPermanente);

		ChoferPermanente choferPermanente = (ChoferPermanente) choferAgregado;
		assertEquals(dniChofer, choferPermanente.getDni());
		assertEquals(nombreChofer, choferPermanente.getNombre());
		assertEquals(anioIngresoChofer, choferPermanente.getAnioIngreso());
		assertEquals(cantHijosChofer, choferPermanente.getCantidadHijos());
	}

	@Test
	public void test_panelAdministrador_crearChoferTemporario() {
		/*
		 * Escenario: Crear un chofer temporario a traves de la GUI
		 * y verificar que se agrego correctamente a la empresa
		 */

		String dniChofer = "87654321";
		String nombreChofer = "Maria Garcia";

		JTextField campoDNI = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
		JTextField campoNombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
		JRadioButton radioTemporario = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.TEMPORARIO);
		JButton botonAceptar = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);

		// Seleccionar TEMPORARIO
		TestUtils.clickComponent(radioTemporario, robot);
		robot.delay(TestUtils.getDelay());

		// Llenar los campos
		TestUtils.clickComponent(campoDNI, robot);
		TestUtils.tipeaTexto(dniChofer, robot);

		TestUtils.clickComponent(campoNombre, robot);
		TestUtils.tipeaTexto(nombreChofer, robot);

		robot.delay(TestUtils.getDelay());

		// Hacer click en el boton aceptar
		TestUtils.clickComponent(botonAceptar, robot);
		robot.delay(TestUtils.getDelay());

		// Verificar que el chofer se agrego a la empresa
		HashMap<String, Chofer> choferes = empresa.getChoferes();
		assertTrue(choferes.containsKey(dniChofer));

		Chofer choferAgregado = choferes.get(dniChofer);
		assertNotNull(choferAgregado);
		assertTrue(choferAgregado instanceof ChoferTemporario);

		ChoferTemporario choferTemporario = (ChoferTemporario) choferAgregado;
		assertEquals(dniChofer, choferTemporario.getDni());
		assertEquals(nombreChofer, choferTemporario.getNombre());
	}

}
