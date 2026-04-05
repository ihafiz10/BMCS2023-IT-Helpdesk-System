/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment;

/**
 *
 * @author User
 */
import java.util.ArrayList;

public class Ticket {
    private String ticketID;
    private String description;
    private String priority;
    private String status;
    private String dateSubmitted;
    private ArrayList<String> responses;

    public Ticket(String ticketID, String description, String priority) {
        this.ticketID = ticketID;
        this.description = description;
        this.priority = priority;
        this.status = "Open";
        this.dateSubmitted = "Today";
        responses = new ArrayList<>();
    }

    public String getTicketID() {
        return ticketID;
    }

    public String getStatus() {
        return status;
    }

    public boolean updateStatus(String newStatus) {
        if (newStatus == null || newStatus.isEmpty()) {
            System.out.println("Status cannot be empty");
            return false;
        }

        if (!newStatus.equalsIgnoreCase("Open") &&
            !newStatus.equalsIgnoreCase("In Progress") &&
            !newStatus.equalsIgnoreCase("Resolved") &&
            !newStatus.equalsIgnoreCase("Closed")) {

            System.out.println("Invalid status!");
            return false;
        }

        if (this.status.equalsIgnoreCase("Closed")) {
            System.out.println("Cannot update a closed ticket!");
            return false;
        }

        this.status = newStatus;
        return true;
    }

    public boolean addResponse(String response) {
        if (response == null || response.trim().isEmpty()) {
            System.out.println("Response cannot be empty!");
            return false;
        }

        responses.add(response);
        return true;
    }

    public void viewHistory() {
        if (responses.isEmpty()) {
            System.out.println("No responses yet.");
        } else {
            for (String r : responses) {
                System.out.println("- " + r);
            }
        }
    }

    public String toString() {
        return ticketID + " | " + description + " | " + priority + " | " + status;
    }
}