/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment;

/**
 *
 * @author User
 */

import java.util.List;

public class Staff extends User {

    private String staffID;

    public Staff(String username, String password, String staffID) {
        super(username, password);
        this.staffID = staffID;
    }

    // ✅ FIXED LOGIN
    public boolean login(String pass) {
        return getPassword().equals(pass);
    }

    public void viewAllTickets(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        for (Ticket t : tickets) {
            System.out.println(t);
        }
    }

    public void respondTicket(Ticket t, String response) {
        if (t.addResponse(response)) {
            System.out.println("Response added!");
        }
    }

    public void updateStatus(Ticket t, String status) {
        if (t.updateStatus(status)) {
            System.out.println("Status updated!");
        }
    }
}