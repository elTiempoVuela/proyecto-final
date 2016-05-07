package co.edu.uniandes.matiang01.iot.model;

public class Message {

	private String cuenta;
	private String interes;
	private String message;

	public Message(String cuenta, String interes, String message) {
		this.cuenta = cuenta;
		this.interes = interes;
		this.message = message;
	}

	public Message() {
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
