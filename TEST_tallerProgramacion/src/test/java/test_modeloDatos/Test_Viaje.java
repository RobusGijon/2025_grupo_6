package test_modeloDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import util.Constantes;

public class Test_Viaje {

    private Cliente cliente;
    private Vehiculo combi;
    private Chofer choferTemp;

    @Before
    public void setUp() {
        cliente = new Cliente("nombreUsuario", "pass", "nombreReal");
        combi = new Combi("AB123CD", 8, true);
        choferTemp = new ChoferTemporario("21931123", "Armando Paredes");

    }


    @Test
    public void test_getters() {
        Pedido pedido = new Pedido(cliente, 2, false, false, 3, Constantes.ZONA_STANDARD);

        Viaje viaje = new Viaje(pedido, choferTemp, combi);
         
        assertEquals(viaje.getPedido(), pedido);
        assertEquals(viaje.getChofer(), choferTemp);
        assertEquals(viaje.getVehiculo(), combi);
        assertEquals(viaje.getCalificacion(), 0);
        assertFalse(viaje.isFinalizado());
    }
    
    public double getValorEsperado(Viaje viaje) {
        Pedido pedidoDelViaje = viaje.getPedido();

        // Porcentajes acumulados por pasajero y por km según reglas
    	double pPorPasajero = 0.0;
        double pPorKm = 0.0;

        // Incrementos por ZONA
        switch (pedidoDelViaje.getZona()) {
            case Constantes.ZONA_STANDARD:
                pPorPasajero += 0.10; // +10% por pasajero
                pPorKm       += 0.10; // +10% por km
                break;
            case Constantes.ZONA_SIN_ASFALTAR:
                pPorPasajero += 0.20; // +20% por pasajero
                pPorKm       += 0.15; // +15% por km
                break;
            case Constantes.ZONA_PELIGROSA:
                pPorPasajero += 0.10; // +10% por pasajero
                pPorKm       += 0.20; // +20% por km
                break;
            default: break;
        }

        // Incrementos traslado de mascota
        if (pedidoDelViaje.isMascota()) {
            pPorPasajero += 0.10; // +10% por pasajero
            pPorKm       += 0.20; // +20% por km
        }

        // Incrementos uso de baúl
        if (pedidoDelViaje.isBaul()) {
            pPorPasajero += 0.10; // +10% por pasajero
            pPorKm       += 0.05; // +5% por km
        }
        
        int pasajeros = pedidoDelViaje.getCantidadPasajeros();
        double kms = pedidoDelViaje.getKm();

        // Fórmula: base * (1 + %*pasajeros + %*kms)
        double total = Viaje.getValorBase() * (1.0 + pPorPasajero * pasajeros + pPorKm * kms);

        
        return total;
    }

    @Test
    public void test_getValor_zonaEstandar_sinMascotaNiBaul() {
    	/*
    	 * 	Escenario: 	Viaje por zona estandar
    	 * 
    	 * 				cliente: ("nombreUsuario", "pass", "nombreReal");
    	 * 				pedidoEstandar: (cliente, 3, SIN Mascota, SIN Baul, 10, Constantes.ZONA_STANDARD)
    	 * 				combi: ("AB123CD", 8, true);
    	 * 				choferTemp: ("21931123", "Armando Paredes");
    	 * 			
    	 * 				viajeEstandar: (pedidoEstandar, choferTemp, combi) 
    	 * 			
    	 * 	Prueba:	 	getValor deberia devolver el incremento correcto de solo la zona estandar
    	 * 			
    	 * 				>> Esperado: 2300.0
    	 * 				>> Resultado: INCORRECTO -> 1000.0
    	 * 
    	 * */
    
    	
    	Pedido pedidoEstandar = new Pedido(cliente, 3, false, false, 10, Constantes.ZONA_STANDARD);
    	Viaje viajeEstandar = new Viaje(pedidoEstandar, choferTemp, combi);
    	
    	double valorEsperado = this.getValorEsperado(viajeEstandar);
    	
    	
    	
    	String msjError = "El metodo calculo mal el valor. valorEsperado: " + valorEsperado + 
    										" | valorObtenido: " + viajeEstandar.getValor();  
    	assertEquals(msjError, valorEsperado, viajeEstandar.getValor(), 1e-5);
    	
    	
    }
    
