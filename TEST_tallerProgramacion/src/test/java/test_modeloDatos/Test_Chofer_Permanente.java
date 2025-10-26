package test_modeloDatos;

import static org.junit.Assert.*;

import java.time.Year;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;

public class Test_Chofer_Permanente {

	private ChoferPermanente chofer;
	private String choferNombre;;
	private String choferDNI;
	private int anioIngreso;
	private int cantHijos;
	
	@Before
	public void setUp() throws Exception {
	
		choferDNI = "44312321";
		choferNombre = "Juan Perez";
		anioIngreso = 2010;
		cantHijos = 2;
		
		chofer = new ChoferPermanente(choferDNI, choferNombre, anioIngreso, cantHijos);
	
	}

	@Test
	public void test_getAnioIngreso() {
		assertEquals(chofer.getAnioIngreso(), anioIngreso);	
	}
	
	@Test
	public void test_getAntiguedad() {
		int anioActual = Year.now().getValue();		
		assertEquals(chofer.getAntiguedad(), anioActual - anioIngreso);
	}
	
	@Test
	public void test_get_cantHijos() {
		assertEquals(chofer.getCantidadHijos(), cantHijos);	
		chofer.setCantidadHijos(3);
		assertEquals(chofer.getCantidadHijos(), 3);
	}
	
	//	El sueldo bruto se calcula incrementando el sueldo basico a partir de un plus por antiguedad y un plus por cantidad de hijos. 
	//	Se icrementa un 5% del basico por cada aÃ±o de antiguedad, hasta llegar a un maximo incremento de 100% que se logra a los 20 anios.
	//	Se icrementa un 7% del basico por cada hijo
		
	private double formulaSueldoBruto(ChoferPermanente chofer) {
		double porcentajeHijos = 0.07;
		double porcentajeAnt = 0.05;
		
		double salarioBasico = Chofer.getSueldoBasico();
		int antiguedad = chofer.getAntiguedad();
		int cantHijos = chofer.getCantidadHijos();
		
		antiguedad = antiguedad > 20? 20 : antiguedad;
		
		double plusAntiguedad = salarioBasico * porcentajeAnt * antiguedad;
		double plusHijos = salarioBasico * porcentajeHijos * cantHijos;		
		
		return salarioBasico + plusAntiguedad + plusHijos;
	}
	
	@Test
	public void test_getSueldoBruto_sinPlus() {
		/*	
		 * 	Escenario: Chofer sin ningun plus: sin hijos ni antiguedad	
		 * 
		 * 			choferSinPlus = ("11222333", "Juan Perez", Year.now().getValue(), 0);
		 * 
		 * 	  Prueba: Deberia devolver el salarioBasico
		 * 			
		 * 			>> Esperado: 500000.0
		 * 			>> Resultado: Correcto
		 * 
		 * */
		
		ChoferPermanente choferSinPlus = new ChoferPermanente("11222333", "Juan Perez", Year.now().getValue(), 0);
		
		double sueldoBrutoEsperado = formulaSueldoBruto(choferSinPlus);
		
		assertEquals("El metodo no calculo bien su sueldo bruto: sueldoObtenido: " + choferSinPlus.getSueldoBruto()
		+ " | sueldoEsperado: " + Chofer.getSueldoBasico(), choferSinPlus.getSueldoBruto(), Chofer.getSueldoBasico(), 1e-5);
		
	}
	
	@Test
	public void test_getSueldoBruto_plus_hijos() {
		/*	
		 * 	Escenario: Chofer con hijos, sin antiguedad
		 * 
		 * 			choferConHijos = ("11222333", "Juan Perez", Year.now().getValue(), 3);
		 * 
		 * 	  Prueba: Deberia devolver el salarioBasico mas el plus de los hijos
		 * 			
		 * 			>> Esperado: 605000.0
		 * 			>> Resultado: Correcto
		 * 
		 * */
		
		ChoferPermanente choferConHijos = new ChoferPermanente("11222333", "Juan Perez", Year.now().getValue(), 3);
		
		double sueldoBrutoEsperado = formulaSueldoBruto(choferConHijos);
		
		assertEquals("El metodo no calculo bien su sueldo bruto: sueldoObtenido: " + choferConHijos.getSueldoBruto()
				+ " | sueldoEsperado: " + sueldoBrutoEsperado, choferConHijos.getSueldoBruto(), sueldoBrutoEsperado, 1e-5);
	}
	
	@Test
	public void test_getSueldoBruto_plus_antiguedad() {
		/*	
		 * 	Escenario: Chofer con antiguedad sin hijos
		 * 
		 * 			AnioActual = 2025
		 * 
		 * 			choferConAntiguedad = ("11222333", "Juan Perez", 2000, 0);
		 * 
		 * 	  Prueba: Deberia devolver el salarioBasico mas el plus de la antiguedad
		 * 			
		 * 			>> Esperado: 1000000.0
		 * 			>> Resultado: INCORRECTO -> 1125000.0
		 * 
		 * */
		
		ChoferPermanente choferConAntiguedad = new ChoferPermanente("11222333", "Juan Perez", 2000, 0);
		
		double sueldoBrutoEsperado = formulaSueldoBruto(choferConAntiguedad);
		
		assertEquals("El metodo no calculo bien su sueldo bruto: sueldoObtenido: " + choferConAntiguedad.getSueldoBruto()
		+ " | sueldoEsperado: " + sueldoBrutoEsperado, choferConAntiguedad.getSueldoBruto(), sueldoBrutoEsperado, 1e-5);
	}
	
	
	@Test
	public void test_getSueldoBruto_plus_varios() {
		/*	
		 * 	Escenario: Chofer con antiguedad y con hijos
		 * 
		 * 			AnioActual = 2025
		 * 
		 * 			choferConAntiguedad = ("11222333", "Juan Perez", 2000, 5);
		 * 
		 * 	  Prueba: Deberia devolver el salarioBasico mas el plus de los hijos mas el plus de la antiguedad
		 * 			
		 * 			>> Esperado: 1300000.0
		 * 			>> Resultado: INCORRECTO -> 1175000.0
		 * 
		 * */
		
		ChoferPermanente choferVariosPlus = new ChoferPermanente("11222333", "Juan Perez", 2000, 5);
		
		double sueldoBrutoEsperado = formulaSueldoBruto(choferVariosPlus);
		
		assertEquals("El metodo no calculo bien su sueldo bruto: sueldoObtenido: " + choferVariosPlus.getSueldoBruto()
		+ " | sueldoEsperado: " + sueldoBrutoEsperado, choferVariosPlus.getSueldoBruto(), sueldoBrutoEsperado, 1e-5);
	}
	
	
}
