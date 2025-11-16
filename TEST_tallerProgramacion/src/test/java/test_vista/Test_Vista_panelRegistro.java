package test_vista;

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

public class Test_Vista_panelRegistro {
	private Ventana ventana;
	private Robot robot;
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
	
	private String[][] escenarioBotonRegistrar() {
		
		return new String[][]{
	        // user              , pass    , passRep , nombreReal
	        { "nombreUsuario"    , "pass"  , "pass"  , "nombreReal"   }, // Todo completo
	        { "nombre4Us1uario"  , "pass32", "pass32", "nombreReal12" }, // alfa numerico
	        { "a"                , "a"     , "a"     , "a"            }, // un caracter
	        { ""                 , "pass"  , "pass"  , "nombreReal"   }, // sin nombre usuario
	        { "nombreUsuario"    , ""      , "pass"  , "nombreReal"   }, // sin pass
	        { "nombreUsuario"    , "pass"  , ""      , "nombreReal"   }, // sin pass repetida
	        { "nombreUsuario"    , "pass"  , "pass"  , ""             }, //  sin nombre real
	        { ""                 , ""      , ""      , ""             }  // sin nada
	    };
	    
	}
	
	@Test
	public void test_botonRegistrarHabilitado() throws InterruptedException {
		
		JTextField regNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
		JTextField regPass = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
		JTextField regPassRep = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
		JTextField regNombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);	
		
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
		JButton botonCancelar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_CANCELAR);	
		
		String[][] casos = escenarioBotonRegistrar();	
		boolean isEnabledBotonReg;
		
		
		String nombreUsuario, pass, passRep, nombreReal;
		for(int i = 0; i < casos.length; i++) {
			nombreUsuario = casos[i][0];
			pass = casos[i][1];
			passRep = casos[i][2];
			nombreReal = casos[i][3];
			
			TestUtils.clickComponent(regNombreUsuario, robot);
			TestUtils.tipeaTexto(nombreUsuario, robot);
			TestUtils.clickComponent(regPass, robot);
			TestUtils.tipeaTexto(pass, robot);
			TestUtils.clickComponent(regPassRep, robot);
			TestUtils.tipeaTexto(passRep, robot);
			TestUtils.clickComponent(regNombreReal, robot);
			TestUtils.tipeaTexto(nombreReal, robot);
			
			robot.delay(TestUtils.getDelay());
			
			if(nombreUsuario == "" || pass == "" || passRep == "" || nombreReal == "")
				isEnabledBotonReg = !botonRegistrar.isEnabled();
			else
				isEnabledBotonReg = botonRegistrar.isEnabled();
			
			assertTrue(isEnabledBotonReg);
			
			
			TestUtils.borraJTextField(regNombreUsuario, robot);
			TestUtils.borraJTextField(regPass, robot);
			TestUtils.borraJTextField(regPassRep, robot);
			TestUtils.borraJTextField(regNombreReal, robot);	
		}
	}
	
	@Test
	public void test_registroPanel_getters() throws InterruptedException {
		
		JTextField regNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
		JTextField regPass = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
		JTextField regPassRep = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
		JTextField regNombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);	
		
		JButton botonRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
		JButton botonCancelar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_CANCELAR);	
		
		String[][] casos = escenarioBotonRegistrar();	
		boolean isEnabledBotonReg;
		
		
		String nombreUsuario, pass, passRep, nombreReal;
		for(int i = 0; i < casos.length; i++) {
			nombreUsuario = casos[i][0];
			pass = casos[i][1];
			passRep = casos[i][2];
			nombreReal = casos[i][3];
			
			TestUtils.clickComponent(regNombreUsuario, robot);
			TestUtils.tipeaTexto(nombreUsuario, robot);
			TestUtils.clickComponent(regPass, robot);
			TestUtils.tipeaTexto(pass, robot);
			TestUtils.clickComponent(regPassRep, robot);
			TestUtils.tipeaTexto(passRep, robot);
			TestUtils.clickComponent(regNombreReal, robot);
			TestUtils.tipeaTexto(nombreReal, robot);
			
			robot.delay(TestUtils.getDelay());
			
			assertEquals(nombreUsuario, ventana.getRegUsserName());
			assertEquals(pass, ventana.getRegPassword());
			assertEquals(passRep, ventana.getRegConfirmPassword());
			assertEquals(nombreReal, ventana.getRegNombreReal());
			
			
			TestUtils.borraJTextField(regNombreUsuario, robot);
			TestUtils.borraJTextField(regPass, robot);
			TestUtils.borraJTextField(regPassRep, robot);
			TestUtils.borraJTextField(regNombreReal, robot);	
		}
	}

	
	
	
}
































