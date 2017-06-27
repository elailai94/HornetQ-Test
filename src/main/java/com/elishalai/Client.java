package com.elishalai;

import java.util.Date;
import java.util.HashMap;

import org.hornetq.api.core.SimpleString;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSession.QueueQuery;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.api.core.client.HornetQClient;
import org.hornetq.api.core.client.ServerLocator;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;

public class Client {
  private static final String queueName = "testQueue";
  private static final int numMessages = 10;
  private static final String propName = "testProperty";

  public static void main(String[] args) throws Exception {
    try {
      // Set configuration values
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("host", "localhost");
      map.put("port", 5445);

      TransportConfiguration transportConfiguration =
        new TransportConfiguration(NettyConnectorFactory.class.getName(), map);
      ServerLocator serverLocator =
        HornetQClient.createServerLocatorWithoutHA(transportConfiguration);

      ClientSessionFactory clientSessionFactory =
        serverLocator.createSessionFactory();

      ClientSession clientSession =
        clientSessionFactory.createSession(false, false, false);

      SimpleString simpleString = new SimpleString(queueName);
      QueueQuery queueQuery = clientSession.queueQuery(simpleString);
      if (!queueQuery.isExists()) {
        clientSession.createQueue(queueName, queueName, true);
      }

      clientSession.close();
      clientSession = null;

      try {
        clientSession = clientSessionFactory.createSession();

        ClientProducer clientProducer = clientSession.createProducer(queueName);
        for (int i = 0; i < numMessages; i++) {
          ClientMessage messageToServer = clientSession.createMessage(true);
          messageToServer.putStringProperty(propName, "Hello sent at " + new Date());
          //System.out.println("Sending the message.");
          clientProducer.send(messageToServer);
        }

        ClientConsumer clientConsumer = clientSession.createConsumer(queueName);
        clientSession.start();
        for (int i = 0; i < numMessages; i++) {
          ClientMessage messageFromServer = clientConsumer.receive(1000);
          //System.out.println("Received TextMessage:" + messageFromServer.getStringProperty(propName));
        }
      } finally {
        if (clientSession != null) {
          clientSession.close();
        }

        if (clientSessionFactory != null) {
          clientSessionFactory.close();
        }

        if (serverLocator != null) {
          serverLocator.close();
        }
      }

      System.out.println("HornetQ Client executed successfully.");
    } catch (Exception e) {
      System.out.println("HornetQ Client wasn't able to execute successfully. An error has occured");
      e.printStackTrace();
    }
  }
}
