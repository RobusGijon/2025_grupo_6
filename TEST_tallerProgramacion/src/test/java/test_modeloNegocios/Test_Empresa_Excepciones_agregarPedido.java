package test_modeloNegocios;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;

public class Test_Empresa_Excepciones_agregarPedido {

    Empresa empresa;
    Cliente cliente;
    Pedido pedido;
    Auto auto;
    ChoferTemporario chofer;
    
    @Before
    public void setUp() throws Exception {
        empresa = Empresa.getInstance();
        empresa.setClientes(new HashMap<>());
        empresa.setPedidos(new HashMap<>());
        empresa.setVehiculos(new HashMap<>());
        empresa.setVehiculosDesocupados(new java.util.ArrayList<>());
        empresa.setChoferes(new HashMap<>());
        empresa.setChoferesDesocupados(new java.util.ArrayList<>());
        empresa.setViajesIniciados(new HashMap<>());
        empresa.setViajesTerminados(new java.util.ArrayList<>());
        empresa.setUsuarioLogeado(null);
        
        cliente = new Cliente("nombreUsuario", "pass", "nombreReal");
        pedido = new Pedido(cliente, 2, false, false, 1, Constantes.ZONA_STANDARD);
        auto = new Auto("AA123AA", 4, true);
        chofer = new ChoferTemporario("11222333", "Juan Jose");
        
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

    private void agregarClienteEmpresa() {
    	HashMap<String, Cliente> mapaCliente = new HashMap<>();
    	mapaCliente.put(cliente.getNombreUsuario(), cliente);
    	empresa.setClientes(mapaCliente);
    }
    
    private void agregarAutoEmpresa() {
    	HashMap<String, Vehiculo> mapaVehiculo = new HashMap<>();
    	mapaVehiculo.put(auto.getPatente(), auto);
    	empresa.setVehiculos(mapaVehiculo);
    }
    
    private void agregarMotoEmpresa() {
    	HashMap<String, Vehiculo> mapaVehiculo = new HashMap<>();
    	mapaVehiculo.put("MM123MM", new Moto("MM123MM"));
    	empresa.setVehiculos(mapaVehiculo);
    }
    
    private void agregarViajeIniciadoEmpresa() {
        Viaje viaje = new Viaje(pedido, chofer, auto);
        HashMap<Cliente, Viaje> mapaViajesPendientes = new HashMap<>();
        mapaViajesPendientes.put(cliente, viaje);
        empresa.setViajesIniciados(mapaViajesPendientes);
    }
    
    
    @Test
    public void test_agregarPedidoSinVehiculo_deberiaTirarExpecionCorrecta() {
        /*
         *  Escenario:  La empresa tiene al cliente registrado y SOLO una moto disponible.
         *              El pedido requiere un vehículo apto para 2 pasajeros (la moto NO califica).
         *
         *              empresa.clientes: { "nombreUsuario" -> cliente }
         *              empresa.vehiculos: { "MM123MM" -> Moto(...) }
         *              pedido: (cliente, 2 pax, SIN mascota, SIN baúl, 1 km, ZONA_STANDARD)
         *
         *  Prueba:     agregarPedido DEBERIA lanzar SinVehiculoParaPedidoException
         *              al no existir un vehículo compatible/disponible.
         *
         *              >> Esperado: Lanzar SinVehiculoParaPedidoException
         *              >> Resultado: Correcto
         */
    	
    	this.agregarClienteEmpresa();
    	this.agregarMotoEmpresa();
    	
    	
    	try {
            empresa.agregarPedido(pedido);
            fail("El método debía lanzar SinVehiculoParaPedidoException y no lanzó ninguna excepción");
        } catch (ClienteNoExisteException e) {
            fail("El método no lanzó la excepción correcta (ClienteNoExisteException)");
        } catch (ClienteConViajePendienteException e) {
            fail("El método no lanzó la excepción correcta (ClienteConViajePendienteException)");
        } catch (ClienteConPedidoPendienteException e) {
            fail("El método no lanzó la excepción correcta (ClienteConPedidoPendienteException)");
        } catch (SinVehiculoParaPedidoException e) {
        	assertTrue(true);
        }
    }

    @Test
    public void test_agregarPedidoSinCliente_deberiaTirarExpecionCorrecta() {
        /*
         *  Escenario:  La empresa tiene un vehículo apto, pero el cliente del pedido
         *              NO está registrado en la empresa.
         *
         *              empresa.vehiculos: { "AA123AA" -> Auto(4 pax, admiteMascota = true) }
         *              empresa.clientes: {}
         *              pedido: (cliente NO registrado, 2 pax, SIN mascota, SIN baúl, 1 km, ZONA_STANDARD)
         *
         *  Prueba:     agregarPedido DEBERIA lanzar ClienteNoExisteException.
         *
         *              >> Esperado: Lanzar ClienteNoExisteException
         *              >> Resultado: Correcto 
         */
    	
    	this.agregarAutoEmpresa();
    	
        try {
            empresa.agregarPedido(pedido);
            fail("El método debía lanzar ClienteNoExisteException y no lanzó ninguna excepción");
        } catch (SinVehiculoParaPedidoException e) {
            fail("El método no lanzó la excepción correcta (SinVehiculoParaPedidoException)");
        } catch (ClienteConViajePendienteException e) {
            fail("El método no lanzó la excepción correcta (ClienteConViajePendienteException)");
        } catch (ClienteConPedidoPendienteException e) {
            fail("El método no lanzó la excepción correcta (ClienteConPedidoPendienteException)");
        } catch (ClienteNoExisteException e) {
        	assertTrue(true);
        }
    }

    @Test
    public void test_agregarPedidoConViajePendiente_deberiaTirarExpecionCorrecta() {
        /*
         *  Escenario:  Cliente registrado y vehículo agregado. El cliente YA tiene
         *              un viaje iniciado. Se intenta agregar un nuevo pedido.
         *
         *              empresa.clientes: { cliente }
         *              empresa.vehiculos: { auto }
         *              empresa.viajesIniciados: { cliente -> viajeEnCurso }
         *              pedido: (cliente, 2 pax, SIN mascota, SIN baúl, 1 km, ZONA_STANDARD)
         *
         *  Prueba:     agregarPedido DEBERIA lanzar ClienteConViajePendienteException.
         *
         *              >> Esperado: Lanzar ClienteConViajePendienteException
         *              >> Resultado: INCORRECTO -> No lanzo excepcion
         */
        
    	this.agregarClienteEmpresa();
    	this.agregarAutoEmpresa();
    	this.agregarViajeIniciadoEmpresa();

        try {
            empresa.agregarPedido(pedido);
            fail("El metodo debía lanzar ClienteConViajePendienteException y no lanzó ninguna excepción");
        } catch (SinVehiculoParaPedidoException e) {
            fail("El metodo no lanzo la excepción correcta (SinVehiculoParaPedidoException)");
        } catch (ClienteNoExisteException e) {
            fail("El metodo no lanzo la excepción correcta (ClienteNoExisteException)");
        } catch (ClienteConPedidoPendienteException e) {
            fail("El metodo no lanzo la excepción correcta (ClienteConPedidoPendienteException)");
        } catch (ClienteConViajePendienteException e) {
        	assertTrue(true);
        }
    }
    
    @Test
    public void test_agregarPedidoConPedidoPediente_deberiaTirarExpecionCorrecta() {
        /*
         *  Escenario:  Cliente y vehículo registrados. El cliente YA tiene un pedido
         *              pendiente (aún no iniciado en viaje). Se intenta agregar otro pedido.
         *
         *              empresa.clientes: { cliente }
         *              empresa.vehiculos: { auto }
         *              empresa.pedidos: contiene un pedido previo del cliente
         *              pedidoNuevo: (cliente, 1 pax, SIN mascota, SIN baúl, 10 km, ZONA_SIN_ASFALTAR)
         *
         *  Prueba:     agregarPedido DEBERIA lanzar ClienteConPedidoPendienteException.
         *
         *              >> Esperado: Lanzar ClienteConPedidoPendienteException
         *              >> Resultado: Correcto
         */
        this.agregarAutoEmpresa();
        this.agregarClienteEmpresa();
        
        try {
        	empresa.agregarPedido(pedido);
        } catch (SinVehiculoParaPedidoException e) {
        	fail("El metodo lanzo una expecion no esperada (SinVehiculoParaPedidoException)");
        } catch (ClienteNoExisteException e) {
        	fail("El metodo lanzo una expecion no esperada (ClienteNoExisteException)");
        } catch (ClienteConViajePendienteException e) {
        	fail("El metodo lanzo una expecion no esperada (ClienteConViajePendienteException)");
        } catch (ClienteConPedidoPendienteException e) {
        	fail("El metodo lanzo una expecion no esperada (ClienteConPedidoPendienteException)");
        }
        
        Pedido pedidoNuevo = new Pedido(cliente, 1, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);

        try {
			empresa.agregarPedido(pedidoNuevo);
			fail("El metodo debía lanzar ClienteConPedidoPendienteException y no lanzó ninguna excepción");
		} catch (SinVehiculoParaPedidoException e) {
			fail("El metodo no lanzo la excepción correcta (SinVehiculoParaPedidoException)");
		} catch (ClienteNoExisteException e) {
			fail("El metodo no lanzo la excepción correcta (ClienteNoExisteException)");
		} catch (ClienteConViajePendienteException e) {
			fail("El metodo no lanzo la excepción correcta (ClienteConViajePendienteException)");
		} catch (ClienteConPedidoPendienteException e) {
			assertTrue(true);
		}
        
    }
    
    @Test
    public void test_agregarPedido_correcto() {
        /*
         *  Escenario:  Cliente y vehículo registrados. No existen pedidos ni viajes pendientes.
         *
         *              empresa.clientes: { cliente }
         *              empresa.vehiculos: { auto }
         *              pedido: (cliente, 2 pax, SIN mascota, SIN baúl, 1 km, ZONA_STANDARD)
         *
         *  Prueba:     agregarPedido NO DEBERIA lanzar ninguna excepción.
         *
         *              >> Esperado: No lanzar excepciones
         *              >> Resultado: Correcto
         */
        
    	this.agregarClienteEmpresa();
    	this.agregarAutoEmpresa();

        try {
            empresa.agregarPedido(pedido);
            assertTrue(true);
        } catch (SinVehiculoParaPedidoException e) {
            fail("El metodo no debio lanzar excepcion (SinVehiculoParaPedidoException)");
        } catch (ClienteNoExisteException e) {
            fail("El metodo no debio lanzar excepcion (ClienteNoExisteException)");
        } catch (ClienteConPedidoPendienteException e) {
            fail("El metodo no debio lanzar excepcion (ClienteConPedidoPendienteException)");
        } catch (ClienteConViajePendienteException e) {
        	fail("El metodo no debio lanzar excepcion (ClienteConViajePendienteException)");
        }
    }
}
