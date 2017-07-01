//=============================================================================
// HornetQ-Tests
//
// @description: Module for providing functions to work with BaseClient objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;

import java.util.HashMap;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.api.core.client.HornetQClient;
import org.hornetq.api.core.client.ServerLocator;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;

public abstract class BaseClient {
  private String serverAddress = "";
  private int serverPort = -1;

  private ServerLocator serverLocator = null;
  private ClientSessionFactory sessionFactory = null;

  // Constructor
  protected BaseClient(String serverAddress, int serverPort) {
    this.serverAddress = serverAddress;
    this.serverPort = serverPort;
  }

  // Perform client initialization
  protected void initialize() throws Exception {
    // Set parameters needed to create the connection to the server
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("host", serverAddress);
    parameters.put("port", serverPort);

    // Create a connection between the client and the server
    TransportConfiguration transportConfiguration =
      new TransportConfiguration(
        NettyConnectorFactory.class.getName(), parameters
      );

    // Locate the server based on a list of members to the server
    serverLocator =
      HornetQClient.createServerLocatorWithoutHA(transportConfiguration);

    // Create entry point to create and configure HornetQ resources
    sessionFactory = serverLocator.createSessionFactory();
  }

  // Return the session factory
  protected ClientSessionFactory getSessionFactory() {
    return sessionFactory;
  }

  // Calculate the throughput of the client
  protected double calculateThroughput(int numMessages, long duration) {
    double durationInSeconds = (1.0 * duration) / 1000;
    double throughput = (1.0 * numMessages) / durationInSeconds;

    return throughput;
  }

  // Perform client cleanup
  protected void cleanup() throws Exception {
    if (sessionFactory != null) {
      sessionFactory.close();
    }

    if (serverLocator != null) {
      serverLocator.close();
    }
  }
}
