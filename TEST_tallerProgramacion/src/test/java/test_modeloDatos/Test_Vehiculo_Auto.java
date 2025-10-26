package test_modeloDatos;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import util.Constantes;

public class Test_Vehiculo_Auto {
	
	private Auto autoMascota;
	private Auto autoSinMascota;
	private Cliente cliente;
	
	private static final String PATENTE = "AA123AA";
	private static final int CANTIDAD_PLAZAS = 4;
	private static final int CANT_PASAJEROS = 2;
	private static final int KM = 10;
	
	@Before
	public void setUp() throws Exception {
		cliente = new Cliente("nombreUsuario", "pass", "nombreReal");
		
		
		autoMascota = new Auto(PATENTE, CANTIDAD_PLAZAS, true);
		autoSinMascota = new Auto(PATENTE, CANTIDAD_PLAZAS, false);
	}
	
	@Test
	public void test_getCantidadPlazas() {
		assertEquals(autoMascota.getCantidadPlazas(), CANTIDAD_PLAZAS);
	}
	
	@Test
	public void test_getPatente() {
		assertEquals(autoMascota.getPatente(), PATENTE);
	}
	
	@Test
	public void test_isMascota() {
		assertTrue(autoMascota.isMascota());
		assertFalse(autoSinMascota.isMascota());
	}
	
	
	@Test
	public void test_getPuntajePedido_excesoCapacidad() {
		/*
		 * 	Escenario: Pedido que excede la cantidad de asientos
		 * 
		 * 		  			  cliente: ("nombreUsuario", "pass", "nombreReal");
		 * 		PedidoExcesoCapacidad: (cliente, 5 pasajeros, SIN mascota, SIN baul, 10km, Zona peligrosa) 
		 * 
		 * 	Prueba: ambos autos deberian devolver null
		 * 				
		 * 			autoMascota: ("AA123AA", 4, puede llevar mascota)
		 * 			>>  Esperado: null
		 * 			>> Resultado: Correcto
		 * 
		 * 			autoSinMascota: ("AA123AA", 4, NO puede llevar mascota)
		 * 			>>  Esperado: null
		 * 			>> Resultado: Correcto
		 * */	
		
		int cantPasajeros = autoMascota.getCantidadPlazas() + 1;
		Pedido pedido = new Pedido(cliente, cantPasajeros, false, false, 1000, Constantes.ZONA_PELIGROSA);
		assertEquals(autoMascota.getPuntajePedido(pedido), null);
		assertEquals(autoSinMascota.getPuntajePedido(pedido), null);
	}
	
	@Test
	public void test_getPuntajePedido_autoSinMascotas() {
		/*
		 * 	Escenario: Pedido que lleva mascota
		 * 
		 * 		  			  cliente: ("nombreUsuario", "pass", "nombreReal");
		 * 				PedidoMascota: (cliente, 1 pasajeros, CON mascota, SIN baul, 10km, Zona peligrosa) 
		 * 
		 * 	Prueba: autoSinMascota deberia devolver null, mientras que el otro no
		 * 				
		 * 			autoSinMascota: ("AA123AA", 4, NO puede llevar mascota)
		 * 			>>   Esperado: null
		 * 			>>  Resultado: Correcto
		 * 
		 * 			   autoMascota: ("AA123AA", 4, puede llevar mascota)
		 * 			>> 	 Esperado: NOT null
		 * 			>> 	Resultado: Correcto
		 * 
		 * */
		
		Pedido pedidoMascota = new Pedido(cliente, 1, true, false, 1, Constantes.ZONA_PELIGROSA);
		assertEquals(autoSinMascota.getPuntajePedido(pedidoMascota), null);
		assertNotNull(autoMascota.getPuntajePedido(pedidoMascota));
	}
	