    @Test
    public void test_getValor_zonaSinAsfaltar_sinMascotaNiBaul() {
    	/*
    	 * 	Escenario: 	Viaje por zona sin asfaltar
    	 * 
    	 * 				cliente: ("nombreUsuario", "pass", "nombreReal");
    	 * 				pedidoEstandar: (cliente, 3, SIN Mascota, SIN Baul, 10, Constantes.ZONA_SIN_ASFALTAR)
    	 * 				combi: ("AB123CD", 8, true);
    	 * 				choferTemp: ("21931123", "Armando Paredes");
    	 * 			
    	 * 				viajeEstandar: (pedidoEstandar, choferTemp, combi) 
    	 * 			
    	 * 	Prueba:	 	getValor deberia devolver el incremento correcto de solo la zona sin asfaltar
    	 * 			
    	 * 				>> Esperado: 3100.0
    	 * 				>> Resultado: Correcto
    	 * 
    	 * */
    
    	
    	Pedido pedidoEstandar = new Pedido(cliente, 3, false, false, 10, Constantes.ZONA_SIN_ASFALTAR);
    	
    	Viaje viajeEstandar = new Viaje(pedidoEstandar, choferTemp, combi);
    	
    	double valorEsperado = this.getValorEsperado(viajeEstandar);
    	
    	String msjError = "El metodo calculo mal el valor. valorEsperado: " + valorEsperado + 
    										" | valorObtenido: " + viajeEstandar.getValor();  
    	
    	assertEquals(msjError, valorEsperado, viajeEstandar.getValor(), 1e-5);
    	
    	
    }
    
    @Test
    public void test_getValor_zonaPeligrosa_sinMascotaNiBaul() {
    	/*
    	 * 	Escenario: 	Viaje por zona peligrosa
    	 * 
    	 * 				cliente: ("nombreUsuario", "pass", "nombreReal");
    	 * 				pedidoEstandar: (cliente, 3, SIN Mascota, SIN Baul, 10, Constantes.ZONA_PELIGROSA)
    	 * 				combi: ("AB123CD", 8, true);
    	 * 				choferTemp: ("21931123", "Armando Paredes");
    	 * 			
    	 * 				viajeEstandar: (pedidoEstandar, choferTemp, combi) 
    	 * 			
    	 * 	Prueba:	 	getValor deberia devolver el incremento correcto de solo la zona peligrosa
    	 * 			
    	 * 				>> Esperado: 3300.0
    	 * 				>> Resultado: Correcto
    	 * 
    	 * */
    
    	
    	Pedido pedidoEstandar = new Pedido(cliente, 3, false, false, 10, Constantes.ZONA_PELIGROSA);
    	
    	Viaje viajeEstandar = new Viaje(pedidoEstandar, choferTemp, combi);
    	
    	double valorEsperado = this.getValorEsperado(viajeEstandar);
    	
    	
    	
    	String msjError = "El metodo calculo mal el valor. valorEsperado: " + valorEsperado + 
    										" | valorObtenido: " + viajeEstandar.getValor();  
    	
    	assertEquals(msjError, valorEsperado, viajeEstandar.getValor(), 1e-5);
    	
    }
    
