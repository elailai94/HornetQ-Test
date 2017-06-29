//=============================================================================
// HornetQ-Tests
//
// @description: Module for providing functions to work with Producer objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;


import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSessionFactory;

public class Producer extends BaseClient {
  private ClientSessionFactory sessionFactory = null;
  private ClientSession session = null;

  public static void main(String[] args) throws Exception {
    try {
      new Producer().run();
      System.out.println("Producer executed successfully.");
    } catch (Exception e) {
      System.out.println("Producer wasn't able to execute successfully. An error has occurred.");
      e.printStackTrace();
    }
  }

  // Constructor
  private Producer() {
    String serverAddress = "localhost";
    int serverPort = 5445;
    super(serverAddress, serverPort);
  }

  // Send messages to the server
  private run() throws Exception {
    try {
      // 
      initialize();
      sessionFactory = getSessionFactory();

      // Create a session
      session = sessionFactory.createSession(false, false, false);

      SimpleString simpleString = new SimpleString(queueName);
      QueueQuery queueQuery = session.queueQuery(simpleString);
      if (!queueQuery.isExists()) {
        session.createQueue(queueName, queueName, false);
      }

      clientSession.close();
      clientSession = null;
    } finally {

    }
  }
}
