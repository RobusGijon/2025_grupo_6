package test_modeloNegocios;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConViajePendienteException;
import excepciones.PedidoInexistenteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import junit.framework.Assert;
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


public class Test_Empresa_Excepciones_agregarViaje {

	private Empresa empresa;
	private Auto autoEmpresa;
	private Cliente clienteEmpresa;
	private Pedido pedidoEmpresa;
	private ChoferTemporario choferEmpresa;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
		
		autoEmpresa = new Auto("AA123AA", 3, true);
		clienteEmpresa = new Cliente("nombreUsuario", "pass", "nombreReal");
		pedidoEmpresa = new Pedido(clienteEmpresa, 1, true, true, 10, Constantes.ZONA_STANDARD);
		choferEmpresa = new ChoferTemporario("11222333", "Chofer temporario");
	}
	
	/*
	 * 	PARAMETROS LOCALES:
	 * 
	 * 		   autoEmpresa:  	("AA123AA", 3, true)
	 * 		clienteEmpresa: 	("nombreUsuario", "pass", "nombreReal")
	 * 		 pedidoEmpresa:		(clienteEmpresa, 1, true, true, 10, Constantes.ZONA_STANDARD)
	 * 		 choferEmpresa: 	("11222333", "Chofer temporario")
	 * 	
	 * 	Estos se van a agregar o no a la empresa dependiendo de si lo amerita la prueba o no.
	 * 	Si en los test locales se indica que se usa "el de la empresa", significa que este se 
	 * 	añadio a la empresa. 
	 * 
	 * */
	
	
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
	

   private void agregarChoferGenerico() {
	   ArrayList<Chofer> choferesDisponibles = new ArrayList<>();
	   choferesDisponibles.add(choferEmpresa);
	   empresa.setChoferesDesocupados(choferesDisponibles);
   }
	
    private void agregarVehiculosGenericos() {
    	ArrayList<Vehiculo> vehiculosDisponibles = new ArrayList<>();
    	vehiculosDisponibles.add(autoEmpresa);
    	empresa.setVehiculosDesocupados(vehiculosDisponibles);    	
    }
    
    private void agregarPedidosGenericos() {
    	HashMap<Cliente, Pedido> mapaPedidos = new HashMap<>();
    	mapaPedidos.put(clienteEmpresa, pedidoEmpresa);
    	empresa.setPedidos(mapaPedidos);
    }
    
    
	
	@Test
	public void test_agregarViajeSinPedido_deberiaTirarExpecionCorrecta() {
		/*
		 * 	Test para comprobar que lance la excepcion: PedidoInexistenteException
		 *  Escenario:	
		 *  			Pedido Prueba 1: pedido1 = Pedido(clienteEmpresa, 1, true, true, 1, Constantes.ZONA_SIN_ASFALTAR)
		 *  			Pedido Prueba 2: pedido2 = (clienteEmpresa, 4 pasajeros, si mascota, si baul, 10km, zona estandar)
		 *  				>> No estan agregados a la empresa			
		 *  
		 *  			Cliente: El de la empresa
		 *  
		 *  			Chofer: El de la empresa
		 *  
		 *  			Vehiculo: El de la empresa
		 *  
		 * 	Prueba1:
		 * 				crearViaje(pedido1, choferEmpresa, autoEmpresa)
		 * 				Esperado: Excepcion PedidoInexistenteException 
		 * 				>> Resultado: Correcto
		 * 
		 * 	Prueba2: 
		 * 				crearViaje(pedido2, choferEmpresa, autoEmpresa)
		 * 				Esperado: Excepcion PedidoInexistenteException 
		 * 				>> Resultado: Correcto
		 * */
		
		
		Pedido pedido1 = new Pedido(clienteEmpresa, 1, true, true, 1, Constantes.ZONA_SIN_ASFALTAR);
		Pedido pedido2 = new Pedido(clienteEmpresa, 4, true, true, 10, Constantes.ZONA_STANDARD);
		
		this.agregarVehiculosGenericos();
		this.agregarChoferGenerico();
		
		try {
			empresa.crearViaje(pedido1, choferEmpresa, autoEmpresa);
			fail("El metodo NO lanzo excepcion");
		} catch (ChoferNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (ChoferNoDisponibleException)");
		} catch (VehiculoNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoDisponibleException)");
		} catch (VehiculoNoValidoException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoValidoException)");
		} catch (ClienteConViajePendienteException e) {
			fail("El metodo lanzo una expecion erronea: (ClienteConViajePendienteException)");
		} catch (PedidoInexistenteException e) {
			assertTrue(true);
		}
		
		try {
			empresa.crearViaje(pedido2, choferEmpresa, autoEmpresa);
			fail("El metodo NO lanzo excepcion");
		} catch (ChoferNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (ChoferNoDisponibleException)");
		} catch (VehiculoNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoDisponibleException)");
		} catch (VehiculoNoValidoException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoValidoException)");
		} catch (ClienteConViajePendienteException e) {
			fail("El metodo lanzo una expecion erronea: (ClienteConViajePendienteException)");
		} catch (PedidoInexistenteException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void test_agregarViajeChoferNoDisponible_deberiaTirarExpecionCorrecta() {
		/*
		 *  Escenario:	
		 *  			ChoferPrueba 1: choferPemanente = ("31419314", "Juan Perez", 2000, 3)
		 *  			ChoferPrueba 2:	choferTemporario = ("31232121", "Jose Perez")
		 *					>> No estan agregados a la empresa  			
		 *  
		 *  			Vehiculo: El de la empresa
		 *
		 *  			Cliente: El de la empresa
		 *  	
		 *  			Pedido: El de la empresa
		 *  
		 * 	Prueba1:
		 * 				crearViaje(pedidoEmpresa, choferTemporario, autoEmpresa)
		 * 				Esperado: Excepcion ChoferNoDisponibleException
		 * 				>> Resultado: Correcto
		 * 				
		 * 	Prueba2: 
		 * 				crearViaje(pedidoEmpresa, choferPermanente, autoEmpresa);
		 * 				Esperado: Excepcion ChoferNoDisponibleException
		 * 				>> Resultado: Correcto
		 * */
		
		ChoferPermanente choferPermanente = new ChoferPermanente("31419314", "Juan Perez", 2000, 3);
		ChoferTemporario choferTemporario = new ChoferTemporario("31232121", "Jose Perez");

		this.agregarPedidosGenericos();
		this.agregarVehiculosGenericos();
		
		try {
			empresa.crearViaje(pedidoEmpresa, choferTemporario, autoEmpresa);
			fail("El metodo NO lanzo excepcion");
		} catch (PedidoInexistenteException e) {
			fail("El metodo lanzo una expecion erronea: (PedidoInexistenteException)");
		} catch (VehiculoNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoDisponibleException)");
		} catch (VehiculoNoValidoException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoValidoException)");
		} catch (ClienteConViajePendienteException e) {
			fail("El metodo lanzo una expecion erronea: (ClienteConViajePendienteException)");
		} catch (ChoferNoDisponibleException e) {
			assertTrue(true);
		}
		
		try {
			empresa.crearViaje(pedidoEmpresa, choferPermanente, autoEmpresa);
			fail("El metodo NO lanzo excepcion");
		} catch (PedidoInexistenteException e) {
			fail("El metodo lanzo una expecion erronea: (PedidoInexistenteException)");
		} catch (VehiculoNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoDisponibleException)");
		} catch (VehiculoNoValidoException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoValidoException)");
		} catch (ClienteConViajePendienteException e) {
			fail("El metodo lanzo una expecion erronea: (ClienteConViajePendienteException)");
		} catch (ChoferNoDisponibleException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void test_agregarViajeVehiculoNoDisponible_deberiaTirarExpecionCorrecta() {
		/*
		 *  Escenario:	
		 *  			Vehiculo prueba 1 : auto = ("ZZ999ZZ", 3, true)
		 				Vehiculo prueba 2 : combi = ("MM888MM", 9, true)
		 *  				>> No estan agregados a la empresa
		 *  
		 *  			Cliente: El de la empresa
		 *  	
		 *  			Pedido: El de la empresa
		 *  
		 *  			Chofer: El de la empresa
		 *  
		 * 	Prueba1:
		 * 				crearViaje(pedidoEmpresa, choferEmpresa, auto)
		 * 				Esperado: Excepcion VehiculoNoDisponibleException
		 * 				>> Resultado: Correcto
		 * 				
		 * 	Prueba2: 
		 * 				crearViaje(pedidoEmpresa, choferEmpresa, combi);
		 * 				Esperado: Excepcion VehiculoNoDisponibleException
		 * 				>> Resultado: Correcto
		 * */
		
		Auto auto = new Auto("ZZ999ZZ", 3, true);
		Combi combi = new Combi("MM888MM", 9, true);
		
		
		this.agregarChoferGenerico();
		this.agregarPedidosGenericos();
		this.agregarVehiculosGenericos();
		
		try {
			empresa.crearViaje(pedidoEmpresa, choferEmpresa, auto);
			fail("El metodo NO lanzo excepcion");
		} catch (PedidoInexistenteException e) {
			fail("El metodo lanzo una expecion erronea: (PedidoInexistenteException)");
		} catch (VehiculoNoValidoException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoValidoException)");
		} catch (ClienteConViajePendienteException e) {
			fail("El metodo lanzo una expecion erronea: (ClienteConViajePendienteException)");
		} catch (ChoferNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (ChoferNoDisponibleException)");
		} catch (VehiculoNoDisponibleException e) {
			assertTrue(true);
		}
		
		try {
			empresa.crearViaje(pedidoEmpresa, choferEmpresa, combi);
			fail("El metodo NO lanzo excepcion");
		} catch (PedidoInexistenteException e) {
			fail("El metodo lanzo una expecion erronea: (PedidoInexistenteException)");
		} catch (VehiculoNoValidoException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoValidoException)");
		} catch (ClienteConViajePendienteException e) {
			fail("El metodo lanzo una expecion erronea: (ClienteConViajePendienteException)");
		} catch (ChoferNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (ChoferNoDisponibleException)");
		} catch (VehiculoNoDisponibleException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void test_agregarViajeVehiculoNoValido_deberiaTirarExpecionCorrecta() {
		/*
		 *  Escenario:	
		 *  			Vehiculo prueba 1 : autoQueNoCumple  = ("ZZ999ZZ", 3, false)
		 				Vehiculo prueba 2 : combiQueNoCumple = ("MM888MM", 9, false)
		 *  				>> Estan agregados a la empresa
		 *  
		 *  			Cliente: El de la empresa
		 *  	
		 *  			Pedido: El de la empresa (necesita llevar mascota y usar baul)
		 *  
		 *  			Chofer: El de la empresa
		 *  
		 * 	Prueba1:
		 * 				crearViaje(pedidoEmpresa, choferEmpresa, autoQueNoCumple)
		 * 				Esperado: Excepcion VehiculoNoValidoException
		 * 				>> Resultado: Correcto
		 * 				
		 * 	Prueba2: 
		 * 				crearViaje(pedidoEmpresa, choferPermanente, combiQueNoCumple);
		 * 				Esperado: Excepcion VehiculoNoValidoException
		 * 				>> Resultado: Correcto
		 * */
		
		this.agregarChoferGenerico();
		this.agregarPedidosGenericos();

		Auto autoQueNoCumple = new Auto("ZZ999ZZ", 3, false);
		Combi combiQueNoCumple = new Combi("MM888MM", 9, false);
		
		// Agregamos los vehiculos a la empresa
		ArrayList<Vehiculo> vehiculosDisponibles = new ArrayList<>();
    	vehiculosDisponibles.add(autoQueNoCumple);
    	vehiculosDisponibles.add(combiQueNoCumple);
    	empresa.setVehiculosDesocupados(vehiculosDisponibles);    
    	
		try {
			empresa.crearViaje(pedidoEmpresa, choferEmpresa, autoQueNoCumple);
			fail("El metodo NO lanzo excepcion");
		} catch (PedidoInexistenteException e) {
			fail("El metodo lanzo una expecion erronea: (PedidoInexistenteException)");
		} catch (VehiculoNoValidoException e) {
			assertTrue(true);
		} catch (ClienteConViajePendienteException e) {
			fail("El metodo lanzo una expecion erronea: (ClienteConViajePendienteException)");
		} catch (ChoferNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (ChoferNoDisponibleException)");
		} catch (VehiculoNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoDisponibleException)");
		}
		
		try {
			empresa.crearViaje(pedidoEmpresa, choferEmpresa, combiQueNoCumple);
			fail("El metodo NO lanzo excepcion");
		} catch (PedidoInexistenteException e) {
			fail("El metodo lanzo una expecion erronea: (PedidoInexistenteException)");
		} catch (VehiculoNoValidoException e) {
			assertTrue(true);
		} catch (ClienteConViajePendienteException e) {
			fail("El metodo lanzo una expecion erronea: (ClienteConViajePendienteException)");
		} catch (ChoferNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (ChoferNoDisponibleException)");
		} catch (VehiculoNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoDisponibleException)");
		}
	}
	
	@Test
	public void test_agregarViajeClienteConViaje_deberiaTirarExpecionCorrecta() {
		/*
		 *  Escenario:	
		 *  			Cliente Prueba: clienteEmpresa
		 *  			
		 *  			Pedido Prueba: pedidoEmpresa // El cliente trata de hacer el mismo pedido
		 *  
		 *  			Auto: El de la empresa
		 *  	
		 *  			Pedido: El de la empresa 
		 *  
		 *  			Chofer: El de la empresa
		 *  
		 *  			Viaje: (pedidoEmpresa, choferEmpresa, autoEmpresa)
		 *  				>> Esta agregado a la empresa
		 *  
		 * 	Prueba:
		 * 				crearViaje(pedidoEmpresa, choferEmpresa, autoEmpresa)
		 * 				Esperado: Excepcion ClienteConViajePendienteException
		 * 				>> Resultado: INCORRECTO -> El metodo no lanzo excepcion
		 * 				
		 * */
		
		this.agregarVehiculosGenericos();
		this.agregarChoferGenerico();
		this.agregarPedidosGenericos();
		
		// Se agrega el viaje del cliente QUE NO finalizo
		Viaje viajeEmpresa = new Viaje(pedidoEmpresa, choferEmpresa, autoEmpresa);
		ArrayList<Viaje> viajesIniciados = new ArrayList<>();
		viajesIniciados.add(viajeEmpresa);
		
		
		try {
			empresa.crearViaje(pedidoEmpresa, choferEmpresa, autoEmpresa);
			fail("El metodo NO lanzo excepcion");
		} catch (PedidoInexistenteException e) {
			fail("El metodo lanzo una expecion erronea: (PedidoInexistenteException)");
		} catch (VehiculoNoValidoException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoValidoException)");
		} catch (ChoferNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (ChoferNoDisponibleException)");
		} catch (VehiculoNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoDisponibleException)");
		} catch (ClienteConViajePendienteException e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void test_agregarViaje_sinErrores_noDeberiaTirarExcepcion() {
		/*
		 *  Escenario:	
		 *  			Cliente: El de la empresa
		 *  
		 *  			Pedido: El de la empresa
		 *  			
		 *  			Auto: El de la empresa
		 *  	 
		 *  			Chofer: El de la empresa
		 *  
		 *  			Viaje: (pedidoEmpresa, choferEmpresa, autoEmpresa)
		 *  				>> Esta agregado a la empresa
		 *  
		 * 	Prueba:
		 * 				crearViaje(pedidoEmpresa, choferEmpresa, autoEmpresa)
		 * 				Esperado: No deberia lanzar excepcion
		 * 				>> Resultado: Correcto
		 * 				
		 * */
		
		this.agregarChoferGenerico();
		this.agregarPedidosGenericos();
		this.agregarVehiculosGenericos();
		
		try {
			empresa.crearViaje(pedidoEmpresa, choferEmpresa, autoEmpresa);
			assertTrue(true);
		} catch (PedidoInexistenteException e) {
			fail("El metodo lanzo una expecion erronea: (PedidoInexistenteException)");
		} catch (VehiculoNoValidoException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoValidoException)");
		} catch (ChoferNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (ChoferNoDisponibleException)");
		} catch (VehiculoNoDisponibleException e) {
			fail("El metodo lanzo una expecion erronea: (VehiculoNoDisponibleException)");
		} catch (ClienteConViajePendienteException e) {
			fail("El metodo lanzo una expecion erronea: (ClienteConViajePendienteException)");
		}
		
	}
	
	
	
	

}
