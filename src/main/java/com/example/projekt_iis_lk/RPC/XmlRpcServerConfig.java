package com.example.projekt_iis_lk.RPC;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlRpcServerConfig {

    @Bean
    public XmlRpcServletServer xmlRpcServletServer() {
        XmlRpcServletServer xmlRpcServer = new XmlRpcServletServer();

        // Create a new instance of PropertyHandlerMapping, which maps Java classes to XML-RPC handlers
        PropertyHandlerMapping propertyMapping = new PropertyHandlerMapping();
        try {
            propertyMapping.addHandler("server", RPC_Server.class);
        } catch (XmlRpcException e) {
            throw new RuntimeException(e);
        }

        // Set the handler mapping on the XmlRpcServer instance
        xmlRpcServer.setHandlerMapping(propertyMapping);

        // Create a new instance of XmlRpcServerConfigImpl, which holds the server configuration options
        XmlRpcServerConfigImpl serverConfig = new XmlRpcServerConfigImpl();
        serverConfig.setEnabledForExtensions(true);

        // Set the server configuration on the XmlRpcServer instance
        xmlRpcServer.setConfig(serverConfig);

        return xmlRpcServer;
    }
}
