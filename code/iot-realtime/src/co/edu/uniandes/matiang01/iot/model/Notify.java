package co.edu.uniandes.matiang01.iot.model;

public class Notify {

	private String event;
	private Message message;

	public Notify() {
	}

	
	public Notify(String event, Message message) {
		super();
		this.event = event;
		this.message = message;
	}



	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