    @Test
    public void test_getValor_zonaPeligrosa_conMascota_SinBaul() {
    	/*
    	 * 	Escenario: 	Viaje por zona peligrosa con mascota y sin baul
    	 * 
    	 * 				cliente: ("nombreUsuario", "pass", "nombreReal");
    	 * 				pedidoEstandar: (cliente, 3, CON Mascota, SIN Baul, 10, Constantes.ZONA_PELIGROSA)
    	 * 				combi: ("AB123CD", 8, true);
    	 * 				choferTemp: ("21931123", "Armando Paredes");
    	 * 			
    	 * 				viajeEstandar: (pedidoEstandar, choferTemp, combi) 
    	 * 			
    	 * 	Prueba:	 	getValor deberia devolver el incremento correcto de solo la zona peligrosa 
    	 * 				agregando ademas el plus de llevar mascota 
    	 * 			
    	 * 				>> Esperado: 5600.0
    	 * 				>> Resultado: Correcto
    	 * 
    	 * */
    
    	
    	Pedido pedidoEstandar = new Pedido(cliente, 3, true, false, 10, Constantes.ZONA_PELIGROSA);
    	
    	Viaje viajeEstandar = new Viaje(pedidoEstandar, choferTemp, combi);
    	
    	double valorEsperado = this.getValorEsperado(viajeEstandar);
    	
    	
    	
    	String msjError = "El metodo calculo mal el valor. valorEsperado: " + valorEsperado + 
    										" | valorObtenido: " + viajeEstandar.getValor();  
    	
    	assertEquals(msjError, valorEsperado, viajeEstandar.getValor(), 1e-5);
    	
    }
    
    @Test
    public void test_getValor_zonaPeligrosa_sinMascota_conBaul() {
    	/*
    	 * 	Escenario: 	Viaje por zona peligrosa usando baul y sin mascota
    	 * 
    	 * 				cliente: ("nombreUsuario", "pass", "nombreReal");
    	 * 				pedidoEstandar: (cliente, 3, SIN Mascota, CON Baul, 10, Constantes.ZONA_PELIGROSA)
    	 * 				combi: ("AB123CD", 8, true);
    	 * 				choferTemp: ("21931123", "Armando Paredes");
    	 * 			
    	 * 				viajeEstandar: (pedidoEstandar, choferTemp, combi) 
    	 * 			
    	 * 	Prueba:	 	getValor deberia devolver el incremento correcto de solo la zona peligrosa 
    	 * 				agregando ademas el plus de usar baul
    	 * 			
    	 * 				>> Esperado: 4100.0
    	 * 				>> Resultado: INCORRECTO -> 3300.0
    	 * 
    	 * */
    
    	
    	Pedido pedidoEstandar = new Pedido(cliente, 3, false, true, 10, Constantes.ZONA_PELIGROSA);
    	
    	Viaje viajeEstandar = new Viaje(pedidoEstandar, choferTemp, combi);
    	
    	double valorEsperado = this.getValorEsperado(viajeEstandar);
    	
    	
    	
    	String msjError = "El metodo calculo mal el valor. valorEsperado: " + valorEsperado + 
    										" | valorObtenido: " + viajeEstandar.getValor();  
    	System.out.println(msjError);
    	
    	assertEquals(msjError, valorEsperado, viajeEstandar.getValor(), 1e-5);
    	
    }

    @Test
    public void test_finalizarViaje_califacion_0() {
    	
        Pedido pedido = new Pedido(cliente, 1, false, false, 1, Constantes.ZONA_STANDARD);
        Viaje viaje = new Viaje(pedido, choferTemp, combi);

        viaje.finalizarViaje(0);

        assertTrue(viaje.isFinalizado());
        assertEquals(viaje.getCalificacion(), 0);
    }

    @Test
    public void test_finalizar_calificacion_5() {
        Pedido pedido = new Pedido(cliente, 1, false, false, 1, Constantes.ZONA_STANDARD);
        Viaje viaje = new Viaje(pedido, choferTemp, combi);

        viaje.finalizarViaje(5);

        assertTrue(viaje.isFinalizado());
        assertEquals(viaje.getCalificacion(), 5);
    }
}
