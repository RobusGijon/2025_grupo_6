package test_gui_panelCliente;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;

import controlador.Controlador;
import modeloDatos.Auto;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;
import util.FalsoOptionPanel;
import util.Mensajes;
import util.TestUtils;
import vista.Ventana;


public class Test_Gui_PanelCliente_ClineteConViajeIniciado {

	private Ventana ventana;
	private Robot robot;
	private Cliente clienteEmpresa;
	private FalsoOptionPanel miOptionPanel;
	private Controlador controlador;
	private Empresa empresa;
	private Viaje viajeIniciadoCliente;
	
	public Cliente getClienteEmpresa() {
		Cliente cliente = new Cliente("us", "pass", "nombreReal");
		
		HashMap<String, Cliente> mapaCliente = new HashMap<>();
		mapaCliente.put(cliente.getNombreUsuario(), cliente);
		empresa.setClientes(mapaCliente);
		return cliente;
	}

	public Viaje getViajeIniciadoEmpresa(Cliente cliente, int pasajeros, int km, boolean mascota, boolean baul) {
		Pedido pedido = new Pedido(cliente, pasajeros, mascota, baul, km, Constantes.ZONA_STANDARD);
		ChoferTemporario chofer = new ChoferTemporario("11222333", "Chofer temporario");
		Auto vehiculo = new Auto("AA123AA", 4, true);
		
		Viaje viaje = new Viaje(pedido, chofer, vehiculo);

		HashMap<Cliente, Viaje> mapaViajesIniciados = new HashMap<>();
		mapaViajesIniciados.put(cliente, viaje);
		empresa.setViajesIniciados(mapaViajesIniciados);
		
		return viaje;
	}
	
