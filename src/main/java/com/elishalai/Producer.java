//=============================================================================
// HornetQ-Tests
//
// @description: Module for providing functions to work with Producer objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;

import java.util.Date;

import org.hornetq.api.core.SimpleString;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSession.QueueQuery;
import org.hornetq.api.core.client.ClientSessionFactory;

public class Producer extends BaseClient {
  private static final String QUEUE_NAME = "testQueue";
  private static final String PROPERTY_NAME = "timestamp";
  
  private static int numMessages = -1;
  private ClientSession session = null;

  public static void main(String[] args) throws Exception {
    try {
      String serverAddress = args[0];
      int serverPort = Integer.parseInt(args[1]);
      numMessages = Integer.parseInt(args[2]);
      
      new Producer(serverAddress, serverPort).run();
      System.out.println("Producer executed successfully.");
    } catch (Exception e) {
      System.out.println("Producer wasn't able to execute successfully. An error has occurred.");
      e.printStackTrace();
    }
  }

  // Constructor
  private Producer(String serverAddress, int serverPort) {
    super(serverAddress, serverPort);
  }

  // Send messages to the server
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

      // Create a session to produce messages
      session = getSessionFactory().createSession();

      // Create a producer to produce messages to the queue
      ClientProducer producer = session.createProducer(QUEUE_NAME);

      // Produce messages to the queue
      long duration = 0;
      for (int i = 0; i < numMessages; i++) {
        ClientMessage message = session.createMessage(true);
        message.putLongProperty(PROPERTY_NAME, System.currentTimeMillis());

        long startTime = System.currentTimeMillis();
        producer.send(message);
        long endTime = System.currentTimeMillis();
        duration += endTime - startTime;
      }

      // Calculate the throughput of the producer
      double throughput = calculateThroughput(numMessages, duration);
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
