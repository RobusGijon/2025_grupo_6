package test_modeloNegocios;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;

public class Test_Empresa_Otros {

	private Empresa empresa;
	private Auto autoEmpresa;
	private Cliente clienteEmpresa;
	private Pedido pedidoEmpresa;

	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();	
		autoEmpresa = new Auto("AA123AA", 3, true);
		clienteEmpresa = new Cliente("nombreUsuario", "pass", "nombreReal");
		pedidoEmpresa = new Pedido(clienteEmpresa, 1, true, true, 10, Constantes.ZONA_STANDARD);
	}

	/*
	 * 	PARAMETROS LOCALES (para referencia):
	 *
	 * 		  autoEmpresa: ("AA123AA", 3, true)
	 * 	   clienteEmpresa: ("nombreUsuario", "pass", "nombreReal")
	 * 		pedidoEmpresa: (clienteEmpresa, 1, con mascota, con baul, 10km, zona estándar)
	 * 			>> pedidoEmpresa se supone que es un pedido que puede cumplir cualquier vehiculo
	 *
	 * 	Estos se usarán o no según cada prueba.
	 */

	@After
	public void tearDown() {
		empresa.setClientes(new HashMap<>());
		empresa.setPedidos(new HashMap<>());
		empresa.setVehiculos(new HashMap<>());
		empresa.setVehiculosDesocupados(new ArrayList<>());
		empresa.setChoferes(new HashMap<>());
		empresa.setChoferesDesocupados(new ArrayList<>());
		empresa.setViajesIniciados(new HashMap<>());
		empresa.setViajesTerminados(new ArrayList<>());
		empresa.setUsuarioLogeado(null);
	}

	
	private void agregarVehiculoASistema(Vehiculo v) {
		try {
			empresa.agregarVehiculo(v);
		} catch (VehiculoRepetidoException e) {
			fail("No se pudo preparar el escenario: vehiculo repetido (" + v.getPatente() + ")");
		}
	}

	// ======================
	// Tests
	// ======================

	@Test
	public void test_vehiculoOrdenadoPorPedido() {
		/*
		 *  Escenario:
		 *  	Auto   	= ( "AA123AA", 4 asientos, puede llevar mascota)
		 *  	Combi  	= ( "CC531CC", 10 asientos, puede llevar mascota)
		 *  	Moto   	= ( "BB412BB" )  // no puede llevar el pedido
		 * 		Cliente = clienteEmpresa
		 * 		Pedido 	= ( Cliente, 3 pasajeros, CON mascota, CON baul, 10km, zona estándar)
		 *
		 * 	Esperado:
		 * 		Array = [Combi, Auto]  // La Moto no debe aparecer
		 * 		>> Resultado: Correcto
		 */

		boolean mascota = true;
		boolean baul = true;

		Pedido pedido = new Pedido(clienteEmpresa, 3, mascota, baul, 10, Constantes.ZONA_STANDARD);

		Auto auto = new Auto("AA123AA", 4, mascota);
		Moto moto = new Moto("BB412BB");
		Combi combi = new Combi("CC531CC", 10, mascota);

		agregarVehiculoASistema(combi);
		agregarVehiculoASistema(auto);
		agregarVehiculoASistema(moto);

		ArrayList<Vehiculo> arrayEmpresa = empresa.vehiculosOrdenadosPorPedido(pedido);

		ArrayList<Vehiculo> arrayEsperado = new ArrayList<>();
		arrayEsperado.add(combi);
		arrayEsperado.add(auto);
		

		assertEquals("El método no devolvió los vehículos ordenados correctamente", arrayEsperado, arrayEmpresa);
	}

	@Test
	public void test_validarPedido() {
		/*
		 *  Escenario:
		 *  	   Auto: ("AA123AA", 4 asientos, NO puede llevar mascota)
		 *  		>> Agregado en la prueba 1
		 *  	  Combi: ("CC531CC", 8 asientos, puede llevar mascota)
		 * 			>> Agreado en la prueba 2 y 3
		 * 
		 * 		Cliente: El de la empresa
		 * 
		 * 	Prueba 1: Test para comprobar que se verifique la condicion de mascota
		 * 		pedidoEmpresa (Cliente, 1 pasajeros, CON mascota, CON baul, 10km, zona estándar)
		 * 		Esperado: Falso (lo puede cubrir la Combi)
		 * 		>> Resultado: Correcto
		 *
		 * 	Prueba 2: Test para comprobar que verifique un pedido que cumple
		 * 		Pedido: pedidoEmpresa
		 * 		Esperado: Verdadero
		 * 		>> Resultado: Correcto
		 * 	
		 * 	Prueba 3: Test para comprobar que se verifique la condicion de capacidad  
		 * 		Pedido (Cliente, 11 pasajeros, CON mascota, CON baul, 10km, zona estándar)
		 * 		Esperado: FALSO (exceso de pasajeros respecto a la Combi)
		 * 		>> Resultado: INCORRECTO -> validó el pedido no valido.
		 * 
		 */

		boolean mascota = true;
		boolean baul = true;
		
		Auto auto = new Auto("AA123AA", 4, !mascota); 
		Combi combi = new Combi("CC531CC", 8, mascota);

		agregarVehiculoASistema(auto);
		assertFalse("El método no validó un pedido apto (Prueba 1)", empresa.validarPedido(pedidoEmpresa));
		
		agregarVehiculoASistema(combi);
		assertTrue("El metodo no valido un pedido apto (Prueba 2)", empresa.validarPedido(pedidoEmpresa));

		Pedido pedidoNoApto = new Pedido(clienteEmpresa, 10, mascota, baul, 10, Constantes.ZONA_STANDARD);
		assertFalse("El método validó un pedido no apto (Prueba 3)", empresa.validarPedido(pedidoNoApto));
		
	}
		
	
	@Test
	public void test_getPedidoDeCliente() {
		/*
		 * 	Escenario:	
		 * 				clienteEmpresa = ("nombreUsuario", "pass", "nombreReal")
		 * 				pedidoEmpresa  = (clienteEmpresa, 1, con mascota, con baul, 10km, zona estándar)
		 * 					>> pedido agregado a la empresa					
		 * 
		 * 				clienteSinPedido = ("clienteSinPedido", "passSinPedido", "nombreSinPedido")
		 * 
		 * 	Prueba 1: el pedido de cliente empresa deberia ser el pedido de la empresa
		 * 				
		 * 				>>  Esperado: pedidoEmpresa
		 * 				>> Resultado: Correcto
		 * 
		 * 	Prueba 2: el pedido del cliente sin pedido deberia ser nulo
		 * 	
		 * 				>>  Esperado: null
		 * 				>> Resultado: Correcto
		 * 			
		 * 	
		 * */
		
		HashMap<Cliente, Pedido> mapaPedido = new HashMap<>();
		mapaPedido.put(clienteEmpresa, pedidoEmpresa);
		empresa.setPedidos(mapaPedido);
		
		Cliente clienteSinPedido = new Cliente("clienteSinPedido", "passSinPedido", "nombreSinPedido");
		
		Pedido pedidoObtenido = empresa.getPedidoDeCliente(clienteEmpresa);
		assertNotNull("El pedido del cliente con pedido es nulo", pedidoObtenido);
		assertEquals("El pedido del cliente con pedido no es el mismo", pedidoObtenido, pedidoEmpresa);
		
		pedidoObtenido = empresa.getPedidoDeCliente(clienteSinPedido);
		assertNull("El pedido del cliente sin pedido es distinto de null", pedidoObtenido);
		
		
	}
	
	
	@Test
	public void test_getTotalSalarios_SinChoferes_DeberiaSer0 () {
		/*
		 * 	Escenario:	
		 * 				empresa sin choferes
		 * 	
		 * 	Prueba: Verificar que devuelva el resultado correcto sin choferes
		 * 
		 * 			>>  Esperado: 0.0
		 * 			>> Resultado: 
		 * */
		
		double total = empresa.getTotalSalarios();
		
		assertTrue(total == 0.0);
	}
	
	@Test
	public void test_getTotalSalarios_ConChoferes() {
		/*
		 * 	Escenario:	
		 * 				ChoferTemporal 1: ("11222333", "Juan Perez")
		 * 				ChoferTemporal 2: ("41209412", "Adrian Martinez");
		 * 				
		 * 				ChoferPermanente 1: ("32190312", "Perez Juan", 2000, 5);
		 * 				ChoferPermanente 2: ("23190117", "Perez Juan", 2000, 2);
		 * 
		 * 				>> Los 4 se agregan a la empresa
		 * 	
		 * 	Prueba: Verificar que devuelva el total correcto de los salarios
		 * 
		 * 			>>  Esperado: 3005700.0
		 * 			>> 	Resultado: Correcto
		 * */
		
		
		
		ChoferTemporario choferT1 = new ChoferTemporario("11222333", "Juan Perez");
		ChoferTemporario choferT2 = new ChoferTemporario("41209412", "Adrian Martinez");
		
		ChoferPermanente choferPer1 = new ChoferPermanente("32190312", "Perez Juan", 2000, 5);
		ChoferPermanente choferPer2 = new ChoferPermanente("23190117", "Perez Juan", 2000, 2);
		
		
		double totalEsperado = choferT1.getSueldoNeto() + choferT2.getSueldoNeto()
								+ choferPer1.getSueldoNeto() + choferPer2.getSueldoNeto();
		
		HashMap<String, Chofer> mapaChofer = new HashMap<>();
		
		mapaChofer.put(choferT1.getDni(), choferT1);
		mapaChofer.put(choferT2.getDni(), choferT2);
		mapaChofer.put(choferPer1.getDni(), choferPer1);
		mapaChofer.put(choferPer2.getDni(), choferPer2);
		
		empresa.setChoferes(mapaChofer);
		
		double totalObtenido = empresa.getTotalSalarios();
		
		assertTrue("No se obtuvieron los salarios esperados: totalObtenido: " + totalObtenido
														+ " totalEsperado: " + totalEsperado,
															totalObtenido == totalEsperado);
		
	}
	
	@Test
	public void test_getViajeDelCliente_clienteSinViaje() {
		/*
		 * 	Escenario: 
		 * 				clienteSinViajes: ("nombreUsuario", "pass", "nombreReal");
		 * 
		 * 
		 * 	Prueba:		Verificar que el metodo devuelva null
		 * 			
		 * 				>> Esperado: null
		 * 				>> Resultado: Correcto
		 * */
		
		Cliente cliente = new Cliente("nombreUsuario", "pass", "nombreReal");
		HashMap<String, Cliente> mapaCliente = new HashMap<>();
		mapaCliente.put(cliente.getNombreUsuario(), cliente);
		empresa.setClientes(mapaCliente);
		empresa.setUsuarioLogeado(cliente);
		
		Viaje viaje = empresa.getViajeDeCliente(cliente);
		
		assertNull(viaje);
		
		
	}
	
	@Test
	public void test_getViajeDelCliente_clienteConViaje() {
		/*
		 * 	Escenario: 
		 * 				clientePrueba: ("nombreUsuario", "pass", "nombreReal");
		 * 				  viajePrueba: (pedidoPrueba, chofer, auto)
		 * 
		 * 				cliente2: 	   ("nombreUsuario1", "pass1", "nombreReal1");
		 * 				  viaje2: 	   (pedido2, chofer2, auto2);
		 * 
		 * 
		 * 	Prueba: 	Verificar que el metodo devuelva el viaje correcto de los que estan
		 * 
		 * 				>>  Esperado: viajePrueba
		 * 				>> Resultado: Correcto
		 * 			
		 * 
		 * */
		
		Cliente clientePrueba = new Cliente("nombreUsuario", "pass", "nombreReal");

		Pedido pedido = new Pedido(clientePrueba, 2, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		Chofer chofer = new ChoferTemporario("11222333", "Juan Carlos");
		Auto auto = new Auto("AA123AA", 4, true);
		Viaje viaje = new Viaje(pedido, chofer, auto);
		
		Cliente cliente2 = new Cliente("nombreUsuario1", "pass1", "nombreReal1");
		Pedido pedido2 = new Pedido(cliente2, 6, true, false, 23, Constantes.ZONA_SIN_ASFALTAR);
		Chofer chofer2 = new ChoferPermanente("11222333", "Carlos Miguel", 2000, 2);
		Combi auto2 = new Combi("AA123AA", 9, true);
		Viaje viaje2 = new Viaje(pedido2, chofer2, auto2);
		
		HashMap<Cliente, Viaje> mapaViajesIniciados = new HashMap<>();
		mapaViajesIniciados.put(clientePrueba, viaje);
		mapaViajesIniciados.put(cliente2, viaje2);
		

		empresa.setViajesIniciados(mapaViajesIniciados);
		
		HashMap<String, Cliente> mapaCliente = new HashMap<>();
		mapaCliente.put(clientePrueba.getNombreUsuario(), clientePrueba);
		mapaCliente.put(cliente2.getNombreUsuario(), cliente2);
		empresa.setClientes(mapaCliente);
		empresa.setUsuarioLogeado(clientePrueba);
		
		Viaje viajeObtenido = empresa.getViajeDeCliente(clientePrueba);
		
		assertEquals(viajeObtenido, viaje);
		
		
	}
	
	

	
	
}
