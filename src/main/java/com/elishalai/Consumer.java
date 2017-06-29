//=============================================================================
// HornetQ-Tests
//
// @description: Module for providing functions to work with Consumer objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;

import java.util.Date;

import org.hornetq.api.core.SimpleString;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSession.QueueQuery;
import org.hornetq.api.core.client.ClientSessionFactory;

public class Consumer extends BaseClient {
  private static final String QUEUE_NAME = "testQueue";

  private static int numMessages = -1;
  private ClientSession session = null;

  public static void main(String[] args) throws Exception {
    try {
      String serverAddress = args[0];
      int serverPort = Integer.parseInt(args[1]);
      numMessages = Integer.parseInt(args[2]);
      
      new Consumer(serverAddress, serverPort).run();
      System.out.println("Consumer executed successfully.");
    } catch (Exception e) {
      System.out.println("Consumer wasn't able to execute successfully. An error has occurred.");
      e.printStackTrace();
    }
  }

  // Constructor
  private Consumer(String serverAddress, int serverPort) {
    super(serverAddress, serverPort);
  }

  // Receive messages from the server
  private void run() throws Exception {
    try {
      // Initialize the base client
      initialize();

      // Create a session to check if the queue exists
      session = getSessionFactory().createSession(false, false, false);

      // Check if the queue exists. If not, create the queue
      SimpleString simpleString = new SimpleString(QUEUE_NAME);
      QueueQuery queueQuery = session.queueQuery(simpleString);
      if (!queueQuery.isExists()) {
        session.createQueue(QUEUE_NAME, QUEUE_NAME, false);
      }

      session.close();
      session = null;

      // Create a session to consume messages
      session = getSessionFactory().createSession();

      // Create a consumer to consume messages from the queue
      ClientConsumer consumer = session.createConsumer(QUEUE_NAME);

      session.start();
      final long startTime = System.currentTimeMillis();
      
      // Consume messages from the queue  
      for (int i = 0; i < numMessages; i++) {
        ClientMessage message = consumer.receive(0);
      }
        
      final long endTime = System.currentTimeMillis();
      session.stop();

      // Calculate the throughput of the producer
      final double throughput =
        calculateThroughput(numMessages, startTime, endTime);
      System.out.println(
        String.format("Throughtput: %.2f msg/s", throughput));
    } catch (Exception e) {
      throw e;
    } finally {
      if (session != null) {
        session.close();
      }

      // Clean up the base client
      cleanup();
    }
  }
}
