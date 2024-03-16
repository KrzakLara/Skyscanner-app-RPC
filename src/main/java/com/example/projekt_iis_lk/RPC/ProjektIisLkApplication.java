package com.example.projekt_iis_lk.RPC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ProjektIisLkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektIisLkApplication.class, args);

        // Unos naziva grada
        Scanner scanner = new Scanner(System.in);
        String city = scanner.nextLine();

        // Stvaranje instance RPC_Client-a
        RPC_Client client;
        try {
            client = new RPC_Client(city);
        } catch (Exception e) {
            System.err.println("Error during client initialization: " + e.getMessage());
            return;
        }

        // Dohvat i ispis podataka za uneseni grad
        try {
            client.run();
        } catch (Exception e) {
            System.err.println("Error while retrieving data for the city " + city + ": " + e.getMessage());
        }
    }
}
