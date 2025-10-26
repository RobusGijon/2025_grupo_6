package test_excepciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.ClienteSinViajePendienteException;
import excepciones.PasswordErroneaException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.SinViajesException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import excepciones.VehiculoRepetidoException;
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
import modeloNegocio.Empresa;
import util.Constantes;

import org.junit.Before;
import org.junit.Test;

public class LanzadorExcepciones {
	
	
	public void lanzar_choferNoDisponibleException(Chofer chofer) 
							throws ChoferNoDisponibleException {
		throw new ChoferNoDisponibleException(chofer);
	}
	
	public void lanzar_choferRepetidoException(String dniPretendido, Chofer choferExistente)
												throws ChoferRepetidoException {
		throw new ChoferRepetidoException(dniPretendido, choferExistente);
	}
	
	public void lanzar_clienteConPedidoPendienteException() throws ClienteConPedidoPendienteException {
		throw new ClienteConPedidoPendienteException();
	}
	
	public void lanzar_clienteConViajePendienteException() throws ClienteConViajePendienteException {
		throw new ClienteConViajePendienteException();
	}
	
	public void lanzar_clienteNoExisteException() throws ClienteNoExisteException {
		throw new ClienteNoExisteException();
	}
	
	public void lanzar_clienteSinViajePendienteException() throws ClienteSinViajePendienteException {
		throw new ClienteSinViajePendienteException();
	}
	
	public void lanzar_passwordErroneaException(String usuarioPretendido, String passwordPretendida) 
													throws PasswordErroneaException {
		throw new PasswordErroneaException(usuarioPretendido, passwordPretendida);
	}
	
	public void lanzar_pedidoInexistenteException(Pedido pedido) throws PedidoInexistenteException {
		throw new PedidoInexistenteException(pedido);
	}
	
	public void lanzar_sinVehiculoParaPedidoException(Pedido pedido) throws SinVehiculoParaPedidoException {
		throw new SinVehiculoParaPedidoException(pedido);
	}
	
	public void lanzar_sinViajesException() throws SinViajesException {
		throw new SinViajesException();
	}
	
	public void lanzar_usuarioNoExisteException(String usuarioPretendido) throws UsuarioNoExisteException {
		throw new UsuarioNoExisteException(usuarioPretendido);
	}
	
	public void lanzar_usuarioYaExisteException(String usuarioPretendido) throws UsuarioYaExisteException {
		throw new UsuarioYaExisteException(usuarioPretendido);
	}
	
	public void lanzar_vehiculoNoDisponibleException(Vehiculo vehiculo) throws VehiculoNoDisponibleException{
		throw new VehiculoNoDisponibleException(vehiculo);
	}
	
	public void lanzar_vehiculoNoValidoException(Vehiculo vehiculo, Pedido pedido) throws VehiculoNoValidoException{
		throw new VehiculoNoValidoException(vehiculo, pedido);
	}	
	
	public void lanzar_vehiculoRepetidoException(String patentePrentendida, Vehiculo vehiculoExistente) throws VehiculoRepetidoException{
		throw new VehiculoRepetidoException(patentePrentendida, vehiculoExistente);
	}	
}