	public void irAlPanelDeCliente() {
		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		JButton botonLogin = (JButton)  TestUtils.getComponentForName(ventana, Constantes.LOGIN);
		
		TestUtils.clickComponent(campoNombreUsuario, robot);

		TestUtils.tipeaTexto(clienteEmpresa.getNombreUsuario(), robot);
		TestUtils.clickComponent(campoPassword, robot);
		TestUtils.tipeaTexto(clienteEmpresa.getPass(), robot);
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonLogin, robot);		
		
	}
	
	@Before
	public void setUp() throws Exception {
		controlador = new Controlador();
		ventana = (Ventana) controlador.getVista();
		
		miOptionPanel = new FalsoOptionPanel();
		ventana.setOptionPane(miOptionPanel);
		
		robot = new Robot();		
		empresa = Empresa.getInstance();
		clienteEmpresa = getClienteEmpresa();

		viajeIniciadoCliente = getViajeIniciadoEmpresa(clienteEmpresa, 2, 5, true, false);

		this.irAlPanelDeCliente();
		
		empresa.setUsuarioLogeado(clienteEmpresa);
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
		if (ventana != null) {
			ventana.dispose();
		}
    }

	@Test
	public void test_clienteConViajeIniciado_componentesParaFinalizarViaje_debenEstarHabilitados() {
		JTextField campoCalificacion = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_DE_VIAJE);
		JTextField campoValorViaje = (JTextField) TestUtils.getComponentForName(ventana, Constantes.VALOR_VIAJE);
		
		assertTrue("Error en campo calificacion", campoCalificacion.isEnabled());
		
		assertEquals("Error en el valor", viajeIniciadoCliente.getValor(), Double.parseDouble(campoValorViaje.getText()), 0.0001);


	}

	@Test
	public void test_clienteConViajeIniciado_calificarCon3_debeFinalizarViajeCorrectamente() {		
		int calificacionEsperada = 3;
		
		JTextField campoCalificacion = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_DE_VIAJE);
		JButton botonCalificar = (JButton) TestUtils.getComponentForName(ventana, Constantes.CALIFICAR_PAGAR);
		
		miOptionPanel.setMensaje(null);
		
		
		TestUtils.clickComponent(campoCalificacion, robot);
		TestUtils.tipeaTexto(String.valueOf(calificacionEsperada), robot);
		
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonCalificar, robot);
		robot.delay(TestUtils.getDelay());
		
		assertNull("No debería haber mensaje de error", miOptionPanel.getMensaje());
		
		// Verificar que el viaje se finalizó correctamente
		ArrayList<Viaje> viajesTerminados = empresa.getViajesTerminados();
		assertFalse("El viaje debería estar en viajes terminados", viajesTerminados.isEmpty());
		
		Viaje viajeTerminado = viajesTerminados.get(0);
		assertTrue("El viaje debería estar finalizado", viajeTerminado.isFinalizado());
		assertEquals("La calificación debería ser " + calificacionEsperada, calificacionEsperada, viajeTerminado.getCalificacion());
		
		assertTrue("El viaje no debería estar en viajes iniciados", empresa.getViajesIniciados().isEmpty());
	}

	@Test
	public void test_clienteConViajeIniciado_calificarConNumeroMayorA5_debeMostrarError() {
		
		int calificacionInvalida = 6;
		
		JTextField campoCalificacion = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_DE_VIAJE);
		JButton botonCalificar = (JButton) TestUtils.getComponentForName(ventana, Constantes.CALIFICAR_PAGAR);

		miOptionPanel.setMensaje(null);
		
		// Verificar que inicialmente hay un viaje iniciado
		assertFalse("Debería haber un viaje iniciado", empresa.getViajesIniciados().isEmpty());
		assertTrue("No debería haber viajes terminados inicialmente", empresa.getViajesTerminados().isEmpty());
		
		TestUtils.clickComponent(campoCalificacion, robot);
		TestUtils.tipeaTexto(String.valueOf(calificacionInvalida), robot);
		
		robot.delay(TestUtils.getDelay());
		
		assertFalse(botonCalificar.isEnabled());

	}

	@Test
	public void test_clienteConViajeTerminado_debeAparecerEnListaViajesCliente() {
		
		miOptionPanel.setMensaje(null);
		int calificacionEsperada = 3;
		
		JTextField campoCalificacion = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_DE_VIAJE);
		JButton botonCalificar = (JButton) TestUtils.getComponentForName(ventana, Constantes.CALIFICAR_PAGAR);
		JList<Viaje> listaViajesCliente = (JList<Viaje>) TestUtils.getComponentForName(ventana, Constantes.LISTA_VIAJES_CLIENTE);
		
		assertEquals("La lista debería estar vacía inicialmente", 0, listaViajesCliente.getModel().getSize());
		
		TestUtils.clickComponent(campoCalificacion, robot);
		TestUtils.tipeaTexto(String.valueOf(calificacionEsperada), robot);

		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonCalificar, robot);
		robot.delay(TestUtils.getDelay());
		
		assertEquals("El campo de calificación debería estar vacío tras calificar", "", campoCalificacion.getText());
		
		assertNull("No debería haber mensaje de error", miOptionPanel.getMensaje());
		
		// Verificar que el viaje se termino correctamente
		ArrayList<Viaje> viajesTerminados = empresa.getViajesTerminados();
		assertFalse("El viaje debería estar en viajes terminados", viajesTerminados.isEmpty());
		Viaje viajeTerminado = viajesTerminados.get(0);
		
		// La lista debe contener el viaje terminado
		DefaultListModel<Viaje> listaClienteDeViajesTerminados = (DefaultListModel<Viaje>) listaViajesCliente.getModel();
		assertEquals("La lista debería tener exactamente un viaje", 1, listaClienteDeViajesTerminados.getSize());
		
		Viaje viajeEnLista = listaClienteDeViajesTerminados.getElementAt(0);
		assertEquals("error calificacion", calificacionEsperada, viajeEnLista.getCalificacion());
		assertEquals("error, viaje no igual", viajeTerminado, viajeEnLista);
	}
}