	@Test
	public void test_getPuntajePedido_usoBaul_deberiaPoder() {
		/*
		 * 	Escenario: Pedido que necesita uso de baul
		 * 
		 * 		  			  cliente: ("nombreUsuario", "pass", "nombreReal");
		 * 				   PedidoBaul: (cliente, 1 pasajeros, SIN mascota, CON baul, 10km, Zona sin asfaltar) 
		 * 
		 * 	Prueba: ambos autos deberian retornar un puntaje no nulo
		 * 				
		 * 			autoSinMascota: ("AA123AA", 4, NO puede llevar mascota)
		 * 			>>   Esperado: NOT null
		 * 			>>  Resultado: Correcto
		 * 		
		 * 			autoMascota: ("AA123AA", 4, puede llevar mascota)
		 * 			>>  Esperado: NOT null
		 * 			>> Resultado: Correcto
		 * 
		 * */
		
		Pedido pedidoSinBaul = new Pedido(cliente, 1, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		
		assertNotNull(autoMascota.getPuntajePedido(pedidoSinBaul));
		assertNotNull(autoSinMascota.getPuntajePedido(pedidoSinBaul));
	}
	

	
	@Test
	public void test_getPuntajePedido_usoBaul() {
		/*
		 * 	Escenario: Pedidos con diferentes pasajeros, todos necesitando uso de baul
		 * 
		 * 		  			  cliente: ("nombreUsuario", "pass", "nombreReal");
		 * 		 PedidoBaul4Pasajeros: (cliente, 4 pasajeros, SIN mascota, CON baul, 10km, Zona peligrosa) 
		 * 		  PedidoBaul1Pasajero: (cliente, 1 pasajeros, SIN mascota, CON baul, 10km, Zona peligrosa)
		 * 
		 * 	Prueba: ambos autos deberian devolver el mismo puntaje
		 * 				
		 * 			autoSinMascota: ("AA123AA", 4, NO puede llevar mascota)
		 * 				Pedido4Pasajero
		 * 				>>  Esperado: 160
		 * 				>> Resultado: Correcto
		 * 							
		 * 				Pedido1Pasajero
		 * 				>>  Esperado: 40
		 * 				>> Resultado: Correcto
		 * 		
		 * 
		 * 			autoMascota: ("AA123AA", 4, puede llevar mascota)
		 * 				Pedido4Pasajero
		 * 				>>  Esperado: 160
		 * 				>> Resultado: Correcto
		 * 							
		 * 				Pedido1Pasajero
		 * 				>>  Esperado: 40
		 * 				>> Resultado: Correcto
		 * 	
		 * */
		 
		Pedido pedidoBaul4Pasajeros = new Pedido(cliente, 4, false, true, 10, Constantes.ZONA_PELIGROSA);
		Pedido pedidoBaul1Pasajeros = new Pedido(cliente, 1, false, true, 10, Constantes.ZONA_PELIGROSA);
		
		double puntajeEsperado4 = 40 * pedidoBaul4Pasajeros.getCantidadPasajeros();
		double puntajeEsperado1 = 40 * pedidoBaul1Pasajeros.getCantidadPasajeros();
		
		assertTrue(autoMascota.getPuntajePedido(pedidoBaul1Pasajeros) == puntajeEsperado1);
		assertTrue(autoMascota.getPuntajePedido(pedidoBaul4Pasajeros) == puntajeEsperado4);
		
		assertTrue(autoSinMascota.getPuntajePedido(pedidoBaul1Pasajeros) == puntajeEsperado1);
		assertTrue(autoSinMascota.getPuntajePedido(pedidoBaul4Pasajeros) == puntajeEsperado4);
		
	}
	
	@Test
	public void test_getPuntajePedido_SinUsoBaul() {
		/*
		 * 	Escenario: Pedidos con diferentes pasajeros, todos SIN necesitar uso de baul
		 * 
		 * 		  			  cliente: ("nombreUsuario", "pass", "nombreReal");
		 * 		  PedidoBaul1Pasajero: (cliente, 1 pasajeros, SIN mascota, SIN baul, 10km, Zona peligrosa)
		 * 
		 * 	Prueba: ambos autos deberian devolver el mismo puntaje
		 * 				
		 * 			autoSinMascota: ("AA123AA", 4, NO puede llevar mascota)
		 * 				Pedido1Pasajero
		 * 				>>  Esperado: 30
		 * 				>> Resultado: INCORRECTO -> 40
		 * 			
		 * 			autoMascota: ("AA123AA", 4, puede llevar mascota)
		 * 				Pedido1Pasajero
		 * 				>>  Esperado: 30
		 * 				>> Resultado: INCORRECTO -> 40
		 * */		
		
		Pedido pedidoBaul1Pasajeros = new Pedido(cliente, 1, false, false, 10, Constantes.ZONA_PELIGROSA);
		

		double puntajeEsperado1 = 30 * pedidoBaul1Pasajeros.getCantidadPasajeros();
		
		assertTrue("El vehiculo calculo mal el puntaje. puntajeCalculado: " + autoMascota.getPuntajePedido(pedidoBaul1Pasajeros)
																				  + " | puntajeEspeerado: " + puntajeEsperado1,
														autoMascota.getPuntajePedido(pedidoBaul1Pasajeros) == puntajeEsperado1);
		
		assertTrue("El vehiculo calculo mal el puntaje. puntajeCalculado: " + autoSinMascota.getPuntajePedido(pedidoBaul1Pasajeros)
																						+ " | puntajeEspeerado: " + puntajeEsperado1,
															autoSinMascota.getPuntajePedido(pedidoBaul1Pasajeros) == puntajeEsperado1);
		
	}
	

}
