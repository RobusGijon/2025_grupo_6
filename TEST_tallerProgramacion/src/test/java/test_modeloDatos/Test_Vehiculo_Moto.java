package test_modeloDatos;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import util.Constantes;

public class Test_Vehiculo_Moto {

	private Moto moto;
	private String patente;
	private Cliente cliente;

	private Pedido pedido;
	
	public void iniciaVariables() {
		
	}
	
	@Before
	public void setUp() throws Exception {

		cliente  = new Cliente("juanzerPerez", "123456789", "Juan Perez");
		
		patente = "MM123MM";
		moto = new Moto(patente);
	}
	

	@Test
	public void test_getCantidadPlazas() {
		// Se inicializa en 1
		Assert.assertEquals(moto.getCantidadPlazas(), 1);
	}

	@Test
	public void test_isMascota() {
		// Se inicializa en false
		Assert.assertEquals(moto.isMascota(), false);	
	}
	
	@Test
	public void test_getPatente() {
		Assert.assertEquals(moto.getPatente(), patente);
	}
	

	private ArrayList<Pedido> pedidosQueNoCumplen() {
		/*
		 * 	Escenario: Pedidos que no cumplen
		 * 			
		 * 						  cliente: ("A", "A", "A");
		 * 			pedidoExcesoPasajeros: (cliente, 2 pasajero, sin mascota, sin baul, km, zona));
		 * 			pedidoTrasladoMascota: (cliente, 1 pasajero, CON mascota, sin baul, km, zona));
		 * 					pedidoUsoBaul: (cliente, 1 pasajero, sin mascota, CON baul, km, zona));
		 * 
		 * */
		
		
		ArrayList<Pedido> pedidos = new ArrayList<>(); 
		
		cliente = new Cliente("A", "A", "A");
		int cantidadPasajeros = 1;
		boolean mascota = true;
		boolean baul = true;
		int km = 1000;
		String zona = Constantes.ZONA_PELIGROSA;
		
		// cantidadPasajeros > 1
		pedidos.add(new Pedido(cliente, 2, !mascota, !baul, km, zona));
		
		// Traslado de mascota
		pedidos.add(new Pedido(cliente, cantidadPasajeros, mascota, !baul, km, zona));

		// Uso del baul
		pedidos.add(new Pedido(cliente, cantidadPasajeros, !mascota, baul, km, zona));
		
		
		return pedidos;
	}
	
	@Test
	public void test_getPuntajeEnvio_debeRetonarNull() {
		
		/*
		 * 	Escenario: Pedidos que no cumplen
		 * 
		 * 	Prueba: Todos los pedidos deben devolver null 
		 * 			
		 * 			>> Esperado1: null
		 * 			>> Resultado: Correcto
		 * 
		 * 		 	>> Esperado2: null
		 * 			>> Resultado: Correcto
		 * 
		 * 			>> Esperado3: null
		 * 			>> Resultado: Correcto	
		 * 	
		 * */
		
		for (Pedido pedido : pedidosQueNoCumplen() ) {
			Assert.assertEquals("La moto pudo hacer un pedido que no debia hacer: " + pedido, 
														moto.getPuntajePedido(pedido), null);			
		}
	}
	
	private ArrayList<Pedido> pedidosQueCumplen() {
		/*
		 * 	Escenario: Pedidos que cumplen
		 * 
		 * 					cliente: 	("A", "A", "A")
		 * 			PedidoQueCumple1: 	(cliente, 1 pasajero, SIN mascota, SIN baul, 3312km, Zona sin asfaltar)
		 * 			PedidoQueCumple2: 	(cliente, 1 pasajero, SIN mascota, SIN baul, 12341, Zona estandar)
		 * 			PedidoQueCumple3: 	(cliente, 1 pasajero, SIN mascota, SIN baul, 42891, Zona peligrosa)
		 * 
		 * 
		 * */
		
		ArrayList<Pedido> pedidos = new ArrayList<>(); 
		
		cliente = new Cliente("A", "A", "A");
		int cantidadPasajeros = 1;
		boolean mascota = false;
		boolean baul = false;
		
		pedidos.add(new Pedido(cliente, cantidadPasajeros, mascota, baul, 3312, Constantes.ZONA_SIN_ASFALTAR));
		
		pedidos.add(new Pedido(cliente, cantidadPasajeros, mascota, baul, 12341, Constantes.ZONA_STANDARD));

		pedidos.add(new Pedido(cliente, cantidadPasajeros, mascota, baul, 42891, Constantes.ZONA_PELIGROSA));
				
		return pedidos;
	}
	
	@Test
	public void test_getPuntajeEnvio_debeRetonar1000() {
		
		/*
		 * 	Escenario: Pedidos que cumplen
		 * 
		 * 	Prueba: Todos los pedidos deben devolver 1000 
		 * 	
		 * 			>> Esperado1: 1000
		 * 			>> Resultado: Correcto
		 * 
		 * 		 	>> Esperado2: 1000
		 * 			>> Resultado: Correcto
		 * 
		 * 			>> Esperado3: 1000
		 * 			>> Resultado: Correcto	
		 * 
		 * */
		
		int puntajeEseprado = 1000;
		for (Pedido pedido : pedidosQueCumplen() ) {
			Assert.assertTrue(moto.getPuntajePedido(pedido) == puntajeEseprado);			
		}
	}
	
	
	
}
