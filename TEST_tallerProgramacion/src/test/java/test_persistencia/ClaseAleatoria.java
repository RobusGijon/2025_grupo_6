package test_persistencia;

import java.io.Serializable;

public class ClaseAleatoria implements Serializable{

	private static final long serialVersionUID = 1L;

	public int x, y;
	public String mensaje;
	public double z;
	
	
	public ClaseAleatoria() {
		this.x = 1;
		this.y = 231231;
		this.z = 492132.3123941;
		this.mensaje = "Soy una clase aleatoria";
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public double getZ() {
		return z;
	}


	public void setZ(double z) {
		this.z = z;
	}
	
	
}
