package test_modeloNegocios;

import static org.junit.Assert.*;


import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
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


public class Test_Empresa_Excepciones_agregarChofer {

	
	Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
	}
	
    @After
    public void tearDown() {
        empresa.setChoferes(new HashMap<>());
    }


	@Test
	public void test_agregarChoferUnico_NOdeberiaTirarExpecion() {
		/*
		 *  Escenario:  Se agregan dos choferes con MISMO DNI pero NOMBRE distinto.
		 *  
		 *  
		 *              chofer1: Temporario("42184312", "Juan Perez")
		 *              chofer2: Temporario("42184312", "Juan Carlos")
		 *              
		 *              Se intenta registrar ambos en la empresa.
		 *  
		 *  Prueba:     agregarChofer NO deberia tirar ChoferRepetidoException
		 *              cuando el nombre no se repite (aunque el DNI coincida).
		 *  
		 *              >> Esperado: NO lanzar excepcion (ChoferRepetidoException)
		 *              >> Resultado: Correcto
		 */
		
		ChoferTemporario chofer1 = new ChoferTemporario("42184312", "Juan Perez");
		ChoferTemporario chofer2 = new ChoferTemporario("42184312", "Juan Carlos");
		
		try {
			empresa.agregarChofer(chofer1);
			empresa.agregarChofer(chofer2);
			assertTrue(true);
		} catch (ChoferRepetidoException e) {
			fail("El metodo(Empresa | agregarChofer) devolvio una expecion que no debia tirar");
		}
	}
	
	@Test
	public void test_agregarChoferRepetido_deberiaTirarExpecion() {
		/*
		 *  Escenario:  Se agregan dos choferes con NOMBRE y DNI iguales.
		 *  
		 *              chofer1: ("49120123", "Juan Perez")
		 *              chofer2: ("49120123", "Perez Jose")
		 *              
		 *              Se intenta registrar ambos en la empresa.
		 *  
		 *  Prueba:     agregarChofer DEBERIA tirar ChoferRepetidoException ante nombres repetidos.
		 *  
		 *              >> Esperado: Lanzar ChoferRepetidoException
		 *              >> Resultado: INCORRECTO -> agrego a los dos choferes 
		 */
		
		ChoferTemporario chofer1 = new ChoferTemporario("49120123", "Juan Perez");
		ChoferTemporario chofer2 = new ChoferTemporario("49120123", "Perez Jose");
		
		try {
			empresa.agregarChofer(chofer1);
			empresa.agregarChofer(chofer2);
			fail("El metodo (Empresa | agregarChofer) no devolvio la expecion esperada");
		} catch (ChoferRepetidoException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void test_agregarChofer_verificarQueSeAgregaron() {
		/*
		 *  Escenario:  Se agregan dos choferes y luego se verifica que la empresa los agrego
		 *              en la estructura interna (HashMap)
		 *  
		 *              empresa: Empresa.getInstance();
		 *              dniChofer1 = "42184312"
		 *              dniChofer2 = "42184312"
		 *              chofer1: (dniChofer1, "Juan Perez")
		 *              chofer2: (dniChofer2, "Juan Carlos")
		 *  
		 *  Prueba:     Luego de agregar ambos, el HashMap de choferes deberia
		 *              reflejar los ingresos realizados.
		 *  
		 *              >> Esperado: El HashMap contiene las claves utilizadas
		 *                           (dniChofer1 y dniChofer2) y no se produce
		 *                           ChoferRepetidoException.
		 *              >> Resultado: Correcto
		 */
		
		String dniChofer1 = "42184312";
		String dniChofer2 = "42184312";
		ChoferTemporario chofer1 = new ChoferTemporario(dniChofer1, "Juan Perez");
		ChoferTemporario chofer2 = new ChoferTemporario(dniChofer2, "Juan Carlos");
		
		try {
			empresa.agregarChofer(chofer1);
			empresa.agregarChofer(chofer2);
			
			HashMap<String, Chofer> choferes = empresa.getChoferes();
			assertTrue(choferes.containsKey(dniChofer1));
			assertTrue(choferes.containsKey(dniChofer2));
			
		} catch (ChoferRepetidoException e) {
			fail("El metodo(Empresa | agregarChofer) devolvio una expecion que no debia tirar");
		}
	}
	
	
	

}
