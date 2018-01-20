package bradypod.jstorm;

import java.util.concurrent.TimeUnit;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

public class BootStrap {

	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
		
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("testSout", new TestSpout());
		builder.setBolt("testBolt", new TestBolt());
		Config conf = new Config();
		conf.setDebug(false);
		if (args != null  && args.length > 0) {
			conf.setNumWorkers(3);
			StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("firstTopology", conf, builder.createTopology());
			TimeUnit.SECONDS.sleep(10000);
			cluster.killTopology("firstTopology");
			cluster.shutdown();
		}
	}


}
