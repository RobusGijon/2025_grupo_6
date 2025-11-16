package test_gui_panelAdministrador;

import static org.junit.Assert.*;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Administrador;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;
import util.FalsoOptionPanel;
import util.TestUtils;
import vista.Ventana;

public class Test_Gui_PanelAdmin_GestionarPedido {

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

	private Chofer crearChoferEmpresa(Chofer chofer) {
		HashMap<String, Chofer> mapaChoferes = new HashMap<>();
		mapaChoferes.put(chofer.getDni(), chofer);
		empresa.setChoferes(mapaChoferes);
		empresa.getChoferesDesocupados().add(chofer);
		return chofer;
	}

	private Viaje crearViajeEmpresa(Pedido pedido, Vehiculo vehiculo, Chofer chofer) {
		Viaje viaje = new Viaje(pedido, chofer, vehiculo);

		HashMap<Cliente, Viaje> mapaViajesIniciados = new HashMap<>();
		mapaViajesIniciados.put(pedido.getCliente(), viaje);
		empresa.setViajesIniciados(mapaViajesIniciados);
		return viaje;

	}

	private Pedido crearPedidoEmpresa(Pedido pedido) {

		HashMap<Cliente, Pedido> mapaPedidos = new HashMap<>();
		mapaPedidos.put(pedido.getCliente(), pedido);
		empresa.setPedidos(mapaPedidos);
		return pedido;
	}

	private Vehiculo crearVehiculoEmpresa(Vehiculo vehiculo) {
		HashMap<String, Vehiculo> mapaVehiculos = new HashMap<>();
		mapaVehiculos.put(vehiculo.getPatente(), vehiculo);
		empresa.setVehiculos(mapaVehiculos);
		empresa.getVehiculosDesocupados().add(vehiculo);
		return vehiculo;

	}


	
}
