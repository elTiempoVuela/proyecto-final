package com.uniandes.mascotas.spout;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class SpoutTweetsStreamingConsumer extends BaseRichSpout {

	private SpoutOutputCollector collector;
	private LinkedBlockingQueue<Status> queue;
	private TwitterStream twitterStream;
	private String [] accounts;
	
	public SpoutTweetsStreamingConsumer (String [] accounts){
		this.accounts = accounts;
	}
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		this.twitterStream = new TwitterStreamFactory().getInstance();
                 
        this.queue = new LinkedBlockingQueue<Status>();
		
		final StatusListener listener = new StatusListener() {
                    

			@Override
			public void onStatus(Status status) {
				queue.offer(status);
                System.out.println("JHLCSTATUS: " + status.getText());
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice sdn) {
			}

			@Override
			public void onTrackLimitationNotice(int i) {
			}

			@Override
			public void onScrubGeo(long l, long l1) {
			}

			@Override
			public void onException(Exception e) {
			}

			@Override
			public void onStallWarning(StallWarning warning) {
			}
		};

		twitterStream.addListener(listener);
	}

	@Override
	public void nextTuple() {
		final Status status = queue.poll();
		if (status == null) {
			Utils.sleep(50);
		} else {
			collector.emit(new Values(status));
		}
	}

	@Override
	public void activate() {
		//twitterStream.sample();
            FilterQuery fq = new FilterQuery();
            long filtros[] = SpoutTweetsStreamingConsumer.toLong(accounts);
            fq.follow(filtros);
            twitterStream.filter(fq);
	};
	
	@Override
	public void deactivate() {
		twitterStream.cleanUp();
	};

	@Override
	public void close() {
		twitterStream.shutdown();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("status"));
	}

	public static long[] toLong(String[] intArray) {

		long[] result = new long[intArray.length];
		for (int i = 0; i < intArray.length; i++) {
			result[i] = Long.valueOf(intArray[i]);
			System.out.println(result[i]);
		}
		return result;
	}
}
