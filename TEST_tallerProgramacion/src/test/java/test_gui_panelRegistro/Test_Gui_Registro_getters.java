package test_gui_panelRegistro;

import static org.junit.Assert.*;

import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import util.Constantes;
import util.TestUtils;
import vista.Ventana;

public class Test_Gui_Registro_getters {

	Robot robot;
	Ventana ventana;
	private static JButton botonRegistro;
	
	@BeforeClass
	public static void getComponenteRegistro() throws Exception {
		botonRegistro = (JButton) TestUtils.getComponentForName(new Ventana(), Constantes.REGISTRAR);
	}
	
	@Before
	public void setUp() throws Exception {
		ventana = new Ventana();
		robot = new Robot();
		TestUtils.clickComponent(botonRegistro, robot);
	}
	
	
		
	@Test
	public void test_getRegUsserName() {
		JTextField regNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
		TestUtils.clickComponent(regNombreUsuario, robot);
		String nombreUsuario = "nombreUsuario";
		TestUtils.tipeaTexto(nombreUsuario, robot);
		robot.delay(TestUtils.getDelay());	
		assertEquals(nombreUsuario, ventana.getRegUsserName());
	}
	
	@Test
	public void test_getRegPassword() {
		JTextField regPass = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
		TestUtils.clickComponent(regPass, robot);
		String pass = "password";
		TestUtils.tipeaTexto(pass, robot);
		robot.delay(TestUtils.getDelay());	
		assertEquals(pass, ventana.getRegPassword());
	}
	
	@Test
	public void test_getRegConfirmPassword() {
		JTextField regPassRep = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
		TestUtils.clickComponent(regPassRep, robot);
		String pass = "password";
		TestUtils.tipeaTexto(pass, robot);
		robot.delay(TestUtils.getDelay());	
		assertEquals(pass, ventana.getRegConfirmPassword());
	}
	
	@Test
	public void test_getRegNombreReal() {
		JTextField regNombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);
		TestUtils.clickComponent(regNombreReal, robot);
		String nombreReal = "nombreReal";
		TestUtils.tipeaTexto(nombreReal, robot);
		robot.delay(TestUtils.getDelay());	
		assertEquals(nombreReal, ventana.getRegNombreReal());
	}

}