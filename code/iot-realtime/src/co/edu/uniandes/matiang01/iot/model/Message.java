package co.edu.uniandes.matiang01.iot.model;

public class Message {

	private String cuenta;
	private String interes;
	private String message;
	private String tweet;
	private String tone;
	private String value;
	


	public Message(String cuenta, String interes, String message, String tweet,
			String tone, String value) {
		super();
		this.cuenta = cuenta;
		this.interes = interes;
		this.message = message;
		this.tweet = tweet;
		this.tone = tone;
		this.value = value;
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

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public String getTone() {
		return tone;
	}

	public void setTone(String tone) {
		this.tone = tone;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
