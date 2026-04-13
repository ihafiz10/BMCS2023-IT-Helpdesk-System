package Assignment;

import java.util.Scanner;

public class CustomerModule {
    
    private void pause(Scanner sc) {
        System.out.print("\nPress ENTER to return to the Customer Menu...");
        sc.nextLine();
    }

    public void run(Scanner sc, TicketService service, Customer customer, DataRepository repo) {
        int choice;

        do {
            System.out.println("\n==================================================");
            System.out.println("                CUSTOMER MAIN MENU                ");
            System.out.println("==================================================");
            System.out.println(" Welcome, " + customer.getUsername() + " (ID: " + customer.getCustomerId() + ")");
            System.out.println("--------------------------------------------------");
            System.out.println(" 1. Create New Ticket");
            System.out.println(" 2. View My Ticket History");
            System.out.println(" 3. Close an Active Ticket");
            System.out.println(" 4. Submit Service Feedback");
            System.out.println(" 5. Logout & Return");
            System.out.println("==================================================");

            choice = service.getValidIntInput(sc, "Choice (1-5): ", 1, 5);

            switch (choice) {

                case 1 -> {
                    System.out.println("\n===== Select Issue Category =====");
                    System.out.println("1. Hardware");
                    System.out.println("2. Software");
                    System.out.println("3. Network/Wi-Fi");
                    System.out.println("4. Account/Login");
                    System.out.println("5. Others");
                    while(true){

                    int categoryChoice = service.getValidIntInput(sc, "Select Category: ", 1, 5);
                    String category = switch (categoryChoice) {
                        case 1 -> "Hardware";
                        case 2 -> "Software";
                        case 3 -> "Network/Wi-Fi";
                        case 4 -> "Account/Login";
                        default -> "Others";
                    };
                    
                    String desc = service.getValidStringInput(sc, 
                            "Enter problem description (10-200 chars): ", 
                            "^.{10,200}$", 
                            "Description must be between 10 and 200 characters.");
                    
                    if (desc.equals("0")) {
                        System.out.println("Ticket creation cancelled.");
                        break;
                    }

                    System.out.println("\n===== Select Priority =====");
                    System.out.println("1. High   (System Down / Cannot Work)");
                    System.out.println("2. Medium (Function Error / Hard to Work)");
                    System.out.println("3. Low    (General Question / Minor Issue)");
                    int priChoice = service.getValidIntInput(sc, "Select Priority: ", 1, 3);
                    String pri = switch (priChoice) {
                        case 1 -> "High";
                        case 2 -> "Medium";
                        default -> "Low";
                    };
                        String finalDesc = "[" + category + "] " + desc;
                        
                        Ticket t = new Ticket(
                                customer.getCustomerId(),
                                finalDesc,
                                pri,
                                java.time.LocalDate.now().toString()
                        );
                        service.createTicket(t);
                        repo.saveTickets(service.getTickets());
                        pause(sc);
                        break;
                    } 
                }

                case 2 -> {
                    service.viewTicketsByCustomer(sc, customer.getCustomerId());
                    pause(sc);
                }

                case 3 -> {
                    while (true) {
                    String closeId = service.getValidStringInput(sc, 
                                "Enter Ticket ID to close (or 0 to return): ", 
                                "^[a-zA-Z0-9]+$", "Invalid ID format!");
                    
                    if (closeId.equals("0")) break;
                    
                    Ticket ticket = service.findTicketById(closeId);
                    
                    if (ticket == null) {
                        System.out.println("Ticket not found. Please try again.\n"); continue;
                    }
                    
                    // Ensure customer owns the ticket
                    if (!ticket.getCustomerId().equals(customer.getCustomerId())) {
                        System.out.println("You can only close your own tickets.\n");
                        continue;
                    }

                    // Prevent closing already closed ticket
                    if (ticket.getStatus().equalsIgnoreCase("Closed")) {
                        System.out.println("Ticket is already closed.\n");
                        continue;
                    }
                    ticket.setStatus("Closed");
                    System.out.println("Ticket " + closeId + " closed successfully.");
                    repo.saveTickets(service.getTickets());
                    break;
                    }
                    pause(sc);
                }
                    

                case 4 -> {
                    while (true) {
                    String feedbackTicketId = service.getValidStringInput(sc, 
                                "Enter closed Ticket ID for feedback (or 0 to return): ", 
                                "^[a-zA-Z0-9]+$", "Invalid ID!");
                    if (feedbackTicketId.equals("0")) break;
                    
                    Ticket feedbackTicket = service.findTicketById(feedbackTicketId);

                    if (feedbackTicket == null) {
                        System.out.println("Ticket not found."); continue;
                    }
                    
                    if (!feedbackTicket.getCustomerId().equals(customer.getCustomerId())) {
                        System.out.println("You can only give feedback for your own tickets."); continue;
                    }
                    
                    if (!feedbackTicket.getStatus().equalsIgnoreCase("Closed")) {
                        System.out.println("Feedback can only be given for closed tickets."); continue;
                    }
                    
                    // Prevent duplicate feedback
                    if (service.hasFeedbackForTicket(feedbackTicketId)) {
                        System.out.println("Feedback has already been submitted for this ticket."); continue;
                    }
                    
                    System.out.println("\nHow would you rate our service?");
                    System.out.println("5 - Excellent");
                    System.out.println("4 - Good");
                    System.out.println("3 - Fair");
                    System.out.println("2 - Poor");
                    System.out.println("1 - Very Poor");
                    int rating = service.getValidIntInput(sc, "Your rating (1-5): ", 1, 5);
                    

                    System.out.print("Enter comment (or press Enter to skip): ");
                    String inputComment = sc.nextLine().trim();
                    if (inputComment.isEmpty()) inputComment = "No comment provided";
                    
                    String feedbackId = "F" + feedbackTicketId.substring(1);
                        Feedback feedback = new Feedback(feedbackId, feedbackTicketId, rating, inputComment);
                        service.addFeedback(feedback);
                        repo.saveFeedback(service.getFeedbackList());
                        System.out.println("Thank you for your feedback!");
                        break;
                    }
                    pause(sc);
                }

                case 5 -> System.out.println("Exiting Customer Module...");

                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 5);
    }
}
