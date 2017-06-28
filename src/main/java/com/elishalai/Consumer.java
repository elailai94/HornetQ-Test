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

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.api.core.client.HornetQClient;
import org.hornetq.api.core.client.ServerLocator;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;

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

        final long start = System.currentTimeMillis();
        for (int i = 0; i < numMessages; i++) {
          ClientMessage message = consumer.receive(1000);
        }

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
}
