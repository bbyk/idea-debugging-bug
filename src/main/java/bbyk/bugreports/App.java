package bbyk.bugreports;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * @author bbyk
 */
public class App {
    public static void main(String[] args) {
        /*
         * Start simple Kafka config locally following
         * https://www.mapr.com/blog/getting-started-sample-programs-apache-kafka-09
         * 
         * Stop after running
         * bin/zookeeper-server-start.sh config/zookeeper.properties &
         * 
         * 1. Run the code below having put a break-point on the line 43
         * 2. Step over and observe that "stream" variable in debugger is stuck in "Collecting data... "
         * 3. Step over again and observe that debugger has crashed
         */
        final Properties consumerProperties = new Properties();
        consumerProperties.put("zookeeper.connect", "localhost:2181");
        consumerProperties.put("group.id", UUID.randomUUID().toString());
        final String topic = "dummy";
        final ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(consumerProperties));
        final Map<String, Integer> topicCounts = new HashMap<>();
        topicCounts.put(topic, 1);

        final StringDecoder stringDecoder = new StringDecoder(new VerifiableProperties(consumerProperties));
        final Map<String, List<KafkaStream<String, String>>> messageStreams = consumer.createMessageStreams(topicCounts, stringDecoder, stringDecoder);
        final KafkaStream<String, String> stream = messageStreams.get(topic).iterator().next();

        int foo = 5;
        int bar = 6;

    }
}
