package util;

import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import modeloDatos.Auto;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Combi;
import modeloDatos.Vehiculo;
import vista.Ventana;

public class CreacionUtil {

    public static final String AUTO = "AUTO";
    public static final String COMBI = "COMBI";
    public static final String MOTO = "MOTO";

    public static void loguearCliente_crearPedido_cerrarSesion() {

    }


    /**
     * 
     *  pre: hay que estar en el panel de administrador
     * 
     */
    public static void crearVehiculoPanel(Ventana ventana, Robot robot, String tipoVehiculo, Vehiculo vehiculo) {
		JTextField campoPatente = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PATENTE);
		JTextField campoCantidadPlazas = (JTextField) TestUtils.getComponentForName(ventana,
				Constantes.CANTIDAD_PLAZAS);
		JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana,
				Constantes.CHECK_VEHICULO_ACEPTA_MASCOTA);
		JRadioButton radioMoto = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.MOTO);
		JRadioButton radioAuto = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.AUTO);
		JRadioButton radioCombi = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.COMBI);
		JButton botonAceptar = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VEHICULO);
        
        TestUtils.borraJTextField(campoPatente, robot);
        if (tipoVehiculo != MOTO) { 
            TestUtils.clickComponent(radioAuto, robot);
            TestUtils.borraJTextField(campoCantidadPlazas, robot);
        }
        
		switch (tipoVehiculo) {
		case CreacionUtil.MOTO:
			TestUtils.clickComponent(radioMoto, robot);
			TestUtils.clickeaYEscribeTexto(campoPatente, vehiculo.getPatente(), robot);
			break;
		case CreacionUtil.AUTO:
			Auto auto = (Auto) vehiculo;
			TestUtils.clickComponent(radioAuto, robot);
			TestUtils.clickeaYEscribeTexto(campoPatente, auto.getPatente(), robot);
			TestUtils.clickeaYEscribeTexto(campoCantidadPlazas, String.valueOf(auto.getCantidadPlazas()), robot);
			if (auto.isMascota() != checkMascota.isSelected()) {
				TestUtils.clickComponent(checkMascota, robot);
			}
			break;
		case CreacionUtil.COMBI:
			Combi combi = (Combi) vehiculo;
			TestUtils.clickComponent(radioCombi, robot);
			TestUtils.clickeaYEscribeTexto(campoPatente, combi.getPatente(), robot);
			TestUtils.clickeaYEscribeTexto(campoCantidadPlazas, String.valueOf(combi.getCantidadPlazas()), robot);
			if (combi.isMascota() != checkMascota.isSelected()) {
				TestUtils.clickComponent(checkMascota, robot);
			}
			break;
			
		}
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(botonAceptar, robot);
	}

    /**
     * 
     *  pre: hay que estar en el panel de administrador
     * 
     */
    public static void creaUnChoferPermanenteYTemporario(Ventana ventana, Robot robot, ChoferTemporario choferTemporario,
			ChoferPermanente choferPermanente) {
		JTextField campoDNI = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
		JTextField campoNombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
		JTextField campoAnio = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_ANIO);
		JTextField campoCantHijos = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_CANT_HIJOS);
		JRadioButton radioTemporario = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.TEMPORARIO);
		JRadioButton radioPermanente = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.PERMANENTE);
		JButton botonAceptar = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);

		// Crear chofer temporario
		if (choferTemporario != null) {
			TestUtils.clickComponent(radioTemporario, robot);
			
			TestUtils.borraJTextField(campoDNI, robot);
			TestUtils.borraJTextField(campoNombre, robot);
			
			TestUtils.clickeaYEscribeTexto(campoDNI, choferTemporario.getDni(), robot);
			TestUtils.clickeaYEscribeTexto(campoNombre, choferTemporario.getNombre(), robot);
			robot.delay(TestUtils.getDelay());
			TestUtils.clickComponent(botonAceptar, robot);
			robot.delay(TestUtils.getDelay());
		}

		// Crear chofer permanente
		if (choferPermanente != null) {
			TestUtils.clickComponent(radioPermanente, robot);
			
			TestUtils.borraJTextField(campoDNI, robot);
			TestUtils.borraJTextField(campoNombre, robot);
			TestUtils.borraJTextField(campoCantHijos, robot);
			TestUtils.borraJTextField(campoAnio, robot);
			
			TestUtils.clickeaYEscribeTexto(campoDNI, choferPermanente.getDni(), robot);
			TestUtils.clickeaYEscribeTexto(campoNombre, choferPermanente.getNombre(), robot);
			TestUtils.clickeaYEscribeTexto(campoCantHijos, String.valueOf(choferPermanente.getCantidadHijos()), robot);
			TestUtils.clickeaYEscribeTexto(campoAnio, String.valueOf(choferPermanente.getAnioIngreso()), robot);

			robot.delay(TestUtils.getDelay());
			TestUtils.clickComponent(botonAceptar, robot);
			robot.delay(TestUtils.getDelay());
		}

	}
}
