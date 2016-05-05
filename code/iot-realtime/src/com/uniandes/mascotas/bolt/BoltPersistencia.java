package com.uniandes.mascotas.bolt;

import java.util.Properties;

import backtype.storm.tuple.Tuple;
import co.edu.uniandes.matiang01.storm.Keys;
import co.edu.uniandes.matiang01.storm.bolt.MongodbBolt;
import co.edu.uniandes.matiang01.utils.IoTUtils;
import co.edu.uniandes.matiang01.utils.Log;

import com.elibom.client.ElibomRestClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class BoltPersistencia extends MongodbBolt {
	
	private Properties configs;
	
	public BoltPersistencia(String urlConnection, String db, String collection, Properties configs) {
		super(urlConnection, db, collection);
		this.configs = configs;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	@Override
	public void execute(Tuple tuple) {
		final String interes = tuple.getStringByField("interes");
		final String datos = tuple.getStringByField("datos");
		

		if (!interes.isEmpty()) {
			
		}
			System.out.println("******************************************************************************************");
			System.out.println(interes);
			System.out.println(datos);
			System.out.println("******************************************************************************************");
			String content = IoTUtils.getTweet("@jhlopez86",interes,datos);
			
			String predict = configs.getProperty(Keys.MACHINE_LEARNER_PREDICT);
			String auth = configs.getProperty(Keys.MACHINE_LEARNER_AUTH);
			
			//String adopta = RestUtils.call(predict,"[[\"1\", \"2\", \"2\", \"2\", \"0\"]]",auth);
			
			
			Log.info("mensaje json: "+content);	
			if(content!= null){
				DBObject mongoDoc = getMongoDocForInput(content);
	
				try{
					DB database = mongoClient.getDB(db);
					DBCollection dbCollection = database.getCollection(collection);
					dbCollection.insert(mongoDoc);
					
					//if(adopta.equals("SI")){
						ElibomRestClient elibom = new ElibomRestClient("jairo8005@hotmail.com", "LTC6RNXF57");
						//String deliveryId = elibom.sendMessage("573004183070","adopta un lindo " + interes+ " en la universidad de los Alpes");
						//System.out.println("deliveryId: " + deliveryId);
						System.out.println(" SMS enviado OK!");
					//}
					
					collector.ack(tuple);
				}catch(Exception e) {
					e.printStackTrace();
					collector.fail(tuple);
				}		
			}
		
		}
}
