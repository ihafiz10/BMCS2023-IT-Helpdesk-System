package com.mycompany.assignment;

import java.util.Scanner;

public class StaffModule {

    public void run(Scanner sc, TicketService service, Staff staff) {

        int choice;

        do {

            System.out.println("\n======================= Staff Guidelines ========================");
            System.out.println("1. View all tickets or only tickets assigned to you.");
            System.out.println("2. Use “View My Assigned Tickets” to focus on your assigned work.");
            System.out.println("3. Update ticket status as you process the issue.");
            System.out.println("4. Add responses to update customers on progress.");
            System.out.println("5. Closed tickets cannot be modified.");
            System.out.println("=================================================================\n");

            System.out.println("\n===== Staff Menu =====");
            System.out.println("Logged in as Staff ID: " + staff.getStaffID());
            System.out.println("1. View All Tickets");
            System.out.println("2. View My Assigned Tickets");
            System.out.println("3. Update Ticket Status");
            System.out.println("4. Respond to Ticket");
            System.out.println("5. Search Ticket");
            System.out.println("6. Filter by Status");
            System.out.println("7. Filter by Priority");
            System.out.println("8. Exit");
            System.out.print("Choice: ");

            while (true) {
                String input = sc.nextLine().trim();
                try {
                    choice = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Enter a number: ");
                }
            }

            switch (choice) {
                case 1:
                    staff.viewAllTickets(service.getTickets());
                    break;

                case 2:
                    service.viewTicketsByStaff(staff.getStaffID());
                    break;

                case 3:
                    System.out.print("Enter Ticket ID: ");
                    String ticketId = sc.nextLine();

                    System.out.print("New Status (Open / Pending / In Progress / Resolved / Closed): ");
                    String status = sc.nextLine();

                    service.updateTicketStatus(ticketId, status);
                    break;

                case 4:
                    System.out.print("Enter Ticket ID: ");
                    String responseTicketId = sc.nextLine();

                    System.out.print("Response: ");
                    String response = sc.nextLine();

                    service.addResponseToTicket(responseTicketId, response);
                    break;

                case 5:
                    System.out.print("Enter keyword / Ticket ID / Status / Priority / Customer ID: ");
                    String keyword = sc.nextLine();
                    service.searchTickets(keyword);
                    break;

                case 6:
                    System.out.print("Enter status to filter (Open / Pending / In Progress / Resolved / Closed): ");
                    String filterStatus = sc.nextLine();
                    service.filterTicketsByStatus(filterStatus);
                    break;

                case 7:
                    System.out.print("Enter priority to filter (Low / Medium / High): ");
                    String filterPriority = sc.nextLine();
                    service.filterTicketsByPriority(filterPriority);
                    break;

                case 8:
                    System.out.println("Exiting Staff Module...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 8);
    }
}