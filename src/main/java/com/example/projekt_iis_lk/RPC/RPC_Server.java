package com.example.projekt_iis_lk.RPC;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

@Component
public class RPC_Server implements Runnable {

    private static final String XML_URL = "https://vrijeme.hr/hrvatska_n.xml";

    @Override
    public void run() {
        System.out.println("Server is listening...");

        // Unos naziva grada
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the city name: ");
        String city = scanner.nextLine();

        try {
            String temperature = getWeather(city);
            System.out.println("Current temperature for the city " + city + ": " + temperature);
        } catch (Exception e) {
            System.err.println("Error while retrieving data for the city " + city + ": " + e.getMessage());
        }
    }

    public String getWeather(String city) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new URL(XML_URL).openStream());

        NodeList nodeList = document.getElementsByTagName("Grad");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element gradElement = (Element) nodeList.item(i);

            String cityValue = gradElement.getElementsByTagName("GradIme").item(0).getTextContent();

            if (city.equalsIgnoreCase(cityValue)) {
                String temperature = gradElement.getElementsByTagName("Temp").item(0).getTextContent();
                return temperature;
            }
        }

        return null;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        Thread serverThread = new Thread(this);
        serverThread.start();
    }
}
