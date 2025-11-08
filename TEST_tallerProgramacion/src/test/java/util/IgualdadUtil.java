package util;

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

public class IgualdadUtil {

	public static boolean sonIguales(Chofer chofer1, Chofer chofer2) {
		if (chofer1 == null && chofer2 == null) {
			return true;
		}
		if (chofer1 == null || chofer2 == null) {
			return false;
		}
		
		if (!chofer1.getDni().equals(chofer2.getDni()) || 
			!chofer1.getNombre().equals(chofer2.getNombre())) {
			return false;
		}
		// Comprar si son choferes permanentes
		if (chofer1 instanceof ChoferPermanente && chofer2 instanceof ChoferPermanente) {
			ChoferPermanente cp1 = (ChoferPermanente) chofer1;
			ChoferPermanente cp2 = (ChoferPermanente) chofer2;
			return cp1.getAnioIngreso() == cp2.getAnioIngreso() &&
				   cp1.getCantidadHijos() == cp2.getCantidadHijos();
		}
		
		// Comprar si son choferes temporarios
		if (chofer1 instanceof ChoferTemporario && chofer2 instanceof ChoferTemporario) {
			return true;
		}
		
		// Instancias diferentes
		return false;
	}
	
	public static boolean sonIguales(Viaje viaje1, Viaje viaje2) {
		if (viaje1 == null && viaje2 == null) {
			return true;
		}
		if (viaje1 == null || viaje2 == null) {
			return false;
		}
		
		return sonIguales(viaje1.getPedido(), viaje2.getPedido()) &&
			   sonIguales(viaje1.getChofer(), viaje2.getChofer()) &&
			   sonIguales(viaje1.getVehiculo(), viaje2.getVehiculo()) &&
			   viaje1.getCalificacion() == viaje2.getCalificacion() &&
			   viaje1.isFinalizado() == viaje2.isFinalizado();
	}
	
	public static boolean sonIguales(Usuario usuario1, Usuario usuario2) {
		if (usuario1 == null && usuario2 == null) {
			return true;
		}
		if (usuario1 == null || usuario2 == null) {
			return false;
		}
		
		// Comprar nombreUsuario y pass
		if (!usuario1.getNombreUsuario().equals(usuario2.getNombreUsuario()) ||
			!usuario1.getPass().equals(usuario2.getPass())) {
			return false;
		}
		
		// Si ambos son Cliente, comparar nombreReal
		if (usuario1 instanceof Cliente && usuario2 instanceof Cliente) {
			Cliente c1 = (Cliente) usuario1;
			Cliente c2 = (Cliente) usuario2;
			return c1.getNombreReal().equals(c2.getNombreReal());
		}
		
		// Comprar si son administradores (ya tienen el mismo nombreReal)
		if (usuario1 instanceof Administrador && usuario2 instanceof Administrador) {
			return true;
		}
		
		return false;
	}
	
	public static boolean sonIguales(Vehiculo vehiculo1, Vehiculo vehiculo2) {
		if (vehiculo1 == null && vehiculo2 == null) {
			return true;
		}
		if (vehiculo1 == null || vehiculo2 == null) {
			return false;
		}
		
		if (!vehiculo1.getPatente().equals(vehiculo2.getPatente()) ||
			vehiculo1.getCantidadPlazas() != vehiculo2.getCantidadPlazas() ||
			vehiculo1.isMascota() != vehiculo2.isMascota()) {
			return false;
		}
		
		// Comprar si son de la misma clase
		if (vehiculo1 instanceof Auto && vehiculo2 instanceof Auto) {
			return true;
		}
		if (vehiculo1 instanceof Combi && vehiculo2 instanceof Combi) {
			return true;
		}
		if (vehiculo1 instanceof Moto && vehiculo2 instanceof Moto) {
			return true;
		}
		
		return false;
	}
	
	public static boolean sonIguales(Pedido pedido1, Pedido pedido2) {
		if (pedido1 == null && pedido2 == null) {
			return true;
		}
		if (pedido1 == null || pedido2 == null) {
			return false;
		}
		
		return sonIguales(pedido1.getCliente(), pedido2.getCliente()) &&
			   pedido1.getCantidadPasajeros() == pedido2.getCantidadPasajeros() &&
			   pedido1.isMascota() == pedido2.isMascota() &&
			   pedido1.isBaul() == pedido2.isBaul() &&
			   pedido1.getKm() == pedido2.getKm() &&
			   pedido1.getZona().equals(pedido2.getZona());
	}

}
