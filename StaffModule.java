/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment;


/**
 *
 * @author User
 */
import java.util.Scanner;

public class StaffModule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Database db = new Database();

        // Sample staff
        Staff staff = new Staff("Admin", "1234", "S1");

        // 🔐 Login
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        if (!staff.getUsername().equals(username) || !staff.login(pass)) {
            System.out.println("Invalid username or password!");
            return;
        }

        int choice;

        do {
            System.out.println("\n1. Add Ticket");
            System.out.println("2. View Tickets");
            System.out.println("3. Update Ticket");
            System.out.println("4. Respond Ticket");
            System.out.println("5. Exit");
            System.out.print("Choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Enter a number!");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Ticket ID: ");
                    String id = sc.nextLine();

                    System.out.print("Description: ");
                    String desc = sc.nextLine();

                    System.out.print("Priority: ");
                    String pri = sc.nextLine();

                    if (id.isEmpty() || desc.isEmpty() || pri.isEmpty()) {
                        System.out.println("All fields required!");
                        break;
                    }

                    Ticket newT = new Ticket(id, desc, pri);

                    if (db.addTicket(newT)) {
                        System.out.println("Ticket added!");
                    }
                    break;

                case 2:
                    staff.viewAllTickets(db.getTickets());
                    break;

                case 3:
                    System.out.print("Enter Ticket ID: ");
                    String upID = sc.nextLine();

                    Ticket t1 = db.findTicketByID(upID);

                    if (t1 == null) {
                        System.out.println("Ticket not found!");
                        break;
                    }

                    System.out.print("New Status (Open / In Progress / Resolved / Closed): ");
                    String status = sc.nextLine();

                    staff.updateStatus(t1, status);
                    break;

                case 4:
                    System.out.print("Enter Ticket ID: ");
                    String resID = sc.nextLine();

                    Ticket t2 = db.findTicketByID(resID);

                    if (t2 == null) {
                        System.out.println("Ticket not found!");
                        break;
                    }

                    System.out.print("Response: ");
                    String response = sc.nextLine();

                    staff.respondTicket(t2, response);
                    break;

            }

        } while (choice != 5);

        System.out.println("Exiting system...");
    }
}