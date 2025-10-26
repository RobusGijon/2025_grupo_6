package test_modeloNegocios;

import static org.junit.Assert.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import excepciones.PasswordErroneaException;
import excepciones.SinViajesException;
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

public class Test_Empresa_Excepciones_calificacionChofer {

	private Empresa empresa;
	
	private Chofer choferConVariosViajes;
	private double calificacionChoferVariosViajes;
	private Chofer choferConUnSoloViaje;
	private double calificacionChoferUnViaje;
	private Chofer choferSinViajes;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
		choferConVariosViajes = new ChoferPermanente("11222333", "Juan Carlos Jose", 2000, 3);
		choferConUnSoloViaje = new ChoferTemporario("44555666", "Jose Sanchez");
		choferSinViajes = new ChoferTemporario("22111666", "Felix Andres Aranceli");
	}

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
	
	private void setViajesTerminados() {
		
		/*
		 * 
		 * 	Escenario:
		 * 				Cliente = ("nombreUsuario", "pass", "nombreReal")
		 * 				Moto = ("MM123MM")
		 * 				Pedido = (cliente, 1 pasajero, sin mascota, sin baul, 10 km , Constantes.ZONA_STANDARD);
		 * 
		 * 
		 * 				ViajeChoferP1 = (pedido, choferPermanente, moto)
		 * 				>> Calificacion: 5			
		 * 	
		 * 				ViajeChoferP2 = (pedido, choferPermanente, moto)
		 * 				>> Calificacion: 4
		 * 
		 * 				ViajeChoferP3 = (pedido, choferPermanente, moto)
		 * 				>> Calificacion: 3
		 * 
		 * 				Promedio calificacion choferPermanente = 4
		 * 
		 * 
		 * 				ViajeChoferT1 = (pedido, choferTemporario, moto);
		 * 				>> Calificacion: 1
		 * 				
		 * 				Promedio calificacion choferTemporario = 1
		 * 
		 * 				Todos los viajes se agregan a la empresa como viajes terminados
		 * 
		 * */
		
		
		boolean mascota = false;
		boolean baul = false;
		Cliente cliente = new Cliente("nombreUsuario", "pass", "nombreReal");
		Moto moto = new Moto("MM123MM");
		Pedido pedido = new Pedido(cliente, 1, mascota, baul, 10, Constantes.ZONA_STANDARD);
		
		Viaje viajeChoferP1 = new Viaje(pedido, choferConVariosViajes, moto);
		viajeChoferP1.finalizarViaje(5);
		
		Viaje viajeChoferP2 = new Viaje(pedido, choferConVariosViajes, moto);
		viajeChoferP2.finalizarViaje(4);
		
		Viaje viajeChoferP3 = new Viaje(pedido, choferConVariosViajes, moto);
		viajeChoferP3.finalizarViaje(3);
		
		calificacionChoferVariosViajes = (3 + 4 + 5)/3;
		

		Viaje viajeChoferT1 = new Viaje(pedido, choferConUnSoloViaje, moto);
		viajeChoferT1.finalizarViaje(1);
		
		calificacionChoferUnViaje = 1.0;
		
		
		ArrayList<Viaje> viajesTerminados = new ArrayList<>();
		viajesTerminados.add(viajeChoferP1);
		viajesTerminados.add(viajeChoferP2);
		viajesTerminados.add(viajeChoferP3);
		viajesTerminados.add(viajeChoferT1);
		
		empresa.setViajesTerminados(viajesTerminados);
		
	}
	
	
	
	@Test
	public void test_calificacionChofer_choferSinViajesExcepcion() {
		/*
		 * 	Escenario:
		 * 				ChoferSinViajes = ChoferTemporario("22111666", "Felix Andres Aranceli");
		 * 
		 * 	Prueba: 	el metodo deberia lanzar la excepcion choferSinViajes
		 * 				>> Esperado: Excepcion: SinViajesException
		 * 				>> Resultado: INCORRECTO -> calificacion = 0.0
		 * 
		 * */
		

		try {
			double calificacion = empresa.calificacionDeChofer(choferSinViajes);
			fail("El metodo no lanzo la excepcion esperada, el chofer tuvo una calificacion de: " + calificacion);
		} catch (SinViajesException e) {
			assertTrue(true);
		}
	}
	
	
	@Test
	public void test_calificacionChofer_ChoferConVariosViajes() {
		/*
		 * 	Escenario:
		 * 				* Se añaden los viajes genericos a la empresa
		 * 				choferConVariosViajes = ("11222333", "Juan Carlos Jose", 2000, 3);
		 * 
		 * 	Prueba: 	El metodo deberia devolver el promedio calculado de un chofer con varios viajes
		 * 				>> Esperado: calificacion = (3 + 4 + 5) / 3 = 4
		 * 				>> Resultado: INCORRECTO -> calificacion = 1.0
		 * 
		 * */
		
		
		this.setViajesTerminados();
		
		try {
			double calificacion = empresa.calificacionDeChofer(choferConVariosViajes);
			assertTrue("El metodo devolvio una calificacion mal calculada: calificacion = " + calificacion
								   + ". Se esperaba una calificacion de: " + calificacionChoferVariosViajes,  
															calificacion == calificacionChoferVariosViajes);
		} catch (SinViajesException e) {
			fail("El metodo lanzo una excepcion incorrecta (SinViajesException)");
		}
	}
	
	@Test
	public void test_calificacionChofer_ChoferUnViaje() {
		/*
		 * 	Escenario:
		 * 				* Se añaden los viajes genericos a la empresa
		 * 				ChoferConUnSoloViaje = ("44555666", "Jose Sanchez");
		 * 
		 * 	Prueba: 	el metodo deberia lanzar la excepcion choferSinViajes
		 * 				>> Esperado: calificacion = 1.0
		 * 				>> Resultado: Correcto
		 * 
		 * */
		
		
		this.setViajesTerminados();
		
		try {
			double calificacion = empresa.calificacionDeChofer(choferConUnSoloViaje);
			assertTrue("El metodo devolvio una calificacion mal calculada: calificacion = " + calificacion
										+ ". Se esperaba una calificacion de: " + calificacionChoferUnViaje,  
																 calificacion == calificacionChoferUnViaje);
		} catch (SinViajesException e) {
			fail("El metodo lanzo una excepcion incorrecta (SinViajesException)");
		}
	}
	
	
	
	
	
	
	
	
	
	

}
