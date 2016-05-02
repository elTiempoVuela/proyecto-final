package com.uniandes.mascotas.bolt;

import java.util.Properties;

import org.apache.commons.lang.mutable.MutableInt;

import backtype.storm.tuple.Tuple;
import co.edu.uniandes.matiang01.storm.Keys;
import co.edu.uniandes.matiang01.storm.bolt.MongodbBolt;
import co.edu.uniandes.matiang01.utils.IoTUtils;
import co.edu.uniandes.matiang01.utils.Log;
import co.edu.uniandes.matiang01.utils.RestUtils;

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
		final String word = tuple.getStringByField("word");
		final MutableInt count = (MutableInt) tuple.getValueByField("count");
		
			String content = (String) tuple.getString(0);
			Log.info("mensaje: "+content);	
			content = IoTUtils.getPetData( content,count.toString());
			
			String predict = configs.getProperty(Keys.MACHINE_LEARNER_PREDICT);
			String auth = configs.getProperty(Keys.MACHINE_LEARNER_AUTH);
			
			RestUtils.call(predict,"[[\"1\", \"2\", \"2\", \"2\", \"0\"]]",auth);
			
			Log.info("mensaje json: "+content);	
			if(content!= null){
				DBObject mongoDoc = getMongoDocForInput(content);
	
				try{
					DB database = mongoClient.getDB(db);
					DBCollection dbCollection = database.getCollection(collection);
					dbCollection.insert(mongoDoc);
					collector.ack(tuple);
				}catch(Exception e) {
					e.printStackTrace();
					collector.fail(tuple);
				}		
			}
		
		}
}
