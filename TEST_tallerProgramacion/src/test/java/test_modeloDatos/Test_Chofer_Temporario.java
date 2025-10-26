package test_modeloDatos;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import modeloDatos.ChoferTemporario;

public class Test_Chofer_Temporario {

	
	private ChoferTemporario chofer;
	private String choferDNI;
	private String choferNombre;
	
	@Before
	public void setUp() throws Exception {
		choferDNI = "44231481";
		choferNombre = "Juan Perez";
		chofer = new ChoferTemporario(choferDNI, choferNombre);
	}
	
	@Test
	public void test_getDNI() {
		Assert.assertEquals(chofer.getDni(), choferDNI);
	}
	
	@Test
	public void test_getNombre() {
		Assert.assertEquals(chofer.getNombre(), choferNombre);
	}

	@Test
	public void test_setSueldoBasico() {
		
		double nuevoSueldo;
		
		// Test: sueldo entero
		nuevoSueldo = 1000.0;
		ChoferTemporario.setSueldoBasico(nuevoSueldo);
		Assert.assertTrue(nuevoSueldo == ChoferTemporario.getSueldoBasico());
		
		// Test: sueldo flotante
		nuevoSueldo = 41241.421412;
		ChoferTemporario.setSueldoBasico(nuevoSueldo);
		Assert.assertTrue(nuevoSueldo == ChoferTemporario.getSueldoBasico());
	}

	
	@Test
	public void test_getSueldoBruto() {
		double sueldo = 1234.42;
		ChoferTemporario.setSueldoBasico(sueldo);
		Assert.assertEquals(chofer.getSueldoBruto(), ChoferTemporario.getSueldoBasico());
	}
	
	@Test
	public void test_getSueldoNeto() {
		/*
		 * 	Prueba: verificar calculo del sueldo neto
		 * 
		 * 			choferTemporario: ("44231481", "Juan Perez")
		 * 					  sueldo: 1234.1234 
		 * 
		 * 			>> Esperado: 1061.34
		 * 			>> Resultado: Correcto
		 * 
		 * */
		
		double sueldo = 1234.1234;
		double sueldoNetoChofer = chofer.getSueldoBruto() * 0.86;
		
		Assert.assertEquals(chofer.getSueldoNeto(), sueldoNetoChofer);
	}
}
