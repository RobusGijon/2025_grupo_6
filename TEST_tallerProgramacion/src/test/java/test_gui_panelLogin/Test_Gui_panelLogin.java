package test_gui_panelLogin;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Administrador;
import modeloDatos.Cliente;
import modeloDatos.Usuario;
import modeloNegocio.Empresa;
import util.Constantes;
import util.FalsoOptionPanel;
import util.Mensajes;
import util.TestUtils;
import vista.Ventana;

public class Test_Gui_panelLogin {

	private Ventana ventana;
	private Robot robot;
	private FalsoOptionPanel miOptionPanel;
	private Controlador controlador;
	private Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		
		controlador = new Controlador();
		
		ventana = (Ventana) controlador.getVista();
		
		miOptionPanel = new FalsoOptionPanel();
		ventana.setOptionPane(miOptionPanel);
		
		empresa = Empresa.getInstance();
		robot = new Robot();
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
    }

	private void crearBateriaDePruebas(ArrayList<String> nombreUsuarios, ArrayList<String> contrasenias) {
	    nombreUsuarios.clear();
	    contrasenias.clear();

	    // Caso general
	    nombreUsuarios.add("nombreUsuario");
	    contrasenias.add("pass");
	    
	    // 1 Caracter
	    nombreUsuarios.add("a");
	    contrasenias.add("a");
	    
	    // 1 nombre usuario pero sin password
	    nombreUsuarios.add("nombreUsuario");
	    contrasenias.add("");
	    
	    // 1 password pero sin nombre de usuario
	    nombreUsuarios.add("");
	    contrasenias.add("pass");
	    
	    // Caracteres espciales
	    nombreUsuarios.add("n0mbr3Usuar1o");
	    contrasenias.add("m1ContraS3n1a");
	    
	    // Campos vacios
	    nombreUsuarios.add("");
	    contrasenias.add("");
	}
	
	private Cliente getClienteAlmacenado() {
		Cliente clienteGuardado = new Cliente("nombreUsuario", "pass", "nombreReal");
		Empresa empresa = Empresa.getInstance();
		
		HashMap<String, Cliente> mapaClientes = new HashMap<>();
		mapaClientes.put(clienteGuardado.getNombreUsuario(), clienteGuardado);
		
		empresa.setClientes(mapaClientes);
		return clienteGuardado;
	}
	
	@Test
	public void test_textFieldLogin_getters() {
		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		
		ArrayList<String> nombreUsuarios = new ArrayList<>(); 
		ArrayList<String> contrasenias = new ArrayList<>();
		
		this.crearBateriaDePruebas(nombreUsuarios, contrasenias);
		
		for(int i = 0; i < nombreUsuarios.size(); i++) {
			TestUtils.clickComponent(campoNombreUsuario, robot);
			TestUtils.tipeaTexto(nombreUsuarios.get(i), robot);
			TestUtils.clickComponent(campoPassword, robot);
			TestUtils.tipeaTexto(contrasenias.get(i), robot);
			
			robot.delay(1000);
			assertEquals(ventana.getUsserName(), nombreUsuarios.get(i));
			assertEquals(ventana.getPassword(), contrasenias.get(i));
			
			TestUtils.borraJTextField(campoNombreUsuario, robot);
			TestUtils.borraJTextField(campoPassword, robot);
		}
	}
	
	@Test
	public void test_losBotonesDelLoginDeberianEstarEnBlancoAlInicio() {
		/*
		 * 	Segun la documentacion, todos los textField de todos los paneles
		 * 	deben estar inicializados; no deben tener texto
		 * 
		 * */
		
		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		
		robot.delay(TestUtils.getDelay());
		
		assertEquals(ventana.getUsserName(), "");
		assertEquals(ventana.getPassword(), "");
		
	}
	
	@Test
	public void test_botonesLogin_deberianPoderApretarseONo() {
		
		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		JButton botonLogin = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);
		JButton botonRegistro = (JButton) TestUtils.getComponentForName(ventana, Constantes.REGISTRAR);
		
		
		ArrayList<String> nombreUsuarios = new ArrayList<>(); 
		ArrayList<String> contrasenias = new ArrayList<>();
		
		this.crearBateriaDePruebas(nombreUsuarios, contrasenias);
		
		for(int i = 0; i < nombreUsuarios.size(); i++) {
			TestUtils.clickComponent(campoNombreUsuario, robot);
			TestUtils.tipeaTexto(nombreUsuarios.get(i), robot);
			TestUtils.clickComponent(campoPassword, robot);
			TestUtils.tipeaTexto(contrasenias.get(i), robot);
			
			robot.delay(1000);
			
			if(nombreUsuarios.get(i) == "" || contrasenias.get(i) == "")
				assertFalse(botonLogin.isEnabled());
			else
				assertTrue("El boton login fallo", botonLogin.isEnabled());
			
			assertTrue(botonRegistro.isEnabled());
			
			TestUtils.borraJTextField(campoNombreUsuario, robot);
			TestUtils.borraJTextField(campoPassword, robot);
		}
	}
	
	@Test
	public void test_loginClienteExistente_deberiaPasarAlPanelDelCliente() {

		JPanel panelCliente = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_CLIENTE);

		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		JButton botonLogin = (JButton)  TestUtils.getComponentForName(ventana, Constantes.LOGIN);
		
		Usuario clienteGuardado = this.getClienteAlmacenado();
		
		TestUtils.clickComponent(campoNombreUsuario, robot);
		TestUtils.tipeaTexto(clienteGuardado.getNombreUsuario(), robot);
		TestUtils.clickComponent(campoPassword, robot);
		TestUtils.tipeaTexto(clienteGuardado.getPass(), robot);
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonLogin, robot);		
		
		robot.delay(TestUtils.getDelay());
		
		panelCliente = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_CLIENTE);
		robot.delay(TestUtils.getDelay());
		
		assertTrue(panelCliente.isEnabled());
	}
	
	@Test
	public void test_loginAdmin_deberiaPasarAlPanelDelAdministrador() {
		JPanel panelAdmin = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_CLIENTE);

		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		JButton botonLogin = (JButton)  TestUtils.getComponentForName(ventana, Constantes.LOGIN);
		
		Usuario clienteGuardado = Administrador.getInstance();
		
		TestUtils.clickComponent(campoNombreUsuario, robot);
		TestUtils.tipeaTexto(clienteGuardado.getNombreUsuario(), robot);
		TestUtils.clickComponent(campoPassword, robot);
		TestUtils.tipeaTexto(clienteGuardado.getPass(), robot);
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonLogin, robot);		
		
		robot.delay(TestUtils.getDelay());
		
		panelAdmin = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_ADMINISTRADOR);
		robot.delay(TestUtils.getDelay());
		
		assertTrue(panelAdmin.isEnabled());
	}
	
	@Test
	public void test_loginUsuarioDesconocido_deberiaLanzarMensajeCorrecto() {
		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		JButton botonLogin = (JButton)  TestUtils.getComponentForName(ventana, Constantes.LOGIN);
		
		Usuario clienteNoRegistrado = new Cliente("otroNombreUsuario", "otroPass", "otroNombreReal");
		
		TestUtils.clickComponent(campoNombreUsuario, robot);
		TestUtils.tipeaTexto(clienteNoRegistrado.getNombreUsuario(), robot);
		TestUtils.clickComponent(campoPassword, robot);
		TestUtils.tipeaTexto(clienteNoRegistrado.getPass(), robot);
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonLogin, robot);		
		robot.delay(TestUtils.getDelay());
		
		
		assertEquals(miOptionPanel.getMensaje(), Mensajes.USUARIO_DESCONOCIDO.getValor());
	}
	
	@Test
	public void test_loginUsuarioConPassIncorrecta_deberiaLanzarMensajeCorrecto() {
		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		JButton botonLogin = (JButton)  TestUtils.getComponentForName(ventana, Constantes.LOGIN);
		
		Usuario clienteGuardado = this.getClienteAlmacenado();
		
		TestUtils.clickComponent(campoNombreUsuario, robot);
		TestUtils.tipeaTexto(clienteGuardado.getNombreUsuario(), robot);
		TestUtils.clickComponent(campoPassword, robot);
		TestUtils.tipeaTexto(clienteGuardado.getPass() + "39131059", robot);
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonLogin, robot);		
		robot.delay(TestUtils.getDelay());
		
		assertEquals(miOptionPanel.getMensaje(), Mensajes.PASS_ERRONEO.getValor());
	}
	
	@Test
	public void test_loginAdminConPassIncorrecta_deberiaLanzarMensajeCorrecto() {
		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		JButton botonLogin = (JButton)  TestUtils.getComponentForName(ventana, Constantes.LOGIN);
		
		Usuario clienteGuardado = Administrador.getInstance();
		
		TestUtils.clickComponent(campoNombreUsuario, robot);
		TestUtils.tipeaTexto(clienteGuardado.getNombreUsuario(), robot);
		TestUtils.clickComponent(campoPassword, robot);
		TestUtils.tipeaTexto(clienteGuardado.getPass() + "39131059", robot);
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonLogin, robot);		
		robot.delay(TestUtils.getDelay());
		
		assertEquals(miOptionPanel.getMensaje(), Mensajes.PASS_ERRONEO.getValor());
	}
	

}
