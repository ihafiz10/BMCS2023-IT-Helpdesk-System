public class Ticket {
    private String ticketId;
    private String customerId;
    private String assignedStaffId;
    private String description;
    private String priority;
    private String status;
    private String response;
    private String dateSubmitted;

    public Ticket(String ticketId, String customerId, String description, String priority, String dateSubmitted) {
        if (ticketId == null || ticketId.isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be empty");
        }
        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        this.ticketId = ticketId;
        this.customerId = customerId;
        this.description = description;
        this.priority = priority;
        this.status = "Open";
        this.response = "";
        this.assignedStaffId = "Not Assigned";
        this.dateSubmitted = dateSubmitted;
    }

    public String getTicketId() { return ticketId; }
    public String getCustomerId() { return customerId; }
    public String getAssignedStaffId() { return assignedStaffId; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public String getResponse() { return response; }
    public String getDateSubmitted() { return dateSubmitted; }

    public void setAssignedStaffId(String assignedStaffId) {
        if (assignedStaffId == null || assignedStaffId.isEmpty()) {
            System.out.println("Invalid staff ID.");
            return;
        }
        this.assignedStaffId = assignedStaffId;
    }

    public void setStatus(String status) {
        if (status == null || status.isEmpty()) {
            System.out.println("Invalid status.");
            return;
        }
        this.status = status;
    }

    public void setResponse(String response) {
        if (response == null || response.isEmpty()) {
            System.out.println("Response cannot be empty.");
            return;
        }
        this.response = response;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + ticketId +
                "\nCustomer ID: " + customerId +
                "\nAssigned Staff ID: " + assignedStaffId +
                "\nDescription: " + description +
                "\nPriority: " + priority +
                "\nStatus: " + status +
                "\nResponse: " + (response.isEmpty() ? "No response yet" : response) +
                "\nDate Submitted: " + dateSubmitted;
    }
}
