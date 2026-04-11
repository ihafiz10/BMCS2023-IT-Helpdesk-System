package com.mycompany.assignment;

import java.util.ArrayList;

public class TicketService {
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private ArrayList<Feedback> feedbackList = new ArrayList<>();

    public void searchTickets(String keyword) {
        boolean found = false;

        for (Ticket ticket : tickets) {
            if (ticket.getTicketId().equalsIgnoreCase(keyword) ||
                    ticket.getCustomerId().equalsIgnoreCase(keyword) ||
                    ticket.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                    ticket.getPriority().equalsIgnoreCase(keyword) ||
                    ticket.getStatus().equalsIgnoreCase(keyword)) {

                System.out.println(ticket);
                System.out.println("----------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching tickets found.");
        }
    }

    public void filterTicketsByStatus(String status) {
        boolean found = false;

        for (Ticket ticket : tickets) {
            if (ticket.getStatus().equalsIgnoreCase(status)) {
                System.out.println(ticket);
                System.out.println("----------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tickets found with status: " + status);
        }
    }

    public void filterTicketsByPriority(String priority) {
        boolean found = false;

        for (Ticket ticket : tickets) {
            if (ticket.getPriority().equalsIgnoreCase(priority)) {
                System.out.println(ticket);
                System.out.println("----------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tickets found with priority: " + priority);
        }
    }

    public void createTicket(Ticket ticket) {
        try {
            tickets.add(ticket);
            System.out.println("Ticket created successfully.");
            System.out.println("Your Ticket ID is: " + ticket.getTicketId());
        } catch (Exception e) {
            System.out.println("Error creating ticket: " + e.getMessage());
        }
    }

    public Ticket findTicketById(String ticketId) {
        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Invalid ticket ID.");
            return null;
        }

        for (Ticket ticket : tickets) {
            if (ticket.getTicketID().equalsIgnoreCase(ticketId)) {
                return ticket;
            }
        }
        return null;
    }

    public void viewAllTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        for (Ticket ticket : tickets) {
            System.out.println(ticket);
            System.out.println("----------------------------");
        }
    }

    public void viewTicketsByCustomer(String customerId) {
        boolean found = false;

        for (Ticket ticket : tickets) {
            if (ticket.getCustomerId().equalsIgnoreCase(customerId)) {
                System.out.println(ticket);
                System.out.println("----------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tickets found for this customer.");
        }
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void updateTicketStatus(String ticketId, String status) {
        try {
            Ticket ticket = findTicketById(ticketId);
            if (ticket != null) {
                String oldStatus = ticket.getStatus();
                ticket.setStatus(status);

                if (!oldStatus.equalsIgnoreCase(ticket.getStatus())) {
                    System.out.println("Status updated.");
                } else {
                    System.out.println("Status was not updated.");
                }
            } else {
                System.out.println("Ticket not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating status.");
        }
    }

    public void addResponseToTicket(String ticketId, String response) {
        try {
            Ticket ticket = findTicketById(ticketId);
            if (ticket != null) {
                if (ticket.addResponse(response)) {
                    System.out.println("Response added.");
                } else {
                    System.out.println("Failed to add response.");
                }
            } else {
                System.out.println("Ticket not found.");
            }
        } catch (Exception e) {
            System.out.println("Error adding response.");
        }
    }

    public void assignTicketToStaff(String ticketId, String staffId) {
        Ticket ticket = findTicketById(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        if (ticket.getStatus().equalsIgnoreCase("Closed")) {
            System.out.println("Closed tickets cannot be assigned.");
            return;
        }

        ticket.setStaffId(staffId.toUpperCase());
        System.out.println("Assigned successfully.");
    }

    public void closeTicket(String ticketId) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket != null) {
            String oldStatus = ticket.getStatus();
            ticket.setStatus("Closed");

            if (!oldStatus.equalsIgnoreCase(ticket.getStatus())) {
                System.out.println("Ticket closed.");
            } else {
                System.out.println("Ticket could not be closed.");
            }
        } else {
            System.out.println("Ticket not found.");
        }
    }

    public void viewTicketsByStaff(String staffId) {
        boolean found = false;

        for (Ticket t : tickets) {
            if (t.getAssignedStaffId().equalsIgnoreCase(staffId)) {
                System.out.println(t);
                System.out.println("----------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tickets assigned to you.");
        }
    }

    public void addFeedback(Feedback feedback) {
        Ticket ticket = findTicketById(feedback.getTicketId());

        if (ticket != null && ticket.getStatus().equalsIgnoreCase("Closed")) {
            feedbackList.add(feedback);
            ticket.setRating(feedback.getRating());
            System.out.println("Feedback added.");
        } else {
            System.out.println("Only closed tickets can receive feedback.");
        }
    }

    public void viewAllFeedback() {
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback found.");
            return;
        }

        for (Feedback feedback : feedbackList) {
            System.out.println(feedback);
            System.out.println("----------------------------");
        }
    }

    public ArrayList<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public boolean hasFeedbackForTicket(String ticketId) {
        for (Feedback f : feedbackList) {
            if (f.getTicketId().equalsIgnoreCase(ticketId)) {
                return true;
            }
        }
        return false;
    }

    public void generateMonthlyReport() {
        int total = tickets.size();
        int resolved = 0;

        for (Ticket t : tickets) {
            if (t.getStatus().equalsIgnoreCase("Closed") ||
                    t.getStatus().equalsIgnoreCase("Resolved")) {
                resolved++;
            }
        }

        System.out.println("===== REPORT =====");
        System.out.println("Total: " + total);
        System.out.println("Resolved: " + resolved);
        System.out.println("Unresolved: " + (total - resolved));
    }
}