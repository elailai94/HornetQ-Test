package com.elishalai;

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

  private void createQueue() throws Exception {

  }

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
      QueueQuery queueQuery = session.queueQuery(simpleString);
      if (!queueQuery.isExists()) {
        clientSession.createQueue(queueName, queueName, true);
      }

      clientSession.close();
    }
  }

/*
  public static void main(String[] args) throws Exception {
    try {
      // Load the file configuration first of all
      FileConfiguration configuration = new FileConfiguration();
      configuration.setConfigurationUrl("hornetq-configuration.xml");
      configuration.start();

      // Create a new instance of a HornetQ server
      HornetQServer server = HornetQServers.newHornetQServer(configuration);

      // Wrap the HornetQ server instance inside a JMS server
      JMSServerManager jmsServerManager = new JMSServerManagerImpl(
        server, "hornetq-jms.xml");

      // Start the server
      jmsServerManager.start();

      System.out.println("HornetQ server started successfully.");
    } catch (Throwable e) {
      System.out.println("An error has occured. HornetQ server was unable to start.");
      e.printStackTrace();
    }
  }
*/
}
