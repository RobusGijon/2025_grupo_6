package test_modeloNegocios;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.VehiculoRepetidoException;
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


public class Test_Empresa_Excepciones_agregarVehiculo {

	Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
	}
	
    @After
    public void tearDown() {
    	empresa.setVehiculos(new HashMap<>());
    }

	@Test
	public void test_agregarVehiculoUnicos_NOdeberiaDevolverExpecion() {
		/*
		 *  Escenario:  Se agregan dos vehículos (motos) con patentes DISTINTAS.
		 *  
		 *              moto1: ("AB123CD")
		 *              moto2: ("JK312LP")
		 *              
		 *  Prueba:     agregarVehiculo NO debería lanzar VehiculoRepetidoException
		 *              cuando las patentes no se repiten.
		 *  
		 *              >> Esperado: NO lanzar excepción (VehiculoRepetidoException)
		 *              >> Resultado: Correcto
		 */
		Moto moto1 = new Moto("AB123CD");
		Moto moto2 = new Moto("JK312LP");
		
		try {
			empresa.agregarVehiculo(moto1);
			empresa.agregarVehiculo(moto2);
			assertTrue(true);
		} catch (VehiculoRepetidoException e) {
			fail("El metodo (Empresa | agregarVehiculo) devolvio una expecion NO esperada");
		}
	}
	
	@Test
	public void test_agregarVehiculoRepetido_deberiaDevolverExpecion() {
		/*
		 *  Escenario:  Se intenta agregar dos veces el MISMO vehículo / misma patente.
		 *  
		 *              Caso A:
		 *                  moto1: ("AB123CD")
		 *                  Se agrega dos veces moto1.
		 *              
		 *              Caso B:
		 *                  moto1: ("AB123CD")
		 *                  auto:  ("AB123CD", 4 pasajeros, lleva mascota)  
		 *                  Se intenta agregar moto1 y luego auto.
		 *  
		 *  Prueba:     En ambos casos, agregarVehiculo DEBERIA lanzar VehiculoRepetidoException.
		 *  
		 *              >> Esperado: Lanzar VehiculoRepetidoException
		 *              >> Resultado: Correcto
		 */
		Moto moto1 = new Moto("AB123CD");
		Auto auto = new Auto("AA123CD", 4, true);
		
		try {
			empresa.agregarVehiculo(moto1);
			empresa.agregarVehiculo(moto1);
			fail("El metodo (Empresa | agregarVehiculo) NO devolvio una expecion esperada");
		} catch (VehiculoRepetidoException e) {
			assertTrue(true);
		}
		
		try {
			empresa.agregarVehiculo(moto1);
			empresa.agregarVehiculo(auto);
			fail("El metodo (Empresa | agregarVehiculo) NO devolvio una expecion esperada");
		} catch (VehiculoRepetidoException e) {
			assertTrue(true);
		}
	
	}
	
	@Test
	public void test_agregarVehiculo_verificarQueSeAgregaron() {
		/*
		 *  Escenario:  Se agregan dos motos con patentes distintas y luego
		 *              se verifica su presencia en la estructura interna de la empresa.
		 *  
		 *              patente1 = "AB123CD"
		 *              patente2 = "JK312LP"
		 *              moto1: (patente1)
		 *              moto2: (patente2)
		 *  
		 *  Prueba:     El mapa de vehículos debe contener ambas patentes como claves.
		 *  
		 *              >> Esperado: containsKey(patente1) y containsKey(patente2)
		 *              >> Resultado: Correcto
		 */
		
		String patente1 = "AB123CD";
		String patente2 = "JK312LP";
		Moto moto1 = new Moto(patente1);
		Moto moto2 = new Moto(patente2);
		
		try {
			empresa.agregarVehiculo(moto1);
			empresa.agregarVehiculo(moto2);
			
			HashMap<String, Vehiculo> vehiculos = empresa.getVehiculos();
			assertTrue(vehiculos.containsKey(patente1));
			assertTrue(vehiculos.containsKey(patente2));
		} catch (VehiculoRepetidoException e) {
			
		}
	}
	
	
	
	

}
