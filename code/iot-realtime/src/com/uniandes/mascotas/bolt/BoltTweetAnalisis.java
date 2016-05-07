package com.uniandes.mascotas.bolt;

import java.util.Map;
import java.util.Properties;

import twitter4j.Status;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import co.edu.uniandes.matiang01.storm.Keys;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class BoltTweetAnalisis extends BaseRichBolt {

	private static final String TWEET_WORD = "tweet.word.";
	private OutputCollector collector = null;
	private Properties configs;
	
	public BoltTweetAnalisis(Properties configs) {
		this.configs = configs;
	}

	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple tuple) {
		final Status tweet = (Status) tuple.getValueByField("status");
                String interes="";
                String tuit = tweet.getText();
                String usuario = tweet.getUser().getScreenName();
                
                String [] words = this.configs.getProperty(Keys.TWEET_WORDS).split(";");
                   
                int index = 0;
                interes = "indiferente";
                
                do{
                	
                	
                	//Para cada palabra se extraen las palabras asociadas o similares
                	String [] word= this.configs.getProperty(TWEET_WORD+words[index]).split(";"); 
                
                	for (String text : word) {
						if(tuit.indexOf(text) >=0 ){
							interes = words[index];
							break;
						}
					}
                	index++;
                	
                }while (index < words.length);
                collector.emit(new Values(interes,tuit,usuario));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("interes","tweet","usuario"));
	}
	
	
}
