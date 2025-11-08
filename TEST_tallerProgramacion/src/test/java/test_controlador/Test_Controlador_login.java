package test_controlador;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JTextField;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controlador.Controlador;
import util.Constantes;
import util.TestUtils;
import vista.DefaultOptionPane;
import vista.IOptionPane;
import vista.Ventana;

public class Test_Controlador_login {

	
	private Controlador controlador;
	private Ventana ventana;
	private Robot robot;
	private IOptionPane optionPanel;

	
	@Before
	public void setUp() throws Exception {
		File archivoEmpresa = new File("empresa.bin");
		if(archivoEmpresa.exists()) {
			archivoEmpresa.delete();
		}
		
		optionPanel = new DefaultOptionPane();
		robot = new Robot();
		controlador = new Controlador();
		ventana = (Ventana) controlador.getVista();
		ventana.setOptionPane(optionPanel);
		
	}
	
	@AfterClass
	public static void eliminarArchivos_despuesPruebas() throws Exception {
		File archivoEmpresa = new File("empresa.bin");
		if(archivoEmpresa.exists()) {
			archivoEmpresa.delete();
		}
	}
	
	

	@Test
	public void test_login_usuarioNoEncontrado() {
		JTextField compNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField compPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		Component compLogin = TestUtils.getComponentForName(ventana, Constantes.LOGIN);	
		

		TestUtils.clickComponent(compNombreUsuario, robot);
		TestUtils.tipeaTexto("nombreUsuario", robot);
		TestUtils.clickComponent(compPassword, robot);
		TestUtils.tipeaTexto("pass", robot);
		
		robot.delay(1000);
		
		controlador.login();
		
		TestUtils.borraJTextField(compNombreUsuario, robot);
		TestUtils.borraJTextField(compPassword, robot);
	}
	
}





