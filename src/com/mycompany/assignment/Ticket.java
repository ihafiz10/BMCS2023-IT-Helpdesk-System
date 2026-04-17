package Assignment;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ticket {

    private String ticketId;
    private String customerId;
    private String assignedStaffId;
    private String description;
    private String priority;
    private String status;
    private String dateSubmitted;

    private static int counter = 1;

    // Response
    private String response;
    private ArrayList<String> responses;

    // Manager features
    private LocalDateTime creationTime;
    private LocalDateTime resolutionTime;
    private int rating;
    private String formatStatus(String status) {
        String[] words = status.trim().toLowerCase().split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(word.substring(0,1).toUpperCase())
                    .append(word.substring(1))
                    .append(" ");
        }

        return result.toString().trim();
    }

    public Ticket(){
        this.ticketId = "";
        this.customerId = "";
        this.assignedStaffId = "Not Assigned"; 
        this.description = "";
        this.priority = "";
        this.status = "Open";
        this.dateSubmitted = "";
        this.response = "NONE";  
        this.responses = new ArrayList<>();
        this.creationTime = LocalDateTime.now(); 
        this.resolutionTime = null;  
        this.rating = 0;
    }
    
    
    // Constructor (AUTO ID)
    public Ticket(String customerId, String description, String priority, String dateSubmitted) {

        this.ticketId = "T" + counter;
        counter++;

        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        this.customerId = customerId;
        this.description = description;
        this.priority = priority;
        this.status = "Open";
        this.response = "";
        this.responses = new ArrayList<>();
        this.assignedStaffId = "Not Assigned";
        this.dateSubmitted = dateSubmitted;

        this.creationTime = LocalDateTime.now();
        this.resolutionTime = null;
        this.rating = 0;
    }

    // ================= GETTERS =================

    public String getTicketId() {return ticketId;}

    // compatibility (important)
    public String getTicketID() {return ticketId;}

    public String getCustomerId() {return customerId;}

    public String getAssignedStaffId() {return assignedStaffId;}

    // compatibility
    public String getStaffId() {return assignedStaffId;}

    public String getDescription() {return description;}

    public String getPriority() {return priority;}

    public String getStatus() {return status;}

    public String getResponse() {return response;}

    public ArrayList<String> getResponses() {return responses;}

    public String getDateSubmitted() {return dateSubmitted;}

    public LocalDateTime getCreationTime() {return creationTime;}

    public LocalDateTime getResolutionTime() {return resolutionTime;}

    public int getRating() {return rating;}

    // ================= SETTERS =================

    public void setAssignedStaffId(String assignedStaffId) {
        if (assignedStaffId == null || assignedStaffId.isEmpty()) {
            System.out.println("Invalid staff ID.");
            return;
        }
        this.assignedStaffId = assignedStaffId;
    }

    // compatibility
    public void setStaffId(String staffId) {
        setAssignedStaffId(staffId);
    }

    public void setStatus(String status) {
        if (status == null || status.isEmpty()) {
            System.out.println("Invalid status.");
            return;
        }

        if (!status.equalsIgnoreCase("Open") &&
                !status.equalsIgnoreCase("Pending") &&
                !status.equalsIgnoreCase("In Progress") &&
                !status.equalsIgnoreCase("Resolved") &&
                !status.equalsIgnoreCase("Closed")) {

            System.out.println("Invalid status!");
            return;
        }

        if (this.status.equalsIgnoreCase("Closed")) {
            System.out.println("Cannot update a closed ticket!");
            return;
        }

        this.status = formatStatus(status);

        if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Closed")) {
            this.resolutionTime = LocalDateTime.now();
        }
    }

    public void setResponse(String response) {
        if (response == null || response.isEmpty()) {
            System.out.println("Response cannot be empty.");
            return;
        }

        this.response = response;
        this.responses.add(response);
    }

    public boolean addResponse(String response) {

        // Prevent responding to closed ticket (optional rule)
        if (this.status.equalsIgnoreCase("Closed")) {
            System.out.println("Cannot respond to a closed ticket.");
            return false;
        }

        // Empty validation
        if (response == null || response.trim().isEmpty()) {
            System.out.println("Response cannot be empty!");
            return false;
        }

        this.response = response;
        this.responses.add(response);
        return true;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } 
    }
    
    public static void setCounter(int newCounter) {
        counter = newCounter;
    }
    
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
    
    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
    
    public void setResolutionTime(LocalDateTime resolutionTime) {
        this.resolutionTime = resolutionTime;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setCustomerId(String id) { this.customerId = id; }
    public void setPriority(String pri) { this.priority = pri; }
    public void setDateSubmitted(String date) { this.dateSubmitted = date; }


    // ================= METHODS =================

    public void viewHistory() {
        if (responses.isEmpty()) {
            System.out.println("No responses yet.");
        } else {
            for (String r : responses) {
                System.out.println("- " + r);
            }
        }
    }

 @Override
public String toString() {
    String staffDisplay = (assignedStaffId == null || assignedStaffId.isEmpty()) ? "Unassigned" : assignedStaffId;
    String historyDisplay = responses.isEmpty() ? "No responses yet." : String.join("\n          - ", responses);
    
    return """
        ============================== TICKET DETAILS ==============================
          Ticket ID        : %s
          Status           : [%s]
          Priority         : %s
        ----------------------------------------------------------------------------
          Customer ID      : %s
          Staff Assigned   : %s
          Date Submitted   : %s
        ----------------------------------------------------------------------------
          Description      : 
          %s
          
          Responses History:
          %s
        ============================================================================
        """.formatted(
            ticketId, 
            status.toUpperCase(), 
            priority, 
            customerId, 
            staffDisplay, 
            dateSubmitted, 
            description, 
            response.isEmpty() ? "No responses yet." : response
        );
}
}
