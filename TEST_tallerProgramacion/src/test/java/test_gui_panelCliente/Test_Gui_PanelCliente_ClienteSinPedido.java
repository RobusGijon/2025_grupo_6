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

public class Test_Gui_PanelCliente_ClienteSinPedido {
	
	private Ventana ventana;
	private Robot robot;
	private Cliente clienteEmpresa;
	private FalsoOptionPanel miOptionPanel;
	private Controlador controlador;
	private Empresa empresa;
	
	public Cliente getClienteEmpresa() {
		Cliente cliente = new Cliente("us", "pass", "nombreReal");
		
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
		empresa.setUsuarioLogeado(clienteEmpresa);
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

	
	public void crearPedido(int cantidadPasajeros, int cantidadKm, boolean mascota, boolean baul) {
		JTextField campoCantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
		JTextField campoCantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
		JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_MASCOTA);
		JCheckBox checkBaul = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_BAUL);
		JButton botonAceptarPedido = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
		
		TestUtils.clickComponent(campoCantPasajeros, robot);
		TestUtils.tipeaTexto(String.valueOf(cantidadPasajeros), robot);
		TestUtils.clickComponent(campoCantKm, robot);
		TestUtils.tipeaTexto(String.valueOf(cantidadKm), robot);
	
		
		if (mascota) 
			TestUtils.clickComponent(checkMascota, robot);
		
		if (baul) 
			TestUtils.clickComponent(checkBaul, robot);
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonAceptarPedido, robot);
		
	}
	
	
	public void agregarAutoEmpresa(String patente, int cantidadPlazas, boolean mascota) {
		HashMap<String, Vehiculo> mapaVehiculo = new HashMap<>();
		Auto auto = new Auto(patente, cantidadPlazas, mascota);
		mapaVehiculo.put(auto.getPatente(), auto);
		empresa.setVehiculos(mapaVehiculo);
		
		ArrayList<Vehiculo> vehiculosDesocupados = new ArrayList<>();
		vehiculosDesocupados.add(auto);
		empresa.setVehiculosDesocupados(vehiculosDesocupados);
	}

	@Test
	public void test_crearPedido_sinVehiculo_debeLanzarLaVentanaEmergente() {
		
		miOptionPanel.setMensaje(null);
		
		this.crearPedido(5, 5, false, false);
		
		robot.delay(TestUtils.getDelay());
		assertEquals(miOptionPanel.getMensaje(), Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor());
	}
	
	@Test
	public void test_crearPedido_excesoDePasajeros_debeLanzarLaVentanaEmergente() {
		/*
		 * Escenario: La empresa tiene un auto registrado que NO puede soportar el pedido.
		 * 
		 * Auto: ("AA123AA", 2 plazas, NO puede llevar mascota)
		 * Pedido: (cliente, 5 pasajeros, SIN mascota, SIN baul, 5 km, zona estandar)
		 * 
		 * Prueba: Se intenta crear un pedido que requiere mas pasajeros de los que el auto puede llevar.
		 * 
		 * Esperado: Debe lanzar el mensaje SIN_VEHICULO_PARA_PEDIDO
		 * */
		
		this.agregarAutoEmpresa("AA123AA", 2, false);
		
		miOptionPanel.setMensaje(null);
		
		this.crearPedido(5, 5, false, false);
		 
		robot.delay(TestUtils.getDelay());
		assertEquals(miOptionPanel.getMensaje(), Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor());
	}
	
	@Test
	public void test_crearPedido_conAutoSinMascota_pedidoConMascota_debeLanzarLaVentanaEmergente() {
		/*
		 * Escenario: La empresa tiene un auto registrado que NO puede llevar mascota.
		 * 
		 * Auto: ("AA123AA", 4 plazas, NO puede llevar mascota)
		 * Pedido: (cliente, 2 pasajeros, CON mascota, SIN baul, 5 km, zona estandar)
		 * 
		 * Prueba: Se intenta crear un pedido que requiere mascota pero el auto no la acepta.
		 * 
		 * Esperado: Debe lanzar el mensaje SIN_VEHICULO_PARA_PEDIDO
		 * */
		
		// Agregar un auto sin capacidad para mascota
		this.agregarAutoEmpresa("AA123AA", 4, false);
		
		miOptionPanel.setMensaje(null);
		
		// Intentar crear un pedido que requiere mascota
		this.crearPedido(2, 5, true, false);
		
		robot.delay(TestUtils.getDelay());
		assertEquals(miOptionPanel.getMensaje(), Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor());
	}
	
	@Test
	public void test_crearPedido_conAutoQuePuedeSoportar_debeCrearElPedido() {
		/*
		 * Escenario: La empresa tiene un auto registrado que PUEDE soportar el pedido.
		 * 
		 * Auto: ("AA123AA", 4 plazas, puede llevar mascota)
		 * Pedido: (cliente, 2 pasajeros, CON mascota, SIN baul, 5 km, zona estandar)
		 * 
		 * Prueba: Se intenta crear un pedido que el auto puede soportar.
		 * 
		 * Esperado: 
		 * - No debe lanzar ningun mensaje de error
		 * - El pedido debe ser creado y esta en la empresa
		 * - El pedido debe tener las caracteristicas correctas
		 * 
		 * Resultado: INCORRECTO -> El pedido se crea pero carga mal los km almacenados
		 * */
		
		miOptionPanel.setMensaje(null);

		int cantidadPasajeros = 2;
		int cantidadKm = 100;
		boolean mascota = true;
		boolean baul = false;

		this.agregarAutoEmpresa("AA123AA", cantidadPasajeros, mascota);
		
		this.crearPedido(cantidadPasajeros, cantidadKm, mascota, baul);
		
		assertNull("null pedido", miOptionPanel.getMensaje());

		Pedido pedidoCreado = empresa.getPedidoDeCliente(clienteEmpresa);
		assertNotNull(pedidoCreado);
		
		assertEquals(clienteEmpresa, pedidoCreado.getCliente());
		assertEquals(cantidadPasajeros, pedidoCreado.getCantidadPasajeros());
		assertEquals(mascota, pedidoCreado.isMascota());
		assertEquals(baul, pedidoCreado.isBaul());
		assertEquals("Fallo en km, deberia ser " + cantidadKm + " pero es " + pedidoCreado.getKm(), cantidadKm, pedidoCreado.getKm());
		assertEquals(Constantes.ZONA_STANDARD, pedidoCreado.getZona());
	}

	

	
	
}
