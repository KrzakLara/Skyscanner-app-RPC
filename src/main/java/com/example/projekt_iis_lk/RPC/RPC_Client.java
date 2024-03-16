package com.example.projekt_iis_lk.RPC;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;

public class RPC_Client implements Runnable {
    private final XmlRpcClient client;
    private String city;

    public RPC_Client(String city) throws MalformedURLException {
        this.city = city;

        // Create a new XmlRpcClient object
        client = new XmlRpcClient();

        // Create a new XmlRpcClientConfigImpl object and enable extensions
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setEnabledForExtensions(true);

        // Set the server URL for the client to connect to
        config.setServerURL(new URL("https://vrijeme.hr/xml-rpc"));

        // Set the client configuration using the XmlRpcClientConfigImpl object
        client.setConfig(config);
    }

    public String getWeather(String city) throws Exception {
        // Invoke the XML-RPC method on the server and pass the city as a parameter
        Object[] params = new Object[]{city};
        Object temperature = client.execute("weather.getTemperature", params);

        if (temperature != null) {
            return temperature.toString();
        } else {
            throw new Exception("Temperature data not available for the city " + city);
        }
    }

    @Override
    public void run() {
        try {
            String temperature = getWeather(city);
            System.out.println("Current temperature for the city " + city + ": " + temperature);
        } catch (Exception e) {
            System.err.println("Error while retrieving data for the city " + city + ": " + e.getMessage());
        }
    }
}
