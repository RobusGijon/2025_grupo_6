package test_modeloNegocios;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.UsuarioYaExisteException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;

public class Test_Empresa_Excepciones_agregarCliente {

	Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
	}
	
    @After
    public void tearDown() {
  
        empresa.setClientes(new HashMap<>());
    }

	@Test
	public void test_agregarClienteUnicos_noDeberiaVolverExcepcion() {
		/*
		 *  Escenario:  Se intentan agregar tres clientes con NOMBRES DE USUARIO distintos.
		 *  
		 *              empresa: Empresa.getInstance();
		 *              cliente1: ("nombreUsuario1", "pass1", "nombreReal1")
		 *              cliente2: ("nombreUsuario2", "pass2", "nombreReal2")
		 *              cliente3: ("nombreUsuario3", "pass3", "nombreReal3")
		 *              
		 *  Prueba:     agregarCliente NO deberia lanzar UsuarioYaExisteException
		 *              cuando los nombres de usuario no se repiten.
		 *  
		 *              >> Esperado: NO lanzar excepcion (UsuarioYaExisteException)
		 *              >> Resultado: Correcto
		 */
		
		try {
			empresa.agregarCliente("nombreUsuario1", "pass1", "nombreReal1");
			empresa.agregarCliente("nombreUsuario2", "pass2", "nombreReal2");
			empresa.agregarCliente("nombreUsuario3", "pass3", "nombreReal3");
			assertTrue(true);
		} catch (UsuarioYaExisteException e) {
			fail("El metodo (Empresa | agregarCliente) devolvio una expecion no esperada");
		}
	}
	
	@Test
	public void test_agregarClienteRepetidos_DeberiaVolverExcepcion() {
		/*
		 *  Escenario:  Se intenta agregar varias veces el MISMO nombre de usuario.
		 *  
		 *              empresa: Empresa.getInstance();
		 *              cliente:  ("nombreUsuario1", "pass1", "nombreReal1") x 3 veces
		 *              
		 *  Prueba:     agregarCliente DEBERIA lanzar UsuarioYaExisteException
		 *              cuando el nombre de usuario ya existe.
		 *  
		 *              >> Esperado: Lanzar UsuarioYaExisteException
		 *              >> Resultado: Correcto
		 */
		
		try {
			empresa.agregarCliente("nombreUsuario1", "pass1", "nombreReal1");
			empresa.agregarCliente("nombreUsuario1", "pass1", "nombreReal1");
			empresa.agregarCliente("nombreUsuario1", "pass1", "nombreReal1");
			fail("El metodo (Empresa | agregarCliente) NO devolvio una expecion esperada");
		} catch (UsuarioYaExisteException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void test_agregarCliente_verificarQueSeAgregaron() {
		/*
		 *  Escenario:  Se agregan tres clientes con usuarios distintos y luego
		 *              se verifica su presencia en la estructura interna (HashMap) de la empresa.
		 *  
		 *              empresa: Empresa.getInstance();
		 *              nombreUs1 = "nombreUsuario1"
		 *              nombreUs2 = "nombreUsuario2"
		 *              nombreUs3 = "nombreUsuario3"
		 *              
		 *  Prueba:     Luego de agregar, el HashMap de clientes DEBE contener
		 *              las claves (nombres de usuario) utilizadas.
		 *  
		 *              >> Esperado: los 3 clientes dentro del HashMap de empresa
		 *              >> Resultado: Correcto
		 */
		
		String nombreUs1 = "nombreUsuario1";
		String nombreUs2 = "nombreUsuario2";
		String nombreUs3 = "nombreUsuario3";
		
		try {
			empresa.agregarCliente(nombreUs1, "pass1", "nombreReal1");
			empresa.agregarCliente(nombreUs2, "pass2", "nombreReal2");
			empresa.agregarCliente(nombreUs3, "pass3", "nombreReal3");
			
			HashMap<String, Cliente> clientes = empresa.getClientes();
			
			assertTrue(clientes.containsKey(nombreUs1));
			assertTrue(clientes.containsKey(nombreUs2));
			assertTrue(clientes.containsKey(nombreUs3));
			
		} catch (UsuarioYaExisteException e) {
			fail("El metodo lanzo una excepcion inesperada");
		}
	}

	
	


}
