package com.uniandes.mascotas.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class BoltDataBuild extends BaseRichBolt {

	private OutputCollector collector = null;

	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		final String interes = input.getStringByField("interes");
		final String tweet = input.getStringByField("tweet");
		final String usuario = input.getStringByField("usuario");
		
		collector.emit(new Values(interes,tweet,usuario));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("interes", "tweet","usuario"));
	}
}
