package bradypod.jstorm;

import java.io.IOException;
import java.util.Properties;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

public class TestTopology implements ILogTopology {

	@Override
	public void start(Properties properties)
			throws AlreadyAliveException, InvalidTopologyException, InterruptedException, IOException {

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("testspout", new TestSpout(), 1);
		builder.setBolt("testbolt", new TestBolt(), 2).shuffleGrouping("testspout");

		Config conf = ConfigUtils.getStormConfig(properties);
		conf.setNumAckers(1);

		StormSubmitter.submitTopology("testtopology", conf, builder.createTopology());
		System.out.println("storm cluster will start");
	}

}
