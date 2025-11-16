package test_gui_panelAdministrador;

import static org.junit.Assert.*;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Administrador;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;
import util.FalsoOptionPanel;
import util.IgualdadUtil;
import util.TestUtils;
import vista.Ventana;

public class Test_Gui_PanelAdmin_GestionarPedido {

	private Ventana ventana;
	private Robot robot;
	private FalsoOptionPanel miOptionPanel;
	private Controlador controlador;
	private Empresa empresa;
	private Administrador admin;

	private Chofer choferEmpresa;
	private Vehiculo vehiculoEmpresa;
	private Vehiculo vehiculoNoCumplePedido;
	private Pedido pedidoEmpresa;
	private Viaje viajeEmpresa;
	private Cliente cliente;


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

		this.agregarEscenarioGenerico();

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

	private void agregarChoferEmpresa(Chofer chofer) {
		HashMap<String, Chofer> mapaChoferes = new HashMap<>();
		mapaChoferes.put(chofer.getDni(), chofer);
		empresa.setChoferes(mapaChoferes);
		empresa.getChoferesDesocupados().add(chofer);
	}

	private Viaje crearViajeEmpresa(Pedido pedido, Vehiculo vehiculo, Chofer chofer) {
		Viaje viaje = new Viaje(pedido, chofer, vehiculo);
		HashMap<Cliente, Viaje> mapaViajesIniciados = new HashMap<>();
		mapaViajesIniciados.put(pedido.getCliente(), viaje);
		empresa.setViajesIniciados(mapaViajesIniciados);
		return viaje;
	}

	private void agregarPedidoEmpresa(Pedido pedido) {
		HashMap<Cliente, Pedido> mapaPedidos = new HashMap<>();
		mapaPedidos.put(pedido.getCliente(), pedido);
		empresa.setPedidos(mapaPedidos);
	}

	private void agregarVehiculoEmpresa(Vehiculo vehiculo) {
		HashMap<String, Vehiculo> mapaVehiculos = new HashMap<>();
		mapaVehiculos.put(vehiculo.getPatente(), vehiculo);
		empresa.setVehiculos(mapaVehiculos);
		empresa.getVehiculosDesocupados().add(vehiculo);
	}

	public void agregarEscenarioGenerico(){ 
		cliente = new Cliente("cli1", "pass", "Cliente1");
		pedidoEmpresa = new Pedido(cliente, 2, false, false, 10, util.Constantes.ZONA_STANDARD);
		vehiculoEmpresa = new Auto("PAT123", 4, true);
		choferEmpresa = new ChoferTemporario("123456", "ChoferT");
		vehiculoNoCumplePedido = new Moto("MM123MM");
		agregarVehiculoEmpresa(vehiculoEmpresa);
		agregarVehiculoEmpresa(vehiculoNoCumplePedido);
		agregarPedidoEmpresa(pedidoEmpresa);
		agregarChoferEmpresa(choferEmpresa);
	}
	

	@Test
	public void test_listaPedidosPendientes_muestraPedidosCreados() {
		@SuppressWarnings("unchecked")
		JList<Pedido> listaPedidos = (JList<Pedido>) TestUtils.getComponentForName(ventana, Constantes.LISTA_PEDIDOS_PENDIENTES);
	
		Pedido pedidoEnLista = listaPedidos.getModel().getElementAt(0);
		
		assertTrue("El pedido creado debe figurar en LISTA_PEDIDOS_PENDIENTES", IgualdadUtil.sonIguales(pedidoEmpresa, pedidoEnLista));
	}

	@Test
	public void test_listaChoferesLibres_muestraChoferesDesocupados() {
		@SuppressWarnings("unchecked")
		JList<Chofer> listaChoferes = (JList<Chofer>) TestUtils.getComponentForName(ventana, Constantes.LISTA_CHOFERES_LIBRES);

		Chofer choferLista = listaChoferes.getModel().getElementAt(0);

		assertTrue("El chofer agregado debe figurar en LISTA_CHOFERES_LIBRES", IgualdadUtil.sonIguales(choferEmpresa, choferLista));
	}

	@Test
	public void test_listaVehiculosDisponibles_pobladaSoloDespuesDeSeleccionarPedido() {
		robot.delay(TestUtils.getDelay());
		@SuppressWarnings("unchecked")
		JList<Vehiculo> listaVehiculos = (JList<Vehiculo>) TestUtils.getComponentForName(ventana, Constantes.LISTA_VEHICULOS_DISPONIBLES);
		@SuppressWarnings("unchecked")
		JList<Pedido> listaPedidos = (JList<Pedido>) TestUtils.getComponentForName(ventana, Constantes.LISTA_PEDIDOS_PENDIENTES);
		
		System.out.println(listaPedidos);
		System.out.println(listaPedidos.getModel());
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickElementoLista(listaPedidos, 0, robot);
		
		int size = listaVehiculos.getModel().getSize();
		assertTrue("La lista de vehiculos disponibles debe contener al menos 1 vehiculo luego de seleccionar pedido", size > 0);
		
		Vehiculo vehiculoLista = listaVehiculos.getModel().getElementAt(0);;

		assertTrue("El vehiculo compatible debe aparecer en LISTA_VEHICULOS_DISPONIBLES", IgualdadUtil.sonIguales(vehiculoEmpresa, vehiculoLista));
	}

	@Test
	public void test_nuevoViaje_habilitaYcreaViaje_actualizaListas() {
		@SuppressWarnings("unchecked")
		JList<Pedido> listaPedidos = (JList<Pedido>) TestUtils.getComponentForName(ventana, Constantes.LISTA_PEDIDOS_PENDIENTES);
		@SuppressWarnings("unchecked")
		JList<Chofer> listaChoferes = (JList<Chofer>) TestUtils.getComponentForName(ventana, Constantes.LISTA_CHOFERES_LIBRES);
		@SuppressWarnings("unchecked")
		JList<Vehiculo> listaVehiculos = (JList<Vehiculo>) TestUtils.getComponentForName(ventana, Constantes.LISTA_VEHICULOS_DISPONIBLES);
		JButton botonCrearViaje = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VIAJE);

		// Al inicio no deberia clickearse
		assertFalse(botonCrearViaje.isEnabled());
		
		// Solo seleccionando el pedido, no deberia poder
		TestUtils.clickElementoLista(listaPedidos, 0, robot);
		robot.delay(TestUtils.getDelay());
		assertFalse(botonCrearViaje.isEnabled());

		// Solo seleccionando el chofer, no deberia poder
		TestUtils.clickElementoLista(listaChoferes, 0, robot);
		robot.delay(TestUtils.getDelay());
		assertFalse(botonCrearViaje.isEnabled());

		
		TestUtils.clickElementoLista(listaVehiculos, 0, robot);
		robot.delay(TestUtils.getDelay());
		assertTrue(botonCrearViaje.isEnabled());

		// Click en NUEVO_VIAJE
		TestUtils.clickComponent(botonCrearViaje, robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonCrearViaje, robot);

		robot.delay(TestUtils.getDelay());
		assertFalse(botonCrearViaje.isEnabled());
		assertEquals(0, listaPedidos.getModel().getSize());
		assertEquals(0, listaChoferes.getModel().getSize());
		assertEquals(0, listaVehiculos.getModel().getSize());
	}



}
