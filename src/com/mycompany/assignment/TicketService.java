package Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketService {
    Scanner sc = new Scanner(System.in);
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private ArrayList<Feedback> feedbackList = new ArrayList<>();

    public void searchTickets(String keyword) {
    String lowerKey = keyword.toLowerCase(); 
    int count = 0;

    System.out.println("\n====================== SEARCH RESULTS ======================");
    
    for (Ticket t : tickets) {
        boolean matches = t.getTicketId().toLowerCase().contains(lowerKey) ||
                          t.getStatus().toLowerCase().contains(lowerKey) ||
                          t.getPriority().toLowerCase().contains(lowerKey) ||
                          t.getCustomerId().toLowerCase().contains(lowerKey) ||
                          t.getDescription().toLowerCase().contains(lowerKey);

        if (matches) {
            System.out.println(t);
            System.out.println("------------------------------------------------------------");
            count++;
        }
    }

    if (count == 0) {
        System.out.println("No tickets found matching: '" + keyword + "'");
    } else {
        System.out.println("Found " + count + " matching ticket(s).");
    }
}
    
    
    public void filterTicketsByPriority(String priority) {
    int count = 0;
    System.out.println("\n================ " + priority.toUpperCase() + " PRIORITY TICKETS ================");
    
    for (Ticket t : tickets) {
        if (t.getPriority().equalsIgnoreCase(priority)) {
            System.out.println(t);
            System.out.println("------------------------------------------------------------");
            count++;
        }
    }

    if (count == 0) {
        System.out.println("No tickets found with " + priority + " priority.");
    } else {
        System.out.println(">>> Total " + priority + " priority tickets: " + count);
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

    public void viewTicketsByCustomer(Scanner sc, String customerId) {
            System.out.println("\n--- View Options ---");
            System.out.println("1. View All Tickets");
            System.out.println("2. Filter by Month");
            int viewChoice = getValidIntInput(sc, "Choice: ", 1, 2);
            int targetMonth = 0;
  
            if (viewChoice == 2) {
                targetMonth = getValidIntInput(sc, "Enter month number (1-12): ", 1, 12);
            }

        ArrayList<Ticket> activeTickets = new ArrayList<>();
        ArrayList<Ticket> pastTickets = new ArrayList<>();
        
        for (Ticket t : tickets) {
            if (t.getCustomerId().trim().equalsIgnoreCase(customerId.trim())) {
                int ticketMonth = t.getCreationTime().getMonthValue();
                if (targetMonth == 0 || ticketMonth == targetMonth) {
                    if (t.getStatus().equalsIgnoreCase("Closed") || t.getStatus().equalsIgnoreCase("Resolved")) {
                        pastTickets.add(t);
                    } else {
                        activeTickets.add(t);
                    }
                }
            }
        }
        
        activeTickets.sort((t1, t2) -> t2.getCreationTime().compareTo(t1.getCreationTime()));
        pastTickets.sort((t1, t2) -> t2.getCreationTime().compareTo(t1.getCreationTime()));
        
        System.out.println("\n=============================== MY TICKET SUMMARY " + 
                       (targetMonth > 0 ? "(Month: " + targetMonth + ")" : "(All)") + 
                       " ===============================");

        if (activeTickets.isEmpty() && pastTickets.isEmpty()) {
            System.out.println("No tickets found for the selected criteria.");
        } else {
            if (!activeTickets.isEmpty()) {
                System.out.println("ACTIVE TICKETS: " + activeTickets.size() + "\n");
                for (Ticket t : activeTickets) {
                    System.out.println(t);
                }
            }
            if (!pastTickets.isEmpty()) {
                System.out.println("\nTICKET HISTORY (" + pastTickets.size() + ")");
                for (Ticket t : pastTickets) {
                    System.out.println(t);
                }
            }
        }
}
    
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

 
    public boolean updateTicketStatus(String ticketId, String status) {
        Ticket ticket = findTicketById(ticketId);

        if (ticket == null) return false;
        if (ticket.getStatus().equalsIgnoreCase("Closed")) return false;
        if (ticket.getStatus().equalsIgnoreCase(status)) return false;

        ticket.setStatus(status);
        return true; 
    }

    public void addResponseToTicket(String ticketId, String response) {
    try {
        Ticket ticket = findTicketById(ticketId);
        if (ticket != null) {
            if (ticket.getStatus().equalsIgnoreCase("Open")) {
                ticket.setStatus("In Progress");
            }
          
            if (ticket.addResponse(response)) {
                System.out.println("Response recorded in memory.");
            }
        }
    } catch (Exception e) {
        System.out.println("System error while adding response.");
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
                System.out.println("==============================");
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
            System.out.println("Feedback submitted. Thank you!");
        } else {
            System.out.println("Only closed tickets can receive feedback.");
        }
    }

    public void viewAllFeedback() {
    if (feedbackList == null || feedbackList.isEmpty()) {
        System.out.println("\nNo customer feedback received yet.");
        return;
    }

    System.out.println("\n================================================================");
    System.out.println("              CUSTOMER FEEDBACK LOG ");
    System.out.println("================================================================");
    System.out.printf("%-10s | %-12s | %-30s\n", "TICKET ID", "RATING", "CUSTOMER COMMENTS");
    System.out.println("----------------------------------------------------------------");

    double totalRating = 0;

    for (Feedback f : feedbackList) {
        String stars = "";
        for (int i = 0; i < 5; i++) {
            stars += (i < f.getRating()) ? "*" : "-";
        }

        String comment = (f.getComment() == null || f.getComment().isEmpty()) ? "N/A" : f.getComment();
        if (comment.length() > 30) {
            comment = comment.substring(0, 27) + "...";
        }

        System.out.printf("%-10s | %-12s | %-30s\n", 
                          f.getTicketId(), stars, comment);
        
        totalRating += f.getRating();
    }

    double average = totalRating / feedbackList.size();
    System.out.println("================================================================");
    System.out.printf("Total Feedbacks: %-8d | Overall Satisfaction: %.1f / 5.0\n", 
                      feedbackList.size(), average);
    System.out.println("================================================================\n");
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

    public void generateMonthlyTicketReport(int year, int month) {
    int total = 0, resolved = 0;
    for (Ticket t : tickets) {
        if (t.getCreationTime().getYear() == year && t.getCreationTime().getMonthValue() == month) {
            total++;
            if (t.getStatus().equalsIgnoreCase("Resolved") || t.getStatus().equalsIgnoreCase("Closed")) {
                resolved++;
            }
        }
    }
    System.out.println("\n===== Monthly Report [" + year + "-" + month + "] =====");
    System.out.println("Total Tickets: " + total);
    System.out.println("Resolved: " + resolved);
    System.out.println("Success Rate: " + (total > 0 ? (resolved * 100 / total) : 0) + "%");
}
    
    public void viewAllTicketsFiltered(Scanner sc, List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            System.out.println("No tickets found in the system.");
            return;
        }
        
        displaySystemDashboard();
        
        while(true){
            System.out.println("\n=============== Select Time Range ================");
            System.out.println("1. All Time");
            System.out.println("2. Specific Time");
            System.out.println("3. Return to Previous Portal");
            int scopeChoice = getValidIntInput(sc, "Choice: ", 1, 3);

            if (scopeChoice == 3) {break;}

            int targetYear = 0;
            int targetMonth = 0;
            if (scopeChoice == 2) {
                targetYear = getValidIntInput(sc, "Enter Year: ", 2020, 2030);
                targetMonth = getValidIntInput(sc, "Enter month (1-12): ", 1, 12);
            }

            System.out.println("\n=============== Select Status Filter ===============");
            System.out.println("1. View All Statuses");
            System.out.println("2. Open Only");
            System.out.println("3. Pending Only");
            System.out.println("4. In Progress Only");
            System.out.println("5. Resolved Only");
            System.out.println("6. Closed Only");
            int statusChoice = getValidIntInput(sc, "Choice: ", 1, 6);

            int count = 0;
            System.out.println("\n============================= FILTERED RESULTS =============================");

            for (Ticket t : tickets) {
                boolean monthMatch = (scopeChoice == 1 || t.getCreationTime().getYear() == targetYear && t.getCreationTime().getMonthValue() == targetMonth);                // 2. 检查状态是否匹配 (注意：这里必须是 boolean)
                boolean statusMatch = switch (statusChoice) {
                    case 1 -> true;
                    case 2 -> t.getStatus().equalsIgnoreCase("Open");
                    case 3 -> t.getStatus().equalsIgnoreCase("Pending");
                    case 4 -> t.getStatus().equalsIgnoreCase("In Progress");
                    case 5 -> t.getStatus().equalsIgnoreCase("Resolved");
                    case 6 -> t.getStatus().equalsIgnoreCase("Closed");
                    default -> false;
                };

                if (monthMatch && statusMatch) {
                    System.out.println(t);
                    System.out.println("==================================================================");
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("No matching tickets found for your selection.");
            } else {
                System.out.println(">>> Showing " + count + " matching ticket(s).");
            }
            System.out.print("\nPress ENTER to continue viewing or change filters...");
            sc.nextLine();
           }
        }
    
    public int getValidIntInput(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }
            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) return val;
                System.out.println("Invalid range! Please enter " + min + "-" + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

public String getValidStringInput(Scanner sc, String prompt, String regex, String errorMsg) {
    while (true) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();

        if (input.equals("0")) return "0";

        if (input.isEmpty()) {
            System.out.println("Input cannot be empty. Please try again.");
            continue;
        }

        if (input.matches(regex)) {
            return input;
        } else {
            System.out.println(errorMsg);
        }
    }
}

    public void displaySystemDashboard() {
    int o = 0, p = 0, ip = 0, r = 0, c = 0;
    for (Ticket t : tickets) {
        String s = t.getStatus().toLowerCase();
        switch (s) {
            case "open" -> o++;
            case "pending" -> p++;
            case "in progress" -> ip++;
            case "resolved" -> r++;
            case "closed" -> c++;
        }
    }
    System.out.println("\n================ SYSTEM SUMMARY ================");
    System.out.printf("| Open: %d | Pending: %d | In Progress: %d | Resolved: %d | Closed: %d |\n", 
                      o, p, ip, r, c);
    System.out.println("================================================");
}
}
