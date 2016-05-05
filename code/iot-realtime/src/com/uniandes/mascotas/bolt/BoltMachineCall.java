package com.uniandes.mascotas.bolt;

import java.util.Properties;

import backtype.storm.tuple.Tuple;
import co.edu.uniandes.matiang01.storm.bolt.MongodbBolt;

import com.elibom.client.ElibomRestClient;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class BoltMachineCall extends MongodbBolt {

	private Properties configs;

	public BoltMachineCall(String urlConnection, String db, String collection,
			Properties configs) {
		super(urlConnection, db, collection);
		this.configs = configs;
	}

	@Override
	public void execute(Tuple tuple) {
		final String interes = tuple.getStringByField("interes");
		final String datos = tuple.getStringByField("datos");

		try {

			// //bloque mongo
			Mongo mongo = new MongoClient("localhost", 27017);
			DB db;
			DBCollection tabla;
			db = mongo.getDB("mascotas");
			tabla = db.getCollection("Adopcion");

			BasicDBObject document = new BasicDBObject();

			// Aca va el llamado a la ML. Si el resultado es afirmativo se
			// ejecutan las siguientes instrucciones.

			if (!interes.isEmpty()) {
				document.put("cuenta", "'@jhlopez86'");
				document.put("interes", "'" + interes + "'");
				document.put("datos", "'" + datos + "'");

				tabla.insert(document);

				// aca enviamos el sms

				ElibomRestClient elibom = new ElibomRestClient(
						"jairo8005@hotmail.com", "LTC6RNXF57");
				String deliveryId = elibom.sendMessage("573004183070",
						"adopta un lindo " + interes
								+ " en la universidad de los Alpes");
				System.out.println("deliveryId: " + deliveryId);
				System.out.println(" SMS enviado OK!");

			}

		} catch (Exception e) {
			// System.out.println("ERROR");
		}

	}

}
