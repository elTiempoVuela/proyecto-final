package com.uniandes.mascotas.bolt;

import java.util.Properties;

import backtype.storm.tuple.Tuple;
import co.edu.uniandes.matiang01.storm.Keys;
import co.edu.uniandes.matiang01.storm.bolt.MongodbBolt;
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
	private String text="";
	private String movil="";
	private boolean smsEnabled = false;
	
	public BoltPersistencia(String urlConnection, String db, String collection, Properties configs) {
		super(urlConnection, db, collection);
		this.configs = configs;
		this.text = configs.getProperty(Keys.MESSAGE_TEXT);
		this.movil = configs.getProperty(Keys.MESSAGE_NUMBER);
		this.smsEnabled = Boolean.valueOf(configs.getProperty(Keys.MESSAGE_ENABLED));
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
						DBCollection dbCollection = database.getCollection(collection);
						dbCollection.insert(mongoDoc);

						String msg = this.text.replace(INTERES, interes);
						
						String contentNotify = IoTUtils.getNotify(topic,usuario,interes,msg);
						DBObject mongoDocNofify = getMongoDocForInput(contentNotify);
						DBCollection dbCollectionNotify = database.getCollection(collectionNotify);
						dbCollectionNotify.insert(mongoDocNofify);
						
						if(Double.valueOf(adopta).doubleValue() > 0.5D){
							ElibomRestClient elibom = new ElibomRestClient("jairo8005@hotmail.com", "LTC6RNXF57");
							
							String deliveryId ="N/A";
							if(this.smsEnabled){
								deliveryId = elibom.sendMessage(movil,msg);
								System.out.println(" SMS enviado OK!");
							}
							System.out.println("deliveryId: " + deliveryId);
						}
						
						collector.ack(tuple);
					}catch(Exception e) {
						e.printStackTrace();
						collector.fail(tuple);
					}		
				}
			}
		
		}
}
