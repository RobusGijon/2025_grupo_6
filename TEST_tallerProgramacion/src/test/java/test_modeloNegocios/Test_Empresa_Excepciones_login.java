package test_modeloNegocios;

import static org.junit.Assert.*;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import modeloDatos.Administrador;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;

import org.junit.Before;
import org.junit.Test;


public class Test_Empresa_Excepciones_login {

	Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
	}
	
    @After
    public void tearDown() {
        empresa.setClientes(new HashMap<>());
        empresa.setPedidos(new HashMap<>());
        empresa.setVehiculos(new HashMap<>());
        empresa.setVehiculosDesocupados(new java.util.ArrayList<>());
        empresa.setChoferes(new HashMap<>());
        empresa.setChoferesDesocupados(new java.util.ArrayList<>());
        empresa.setViajesIniciados(new HashMap<>());
        empresa.setViajesTerminados(new java.util.ArrayList<>());
        empresa.setUsuarioLogeado(null);
    }
    
    
    private void setEscenarioUsuariosGenerico() {

    	/*
    	 * 	Escenario: 
    	 * 				Cliente1: ("A", "A", "A")
    	 * 				
    	 * 				Cliente2: ("el_TIRRY1", "Pass_1", "nombreReal")
    	 * 
    	 * */
    	
    	Cliente cliente1 = new Cliente("A", "A", "A");
    	Cliente cliente2 = new Cliente("el_TIRRY1", "Pass_1", "nombreReal");
    	
    	HashMap<String, Cliente> mapaCliente = new HashMap<>();
    	mapaCliente.put("A", cliente1);
    	mapaCliente.put("el_TIRRY1", cliente2);
    	
    	empresa.setClientes(mapaCliente);
    	
    	
    }
    
    @Test
    public void login_ok_cliente() throws Exception {
	  	/*
    	 * 	Escenario: 
    	 * 				Cliente1: ("A", "A", "A")
    	 * 				
    	 * 				Cliente2: ("el_TIRRY1", "Pass_1", "nombreReal")
    	 * 
    	 * 	Prueba: 	Verificar que cliente1 se logue correctamente
    	 * 				
    	 * 				>> Esperado: Cliente 1 logueado en el sistema
    	 * 				>> Resultado: Correcto
    	 * */
    	
        setEscenarioUsuariosGenerico();
        Usuario u = empresa.login("A", "A");
        assertEquals("A", u.getNombreUsuario());
        assertNotNull(empresa.getUsuarioLogeado());
        assertEquals("A", empresa.getUsuarioLogeado().getNombreUsuario());
    }

    @Test
    public void login_ok_cliente2() throws Exception {
	  	/*
    	 * 	Escenario: 
    	 * 				Cliente1: ("A", "A", "A")
    	 * 				
    	 * 				Cliente2: ("el_TIRRY1", "Pass_1", "nombreReal")
    	 * 
    	 * 	Prueba: 	Verificar que cliente1 se logue correctamente
    	 * 				
    	 * 				>> Esperado: Cliente 2 logueado en el sistema
    	 * 				>> Resultado: Correcto
    	 * */
        setEscenarioUsuariosGenerico();
        Usuario u = empresa.login("el_TIRRY1", "Pass_1");
        assertEquals("el_TIRRY1", u.getNombreUsuario());
        assertEquals("el_TIRRY1", empresa.getUsuarioLogeado().getNombreUsuario());
    }

    @Test
    public void login_ok_admin() throws Exception {
	  	/*
    	 * 	Escenario: 
    	 * 				Cliente1: ("A", "A", "A")
    	 * 				
    	 * 				Cliente2: ("el_TIRRY1", "Pass_1", "nombreReal")
    	 * 
    	 * 	Prueba: 	Verificar que el admin se logue correctamente
    	 * 				
    	 * 				>> Esperado: Cliente 1 logueado en el sistema
    	 * 				>> Resultado: Correcto
    	 * */
    	
        Administrador admin = Administrador.getInstance();
        Usuario usuario = empresa.login(admin.getNombreUsuario(), admin.getPass());
        assertEquals(admin.getNombreUsuario(), usuario.getNombreUsuario());
        assertEquals(admin.getNombreUsuario(), empresa.getUsuarioLogeado().getNombreUsuario());
    }

	@Test
	public void test_loginUsuario_contraseniaExistente_usuarioInexistente() {
	  	/*
    	 * 	Escenario: 
    	 * 				Cliente1: ("A", "A", "A")
    	 * 				
    	 * 				Cliente2: ("el_TIRRY1", "Pass_1", "nombreReal")
    	 * 
    	 * 	Prueba: 	contrasenia existente pero usuario inexistente
    	 * 				
    	 * 				usserName = "Don Jose"
    	 * 				pass = "Pass_1"  * La del Cliente2
    	 * 				>> Esperado: Excepcion : UsuarioNoExisteException
    	 * 				>> Resultado: Correcto
    	 * */	
		
		this.setEscenarioUsuariosGenerico();
		String usserName = "Don Jose";
		String pass = "Pass_1";
		
		
		try {
			empresa.login(usserName, pass);
			fail("El metodo no lanzo la excepcion");
		} catch (UsuarioNoExisteException e) {
			assertTrue(true);
		} catch (PasswordErroneaException e) {
		
			fail("El metodo lanzo una excepcion erronea (PasswordErroneaException)");
		}
		
	}
	
	@Test
	public void test_loginUsuario_contraseniaExistente_usuarioEnMalFormato() {
	  	/*
    	 * 	Escenario: 
    	 * 				Cliente1: ("A", "A", "A")
    	 * 				
    	 * 				Cliente2: ("el_TIRRY1", "Pass1", "nombreReal")
    	 * 
    	 * 				pass = "Pass_1" * La misma del cliente 2, usada para las pruebas [1 : 4]
    	 * 				
    	 * 	Prueba 1: 	contrasenia existente pero usuario existente sin mayusculas		
    	 * 				
    	 * 				usserName1 = "el_tirry1" 
    	 * 				>> Esperado: Excepcion : UsuarioNoExisteException
    	 * 				>> Resultado: Correcto
    	 * 
    	 * 	Prueba 2: 	usuario sin numeros
    	 * 				 
    	 * 				usserName2 = "el_TIRRY" 
    	 * 				>> Esperado: Excepcion : UsuarioNoExisteException
    	 * 				>> Resultado: Correcto
    	 * 
    	 * 	Prueba 3: 	usuario todo en mayuscula
    	 * 				
    	 *  			usserName3 = "EL_TIRRY1" 
    	 *  			>> Esperado: Excepcion : UsuarioNoExisteException
    	 * 				>> Resultado: Correcto
    	 *  
    	 *  Prueba 4:  	usuario sin caracteres especiales
    	 * 				  
    	 * 				usserNmae4 = "elTIRRY1"
    	 * 				>> Esperado: Excepcion : UsuarioNoExisteException
    	 * 				>> Resultado: Correcto
    	 * 
    	 * 	Prueba 5: 	contrasenia de 1 caracter
    	 * 
    	 * 				pass = "A" * la del cliente 1
    	 * 				usserName5 = "a" 
    	 * 				>> Esperado: Excepcion : UsuarioNoExisteException
    	 * 				>> Resultado: Correcto
    	 * 
    	 * */	
		
		this.setEscenarioUsuariosGenerico();
		String[] usserNames = {"el_tirry1", "el_TIRRY", "EL_TIRRY1", "elTIRRY1"};
		String pass = "Pass_1";
		
		
		for (int i = 0; i < usserNames.length; i++) {			
			try {
				empresa.login(usserNames[i], pass);
				fail("El metodo no lanzo la excepcion | usserName: " + usserNames[i]);
			} catch (UsuarioNoExisteException e) {
				assertTrue(true);
			} catch (PasswordErroneaException e) {
				fail("El metodo lanzo una excepcion erronea (PasswordErroneaException) | usserName: " + usserNames[i]);
			}
		}
		
		try {
			empresa.login("a", "A");
			fail("El metodo no lanzo la excepcion | usserName: " + "a");
		} catch (UsuarioNoExisteException e) {
			assertTrue(true);
		} catch (PasswordErroneaException e) {
			fail("El metodo lanzo una excepcion erronea (PasswordErroneaException) | usserName: " + "a");
		}
		
		
		
	}
	
	
	@Test
	public void test_loginUsuario_usuarioExistente_contraseniaErronea_inexistente() {
	  	/*
    	 * 	Escenario: 
    	 * 				Cliente1: ("A", "A", "A")
    	 * 				
    	 * 				Cliente2: ("el_TIRRY1", "Pass_1", "nombreReal")
    	 * 
    	 * 	Prueba: 	usuario existente pero contrasenia erronea (no parecida)
    	 * 				
    	 * 				usserName1 = "el_TIRRY1"
    	 * 				pass1 = "12390KDASD"
    	 * 				>> Esperado: Excepcion: PasswordErroneaException
    	 * 				>> Resultado: Correcto
    	 * 
    	 *    	 		usserName1 = "A"
    	 * 				pass1 = "AFSMFJAS131"
    	 * 				>> Esperado: Excepcion: PasswordErroneaException
    	 * 				>> Resultado: Correcto
    	 * 				
    	 * */	
		
		this.setEscenarioUsuariosGenerico();
		String usserName1 = "el_TIRRY1";
		String pass1 = "AFSMFJAS131";
		
		String usserName2 = "A";
		String pass2 = "12390KDASD";
		
		try {
			empresa.login(usserName1, pass1);
			fail("El metodo no lanzo la excepcion");
		} catch (UsuarioNoExisteException e) {
			fail("El metodo lanzo una excepcion erronea (UsuarioNoExisteException)");
		} catch (PasswordErroneaException e) {
			assertTrue(true);
			
		}
		
		try {
			empresa.login(usserName2, pass2);
			fail("El metodo no lanzo la excepcion");
		} catch (UsuarioNoExisteException e) {
			fail("El metodo lanzo una excepcion erronea (UsuarioNoExisteException)");
		} catch (PasswordErroneaException e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void test_loginUsuario_usuarioExistente_contraseniaEnMalFormato() {
	  	/*
    	 * 	Escenario: 
    	 * 				Cliente1: ("A", "A", "A")
    	 * 				
    	 * 				Cliente2: ("el_TIRRY1", "Pass_1", "nombreReal")
    	 * 
    	 * 				nombre = "el_TIRRY1" * La misma del cliente 2, usada para las pruebas [1 : 4]
    	 * 				
    	 * 	Prueba 1: 	usuario existente con contrasenia en miniscula	
    	 * 				
    	 * 				pass1 = "pass_1" 
    	 * 				>> Esperado: Excepcion: PasswordErroneaException
    	 * 				>> Resultado: Correcto
    	 * 
    	 * 	Prueba 2: 	contrasenia sin numeros
    	 * 				 
    	 * 				pass2 = "pass_"
    	 * 				>> Esperado: Excepcion: PasswordErroneaException
    	 * 				>> Resultado: Correcto
    	 * 
    	 * 	Prueba 3: 	contrasenia toda en mayusculas
    	 * 				
    	 *  			pass3 = "PASS_1"
    	 *  			>> Esperado: Excepcion: PasswordErroneaException
    	 *  			>> Resultado: Correcto
    	 *  
    	 *  Prueba 4:  	contrasenia sin caracteres especiales
    	 * 				  
    	 * 				pass4 = "pass1"
    	 * 				>> Esperado: Excepcion: PasswordErroneaException
    	 * 				>> Resultado: Correcto
    	 * 
    	 * 	Prueba 5: 	contrasenia 1 caracter
    	 * 
    	 * 				pass = "a" * la del cliente 1
    	 * 				usserName5 = "A" 
    	 * 				>> Esperado: Excepcion: PasswordErroneaException
    	 * 				>> Resultado: Correcto
    	 * 
    	 * */	
		
		this.setEscenarioUsuariosGenerico();
		String usserName = "el_TIRRY1";
		String[] passes = {"pass_1", "pass_", "PASS_1", "pass1", "a"};
		
		
		for (int i = 0; i < passes.length; i++) {			
			try {
				empresa.login(usserName, passes[i]);
				fail("El metodo no lanzo la excepcion | usserName: " + passes[i]);
			} catch (UsuarioNoExisteException e) {
				fail("El metodo lanzo una excepcion erronea (UsuarioNoExisteException) | (cliente 2) pass: " + passes[i]);
			} catch (PasswordErroneaException e) {
				assertTrue(true);
			}
		}
		
		try {
			empresa.login("A", "a");
			fail("El metodo no lanzo la excepcion | usserName: " + "a");
		} catch (UsuarioNoExisteException e) {
			fail("El metodo lanzo una excepcion erronea (UsuarioNoExisteException) | (cliente 1) pass: " + "a");
		} catch (PasswordErroneaException e) {
			assertTrue(true);
		}
		
		
		
	}

}























