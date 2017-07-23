//=============================================================================
// HornetQ-Test
//
// @description: Module for providing functions to work with Producer objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;

import java.nio.ByteBuffer;
import java.util.Random;

import org.hornetq.api.core.HornetQBuffer;
import org.hornetq.api.core.SimpleString;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSession.QueueQuery;
import org.hornetq.api.core.client.ClientSessionFactory;

public class ProducerClient extends BaseClient {
  private static final String QUEUE_NAME = "testQueue";
  
  private static int numMessages = -1;
  private static int messageSize = -1;
  private static Logger throughputLogWriter = null;
  private ClientSession session = null;

  public static void main(String[] arguments) throws Exception {
    try {
      String serverAddress = arguments[0];
      int serverPort = Integer.parseInt(arguments[1]);
      numMessages = Integer.parseInt(arguments[2]);
      messageSize = Integer.parseInt(arguments[3]);

      throughputLogWriter = new Logger("producer-throughput.csv");
      throughputLogWriter.logThroughputLogHeader();
      
      new ProducerClient(serverAddress, serverPort).run();
      System.out.println("Producer executed successfully.");
    } catch (Exception exception) {
      System.out.println("Producer wasn't able to execute successfully. An error has occurred.");
      exception.printStackTrace();
    } finally {
      throughputLogWriter.close();
    }
  }

  // Constructor
  private ProducerClient(String serverAddress, int serverPort) {
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
        session.createQueue(QUEUE_NAME, QUEUE_NAME, true);
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
        ClientMessage message = createMessage(messageSize);

        long startTime = System.currentTimeMillis();
        producer.send(message);
        long endTime = System.currentTimeMillis();
        duration += endTime - startTime;
      }

      // Calculate the throughput of the producer
      double throughput = calculateThroughput(numMessages, duration);
      throughputLogWriter.logThroughputLogEntry(numMessages, duration, throughput);
    } catch (Exception exception) {
      throw exception;
    } finally {
      if (session != null) {
        session.close();
      }

      // Clean up the base client
      cleanup();
    }
  }

  // Create message
  private ClientMessage createMessage(int size) {
    byte[] bytes = new byte[size];
    Random random = new Random();

    // Generate random bytes to fill up byte array
    random.nextBytes(bytes);

    // Put current timestamp in the first four bytes of the byte array
    ClientMessage message = session.createMessage(true);
    HornetQBuffer buffer = message.getBodyBuffer();
    buffer.writeLong(System.currentTimeMillis());
    buffer.writeBytes(bytes);

    return message;
  }
}
