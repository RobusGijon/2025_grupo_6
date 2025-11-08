package test_gui_panelRegistro;

import static org.junit.Assert.*;

import java.awt.Robot;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Administrador;
import modeloDatos.Cliente;
import modeloNegocio.Empresa;
import util.Constantes;
import util.FalsoOptionPanel;
import util.Mensajes;
import util.TestUtils;
import vista.Ventana;

public class Test_Gui_Registro_panelModal {
	private Ventana ventana;
	private Controlador controlador;
	private Robot robot;
	private Empresa empresa;
	private static JButton botonRegistro;
	private FalsoOptionPanel miOptionPanel;
	
	@BeforeClass
	public static void getComponenteRegistro() throws Exception {
		botonRegistro = (JButton) TestUtils.getComponentForName(new Ventana(), Constantes.REGISTRAR);
	}
	
	
	
	@Before
	public void setUp() throws Exception {
		miOptionPanel = new FalsoOptionPanel();
		
		controlador = new Controlador();

		ventana = (Ventana) controlador.getVista(); 
		ventana.setOptionPane(miOptionPanel);
		
		empresa = Empresa.getInstance();
		robot = new Robot();
		TestUtils.clickComponent(botonRegistro, robot);
	}

	@After
	public void eliminarDatosEmpresa() throws Exception {
		empresa.setClientes(new HashMap<>());
	}
	
	private Cliente getClienteAlmacenado() {
		Cliente clienteGuardado = new Cliente("nombreUsuario", "pass", "nombreReal");
		Empresa empresa = Empresa.getInstance();
		
		HashMap<String, Cliente> mapaClientes = new HashMap<>();
		mapaClientes.put(clienteGuardado.getNombreUsuario(), clienteGuardado);
		
		empresa.setClientes(mapaClientes);
		return clienteGuardado;
	}
	
//	@Test
	public void test_usuarioRepetido_deberiaLanzarMensaje() {
		JPanel panelRegistro = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_REGISTRO);
		
		JTextField regNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
		JTextField regPass = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
		JTextField regPassRep = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
		JTextField regNombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);	
		
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
		
		Cliente clienteGuardado = this.getClienteAlmacenado();
		
		TestUtils.clickComponent(regNombreUsuario, robot);
		TestUtils.tipeaTexto(clienteGuardado.getNombreUsuario(), robot);
		TestUtils.clickComponent(regPass, robot);
		TestUtils.tipeaTexto(clienteGuardado.getPass(), robot);
		TestUtils.clickComponent(regPassRep, robot);
		TestUtils.tipeaTexto(clienteGuardado.getPass(), robot);
		TestUtils.clickComponent(regNombreReal, robot);
		TestUtils.tipeaTexto(clienteGuardado.getNombreReal(), robot);
		
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonRegistrar, robot);
		
		assertEquals(miOptionPanel.getMensaje(), Mensajes.USUARIO_REPETIDO.getValor());
		assertTrue(panelRegistro.isEnabled());
	}
	
//	@Test
	public void test_contraseniasIncorrectas_deberiaLanzarMensaje() {
		JPanel panelRegistro = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_REGISTRO);
		
		JTextField regNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
		JTextField regPass = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
		JTextField regPassRep = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
		JTextField regNombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);	
		
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
		
		Cliente clienteNuevo = new Cliente("nombreUsuario", "pass", "nombreReal");
		
		TestUtils.clickComponent(regNombreUsuario, robot);
		TestUtils.tipeaTexto(clienteNuevo.getNombreUsuario(), robot);
		TestUtils.clickComponent(regPass, robot);
		TestUtils.tipeaTexto(clienteNuevo.getPass(), robot);
		TestUtils.clickComponent(regPassRep, robot);
		TestUtils.tipeaTexto(clienteNuevo.getPass() + "125932", robot);
		TestUtils.clickComponent(regNombreReal, robot);
		TestUtils.tipeaTexto(clienteNuevo.getNombreReal(), robot);
		
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonRegistrar, robot);
		
		assertEquals(miOptionPanel.getMensaje(), Mensajes.USUARIO_REPETIDO.getValor());
		assertTrue(panelRegistro.isEnabled()); // Verificamos que seguimos en el panel de registro
	}
	
	@Test
	public void test_registrarAdministrador_deberiaLanzarMensaje() {
		JPanel panelRegistro = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_REGISTRO);
		
		assertTrue(panelRegistro.isEnabled());
		
		JTextField regNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
		JTextField regPass = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
		JTextField regPassRep = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
		JTextField regNombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);	
		
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
		
		Administrador admin = Administrador.getInstance();
		
		Cliente clienteNuevo = new Cliente(admin.getNombreUsuario(), admin.getPass(), "nombreReal");
		
		TestUtils.clickComponent(regNombreUsuario, robot);
		TestUtils.tipeaTexto(clienteNuevo.getNombreUsuario(), robot);
		TestUtils.clickComponent(regPass, robot);
		TestUtils.tipeaTexto(clienteNuevo.getPass(), robot);
		TestUtils.clickComponent(regPassRep, robot);
		TestUtils.tipeaTexto(clienteNuevo.getPass(), robot);
		TestUtils.clickComponent(regNombreReal, robot);
		TestUtils.tipeaTexto(clienteNuevo.getNombreReal(), robot);
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonRegistrar, robot);
		
		assertEquals(miOptionPanel.getMensaje(), Mensajes.USUARIO_REPETIDO.getValor());
		assertTrue(panelRegistro.isEnabled());
	}
	
//	@Test
	public void test_usuarioNuevo_deberiaRegistrarseCorrectamente() {
		JPanel panelRegistro = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_REGISTRO);
		JPanel panelLogin = (JPanel)  TestUtils.getComponentForName(ventana, Constantes.PANEL_LOGIN);
		
		JTextField regNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
		JTextField regPass = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
		JTextField regPassRep = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
		JTextField regNombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);	
		
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
		
		Cliente clienteNuevo = new Cliente("nombreUsuario", "password", "nombreReal");
		
		TestUtils.clickComponent(regNombreUsuario, robot);
		TestUtils.tipeaTexto(clienteNuevo.getNombreUsuario(), robot);
		TestUtils.clickComponent(regPass, robot);
		TestUtils.tipeaTexto(clienteNuevo.getPass(), robot);
		TestUtils.clickComponent(regPassRep, robot);
		TestUtils.tipeaTexto(clienteNuevo.getPass(), robot);
		TestUtils.clickComponent(regNombreReal, robot);
		TestUtils.tipeaTexto(clienteNuevo.getNombreReal(), robot);
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonRegistrar, robot);
		
		HashMap<String, Cliente> mapaClientesEmpresa = empresa.getClientes();
		
		Cliente clienteGuardado = mapaClientesEmpresa.get(clienteNuevo.getNombreUsuario());
		
		assertEquals(clienteNuevo.getNombreUsuario(), clienteGuardado.getNombreUsuario());
		assertEquals(clienteNuevo.getNombreReal(), clienteGuardado.getNombreReal());
		assertEquals(clienteNuevo.getPass(), clienteGuardado.getPass());
		assertTrue(!panelRegistro.isEnabled());
		assertTrue(panelLogin.isEnabled());
	}


}
