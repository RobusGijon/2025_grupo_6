package test_gui_panelAdministrador;

import static org.junit.Assert.*;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Administrador;
import modeloNegocio.Empresa;
import util.Constantes;
import util.FalsoOptionPanel;
import util.TestUtils;
import vista.Ventana;

public class Test_Gui_PanelAdmin_GestionarPedido {

	private Ventana ventana;
	private Robot robot;
	private FalsoOptionPanel miOptionPanel;
	private Controlador controlador;
	private Empresa empresa;
	private Administrador admin;

	public void irAlPanelDelAdministrador() {
		JTextField campoNombreUsuario = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
		JTextField campoPassword = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
		JButton botonLogin = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

		TestUtils.clickComponent(campoNombreUsuario, robot);
		TestUtils.tipeaTexto(admin.getNombreUsuario(), robot);
		TestUtils.clickComponent(campoPassword, robot);
		TestUtils.tipeaTexto(admin.getPass(), robot);

		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonLogin, robot);

	}

	@Before
	public void setUp() throws Exception {
		controlador = new Controlador();
		ventana = (Ventana) controlador.getVista();

		miOptionPanel = new FalsoOptionPanel();
		ventana.setOptionPane(miOptionPanel);

		admin = Administrador.getInstance();
		robot = new Robot();
		empresa = Empresa.getInstance();
		empresa.setUsuarioLogeado(admin);

		this.irAlPanelDelAdministrador();
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
	
	

}
