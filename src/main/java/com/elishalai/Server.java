//=============================================================================
// HornetQ-Tests
//
// @description: Module for providing functions to work with Server objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;

import java.util.HashMap;
import java.util.HashSet;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.config.Configuration;
import org.hornetq.core.config.impl.ConfigurationImpl;
import org.hornetq.core.remoting.impl.netty.NettyAcceptorFactory;
import org.hornetq.core.server.HornetQServer;
import org.hornetq.core.server.HornetQServers;

public class Server {
  public static void main(String[] args) throws Exception {
    try {
      String serverAddress = args[0];
      int serverPort = Integer.parseInt(args[1]);

      Configuration configuration = new ConfigurationImpl();
      configuration.setJournalDirectory("target/data/journal");
      configuration.setPersistenceEnabled(false);
      configuration.setSecurityEnabled(false);

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("host", serverAddress);
      parameters.put("port", serverPort);

      TransportConfiguration transportConfiguration =
        new TransportConfiguration(
          NettyAcceptorFactory.class.getName(), parameters
        );

      HashSet<TransportConfiguration> setTransp =
        new HashSet<TransportConfiguration>();
      setTransp.add(transportConfiguration);

      configuration.setAcceptorConfigurations(setTransp);

      // Create and start the server
      HornetQServer server = HornetQServers.newHornetQServer(configuration);
      server.start();

      System.out.println("HornetQ Server started successfully");
    } catch (Exception e) {
      System.out.println("HornetQ Server couldn't start successfully. An error has occured.");
      e.printStackTrace();
    }
  }
}
