package test_vista;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import util.Constantes;
import util.TestUtils;
import vista.Ventana;

public class Test_Vista_panelLogin {

	private Ventana ventana;
	private Robot robot;
	
	@Before
	public void setUp() throws Exception {
		ventana = new Ventana();
		robot = new Robot();
	}

	private void crearEscenarioLogin(ArrayList<String> nombreUsuarios, ArrayList<String> contrasenias) {
	    nombreUsuarios.clear();
	    contrasenias.clear();

	    // Caso general
	    nombreUsuarios.add("nombreUsuario");
	    contrasenias.add("pass");
	    
	    // 1 Caracter
	    nombreUsuarios.add("a");
	    contrasenias.add("a");
	    
	    // 1 Caracter en nombre usuario y en password no
	    nombreUsuarios.add("c");
	    contrasenias.add("");
	    
	    // 1 Caracter en password y no en nombre de usuario
	    nombreUsuarios.add("");
	    contrasenias.add("B");
	    
	    // Caracteres espciales
	    nombreUsuarios.add("n0mbr3Usuar1o");
	    contrasenias.add("m1ContraS3n1a");
	    
	    // Campos vacios
	    nombreUsuarios.add("");
	    contrasenias.add("");
	}
	
	@Test
	public void test_panelLogin_Constante() {
		
		JTextField compNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField compPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		Component compLogin = TestUtils.getComponentForName(ventana, Constantes.LOGIN);
		
		ArrayList<String> nombreUsuarios = new ArrayList<>(); 
		ArrayList<String> contrasenias = new ArrayList<>();
		
		
		
		this.crearEscenarioLogin(nombreUsuarios, contrasenias);
		
		
		for(int i = 0; i < nombreUsuarios.size(); i++) {
			TestUtils.clickComponent(compNombreUsuario, robot);
			TestUtils.tipeaTexto(nombreUsuarios.get(i), robot);
			TestUtils.clickComponent(compPassword, robot);
			TestUtils.tipeaTexto(contrasenias.get(i), robot);
			
			robot.delay(1000);
			
			if(nombreUsuarios.get(i) == "" || contrasenias.get(i) == "")
				assertFalse(compLogin.isEnabled());
			else
				assertTrue("El boton login fallo", compLogin.isEnabled());
			
			TestUtils.borraJTextField(compNombreUsuario, robot);
			TestUtils.borraJTextField(compPassword, robot);
		}
	}
	
	@Test
	public void test_get_panelLogin() {
		JTextField compNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField compPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		
		ArrayList<String> nombreUsuarios = new ArrayList<>(); 
		ArrayList<String> contrasenias = new ArrayList<>();
		
		
		this.crearEscenarioLogin(nombreUsuarios, contrasenias);
		
		
		for(int i = 0; i < nombreUsuarios.size(); i++) {
			TestUtils.clickComponent(compNombreUsuario, robot);
			TestUtils.tipeaTexto(nombreUsuarios.get(i), robot);
			TestUtils.clickComponent(compPassword, robot);
			TestUtils.tipeaTexto(contrasenias.get(i), robot);
			
			robot.delay(1000);
			assertEquals(ventana.getUsserName(), nombreUsuarios.get(i));
			assertEquals(ventana.getPassword(), contrasenias.get(i));
			
			TestUtils.borraJTextField(compNombreUsuario, robot);
			TestUtils.borraJTextField(compPassword, robot);
		}
	}
	
	
	
	

}
