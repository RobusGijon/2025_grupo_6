package test_modeloDatos;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Pedido;
import util.Constantes;

public class Test_Vehiculo_Combi {

	
	
	private Combi combiMascota;
	private Combi combiSinMascota;
	
	private int cantidadPlazas = 8;
	private boolean mascota = true;
	private String patente = "CC123CC";
	
	private Cliente cliente;
	
	
	@Before
	public void setUp() throws Exception {
		
		cliente = new Cliente("juanzerPerez", "123456789", "Juan Perez");
		
		combiMascota = new Combi(patente, cantidadPlazas, mascota);
		combiSinMascota = new Combi(patente, cantidadPlazas, !mascota);
	}
	
	@Test
	public void test_getPuntajePedido_excesoCapacidad() {
		/*
		 * 	Escenario: Pedido con mascota y sin baul 
		 * 
		 * 				cliente: ("juanzerPerez", "123456789", "Juan Perez");
		 * 				pedidoExceso: (cliente, 9 pasajeros, SIN mascota, SIN baul, 10, Constantes.ZONA_SIN_ASFALTAR);
		 * 
		 * 	Prueba:    el puntaje seberia ser nulo para ambas combis
		 * 
		 * 				combiMascota: ("CC123CC", 8 plazas, lleva mascota)
		 * 				>> Esperado: null
		 * 				>> Resultado: INCORRECTO -> 90
		 * 	
		 * 				combiSinMascota: ("CC123CC", 8 plazas, NO lleva mascota)
		 * 				>> Esperado: null
		 * 				>> Resultado: INCORRECTO -> 90
		 * 
		 * */
		
		
		int pasajerosPedido = combiMascota.getCantidadPlazas() + 1;
		Pedido pedidoExceso = new Pedido(cliente, pasajerosPedido, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		

		assertNull("Combi mascota", combiMascota.getPuntajePedido(pedidoExceso));
		assertNull(combiSinMascota.getPuntajePedido(pedidoExceso));
		
	}

	@Test
	public void test_getPuntajePedido_mascota() {
		/*
		 * 	Escenario: Pedido con mascota y sin baul 
		 * 
		 * 				cliente: ("juanzerPerez", "123456789", "Juan Perez");
		 * 				pedidoMascota: (cliente, 1, CON mascota, SIN baul, 10, Constantes.ZONA_SIN_ASFALTAR);
		 * 
		 * 	Prueba:    el puntaje seberia ser nulo si no puede llevar mascota y no nulo si puede llevar
		 * 
		 * 				combiMascota: ("CC123CC", 8 plazas, lleva mascota)
		 * 				>> Esperado: NOT null
		 * 				>> Resultado: Correcto
		 * 	
		 * 				combiSinMascota: ("CC123CC", 8 plazas, NO lleva mascota)
		 * 				>> Esperado: null
		 * 				>> Resultado: Correcto
		 * 
		 * */
		
		Pedido pedidoMascota = new Pedido(cliente, 1, true, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		
		assertNotNull(combiMascota.getPuntajePedido(pedidoMascota));
		assertNull(combiSinMascota.getPuntajePedido(pedidoMascota));
		
	}
	
	
	
	@Test
	public void test_getPuntajePedido_sinBaul_incremento() {
		/*
		 * 	Escenario: Pedido sin mascota ni baul
		 * 
		 * 				cliente: ("juanzerPerez", "123456789", "Juan Perez");
		 * 				pedidoSinExtras: (cliente, 1, SIN mascota, SIN baul, 10, Constantes.ZONA_SIN_ASFALTAR);
		 * 
		 * 	Prueba:    el puntaje para ambas combis debe ser el mismo
		 * 
		 * 				combiMascota: ("CC123CC", 8 plazas, lleva mascota)
		 * 				>> Esperado: 20
		 * 				>> Resultado: Correcto
		 * 	
		 * 				combiSinMascota: ("CC123CC", 8 plazas, NO lleva mascota)
		 * 				>> Esperado: 20
		 * 				>> Resultado: Correcto
		 * 
		 * */
		
		Pedido pedidoSinExtras = new Pedido(cliente, 2, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		
		double puntajeEsperado = 10 * pedidoSinExtras.getCantidadPasajeros();
		
		assertEquals(puntajeEsperado, combiMascota.getPuntajePedido(pedidoSinExtras), 1e-5);
		assertEquals(puntajeEsperado, combiSinMascota.getPuntajePedido(pedidoSinExtras), 1e-5);
		
	}
	
	@Test
	public void test_getPuntajePedido_conBaul_incremento() {
		/*
		 * 	Escenario: Pedido sin mascota pero con baul
		 * 
		 * 				cliente: ("juanzerPerez", "123456789", "Juan Perez");
		 * 				pedidoSinExtras: (cliente, 2 pasajeros, SIN mascota, CON baul, 10, Constantes.ZONA_SIN_ASFALTAR);
		 * 
		 * 	Prueba:    el puntaje para ambas combis debe ser el mismo
		 * 
		 * 				combiMascota: ("CC123CC", 8 plazas, lleva mascota)
		 * 				>> Esperado: 120
		 * 				>> Resultado: INCORRECTO -> 20
		 * 	
		 * 				combiSinMascota: ("CC123CC", 8 plazas, NO lleva mascota)
		 * 				>> Esperado: 120
		 * 				>> Resultado: INCORRECTO -> 20
		 * 
		 * */
		
		Pedido pedidoSinExtras = new Pedido(cliente, 2, false, true, 10, Constantes.ZONA_SIN_ASFALTAR);
		
		double puntajeEsperado = 10 * pedidoSinExtras.getCantidadPasajeros() + 100;
		
		assertEquals(puntajeEsperado, combiMascota.getPuntajePedido(pedidoSinExtras), 1e-5);
		assertEquals(puntajeEsperado, combiSinMascota.getPuntajePedido(pedidoSinExtras), 1e-5);
		
	}
	
	
	
	
}


























