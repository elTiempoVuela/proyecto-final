package co.edu.uniandes.matiang01.iot.model;

public class Tone {

	private String tone;
	private String value;
	
	public Tone() {
	}
	
	
	public Tone(String tone, String value) {
		super();
		this.tone = tone;
		this.value = value;
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
