//=============================================================================
// HornetQ-Tests
//
// @description: Module for providing functions to work with Consumer objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;

import java.util.Date;
import java.util.HashMap;

import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSessionFactory;

public class Consumer {
  private static final String QUEUE_NAME = "testQueue";

  private static int numMessages = -1;
  private ClientSession session = null;

  public static void main(String[] args) throws Exception {
    
  }

  // Constructor
  private Consumer(String serverAddress, int serverPort) {
    super(serverAddress, serverPort);
  }

  
}

public class Consumer {
  private static final String queueName = "testQueue";
  private static final int numMessages = 500000;
  private static final String propName = "testProperty";

  public static void main(String[] args) throws Exception {
    try {
      // Set parameters needed to create the connection to the server
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("host", "localhost");
      parameters.put("port", 5445);

      // Create a connection between the consumer and the server
      TransportConfiguration transportConfiguration =
        new TransportConfiguration(
          NettyConnectorFactory.class.getName(), parameters
        );
      
      // Locate the server based on a list of members to the server
      ServerLocator serverLocator =
        HornetQClient.createServerLocatorWithoutHA(transportConfiguration);

      // Create entry point to create and configure HornetQ resources
      ClientSessionFactory sessionFactory = serverLocator.createSessionFactory();
      ClientSession session = null;

      try {
        // Create a session to consume messages
        session = sessionFactory.createSession();

        // Create a consumer to consume messages from the queue
        ClientConsumer consumer = session.createConsumer(queueName);

        session.start();

        final long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < numMessages; i++) {
          ClientMessage message = consumer.receive(1000);
        }
        
        final long endTime = System.currentTimeMillis();
        final double averageThroughput = calculateAverageThroughput(startTime, endTime);
        System.out.println("Average throughput: " + averageThroughput);

        session.stop();
      } finally {
        if (session != null) {
          session.close();
        }

        if (sessionFactory != null) {
          sessionFactory.close();
        }

        if (serverLocator != null) {
          serverLocator.close();
        }
      }

      System.out.println("Consumer executed successfully.");
    } catch (Exception e) {
      System.out.println("Consumer wasn't able to execute successfully. An error has occurred.");
      e.printStackTrace();
    }
  }

  // Calculates average throughput of the consumer
  private static double calculateAverageThroughput(long startTime, long endTime) {
    double duration = (1.0 * endTime - startTime) / 1000;
    double averageThroughput = 1.0 * numMessages / duration;
    
    return averageThroughput;
  }
}
