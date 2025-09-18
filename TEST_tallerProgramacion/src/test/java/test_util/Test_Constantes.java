package test_util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.Constantes;

public class Test_Constantes {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkLOGIN() {
        String LOGIN = "LOGIN";
        Assert.assertEquals(Constantes.LOGIN, LOGIN);
    }

    @Test
    public void checkREGISTRAR() {
        String REGISTRAR = "REGISTRAR";
        Assert.assertEquals(Constantes.REGISTRAR, REGISTRAR);
    }

    @Test
    public void checkNOMBRE_USUARIO() {
        String NOMBRE_USUARIO = "NOMBRE_USUARIO";
        Assert.assertEquals(Constantes.NOMBRE_USUARIO, NOMBRE_USUARIO);
    }

    @Test
    public void checkPASSWORD() {
        String PASSWORD = "PASSWORD";
        Assert.assertEquals(Constantes.PASSWORD, PASSWORD);
    }

    @Test
    public void checkREG_BUTTON_REGISTRAR() {
        String REG_BUTTON_REGISTRAR = "REG_BUTTON_REGISTRAR";
        Assert.assertEquals(Constantes.REG_BUTTON_REGISTRAR, REG_BUTTON_REGISTRAR);
    }

    @Test
    public void checkREG_BUTTON_CANCELAR() {
        String REG_BUTTON_CANCELAR = "REG_BUTTON_CANCELAR";
        Assert.assertEquals(Constantes.REG_BUTTON_CANCELAR, REG_BUTTON_CANCELAR);
    }

    @Test
    public void checkREG_REAL_NAME() {
        String REG_REAL_NAME = "REG_REAL_NAME";
        Assert.assertEquals(Constantes.REG_REAL_NAME, REG_REAL_NAME);
    }

    @Test
    public void checkREG_USSER_NAME() {
        String REG_USSER_NAME = "REG_USSER_NAME";
        Assert.assertEquals(Constantes.REG_USSER_NAME, REG_USSER_NAME);
    }

    @Test
    public void checkREG_PASSWORD() {
        String REG_PASSWORD = "REG_PASSWORD";
        Assert.assertEquals(Constantes.REG_PASSWORD, REG_PASSWORD);
    }

    @Test
    public void checkREG_CONFIRM_PASSWORD() {
        String REG_CONFIRM_PASSWORD = "REG_CONFIRM_PASSWORD";
        Assert.assertEquals(Constantes.REG_CONFIRM_PASSWORD, REG_CONFIRM_PASSWORD);
    }

    @Test
    public void checkCH_CANT_HIJOS() {
        String CH_CANT_HIJOS = "CH_CANT_HIJOS";
        Assert.assertEquals(Constantes.CH_CANT_HIJOS, CH_CANT_HIJOS);
    }

    @Test
    public void checkCH_ANIO() {
        String CH_ANIO = "CH_FECHA";
        Assert.assertEquals(Constantes.CH_ANIO, CH_ANIO);
    }

    @Test
    public void checkZONA_STANDARD() {
        String ZONA_STANDARD = "ZONA_STANDARD";
        Assert.assertEquals(Constantes.ZONA_STANDARD, ZONA_STANDARD);
    }

    @Test
    public void checkZONA_PELIGROSA() {
        String ZONA_PELIGROSA = "ZONA_PELIGROSA";
        Assert.assertEquals(Constantes.ZONA_PELIGROSA, ZONA_PELIGROSA);
    }

    @Test
    public void checkZONA_SIN_ASFALTAR() {
        String ZONA_SIN_ASFALTAR = "ZONA_SIN_ASFALTAR";
        Assert.assertEquals(Constantes.ZONA_SIN_ASFALTAR, ZONA_SIN_ASFALTAR);
    }

    @Test
    public void checkCHECK_BAUL() {
        String CHECK_BAUL = "CHECK_BAUL";
        Assert.assertEquals(Constantes.CHECK_BAUL, CHECK_BAUL);
    }

    @Test
    public void checkCHECK_MASCOTA() {
        String CHECK_MASCOTA = "CHECK_MASCOTA";
        Assert.assertEquals(Constantes.CHECK_MASCOTA, CHECK_MASCOTA);
    }

    @Test
    public void checkCANT_KM() {
        String CANT_KM = "CANT_KM";
        Assert.assertEquals(Constantes.CANT_KM, CANT_KM);
    }

    @Test
    public void checkCANT_PAX() {
        String CANT_PAX = "CANT_PAX";
        Assert.assertEquals(Constantes.CANT_PAX, CANT_PAX);
    }

    @Test
    public void checkNUEVO_PEDIDO() {
        String NUEVO_PEDIDO = "NUEVO_PEDIDO";
        Assert.assertEquals(Constantes.NUEVO_PEDIDO, NUEVO_PEDIDO);
    }

