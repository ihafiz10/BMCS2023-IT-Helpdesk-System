package com.mycompany.assignment;

import java.util.List;

public class Staff extends User {

    private String staffID;

    public Staff(String userId, String username, String email, String password, String staffID) {
        super(userId, username, email, password, "Staff", true,
                java.time.LocalDate.now().toString());
        this.staffID = staffID;
    }

    public String getStaffID() {
        return staffID;
    }

    public boolean login(String username, String password) {
        return validateLogin(username, password);
    }

    public void viewAllTickets(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        for (Ticket t : tickets) {
            System.out.println(t);
            System.out.println("----------------------------");
        }
    }

    public void respondTicket(Ticket t, String response) {
        if (t.addResponse(response)) {
            System.out.println("Response added!");
        } else {
            System.out.println("Failed to add response.");
        }
    }

    public void updateStatus(Ticket t, String status) {
        String oldStatus = t.getStatus();
        t.setStatus(status);

        if (!oldStatus.equalsIgnoreCase(t.getStatus())) {
            System.out.println("Status updated!");
        } else {
            System.out.println("Status was not updated.");
        }
    }
}