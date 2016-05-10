package com.uniandes.mascotas.bolt;

import java.util.Properties;

import backtype.storm.tuple.Tuple;
import co.edu.uniandes.matiang01.iot.model.Tone;
import co.edu.uniandes.matiang01.storm.Keys;
import co.edu.uniandes.matiang01.storm.bolt.MongodbBolt;
import co.edu.uniandes.matiang01.utils.GSonUtils;
import co.edu.uniandes.matiang01.utils.IoTUtils;
import co.edu.uniandes.matiang01.utils.Log;
import co.edu.uniandes.matiang01.utils.RestUtils;

import com.elibom.client.ElibomRestClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class BoltPersistencia extends MongodbBolt {
	
	private Properties configs;
	private static final String INTERES = "[INTERES]";
	private static final String WATSON_TONE = "watson.tone.";
	private String text="";
	private String movil="";
	private boolean smsEnabled = false;
	private String watsonUrlservice;
	
	public BoltPersistencia(String urlConnection, String db, String collection, Properties configs) {
		super(urlConnection, db, collection);
		this.configs = configs;
		this.text = configs.getProperty(Keys.MESSAGE_TEXT);
		this.movil = configs.getProperty(Keys.MESSAGE_NUMBER);
		this.smsEnabled = Boolean.valueOf(configs.getProperty(Keys.MESSAGE_ENABLED));
		this.watsonUrlservice =  configs.getProperty(Keys.WATSON_URLSERVICE);
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	@Override
	public void execute(Tuple tuple) {
		final String interes = tuple.getStringByField("interes");
		final String tweet = tuple.getStringByField("tweet");
		final String usuario = tuple.getStringByField("usuario");
		

		if (!interes.isEmpty()) {
			
				String content = IoTUtils.getTweet(usuario,interes,tweet);
				
				String predict = configs.getProperty(Keys.MACHINE_LEARNER_PREDICT);
				String auth = configs.getProperty(Keys.MACHINE_LEARNER_AUTH);
				String input = configs.getProperty(Keys.MACHINE_LEARNER_INPUT);
				
				String collectionNotify = configs.getProperty(Keys.MONGO_PET_COLLECTION_EVENTS);
				String topic = configs.getProperty(Keys.EVENTS_TOPIC);
				
				String adopta = RestUtils.call(predict,input,auth);
				
				
				Log.info("mensaje json: "+content);	
				if(content!= null){
					DBObject mongoDoc = getMongoDocForInput(content);
					
					try{
						DB database = mongoClient.getDB(db);
						watsonProccess(database,collectionNotify,topic,usuario,interes,tweet);
						mlProccess(database,interes, adopta, mongoDoc);
						collector.ack(tuple);
					}catch(Exception e) {
						e.printStackTrace();
						collector.fail(tuple);
					}		
				}
			}
		
		}
	

	/**
	 * Envía notificaciones dependiendo del tono y si supera el 50%
	 * @param database
	 * @param collectionNotify
	 * @param topic
	 * @param usuario
	 * @param interes
	 * @param tweet
	 */
	private void watsonProccess(DB database, String collectionNotify, String topic, String usuario, String interes, String tweet){
		try{
			
			Tone t = GSonUtils.getTone(RestUtils.call(watsonUrlservice, tweet));
			
			String slice = configs.getProperty(Keys.WATSON_SLICE);
			
			if(Double.valueOf(t.getValue()).doubleValue() > Double.valueOf(slice).doubleValue()){
				String tone = t.getTone().toLowerCase();
				String value = t.getValue();
				String msg = configs.getProperty(WATSON_TONE+tone);
				String contentNotify = IoTUtils.getNotify(topic,usuario,interes,msg,tweet,tone,value);
				
				DBObject mongoDocNofify = getMongoDocForInput(contentNotify);
				DBCollection dbCollectionNotify = database.getCollection(collectionNotify);
				dbCollectionNotify.insert(mongoDocNofify);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * Determina a traves en machinelearner si la persona podrìa adoptar una mascota y notifica si supera el 50%
	 * 
	 * @param database
	 * @param interes
	 * @param adopta
	 * @param mongoDoc
	 */
	private void mlProccess(DB database, final String interes, String adopta,DBObject mongoDoc) {
		
		try{
			DBCollection dbCollection = database.getCollection(collection);
			dbCollection.insert(mongoDoc);

			String msg = this.text.replace(INTERES, interes);
			
			if(Double.valueOf(adopta).doubleValue() > 0.5D){
				ElibomRestClient elibom = new ElibomRestClient("jairo8005@hotmail.com", "LTC6RNXF57");
				
				String deliveryId ="N/A";
				if(this.smsEnabled){
					deliveryId = elibom.sendMessage(movil,msg);
					System.out.println(" SMS enviado OK!");
				}
				System.out.println("deliveryId: " + deliveryId);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
