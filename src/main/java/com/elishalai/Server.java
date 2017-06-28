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
      Configuration configuration = new ConfigurationImpl();
      configuration.setJournalDirectory("target/data/journal");
      configuration.setPersistenceEnabled(false);
      configuration.setSecurityEnabled(false);

      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("host", "localhost");
      map.put("port", 5445);

      TransportConfiguration transportConfiguration =
        new TransportConfiguration(NettyAcceptorFactory.class.getName(), map);

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