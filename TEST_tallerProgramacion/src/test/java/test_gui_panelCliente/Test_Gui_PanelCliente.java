package test_gui_panelCliente;

import static org.junit.Assert.*;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Cliente;
import modeloDatos.Usuario;
import modeloNegocio.Empresa;
import util.Constantes;
import util.FalsoOptionPanel;
import util.Mensajes;
import util.TestUtils;
import vista.Ventana;

public class Test_Gui_PanelCliente {

	private Ventana ventana;
	private Robot robot;
	private Cliente clienteEmpresa;
	private FalsoOptionPanel miOptionPanel;
	private Controlador controlador;
	private Empresa empresa;
	
	public Cliente getClienteEmpresa() {
		Cliente cliente = new Cliente("nu", "pass", "nombreReal");
		
		HashMap<String, Cliente> mapaCliente = new HashMap<>();
		mapaCliente.put(cliente.getNombreUsuario(), cliente);
		empresa.setClientes(mapaCliente);
		return cliente;
	}
	
	public void irAlPanelDeCliente() {
		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		JButton botonLogin = (JButton)  TestUtils.getComponentForName(ventana, Constantes.LOGIN);
		
		TestUtils.clickComponent(campoNombreUsuario, robot);
		TestUtils.tipeaTexto(clienteEmpresa.getNombreUsuario(), robot);
		TestUtils.clickComponent(campoPassword, robot);
		TestUtils.tipeaTexto(clienteEmpresa.getPass(), robot);
		
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
		clienteEmpresa = getClienteEmpresa();
		
		this.irAlPanelDeCliente();
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
	public void test_elTituloDelPanelDebeTenerElNombreDeUsuarioDelCliente() {
		JPanel panelCliente = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_CLIENTE);
		
		TitledBorder border = (TitledBorder) panelCliente.getBorder();
		
		assertEquals(border.getTitle(), clienteEmpresa.getNombreReal());
	}

	@Test
	public void test_cerrarSesion_debeVolverAlPanelLogin() {
		
		JPanel panelCliente = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_CLIENTE);
		
		JButton botonCerrarSesion = (JButton) TestUtils.getComponentForName(ventana, Constantes.CERRAR_SESION_CLIENTE);
		
		TestUtils.clickComponent(botonCerrarSesion, robot);
		
		JPanel panelLogin = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_LOGIN);
		
		assertTrue("Panel login no se esta mostrando", panelLogin.isShowing());
		assertTrue("Panel cliente se esta mostrando", !panelCliente.isShowing());
	}
	
	@Test
	public void test_clienteSinViaje_botonesCantPasajerosYCantKM_debenAfectarAlBotonDeCrearPedido() {
		
		JTextField campoCantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
		JTextField campoCantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
		
		JButton botonAceptarPedido = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
	
		// Sin nada
		assertTrue(!botonAceptarPedido.isEnabled());
		
		// Con solo el campo de cantidad de pasajeros
		TestUtils.clickComponent(campoCantPasajeros, robot);
		TestUtils.tipeaTexto("5", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(!botonAceptarPedido.isEnabled());
		TestUtils.borraJTextField(campoCantPasajeros, robot);
		
		// Solo el campo de cantidad de pasajeros, pero sobrepasando el limite
		TestUtils.tipeaTexto("11", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(!botonAceptarPedido.isEnabled());
		TestUtils.borraJTextField(campoCantPasajeros, robot);
		
		// Solo el campo de cantidad de pasajeros, pero con un numero negativo
		TestUtils.tipeaTexto("-1", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(!botonAceptarPedido.isEnabled());
		TestUtils.borraJTextField(campoCantPasajeros, robot);
		
		// Con solo el campo de cantidad de km
		TestUtils.clickComponent(campoCantKm, robot);
		TestUtils.tipeaTexto("10", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.borraJTextField(campoCantKm, robot);
		
		// Con solo el campo de cantidad de km
		TestUtils.tipeaTexto("-1", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(!botonAceptarPedido.isEnabled());
		TestUtils.borraJTextField(campoCantKm, robot);
		
		// Con ambos negativos
		TestUtils.clickComponent(campoCantPasajeros, robot);
		TestUtils.tipeaTexto("-1", robot);
		TestUtils.clickComponent(campoCantKm, robot);
		TestUtils.tipeaTexto("-1", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(!botonAceptarPedido.isEnabled());
		TestUtils.borraJTextField(campoCantPasajeros, robot);
		TestUtils.borraJTextField(campoCantKm, robot);
		
		// Con ambos campos
		TestUtils.clickComponent(campoCantPasajeros, robot);
		TestUtils.tipeaTexto("5", robot);
		TestUtils.clickComponent(campoCantKm, robot);
		TestUtils.tipeaTexto("5", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(botonAceptarPedido.isEnabled());
	}
	
	@Test
	public void test_clienteSinViaje_radioButtonYCheckBox_noDebenAfectarAlBotonDeCrearPedido() {
		/*
		 * 	No existe las siguientes constantes (detalladas en la documentacion): 
		 * 				Constantes.RADIO_STANDARD
		 * 				Constantes.RADIO_SIN_ASFALTAR
		 * 				Constantes.RADIO_ZONA_PELIGROSA
		 * 	
		 * */
		
		JTextField campoCantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
		JTextField campoCantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
		
		JButton botonAceptarPedido = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
		
		JRadioButton botonZonaStandar = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_STANDARD);
		JRadioButton botonZonaSinAsfaltar = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_SIN_ASFALTAR);
		JRadioButton botonZonaPeligrosa = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_PELIGROSA);
		
		
		JCheckBox checkBoxBaul = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_BAUL);
		JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_MASCOTA);
		
		TestUtils.clickComponent(campoCantPasajeros, robot);
		TestUtils.tipeaTexto("5", robot);
		TestUtils.clickComponent(campoCantKm, robot);
		TestUtils.tipeaTexto("10", robot);
		
		for(JRadioButton boton : List.of(botonZonaStandar, botonZonaSinAsfaltar, botonZonaPeligrosa) ) {
			TestUtils.clickComponent(boton, robot);
			robot.delay(TestUtils.getDelay());
			assertTrue(botonAceptarPedido.isEnabled());
		}
		
		TestUtils.clickComponent(checkBoxBaul, robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(botonAceptarPedido.isEnabled());
		TestUtils.clickComponent(checkBoxBaul, robot);
		
		TestUtils.clickComponent(checkMascota, robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(botonAceptarPedido.isEnabled());
		
		TestUtils.clickComponent(checkBoxBaul, robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(botonAceptarPedido.isEnabled());
	}
	
	public void crearPedido() {
		JTextField campoCantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
		JTextField campoCantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
		JButton botonAceptarPedido = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
		
		TestUtils.clickComponent(campoCantPasajeros, robot);
		TestUtils.tipeaTexto("5", robot);
		TestUtils.clickComponent(campoCantKm, robot);
		TestUtils.tipeaTexto("5", robot);
		
		TestUtils.clickComponent(botonAceptarPedido, robot);
		
	}
	
	
	@Test
	public void test_crearPedido_sinVehiculo_debeLanzarLaVentanaEmergente() {
		
		miOptionPanel.setMensaje(null);
		
		this.crearPedido();
		
		assertEquals(miOptionPanel.getMensaje(), Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor());
	}
	
	
	
	
	
	
	
}
