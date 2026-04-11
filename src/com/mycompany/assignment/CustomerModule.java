package com.mycompany.assignment;

import java.util.Scanner;

public class CustomerModule {

    public void run(Scanner sc, TicketService service, Customer customer) {


        int choice;

        do {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("Welcome, " + customer.getUsername());
            System.out.println("Your Customer ID: " + customer.getCustomerId());
            System.out.println("1. Create Ticket");
            System.out.println("2. View My Tickets");
            System.out.println("3. Close Ticket");
            System.out.println("4. Give Feedback");
            System.out.println("5. Exit");
            System.out.print("Choice: ");

            while (true) {
                String input = sc.nextLine().trim();
                try {
                    choice = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Please enter a valid number: ");
                }
            }

            switch (choice) {

                case 1:
                    System.out.print("Enter Description: ");
                    String desc = sc.nextLine();

                    if (desc.trim().isEmpty()) {
                        System.out.println("Description cannot be empty.");
                        break;
                    }

                    String pri;
                    while (true) {
                        System.out.print("Enter Priority (Low / Medium / High): ");
                        pri = sc.nextLine();

                        if (pri.equalsIgnoreCase("Low") ||
                                pri.equalsIgnoreCase("Medium") ||
                                pri.equalsIgnoreCase("High")) {

                            // make it look nice
                            pri = pri.substring(0,1).toUpperCase() +
                                    pri.substring(1).toLowerCase();
                            break;

                        } else {
                            System.out.println("Invalid priority. Please enter Low, Medium, or High.");
                        }
                    }

                    try {
                        Ticket t = new Ticket(
                                customer.getCustomerId(),
                                desc,
                                pri,
                                java.time.LocalDate.now().toString()
                        );

                        service.createTicket(t);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    service.viewTicketsByCustomer(customer.getCustomerId());
                    break;

                case 3:
                    System.out.print("Enter Ticket ID to close: ");
                    String closeId = sc.nextLine();

                    Ticket ticket = service.findTicketById(closeId);

                    if (ticket == null) {
                        System.out.println("Ticket not found.");
                        break;
                    }

                    // Ensure customer owns the ticket
                    if (!ticket.getCustomerId().equals(customer.getCustomerId())) {
                        System.out.println("You can only close your own tickets.");
                        break;
                    }

                    // Prevent closing already closed ticket
                    if (ticket.getStatus().equalsIgnoreCase("Closed")) {
                        System.out.println("Ticket is already closed.");
                        break;
                    }

                    ticket.setStatus("Closed");
                    System.out.println("Ticket closed successfully.");
                    break;

                case 4:
                    System.out.print("Enter closed Ticket ID to give feedback: ");
                    String feedbackTicketId = sc.nextLine();

                    Ticket feedbackTicket = service.findTicketById(feedbackTicketId);

                    // Prevent duplicate feedback
                    if (service.hasFeedbackForTicket(feedbackTicketId)) {
                        System.out.println("Feedback has already been submitted for this ticket.");
                        break;
                    }

                    if (feedbackTicket == null) {
                        System.out.println("Ticket not found.");
                        break;
                    }

                    if (!feedbackTicket.getCustomerId().equals(customer.getCustomerId())) {
                        System.out.println("You can only give feedback for your own tickets.");
                        break;
                    }

                    if (!feedbackTicket.getStatus().equalsIgnoreCase("Closed")) {
                        System.out.println("Feedback can only be given for closed tickets.");
                        break;
                    }

                    int rating;
                    while (true) {
                        System.out.print("Enter rating (1-5): ");
                        String input = sc.nextLine().trim();

                        try {
                            rating = Integer.parseInt(input);
                            if (rating >= 1 && rating <= 5) {
                                break;
                            } else {
                                System.out.println("Invalid rating. Please enter a number from 1 to 5.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid rating. Please enter a number from 1 to 5.");
                        }
                    }

                    System.out.print("Enter comment: ");
                    String comment = sc.nextLine();

                    String feedbackId = "F" + feedbackTicket.getTicketId().substring(1);

                    try {
                        Feedback feedback = new Feedback(feedbackId, feedbackTicket.getTicketId(), rating, comment);
                        service.addFeedback(feedback);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Exiting Customer Module...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);
    }
}