    @Test
    public void checkCALIFICAR_PAGAR() {
        String CALIFICAR_PAGAR = "CALIFICAR_PAGAR";
        Assert.assertEquals(Constantes.CALIFICAR_PAGAR, CALIFICAR_PAGAR);
    }

    @Test
    public void checkCALIFICACION_DE_VIAJE() {
        String CALIFICACION_DE_VIAJE = "CALIFICACION_DE_VIAJE";
        Assert.assertEquals(Constantes.CALIFICACION_DE_VIAJE, CALIFICACION_DE_VIAJE);
    }

    @Test
    public void checkADMINISTRADOR() {
        String ADMINISTRADOR = "ADMINISTRADOR";
        Assert.assertEquals(Constantes.ADMINISTRADOR, ADMINISTRADOR);
    }

    @Test
    public void checkNO_LOGEADO() {
        String NO_LOGEADO = "NO_LOGEADO";
        Assert.assertEquals(Constantes.NO_LOGEADO, NO_LOGEADO);
    }

    @Test
    public void checkCERRAR_SESION_CLIENTE() {
        String CERRAR_SESION_CLIENTE = "CERRAR_SESION_CLIENTE";
        Assert.assertEquals(Constantes.CERRAR_SESION_CLIENTE, CERRAR_SESION_CLIENTE);
    }

    @Test
    public void checkLISTA_CANDIDATOS() {
        String LISTA_CANDIDATOS = "LISTA_CANDIDATOS";
        Assert.assertEquals(Constantes.LISTA_CANDIDATOS, LISTA_CANDIDATOS);
    }

    @Test
    public void checkNUEVO_CHOFER() {
        String NUEVO_CHOFER = "NUEVO_CHOFER";
        Assert.assertEquals(Constantes.NUEVO_CHOFER, NUEVO_CHOFER);
    }

    @Test
    public void checkNUEVO_VEHICULO() {
        String NUEVO_VEHICULO = "NUEVO_VEHICULO";
        Assert.assertEquals(Constantes.NUEVO_VEHICULO, NUEVO_VEHICULO);
    }

    @Test
    public void checkNUEVO_VIAJE() {
        String NUEVO_VIAJE = "NUEVO_VIAJE";
        Assert.assertEquals(Constantes.NUEVO_VIAJE, NUEVO_VIAJE);
    }

    @Test
    public void checkPEDIDO_O_VIAJE_ACTUAL() {
        String PEDIDO_O_VIAJE_ACTUAL = "PEDIDO_O_VIAJE_ACTUAL";
        Assert.assertEquals(Constantes.PEDIDO_O_VIAJE_ACTUAL, PEDIDO_O_VIAJE_ACTUAL);
    }

    @Test
    public void checkDETALLE_VIAJE_CLIENTE() {
        String DETALLE_VIAJE_CLIENTE = "DETALLE_VIAJE_CLIENTE";
        Assert.assertEquals(Constantes.DETALLE_VIAJE_CLIENTE, DETALLE_VIAJE_CLIENTE);
    }

    @Test
    public void checkLISTA_VIAJES_CLIENTE() {
        String LISTA_VIAJES_CLIENTE = "LISTA_VIAJES_CLIENTE";
        Assert.assertEquals(Constantes.LISTA_VIAJES_CLIENTE, LISTA_VIAJES_CLIENTE);
    }

    @Test
    public void checkMOTO() {
        String MOTO = "MOTO";
        Assert.assertEquals(Constantes.MOTO, MOTO);
    }

    @Test
    public void checkAUTO() {
        String AUTO = "AUTO";
        Assert.assertEquals(Constantes.AUTO, AUTO);
    }

    @Test
    public void checkCOMBI() {
        String COMBI = "COMBI";
        Assert.assertEquals(Constantes.COMBI, COMBI);
    }

    @Test
    public void checkPERMANENTE() {
        String PERMANENTE = "PERMANENTE";
        Assert.assertEquals(Constantes.PERMANENTE, PERMANENTE);
    }

    @Test
    public void checkTEMPORARIO() {
        String TEMPORARIO = "TEMPORARIO";
        Assert.assertEquals(Constantes.TEMPORARIO, TEMPORARIO);
    }

    @Test
    public void checkCERRAR_SESION_ADMIN() {
        String CERRAR_SESION_ADMIN = "CERRAR_SESION_ADMIN";
        Assert.assertEquals(Constantes.CERRAR_SESION_ADMIN, CERRAR_SESION_ADMIN);
    }

    @Test
    public void checkPANEL_CLIENTE() {
        String PANEL_CLIENTE = "PANEL_CLIENTE";
        Assert.assertEquals(Constantes.PANEL_CLIENTE, PANEL_CLIENTE);
    }

