package com.mycompany.assignment;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

public class TicketManager {

    private ArrayList<Ticket> ticketList;
    private ArrayList<User> userList;

    public TicketManager() {
        this.ticketList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }

    public void setTicketList(ArrayList<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    // Find ticket
    public Ticket findTicketById(String ticketId) {
        for (Ticket t : ticketList) {
            if (t.getTicketId().equalsIgnoreCase(ticketId)) {
                return t;
            }
        }
        return null;
    }

    // Find staff with least workload
    public String getMostAvailableStaff() {
        String bestStaff = "NONE";
        int minTickets = Integer.MAX_VALUE;

        for (User u : userList) {
            if (u.getRole().equalsIgnoreCase("Staff") && u.isActive()) {

                int workload = 0;

                for (Ticket t : ticketList) {
                    if (t.getStaffId() != null &&
                            t.getStaffId().equals(u.getUserId()) &&
                            !t.getStatus().equalsIgnoreCase("Resolved")
                            && !t.getStatus().equalsIgnoreCase("Closed")) {
                        workload++;
                    }
                }

                if (workload < minTickets) {
                    minTickets = workload;
                    bestStaff = u.getUserId();
                }
            }
        }
        return bestStaff;
    }

    // Critical tickets
    public void identifyCriticalTickets() {
        System.out.println("\n!!! CRITICAL TICKETS !!!");

        boolean found = false;
        LocalDateTime now = LocalDateTime.now();

        for (Ticket t : ticketList) {
            boolean high = t.getPriority().equalsIgnoreCase("High");
            boolean notResolved = !t.getStatus().equalsIgnoreCase("Resolved")
                    && !t.getStatus().equalsIgnoreCase("Closed");

            Duration d = Duration.between(t.getCreationTime(), now);
            boolean overdue = d.toHours() >= 24;

            if (notResolved && (high || overdue)) {
                System.out.println("Ticket: " + t.getTicketId() +
                        " | Priority: " + t.getPriority() +
                        " | Status: " + t.getStatus());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No critical tickets.");
        }
    }

    // Monitoring
    public void monitorTicketStatus() {
        int open = 0;
        int pending = 0;
        int inProgress = 0;
        int resolved = 0;
        int closed = 0;

        for (Ticket t : ticketList) {
            switch (t.getStatus().toLowerCase()) {
                case "open" -> open++;
                case "pending" -> pending++;
                case "in progress" -> inProgress++;
                case "resolved" -> resolved++;
                case "closed" -> closed++;
            }
        }

        System.out.println("\n=== Ticket Monitoring ===");
        System.out.println("Open: " + open);
        System.out.println("Pending: " + pending);
        System.out.println("In Progress: " + inProgress);
        System.out.println("Resolved: " + resolved);
        System.out.println("Closed: " + closed);
        System.out.println("Total: " + ticketList.size());
    }

    // Monthly report
    public void generateMonthlyTicketReport(int year, int month) {
        int total = 0;
        int resolved = 0;

        for (Ticket t : ticketList) {
            LocalDateTime time = t.getCreationTime();

            if (time.getYear() == year && time.getMonthValue() == month) {
                total++;
                if (t.getStatus().equalsIgnoreCase("Resolved") ||
                        t.getStatus().equalsIgnoreCase("Closed")) {
                    resolved++;
                }
            }
        }

        System.out.println("\n--- Monthly Report ---");
        System.out.println("Total: " + total);
        System.out.println("Resolved: " + resolved);
        System.out.println("Unresolved: " + (total - resolved));
    }

    // Performance
    public void calculateAverageResponseTime() {
        long totalMinutes = 0;
        int count = 0;

        for (Ticket t : ticketList) {
            if (t.getResolutionTime() != null) {
                Duration d = Duration.between(
                        t.getCreationTime(),
                        t.getResolutionTime()
                );

                totalMinutes += d.toMinutes();
                count++;
            }
        }

        System.out.println("\n=== Performance ===");

        if (count > 0) {
            System.out.println("Average Time: " + (totalMinutes / count) + " mins");
        } else {
            System.out.println("No resolved tickets.");
        }
    }

    // Feedback view
    public void viewAllFeedback(ArrayList<Feedback> feedbackList) {
        System.out.println("\n--- Feedback ---");

        if (feedbackList.isEmpty()) {
            System.out.println("No feedback available.");
            return;
        }

        for (Feedback f : feedbackList) {
            System.out.println("Ticket: " + f.getTicketId() +
                    " | Rating: " + f.getRating() +
                    " | Comment: " + f.getComment());
        }
    }
}
