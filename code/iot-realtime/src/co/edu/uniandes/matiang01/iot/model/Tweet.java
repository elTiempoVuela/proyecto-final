package co.edu.uniandes.matiang01.iot.model;

public class Tweet {

	private String cuenta;
	private String interes;
	private String datos;
	
	
	
	public Tweet(String cuenta, String interes, String datos) {
		this.cuenta = cuenta;
		this.interes = interes;
		this.datos = datos;
	}
	
	public Tweet() {
	}
	
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getInteres() {
		return interes;
	}
	public void setInteres(String interes) {
		this.interes = interes;
	}
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}
	
}
