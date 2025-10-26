package test_persistencia;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
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
import persistencia.EmpresaDTO;
import persistencia.PersistenciaBIN;
import util.AuxiliarPersistenciaTesting;
import util.Constantes;

public class Test_PersistenciaBIN {

	EmpresaDTO empresaDTOGenerico;
	PersistenciaBIN persistencia;
	private static final String NOMBRE_ARCHIVO_TEMPORAL = "tempEmpresa.bin";
	
	
	
	@Before
	public void setUp() throws Exception {
		persistencia = new PersistenciaBIN();
		File f = new File(NOMBRE_ARCHIVO_TEMPORAL);
		if(f.exists()) {
			f.delete();			
		}
	}
	
	@AfterClass
    public static void eliminarArchivoEmpresa() {
		File f = new File(NOMBRE_ARCHIVO_TEMPORAL);
		if(f.exists()) {
			f.delete();			
		}
		
    }
	
	private void crearArchivoEmpresa() {
		Path path = Path.of(NOMBRE_ARCHIVO_TEMPORAL);
		try {
			EmpresaDTO empresaAuxiliar = AuxiliarPersistenciaTesting.crearEmpresaDTOGenerico();
			AuxiliarPersistenciaTesting.guardar(empresaAuxiliar, path);
		} catch (IOException e) {
			fail("No se pudo crear el escenario del test");
		}
	}
	

//	@AfterClass
//	public static void borrarArchivoTemporal_despuesDeLasPruebas() throws Exception {
//		File f = new File(NOMBRE_ARCHIVO_TEMPORAL);
//		f.delete();
//
//	}

	@Test
	public void test_abrirYCerrarInput() {
		
		crearArchivoEmpresa();
		
		try {
			persistencia.abrirInput(NOMBRE_ARCHIVO_TEMPORAL);
			persistencia.cerrarInput();
			assertTrue(true);
		} catch (IOException e) {
			fail("El metodo lanzo excepcion a un archivo valido");
		}
	}
	
	@Test
	public void test_abrirSinInput_deberiaTirarExcepcion() {
		crearArchivoEmpresa();
		
		try {
			persistencia.cerrarInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			EmpresaDTO empresaDTO = (EmpresaDTO) persistencia.leer();
			fail("El metodo no lanzo excepcion");
		} catch (ClassNotFoundException e) {
			fail("El metodo lanzo la excepcion incorrecta");
		} catch (IOException e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void test_abrirInput_archivoNoEncontrado() {
		eliminarArchivoEmpresa();
		try {
			persistencia.abrirInput(NOMBRE_ARCHIVO_TEMPORAL);
			fail("El metodo abrio un archivo no valido");
		} catch (IOException e) {
			assertTrue(true);
		}		
	}
	
	@Test
	public void test_persistirEmpresa() {
	    eliminarArchivoEmpresa();
	    try {
	        persistencia.abrirOutput(NOMBRE_ARCHIVO_TEMPORAL);
	        EmpresaDTO esperado = AuxiliarPersistenciaTesting.crearEmpresaDTOGenerico();
	        persistencia.escribir(esperado);
	        
	        File archivoCreado = new File(NOMBRE_ARCHIVO_TEMPORAL);
	        
	        assertTrue(archivoCreado.exists());
	        
	        Path pathArchivo = Path.of(NOMBRE_ARCHIVO_TEMPORAL);
	        EmpresaDTO leido = AuxiliarPersistenciaTesting.cargar(pathArchivo); 
	        
	        assertNotNull("No se pudo leer el DTO del archivo", leido);
	        
	        assertTrue(AuxiliarPersistenciaTesting.sonIguales(esperado, leido));
	        
	        persistencia.cerrarOutput();
	    } catch (IOException | ClassNotFoundException e) {
	        fail("El método falló durante la persistencia/lectura: " + e.getMessage());
	    } finally {
	        eliminarArchivoEmpresa();
	    }
	}
	
	@Test
	public void test_leerEmpresa() {
		
		crearArchivoEmpresa();
		try {
			persistencia.abrirInput(NOMBRE_ARCHIVO_TEMPORAL);
			
			EmpresaDTO leido = (EmpresaDTO) persistencia.leer();
			EmpresaDTO esperado = AuxiliarPersistenciaTesting.crearEmpresaDTOGenerico();
			
			assertTrue(AuxiliarPersistenciaTesting.sonIguales(leido, esperado));
	        
			persistencia.cerrarInput();
		} catch (IOException | ClassNotFoundException e) {
			fail("No se puderion leer los datos del escenario");
		}
		
		
	}
	
	
	@Test
	public void test_classNotFound() {
		
		ClaseAleatoria claseAleatoria = new ClaseAleatoria();
		Path path = Path.of(NOMBRE_ARCHIVO_TEMPORAL);
		try {
			AuxiliarPersistenciaTesting.guardar(claseAleatoria, path);
		} catch (IOException e) {
			fail("No se pudieron inicializar las pruebas");
		}
		
		try {
			persistencia.abrirInput(NOMBRE_ARCHIVO_TEMPORAL);
			EmpresaDTO resultado = (EmpresaDTO) persistencia.leer();
			fail("El metodo no lanzo la expecion");
		} catch (IOException e) {
			fail("El metodo lanzo una expecion equivocada");
		} catch (ClassNotFoundException e) {
			assertTrue(true);
		}

		
		
	}
	
	
}



















