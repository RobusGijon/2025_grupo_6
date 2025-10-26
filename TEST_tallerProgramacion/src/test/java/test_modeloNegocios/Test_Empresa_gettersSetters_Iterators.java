
package test_modeloNegocios;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

public class Test_Empresa_gettersSetters_Iterators {

	
	Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
	}

	
	@Test
	public void test_getInstance() {
		assertTrue(empresa instanceof Empresa);
	}
	
	@Test
	public void test_get_set_Clientes() {
		HashMap<String, Cliente> mapaClientes = new HashMap<>();
		
		mapaClientes.put("nombreusuario1", new Cliente("nombreusuario1", "pass1", "nombreReal1"));
		mapaClientes.put("nombreusuario2", new Cliente("nombreusuario2", "pass2", "nombreReal2"));
		
		empresa.setClientes(mapaClientes);
		
		HashMap<String, Cliente> mapaClientesEmpresa = empresa.getClientes();
		assertEquals(mapaClientes, mapaClientesEmpresa);
	}
	
	
	@Test
	public void test_get_set_ChoferesDesocupados() {
		ArrayList<Chofer> choferesDesocupados = new ArrayList<>();
		choferesDesocupados.add(new ChoferTemporario("321341290", "juan perez"));
		choferesDesocupados.add(new ChoferPermanente("213942104", "juan perez", 2010, 2));
		
		empresa.setChoferesDesocupados(choferesDesocupados);
		ArrayList<Chofer> choferesDesocupadosEmpresa = empresa.getChoferesDesocupados();
		
		assertEquals(choferesDesocupados, choferesDesocupadosEmpresa);
	}
	
	@Test
	public void test_get_set_Choferes() {
		HashMap<String, Chofer> choferes = new HashMap<>();
		choferes.put("321341290", new ChoferTemporario("321341290", "juan perez"));
		choferes.put("213942104", new ChoferPermanente("213942104", "juan perez", 2010, 2));
		
		empresa.setChoferes(choferes);
		HashMap<String, Chofer> choferesEmpresa = empresa.getChoferes();
		
		assertEquals(choferes, choferesEmpresa);
	}
	
	@Test
	public void test_get_set_Vehiculos() {
		HashMap<String, Vehiculo> vehiculos = new HashMap<>();
		vehiculos.put("AB123CD", new Moto("AB123CD"));
		vehiculos.put("EF456GH", new Combi("EF456GH", 6, true));
		vehiculos.put("KP983QZ", new Auto("KP983QZ", 3, false));
		
		empresa.setVehiculos(vehiculos);
		HashMap<String, Vehiculo> vehiculosEmpresa = empresa.getVehiculos();
		
		assertEquals(vehiculos, vehiculosEmpresa);
	}
	
	@Test
	public void test_get_set_VehiculosDesocupados() {
		ArrayList<Vehiculo> vehiculos = new ArrayList<>();
		vehiculos.add(new Moto("AB123CD"));
		vehiculos.add(new Combi("EF456GH", 6, true));
		vehiculos.add(new Auto("KP983QZ", 3, false));
		
		empresa.setVehiculosDesocupados(vehiculos);
		ArrayList<Vehiculo> vehiculosEmpresa = empresa.getVehiculosDesocupados();
		assertEquals(vehiculos, vehiculosEmpresa);	
	}
	
	@Test
	public void test_get_set_Pedidos() {
		HashMap<Cliente, Pedido> pedidos = new HashMap<>();
		Cliente cliente1 = new Cliente("nomUs1", "pass1", "nomRe1"); 
		Cliente cliente2 = new Cliente("nomUs2", "pass2", "nomRe2");
		pedidos.put(cliente1, new Pedido(cliente1, 3, false, true, 12, Constantes.ZONA_SIN_ASFALTAR));
		pedidos.put(cliente2, new Pedido(cliente2, 6, true, false, 85, Constantes.ZONA_PELIGROSA));
		
		empresa.setPedidos(pedidos);
		HashMap<Cliente, Pedido> pedidosEmpresa = empresa.getPedidos();
		
		assertEquals(pedidos, pedidosEmpresa);
		
	}
	
	@Test
	public void test_get_set_viajesIniciados() {
		
		Cliente cliente1 = new Cliente("nombreUsuario", "pass", "nombreReal");
		Pedido pedido1 = new Pedido(cliente1, 2, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		Chofer chofer1 = new ChoferTemporario("11222333", "Juan Carlos");
		Auto auto1 = new Auto("AA123AA", 4, true);
		Viaje viaje1 = new Viaje(pedido1, chofer1, auto1);
		
		Cliente cliente2 = new Cliente("nombreUsuario", "pass", "nombreReal");
		Pedido pedido2 = new Pedido(cliente2, 6, true, false, 23, Constantes.ZONA_SIN_ASFALTAR);
		Chofer chofer2 = new ChoferPermanente("11222333", "Carlos Miguel", 2000, 2);
		Combi auto2 = new Combi("AA123AA", 9, true);
		Viaje viaje2 = new Viaje(pedido2, chofer2, auto2);
		
		HashMap<Cliente, Viaje> mapaViajesIniciados = new HashMap<>();
		
		mapaViajesIniciados.put(cliente1, viaje1);
		mapaViajesIniciados.put(cliente2, viaje2);
		
		empresa.setViajesIniciados(mapaViajesIniciados);
		
		HashMap<Cliente, Viaje> empresaViajesIniciados = empresa.getViajesIniciados();
		
		assertEquals(mapaViajesIniciados, empresaViajesIniciados);
		
	}
	
	@Test
	public void test_get_set_viajesTerminados() {
		
		Cliente cliente1 = new Cliente("nombreUsuario", "pass", "nombreReal");
		Pedido pedido1 = new Pedido(cliente1, 2, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		Chofer chofer1 = new ChoferTemporario("11222333", "Juan Carlos");
		Auto auto1 = new Auto("AA123AA", 4, true);
		Viaje viaje1 = new Viaje(pedido1, chofer1, auto1);
		viaje1.finalizarViaje(5);
		
		Cliente cliente2 = new Cliente("nombreUsuario", "pass", "nombreReal");
		Pedido pedido2 = new Pedido(cliente2, 6, true, false, 23, Constantes.ZONA_SIN_ASFALTAR);
		Chofer chofer2 = new ChoferPermanente("11222333", "Carlos Miguel", 2000, 2);
		Combi auto2 = new Combi("AA123AA", 9, true);
		Viaje viaje2 = new Viaje(pedido2, chofer2, auto2);
		viaje2.finalizarViaje(0);
		
		ArrayList<Viaje> viajesTerminados = new ArrayList<>();
		
		viajesTerminados.add(viaje1);
		viajesTerminados.add(viaje2);
		
		empresa.setViajesTerminados(viajesTerminados);
		
		ArrayList<Viaje> empresaViajesTerminados = empresa.getViajesTerminados();
		
		assertEquals(viajesTerminados, empresaViajesTerminados);
		
	}
	
	@Test
	public void test_get_historialDeViajeChofer() {
		Chofer choferConViajes = new ChoferTemporario("11222333", "Juan Carlos");
		Chofer choferSinViajes = new ChoferPermanente("11222333", "Carlos Miguel", 2000, 2);
		
		Cliente cliente1 = new Cliente("nombreUsuario", "pass", "nombreReal");
		Pedido pedido1 = new Pedido(cliente1, 2, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		Auto auto1 = new Auto("AA123AA", 4, true);
		Viaje viaje1 = new Viaje(pedido1, choferConViajes, auto1);
		viaje1.finalizarViaje(5);
		
		Cliente cliente2 = new Cliente("nombreUsuario", "pass", "nombreReal");
		Pedido pedido2 = new Pedido(cliente2, 6, true, false, 23, Constantes.ZONA_SIN_ASFALTAR);
		Combi auto2 = new Combi("AA123AA", 9, true);
		Viaje viaje2 = new Viaje(pedido2, choferConViajes, auto2);
		viaje2.finalizarViaje(0);
		
		ArrayList<Viaje> viajesTerminados = new ArrayList<>();
		
		viajesTerminados.add(viaje1);
		viajesTerminados.add(viaje2);
		
		empresa.setViajesTerminados(viajesTerminados);
		
		ArrayList<Viaje> viajesDelChofer = empresa.getHistorialViajeChofer(choferConViajes);
		assertTrue(viajesDelChofer.contains(viaje1));
		assertTrue(viajesDelChofer.contains(viaje2));
		
		viajesDelChofer = empresa.getHistorialViajeChofer(choferSinViajes);
		assertTrue(viajesDelChofer.isEmpty());
		
	}
	
	@Test
	public void test_get_historialDeViajeCliente() {
		Cliente clienteConViajes = new Cliente("nombreUsuario", "pass", "nombreReal");
		Cliente clienteSinViajes = new Cliente("nombreUsuario", "pass", "nombreReal");
		
		Chofer chofer = new ChoferTemporario("11222333", "Juan Carlos");
		Pedido pedido1 = new Pedido(clienteConViajes, 2, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		Auto auto1 = new Auto("AA123AA", 4, true);
		Viaje viaje1 = new Viaje(pedido1, chofer, auto1);
		viaje1.finalizarViaje(5);
		
		Pedido pedido2 = new Pedido(clienteConViajes, 6, true, false, 23, Constantes.ZONA_SIN_ASFALTAR);
		Combi auto2 = new Combi("AA123AA", 9, true);
		Viaje viaje2 = new Viaje(pedido2, chofer, auto2);
		viaje2.finalizarViaje(0);
		
		ArrayList<Viaje> viajesTerminados = new ArrayList<>();
		
		viajesTerminados.add(viaje1);
		viajesTerminados.add(viaje2);
		
		empresa.setViajesTerminados(viajesTerminados);
		
		ArrayList<Viaje> viajesDelChofer = empresa.getHistorialViajeCliente(clienteConViajes);
		assertTrue(viajesDelChofer.contains(viaje1));
		assertTrue(viajesDelChofer.contains(viaje2));
		
		viajesDelChofer = empresa.getHistorialViajeCliente(clienteSinViajes);
		assertTrue(viajesDelChofer.isEmpty());
	}
	
	
	
	
	@Test
	public void test_iteratorClientes() {
	    Cliente c1 = new Cliente("nombreUsuario1", "pass1", "nombreReal1");
	    Cliente c2 = new Cliente("nombreUsuario2", "pass2", "nombreReal2");
	    Cliente c3 = new Cliente("nombreUsuario3", "pass3", "nombreReal3");

	    HashMap<String, Cliente> mapaClientes = new HashMap<>();
	    mapaClientes.put("nombreUsuario1", c1);
	    mapaClientes.put("nombreUsuario2", c2);
	    mapaClientes.put("nombreUsuario3", c3);
	    empresa.setClientes(mapaClientes); 

	    
	    HashMap<String, Cliente> restante = new HashMap<>(mapaClientes);

	    Iterator<Cliente> it = empresa.iteratorClientes();
	    while (it.hasNext()) {
	        Cliente cli = it.next();
	        assertTrue("El método devolvió un cliente desconocido",
	                   restante.containsValue(cli));
	        restante.remove(cli.getNombreUsuario());
	    }

	    assertTrue("El iterador no devolvió todos los clientes", restante.isEmpty());
	}
	
	@Test
	public void test_iteratorPedidos() {
	    Cliente c1 = new Cliente("nombreUsuario1", "pass1", "nombreReal1");
	    Cliente c2 = new Cliente("nombreUsuario2", "pass2", "nombreReal2");
	    Cliente c3 = new Cliente("nombreUsuario3", "pass3", "nombreReal3");

	    Pedido pedido1 = new Pedido(c1, 8, true, false, 10, Constantes.ZONA_PELIGROSA);
	    Pedido pedido2 = new Pedido(c2, 1, false, true, 10, Constantes.ZONA_SIN_ASFALTAR);
	    Pedido pedido3 = new Pedido(c3, 4, true, true, 10, Constantes.ZONA_STANDARD);
	    
	    HashMap<Cliente, Pedido> mapaPedidos = new HashMap<>();
	    mapaPedidos.put(c1, pedido1);
	    mapaPedidos.put(c2, pedido2);
	    mapaPedidos.put(c3, pedido3);
	    empresa.setPedidos(mapaPedidos); 

	    
	    HashMap<Cliente, Pedido> restante = new HashMap<>(mapaPedidos);

	    Iterator<Pedido> it = empresa.iteratorPedidos();
	    while (it.hasNext()) {
	        Pedido itPedido = it.next();
	        assertTrue("El método devolvio un pedido desconocido",
	                   restante.containsValue(itPedido));
	        restante.remove(itPedido.getCliente());
	    }

	    assertTrue("El iterador no devolvio todos los pedidos", restante.isEmpty());
	}
	
	@Test
	public void test_iteratorVehiculos() {
	    Auto auto = new Auto("AA123AA", 3, true);
	    Moto moto = new Moto("MM123MM");
	    Combi combi = new Combi("CC123CC", 9, false);
	    
	    
	    HashMap<String, Vehiculo> mapaVehiculos = new HashMap<>();
	    mapaVehiculos.put("AA123AA", auto);
	    mapaVehiculos.put("MM123MM", moto);
	    mapaVehiculos.put("CC123CC", combi);
	    empresa.setVehiculos(mapaVehiculos); 

	    
	    HashMap<String, Vehiculo> restante = new HashMap<>(mapaVehiculos);

	    Iterator<Vehiculo> it = empresa.iteratorVehiculos();
	    while (it.hasNext()) {
	        Vehiculo itVehiculo = it.next();
	        assertTrue("El método devolvio un vehiculo desconocido",
	                   restante.containsValue(itVehiculo));
	        restante.remove(itVehiculo.getPatente());
	    }

	    assertTrue("El iterador no devolvio todos los vehiculo", restante.isEmpty());
	}
	
	@Test
	public void test_iteratorChoferes() {
	    
		ChoferTemporario choferT = new ChoferTemporario("4412381", "Juan Perez");
		ChoferPermanente choferP = new ChoferPermanente("3219012", "Perez Juan", 2000, 2);
	    
	    
	    HashMap<String, Chofer> mapaChofer= new HashMap<>();
	    mapaChofer.put("4412381", choferT);
	    mapaChofer.put("3219012", choferP);
	    empresa.setChoferes(mapaChofer); 

	    
	    HashMap<String, Chofer> restante = new HashMap<>(mapaChofer);

	    Iterator<Chofer> it = empresa.iteratorChoferes();
;	    while (it.hasNext()) {
	        Chofer itChofer = it.next();
	        assertTrue("El método devolvio un chofer desconocido",
	                   restante.containsValue(itChofer));
	        restante.remove(itChofer.getDni());
	    }

	    assertTrue("El iterador no devolvio todos los choferes", restante.isEmpty());
	}
	
	@Test
	public void test_iteratorViajesIniciados() {
		
		Cliente cliente1 = new Cliente("nombreUsuario", "pass", "nombreReal");
		Pedido pedido1 = new Pedido(cliente1, 2, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		Chofer chofer1 = new ChoferTemporario("11222333", "Juan Carlos");
		Auto auto1 = new Auto("AA123AA", 4, true);
		Viaje viaje1 = new Viaje(pedido1, chofer1, auto1);
		
		Cliente cliente2 = new Cliente("nombreUsuario", "pass", "nombreReal");
		Pedido pedido2 = new Pedido(cliente2, 6, true, false, 23, Constantes.ZONA_SIN_ASFALTAR);
		Chofer chofer2 = new ChoferPermanente("11222333", "Carlos Miguel", 2000, 2);
		Combi auto2 = new Combi("AA123AA", 9, true);
		Viaje viaje2 = new Viaje(pedido2, chofer2, auto2);
		
		HashMap<Cliente, Viaje> mapaViajesIniciados = new HashMap<>();
		
		mapaViajesIniciados.put(cliente1, viaje1);
		mapaViajesIniciados.put(cliente2, viaje2);
		
		empresa.setViajesIniciados(mapaViajesIniciados);

	    
	    HashMap<Cliente, Viaje> restante = new HashMap<>(mapaViajesIniciados);

	    Iterator<Viaje> it = empresa.iteratorViajesIniciados();
;	    while (it.hasNext()) {
	        Viaje itViaje = it.next();
	        assertTrue("El método devolvio un viaje desconocido",
	                   restante.containsValue(itViaje));
	        restante.remove(itViaje.getPedido().getCliente());
	    }

	    assertTrue("El iterador no devolvio todos los viajes", restante.isEmpty());
	}
	
	
	@Test
	public void test_iteratorViajesTerminados() {
		
		Cliente cliente1 = new Cliente("nombreUsuario", "pass", "nombreReal");
		Pedido pedido1 = new Pedido(cliente1, 2, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
		Chofer chofer1 = new ChoferTemporario("11222333", "Juan Carlos");
		Auto auto1 = new Auto("AA123AA", 4, true);
		Viaje viaje1 = new Viaje(pedido1, chofer1, auto1);
		viaje1.finalizarViaje(5);
		
		Cliente cliente2 = new Cliente("nombreUsuario", "pass", "nombreReal");
		Pedido pedido2 = new Pedido(cliente2, 6, true, false, 23, Constantes.ZONA_SIN_ASFALTAR);
		Chofer chofer2 = new ChoferPermanente("11222333", "Carlos Miguel", 2000, 2);
		Combi auto2 = new Combi("AA123AA", 9, true);
		Viaje viaje2 = new Viaje(pedido2, chofer2, auto2);
		viaje2.finalizarViaje(0);
		
		ArrayList<Viaje> viajesTerminados = new ArrayList<>();
		
		viajesTerminados.add(viaje1);
		viajesTerminados.add(viaje2);
		
		empresa.setViajesTerminados(viajesTerminados);

	    ArrayList<Viaje> iterador = empresa.iteratorViajesTerminados();
	    
;	    assertEquals(iterador, viajesTerminados);
	}
	
	@Test
	public void test_get_set_usuarioLogeado() {
		
		Cliente cliente = new Cliente("nombreUsuario", "pass", "nombreReal");
		empresa.setUsuarioLogeado(cliente);
		
		Usuario usuarioLogeado = empresa.getUsuarioLogeado();
		assertEquals(cliente, usuarioLogeado);
		
		Administrador admin = Administrador.getInstance();
		empresa.setUsuarioLogeado(admin);
		usuarioLogeado = empresa.getUsuarioLogeado();
		assertEquals(admin, usuarioLogeado);
		
	}
	
	@Test
	public void test_isAdmin() {
		
		Cliente cliente = new Cliente("nombreUsuario", "pass", "nombreReal");
		empresa.setUsuarioLogeado(cliente);
		assertFalse(empresa.isAdmin());
		
		Administrador admin = Administrador.getInstance();
		empresa.setUsuarioLogeado(admin);
		assertTrue(empresa.isAdmin());
		
		
	}
	
	@Test
	public void test_logout() {
		Cliente cliente = new Cliente("nombreUsuario", "pass", "nombreReal");
		empresa.setUsuarioLogeado(cliente);
		empresa.logout();
		assertNull(empresa.getUsuarioLogeado());
		
		Administrador admin = Administrador.getInstance();
		empresa.setUsuarioLogeado(admin);
		empresa.logout();
		assertNull(empresa.getUsuarioLogeado());	
		
	}
			
	
	
	
	
}
