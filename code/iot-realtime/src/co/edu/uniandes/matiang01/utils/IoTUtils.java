package co.edu.uniandes.matiang01.utils;

import co.edu.uniandes.matiang01.iot.TemperatureSensor;
import co.edu.uniandes.matiang01.iot.model.Message;
import co.edu.uniandes.matiang01.iot.model.Notify;
import co.edu.uniandes.matiang01.iot.model.Pet;
import co.edu.uniandes.matiang01.iot.model.Tweet;

public class IoTUtils {

	public static String getData(String factory, String data) {
		// Factory - Sesortype
		String rs = "";
		if (!factory.isEmpty()) {
			try {
				TemperatureSensor sensor = new TemperatureSensor(
						data.split(";")[0].split(":")[1]);
				rs = sensor.getData();
			} catch (Exception e) {
				Log.error("getData fail: " + data);
			}
			return rs;
		}
		return rs;
	}

	public static String getPetData(String word, String count) {
		Pet mascota = null;

		if (word != null && !word.isEmpty()) {
			if (word.toLowerCase().charAt(0) == 'p') {
				mascota = new Pet(word, "perro", count);
			} else if (word.toLowerCase().charAt(0) == 'g') {
				mascota = new Pet(word, "gato", count);
			}
		}
		return mascota == null ? null : GSonUtils.serialize(mascota);
	}

	public static String getTweet(String cuenta, String interes, String datos) {
		Tweet t = null;
		t = new Tweet(cuenta,interes,datos);
		return GSonUtils.serialize(t);
	}



	public static String getNotify(String event, String usuario, String interes, String msg) {
		Notify t = null;
		Message m = new Message(usuario,interes,msg);
		t = new Notify(event,m);
		return GSonUtils.serialize(t);
	}

}
