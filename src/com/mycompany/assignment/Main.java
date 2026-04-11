package com.mycompany.assignment;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TicketService service = new TicketService();
        ArrayList<Customer> customers = new ArrayList<>();

        // fixed demo staff account
        Staff demoStaff = new Staff("U002", "staff01", "staff@mail.com", "123456789", "S1");

        // Intro screen
        System.out.println("\n\n\n");
        System.out.println("==========================================================");
        System.out.println("                        Welcome to                        ");
        System.out.println("==========================================================");
        System.out.println("       TARUMT IT Helpdesk Ticket Management System        ");
        System.out.println("==========================================================");
        System.out.println("        Developed by Sukah, Adeline, Chloe, Ikhwan        ");
        System.out.println("==========================================================");
        System.out.println("\n\n\nPress ENTER to start...");
        sc.nextLine();


        int choice;

        do {
            System.out.println("\n===== IT HELPDESK TICKET MANAGEMENT SYSTEM =====");
            System.out.println("1. Customer Portal");
            System.out.println("2. Staff Login");
            System.out.println("3. Manager Module");
            System.out.println("4. Exit");
            System.out.print("Choose role: ");

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
                    handleCustomerAccess(sc, service, customers);
                    break;

                case 2:
                    System.out.println("\n--- Staff Login ---");
                    System.out.println("Demo Staff Username: staff01");
                    System.out.println("Demo Staff Password: 123456789");

                    System.out.print("Enter username: ");
                    String staffUsername = sc.nextLine().trim();

                    System.out.print("Enter password: ");
                    String staffPassword = sc.nextLine().trim();

                    if (staffUsername.equals("staff01") && staffPassword.equals("123456789")) {

                        System.out.print("Enter Staff ID (S1 / S2 / S3 / S4): ");
                        String staffIdInput = sc.nextLine().trim().toUpperCase();

                        if (!staffIdInput.equals("S1") &&
                                !staffIdInput.equals("S2") &&
                                !staffIdInput.equals("S3") &&
                                !staffIdInput.equals("S4")) {
                            System.out.println("Invalid Staff ID.");
                            break;
                        }

                        Staff loggedInStaff = new Staff(
                                "U" + staffIdInput.substring(1),
                                staffUsername,
                                "staff@mail.com",
                                staffPassword,
                                staffIdInput
                        );

                        StaffModule sm = new StaffModule();
                        sm.run(sc, service, loggedInStaff);

                    } else {
                        System.out.println("Invalid staff username or password.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Manager Login ---");
                    System.out.println("Demo Manager Username: manager01");
                    System.out.println("Demo Manager Password: 123456789");

                    System.out.print("Enter username: ");
                    String managerUsername = sc.nextLine();

                    System.out.print("Enter password: ");
                    String managerPassword = sc.nextLine();

                    if (managerUsername.equals("manager01") && managerPassword.equals("123456789")) {
                        TicketManager manager = new TicketManager();
                        manager.setTicketList(service.getTickets());

                        int mChoice;
                        do {
                            System.out.println("\n====================== Manager Guidelines =======================");
                            System.out.println("1. Monitor overall ticket status and workload.");
                            System.out.println("2. Identify critical tickets (High priority).");
                            System.out.println("3. Assign tickets to staff members.");
                            System.out.println("4. Closed tickets cannot be reassigned.");
                            System.out.println("5. Review feedback and system performance.");
                            System.out.println("=================================================================\n");

                            System.out.println("\n--- Manager Menu ---");
                            System.out.println("1. Monitor Tickets");
                            System.out.println("2. Critical Tickets");
                            System.out.println("3. Monthly Report");
                            System.out.println("4. Performance");
                            System.out.println("5. View Feedback");
                            System.out.println("6. View All Tickets");
                            System.out.println("7. Assign Ticket");
                            System.out.println("8. Exit Manager Module");
                            System.out.print("Choice: ");

                            while (true) {
                                String input = sc.nextLine().trim();
                                try {
                                    mChoice = Integer.parseInt(input);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.print("Please enter a valid number: ");
                                }
                            }

                            switch (mChoice) {
                                case 1:
                                    manager.monitorTicketStatus();
                                    break;
                                case 2:
                                    manager.identifyCriticalTickets();
                                    break;
                                case 3:
                                    manager.generateMonthlyTicketReport(2026, 4);
                                    break;
                                case 4:
                                    manager.calculateAverageResponseTime();
                                    break;
                                case 5:
                                    manager.viewAllFeedback(service.getFeedbackList());
                                    break;
                                case 6:
                                    service.viewAllTickets();
                                    break;
                                case 7:
                                    System.out.print("Enter Ticket ID: ");
                                    String ticketId = sc.nextLine();

                                    System.out.print("Enter Staff ID (S1 / S2 / S3 / S4): ");
                                    String staffId = sc.nextLine();

                                    service.assignTicketToStaff(ticketId, staffId);
                                    break;
                                case 8:
                                    System.out.println("Exiting Manager Module...");
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }

                        } while (mChoice != 8);
                    } else {
                        System.out.println("Invalid manager username or password.");
                    }
                    break;

                case 4:
                    System.out.println("System exited.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        sc.close();
    }

    public static void handleCustomerAccess(Scanner sc, TicketService service, ArrayList<Customer> customers) {
        int customerChoice;

        do {

            System.out.println("\n====================== Customer Guidelines ======================");
            System.out.println("1. Sign up to create a new account.");
            System.out.println("2. Sign in using your username and password.");
            System.out.println("3. Create tickets by entering description and priority.");
            System.out.println("4. View your tickets to check status and responses.");
            System.out.println("5. Can close tickets after you feel your issue has been resolved.");
            System.out.println("6. Feedback can only be given for closed tickets.");
            System.out.println("=================================================================\n");

            System.out.println("\n--- Customer Portal ---");
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choice: ");

            while (true) {
                String input = sc.nextLine().trim();
                try {
                    customerChoice = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Please enter a valid number: ");
                }
            }

            switch (customerChoice) {
                case 1:
                    System.out.println("\n--- Customer Sign In ---");
                    System.out.print("Enter username: ");
                    String signInUsername = sc.nextLine().trim();

                    System.out.print("Enter password: ");
                    String signInPassword = sc.nextLine().trim();

                    Customer existingCustomer = findCustomer(signInUsername, signInPassword, customers);

                    if (existingCustomer != null) {
                        System.out.println("Login successful!");
                        CustomerModule cm = new CustomerModule();
                        cm.run(sc, service, existingCustomer);
                    } else {
                        System.out.println("Invalid username or password. Please try again or sign up.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- Customer Sign Up ---");

                    System.out.print("Enter username (min 6 characters): ");
                    String signUpUsername = sc.nextLine().trim();

                    if (signUpUsername.length() < 6) {
                        System.out.println("Invalid username. Must be at least 6 characters.");
                        break;
                    }

                    System.out.print("Enter email: ");
                    String signUpEmail = sc.nextLine().trim();

                    if (!signUpEmail.contains("@") || !signUpEmail.contains(".")) {
                        System.out.println("Invalid email format.");
                        break;
                    }

                    System.out.print("Enter password (minimum 9 characters): ");
                    String signUpPassword = sc.nextLine().trim();

                    // Password validation
                    if (signUpPassword.length() < 9) {
                        System.out.println("Invalid password. Must be at least 9 characters.");
                        break; // go back to Customer Portal
                    }

                    // Duplicate check
                    if (isDuplicateCustomer(signUpUsername, signUpEmail, customers)) {
                        System.out.println("This username or email has already been used.");
                        break;
                    }

                    // Create account
                    Customer newCustomer = new Customer(signUpUsername, signUpEmail, signUpPassword);
                    customers.add(newCustomer);

                    System.out.println("Account created successfully!");
                    System.out.println("Your Customer ID is: " + newCustomer.getCustomerId());

                    CustomerModule cm = new CustomerModule();
                    cm.run(sc, service, newCustomer);
                    break;

                case 3:
                    System.out.println("Exiting Customer Portal...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (customerChoice != 3);
    }

    public static Customer findCustomer(String username, String password, ArrayList<Customer> customers) {

        for (Customer c : customers) {


            if (c.getUsername().trim().equalsIgnoreCase(username.trim()) &&
                    c.getPassword().trim().equals(password.trim())) {

                return c;
            }
        }

        return null;
    }

    public static boolean isDuplicateCustomer(String username, String email, ArrayList<Customer> customers) {
        for (Customer c : customers) {
            if (c.getUsername().equalsIgnoreCase(username) ||
                    c.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}