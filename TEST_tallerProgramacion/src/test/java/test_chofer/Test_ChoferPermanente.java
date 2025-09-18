package test_chofer;

import static org.junit.Assert.*;

import java.time.Year;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;

public class Test_ChoferPermanente {

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
		Assert.assertEquals(chofer.getAnioIngreso(), anioIngreso);	
	}
	
	@Test
	public void test_getAntiguedad() {
		int anioActual = Year.now().getValue();		
		Assert.assertEquals(chofer.getAntiguedad(), anioActual - anioIngreso);
	}
	
	@Test
	public void test_getcantHijos() {
		Assert.assertEquals(chofer.getCantidadHijos(), cantHijos);	
	}

	@Test
	public void test_setcantHijos() {
		ArrayList<Integer> hijos = new ArrayList<>();
		for(int i = 0; i < 10; i++)
			hijos.add(i);
		
		for(int hijo : hijos) {
			chofer.setCantidadHijos(hijo);
			Assert.assertEquals(chofer.getCantidadHijos(), hijo);	
		}
	}
	
	//	El sueldo bruto se calcula incrementando el sueldo basico a partir de un plus por antiguedad y un plus por cantidad de hijos. 
	//	Se icrementa un 5% del basico por cada aÃ±o de antiguedad, hasta llegar a un maximo incremento de 100% que se logra a los 20 aÃ±os.
	//	Se icrementa un 7% del bascio por cada hijo
		
	private double formulaSueldoBruto(double salarioBasico,int antiguedad,int cantHijos) {
		double porcentajeHijos = 0.07;
		double porcentajeAnt = 0.05;
		
		antiguedad = antiguedad > 20? 20 : antiguedad;
		
		double plusAntiguedad = salarioBasico * porcentajeAnt * antiguedad;
		double plusHijos = salarioBasico * porcentajeHijos * cantHijos;		
		
		return salarioBasico + plusAntiguedad + plusHijos;
	}
	
	@Test
	public void test_getSueldoBruto() {
		
		int anioActual = Year.now().getValue();
		int hijosMax = 10;
		double MAX_SUELDO = 200000;
		double MIN_SUELDO = 1000;
		for(int anioIngreso = anioActual - 30; anioIngreso < anioActual; anioIngreso++)
		{
			for(int cantHijos = 0; cantHijos < hijosMax; cantHijos++)
			{
				ChoferPermanente nuevoChofer = new ChoferPermanente(choferDNI, choferNombre, anioIngreso, cantHijos);
				
				double sueldoBasico = Math.random() * (MAX_SUELDO - MIN_SUELDO) + MIN_SUELDO;
				nuevoChofer.setSueldoBasico(sueldoBasico);
				
				double sueldoBruto = formulaSueldoBruto(sueldoBasico, anioActual - anioIngreso, cantHijos);
				Assert.assertEquals(sueldoBruto, nuevoChofer.getSueldoBruto());
			}
		}
		

		
		
		
	}
	
	
}