    @Test
    public void checkDNI_CHOFER() {
        String DNI_CHOFER = "DNI_CHOFER";
        Assert.assertEquals(Constantes.DNI_CHOFER, DNI_CHOFER);
    }

    @Test
    public void checkNOMBRE_CHOFER() {
        String NOMBRE_CHOFER = "NOMBRE_CHOFER";
        Assert.assertEquals(Constantes.NOMBRE_CHOFER, NOMBRE_CHOFER);
    }

    @Test
    public void checkPATENTE() {
        String PATENTE = "PATENTE";
        Assert.assertEquals(Constantes.PATENTE, PATENTE);
    }

    @Test
    public void checkCANTIDAD_PLAZAS() {
        String CANTIDAD_PLAZAS = "CANTIDAD_PLAZAS";
        Assert.assertEquals(Constantes.CANTIDAD_PLAZAS, CANTIDAD_PLAZAS);
    }

    @Test
    public void checkLISTA_PEDIDOS_PENDIENTES() {
        String LISTA_PEDIDOS_PENDIENTES = "LISTA_PEDIDOS_PENDIENTES";
        Assert.assertEquals(Constantes.LISTA_PEDIDOS_PENDIENTES, LISTA_PEDIDOS_PENDIENTES);
    }

    @Test
    public void checkLISTA_CHOFERES_LIBRES() {
        String LISTA_CHOFERES_LIBRES = "LISTA_CHOFERES_LIBRES";
        Assert.assertEquals(Constantes.LISTA_CHOFERES_LIBRES, LISTA_CHOFERES_LIBRES);
    }

    @Test
    public void checkLISTA_VEHICULOS_DISPONIBLES() {
        String LISTA_VEHICULOS_DISPONIBLES = "LISTA_VEHICULOS_DISPONIBLES";
        Assert.assertEquals(Constantes.LISTA_VEHICULOS_DISPONIBLES, LISTA_VEHICULOS_DISPONIBLES);
    }

    @Test
    public void checkLISTA_VEHICULOS_TOTALES() {
        String LISTA_VEHICULOS_TOTALES = "LISTA_VEHICULOS_TOTALES";
        Assert.assertEquals(Constantes.LISTA_VEHICULOS_TOTALES, LISTA_VEHICULOS_TOTALES);
    }

    @Test
    public void checkLISTA_VIAJES_DE_CHOFER() {
        String LISTA_VIAJES_DE_CHOFER = "LISTA_VIAJES_DE_CHOFER";
        Assert.assertEquals(Constantes.LISTA_VIAJES_DE_CHOFER, LISTA_VIAJES_DE_CHOFER);
    }

    @Test
    public void checkCALIFICACION_CHOFER() {
        String CALIFICACION_CHOFER = "CALIFICACION_CHOFER";
        Assert.assertEquals(Constantes.CALIFICACION_CHOFER, CALIFICACION_CHOFER);
    }

    @Test
    public void checkTOTAL_SUELDOS_A_PAGAR() {
        String TOTAL_SUELDOS_A_PAGAR = "TOTAL_SUELDOS_A_PAGAR";
        Assert.assertEquals(Constantes.TOTAL_SUELDOS_A_PAGAR, TOTAL_SUELDOS_A_PAGAR);
    }

    @Test
    public void checkSUELDO_DE_CHOFER() {
        String SUELDO_DE_CHOFER = "SUELDO_DE_CHOFER";
        Assert.assertEquals(Constantes.SUELDO_DE_CHOFER, SUELDO_DE_CHOFER);
    }

    @Test
    public void checkLISTADO_DE_CLIENTES() {
        String LISTADO_DE_CLIENTES = "LISTADO_DE_CLIENTES";
        Assert.assertEquals(Constantes.LISTADO_DE_CLIENTES, LISTADO_DE_CLIENTES);
    }

    @Test
    public void checkLISTA_VIAJES_HISTORICOS() {
        String LISTA_VIAJES_HISTORICOS = "LISTA_VIAJES_HISTORICOS";
        Assert.assertEquals(Constantes.LISTA_VIAJES_HISTORICOS, LISTA_VIAJES_HISTORICOS);
    }

    @Test
    public void checkVALOR_VIAJE() {
        String VALOR_VIAJE = "VALOR_VIAJE";
        Assert.assertEquals(Constantes.VALOR_VIAJE, VALOR_VIAJE);
    }

    @Test
    public void checkCHECK_VEHICULO_ACEPTA_MASCOTA() {
        String CHECK_VEHICULO_ACEPTA_MASCOTA = "CHECK_VEHICULO_ACEPTA_MASCOTA";
        Assert.assertEquals(Constantes.CHECK_VEHICULO_ACEPTA_MASCOTA, CHECK_VEHICULO_ACEPTA_MASCOTA);
    }
}
