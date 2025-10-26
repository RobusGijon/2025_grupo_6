package test_modeloNegocios;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import excepciones.ClienteSinViajePendienteException;
import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import modeloDatos.Administrador;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;

import org.junit.Before;
import org.junit.Test;
public class Test_Empresa_Excepciones_pagarYFinalizarViaje {

	Empresa empresa;
	Cliente clienteConViaje;
	Cliente clienteSinViaje;
	Chofer choferDelViaje;
	Pedido pedidoDelViaje;
	Viaje viajeDelCliente;
	Moto moto;
	
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();

	}
	
    @After
    public void tearDown() {
        empresa.setClientes(new HashMap<>());
        empresa.setPedidos(new HashMap<>());
        empresa.setVehiculos(new HashMap<>());
        empresa.setVehiculosDesocupados(new java.util.ArrayList<>());
        empresa.setChoferes(new HashMap<>());
        empresa.setChoferesDesocupados(new java.util.ArrayList<>());
        empresa.setViajesIniciados(new HashMap<>());
        empresa.setViajesTerminados(new java.util.ArrayList<>());
        empresa.setUsuarioLogeado(null);
    }
	
	public void setEscenario() {
		/*
		 * 	Escenario: Un cliente con un viaje simulado (cliente ocupado, vehiculo ocupado, viaje iniciado, chofer ocupado)
		 * 	y otro cliente sin viaje. Ambos se loguean en el sistema cuando se hace la prueba respectiva
		 * 
		 * 
		 * 				clienteSinViaje  = ("nombreSinViaje", "passSinViaje", "nombreSinViaje");
		 * 				clienteConViaje = ("nombreUsuario1", "pass1", "nombreReal1");
		 *			 	>> Ambos agregados a la empresa
		 * 				
		 * 				pedidoDelViaje = (clienteConViaje, 1, true, true, 10, Constantes.ZONA_STANDARD);
		 * 				>> Agregado a la empresa
		 * 
		 * 				moto = new Moto("MM123MM");
		 * 				>> Agregado (ocupado)
		 * 
		 * 				choferDelViaje = Temporario("1122333", "Juan Perez");
		 * 				>> Agregado (ocupado)
		 * 				
		 * 				viajeIniciado = (pedidoDelViaje, choferDelViaje, moto);
		 * 		
		 * 
		 * */
		clienteSinViaje = new Cliente("nombreSinViaje", "passSinViaje", "nombreSinViaje");
		clienteConViaje = new Cliente("nombreUsuario1", "pass1", "nombreReal1");
		pedidoDelViaje = new Pedido(clienteConViaje, 1, true, true, 10, Constantes.ZONA_STANDARD);
		moto = new Moto("MM123MM");
		choferDelViaje = new ChoferTemporario("1122333", "Juan Perez");
		viajeDelCliente = new Viaje(pedidoDelViaje, choferDelViaje, moto);
		
		// Se agregan los viajes iniciados
		HashMap<Cliente, Viaje> mapaViajesIniciados = new HashMap<>();
		mapaViajesIniciados.put(clienteConViaje, viajeDelCliente);
		empresa.setViajesIniciados(mapaViajesIniciados);
		
		// Se agregan los vehiculos ocupados
		HashMap<String, Vehiculo> mapaVehiculos= new HashMap<>();
		mapaVehiculos.put(moto.getPatente(), moto);
		empresa.setViajesIniciados(mapaViajesIniciados);
		
		// Se agregan los choferes ocupados
		HashMap<String, Chofer> mapaChofer= new HashMap<>();
		mapaChofer.put(choferDelViaje.getDni(), choferDelViaje);
		empresa.setChoferes(mapaChofer);
		
		// Se agregan los clientes
		HashMap<String, Cliente> mapaCliente = new HashMap<>();
		mapaCliente.put(clienteConViaje.getNombreUsuario(), clienteConViaje);
		mapaCliente.put(clienteSinViaje.getNombreUsuario(), clienteSinViaje);
		
	}
	

	@Test
	public void test_clienteSinViaje_deberiaLanzarExcepcion() {
		/*
		 * 	Escenario: el de la prueba
		 * 
		 * 	Prueba:		Verificar que se lance la excepcion de un cliente (logueado) sin viaje
		 * 
		 * 			 	>> Esperado: Expecion
		 * 				>> Resultado: Correcto
		 * 			
		 * */
		
		this.setEscenario();
		empresa.setUsuarioLogeado(clienteSinViaje);
		
		try {
			empresa.pagarYFinalizarViaje(3);
			fail("El metodo no lanzo la excepcion esperada");
		} catch (ClienteSinViajePendienteException e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void test_clienteConViaje_NoDeberiaLanzarExcepcion() {
		/*
		 * 	Escenario: el de la prueba
		 * 
		 * 	Prueba: Verificar que se lance la excepcion de un cliente (logueado) sin viaje
		 * 
		 * 			>> Esperado: No deberia lanzar excepcion
		 * 			>> Resultado: Correcto
		 * 
		 * */
		
		this.setEscenario();
		empresa.setUsuarioLogeado(clienteConViaje);
		
		try {
			empresa.pagarYFinalizarViaje(5);
			assertTrue(true);
		} catch (ClienteSinViajePendienteException e) {
			fail("El metodo lanzo una excepcion incorrecta");
			
		}
		
	}
	
	@Test
	public void test_clienteConViaje_seDebeEstablecerReseniaYLiberarOcupados() {
		/*
		 * 	Escenario: el de la prueba
		 * 
		 * 	Prueba: Verificar que se agregue la calificacion correspondiente, y que se asignen
		 * 			al viaje, vehiculo, chofer los cupos libres y actualizar el historial
		 * 
		 * 			>> Esperado: calificacionViaje = 5
		 * 			>> Resultado: Correcto
		 * 
		 * 			>> Esperado: chofer en array de choferes desocupados
		 * 			>> Resultado: Correcto
		 * 
		 * 			>> Esperado: moto en array de vehiculos desocupados
		 * 			>> Resultado: Correcto
		 * 
		 * 			>> Esperado: historial de viajes del chofer actualizado (debe tener el viajeDelCliente)
		 * 			>> Resultado: Correcto
		 * 
		 * 			>> Esperado: historial de viajes del cliente actualizado (debe tener el viajeDelCliente)
		 * 			>> Resultado: Correcto
		 * 
		 * */
		
		int calificacionViaje = 5;
		
		this.setEscenario();
		empresa.setUsuarioLogeado(clienteConViaje);
		
		try {
			empresa.pagarYFinalizarViaje(calificacionViaje);
			ArrayList<Viaje> viajesTerminados = empresa.getViajesTerminados();
			assertTrue(viajesTerminados.get(0).getCalificacion() == calificacionViaje);
			assertTrue(empresa.getChoferesDesocupados().contains(choferDelViaje));
			assertTrue(empresa.getVehiculosDesocupados().contains(moto));
			assertTrue(empresa.getHistorialViajeChofer(choferDelViaje).get(0) == viajeDelCliente);
			assertTrue(empresa.getHistorialViajeCliente(clienteConViaje).get(0) == viajeDelCliente);
			
		} catch (ClienteSinViajePendienteException e) {
			fail("El metodo lanzo una excepcion incorrecta");
			
		}
		
	}

}
