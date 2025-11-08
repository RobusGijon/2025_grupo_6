package test_gui_panelCliente;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;
import util.FalsoOptionPanel;
import util.Mensajes;
import util.TestUtils;
import vista.Ventana;

public class Test_Gui_PanelCliente_ClienteConPedidoPendiente {
	private Ventana ventana;
	private Robot robot;
	private Cliente clienteEmpresa;
	private FalsoOptionPanel miOptionPanel;
	private Controlador controlador;
	private Empresa empresa;
	private Pedido pedidoEmpresa;
	
	public Cliente getClienteEmpresa() {
		Cliente cliente = new Cliente("us", "pass", "nombreReal");
		
		HashMap<String, Cliente> mapaCliente = new HashMap<>();
		mapaCliente.put(cliente.getNombreUsuario(), cliente);
		empresa.setClientes(mapaCliente);
		return cliente;
	}

	public Pedido getPedidoEmpresa(Cliente cliente, int pasajeros, int km, boolean mascota, boolean baul) {
		Pedido pedido = new Pedido(cliente, pasajeros, mascota, baul, km, Constantes.ZONA_STANDARD);
		
		HashMap<Cliente, Pedido> mapaPedidos = new HashMap<>();
		mapaPedidos.put(cliente, pedido);
		empresa.setPedidos(mapaPedidos);
		return pedido;
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
		empresa.setUsuarioLogeado(clienteEmpresa);

		pedidoEmpresa = getPedidoEmpresa(clienteEmpresa, 2, 5, true, false);

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
	public void test_clienteConPedidoPendiente_componentesParaCrearPedido_debenEstarDeshabilitados() {
		
		JTextField campoCantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
		JTextField campoCantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
		JButton botonAceptarPedido = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
		JRadioButton botonZonaStandar = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_STANDARD);
		JRadioButton botonZonaSinAsfaltar = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_SIN_ASFALTAR);
		JRadioButton botonZonaPeligrosa = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_PELIGROSA);
		JCheckBox checkBoxBaul = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_BAUL);
		JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_MASCOTA);
		
	
		assertFalse(campoCantPasajeros.isEnabled());
		assertFalse(campoCantKm.isEnabled());
		assertFalse(botonAceptarPedido.isEnabled());
		assertFalse(botonZonaStandar.isEnabled());
		assertFalse(botonZonaSinAsfaltar.isEnabled());
		assertFalse(botonZonaPeligrosa.isEnabled());
		assertFalse(checkBoxBaul.isEnabled());
		assertFalse(checkMascota.isEnabled());
	}
	
	@Test
	public void test_clienteConPedidoPendiente_componentesPendienteActual_debenEstarHabilitadosYMostrarPedido() {
		
		JTextArea textAreaPedido = (JTextArea) TestUtils.getComponentForName(ventana, Constantes.PEDIDO_O_VIAJE_ACTUAL);
		JTextField campoValorViaje = (JTextField) TestUtils.getComponentForName(ventana, Constantes.VALOR_VIAJE);
		
		assertTrue(textAreaPedido.isEnabled());
		assertTrue(campoValorViaje.isEnabled());

		// Verificar que el texto del panel coincida con el pedido
		String textoPedido = textAreaPedido.getText();
		assertEquals("El texto del panel no coincide con el pedido", textoPedido, pedidoEmpresa.toString());
	
	}

	
}
