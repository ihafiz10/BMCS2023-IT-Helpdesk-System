import java.util.ArrayList;

public class TicketService {
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private ArrayList<Feedback> feedbackList = new ArrayList<>();

    public void createTicket(Ticket ticket) {
        try {
            tickets.add(ticket);
            System.out.println("Ticket created successfully.");
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
            if (ticket.getTicketId().equalsIgnoreCase(ticketId)) {
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

    public void updateTicketStatus(String ticketId, String status) {
        try {
            Ticket ticket = findTicketById(ticketId);
            if (ticket != null) {
                ticket.setStatus(status);
                System.out.println("Status updated.");
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
                ticket.setResponse(response);
                System.out.println("Response added.");
            } else {
                System.out.println("Ticket not found.");
            }
        } catch (Exception e) {
            System.out.println("Error adding response.");
        }
    }

    public void assignTicketToStaff(String ticketId, String staffId) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket != null) {
            ticket.setAssignedStaffId(staffId);
            System.out.println("Assigned successfully.");
        } else {
            System.out.println("Ticket not found.");
        }
    }

    public void closeTicket(String ticketId) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket != null) {
            ticket.setStatus("Closed");
            System.out.println("Ticket closed.");
        } else {
            System.out.println("Ticket not found.");
        }
    }

    public void addFeedback(Feedback feedback) {
        Ticket ticket = findTicketById(feedback.getTicketId());

        if (ticket != null && ticket.getStatus().equalsIgnoreCase("Closed")) {
            feedbackList.add(feedback);
            System.out.println("Feedback added.");
        } else {
            System.out.println("Only closed tickets can receive feedback.");
        }
    }

    public void generateMonthlyReport() {
        int total = tickets.size();
        int resolved = 0;

        for (Ticket t : tickets) {
            if (t.getStatus().equalsIgnoreCase("Closed")) {
                resolved++;
            }
        }

        System.out.println("===== REPORT =====");
        System.out.println("Total: " + total);
        System.out.println("Resolved: " + resolved);
        System.out.println("Unresolved: " + (total - resolved));
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
}