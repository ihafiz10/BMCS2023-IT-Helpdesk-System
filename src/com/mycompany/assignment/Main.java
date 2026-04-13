package Assignment;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void pause(Scanner scanner) {
        System.out.print("\nPress Enter to return to the Manager Dashboard...");
        scanner.nextLine();
        }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        TicketService service = new TicketService();
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Staff> staffList = new ArrayList<>();
        staffList.add(new Staff("U002", "staff01", "staff@mail.com", "123456789", "S1"));
        
        DataRepository repo = new DataRepository();
        
        repo.loadCustomers(customers);
        repo.loadTicketsFromFile(service.getTickets());
        repo.loadFeedbacksFromFile(service.getFeedbackList());
        repo.loadStaffs(staffList);

        // Intro screen
        System.out.println("\n\n\n");
        System.out.println("==========================================================");
        System.out.println("                        Welcome to                        ");
        System.out.println("==========================================================");
        System.out.println("       TARUMT IT Helpdesk Ticket Management System        ");
        System.out.println("==========================================================");
        System.out.println("        Developed by Sukah, Adeline, Chloe, Ikhwan        ");
        System.out.println("==========================================================");
        System.out.print("\n\n\nPress ENTER to start...");
        sc.nextLine();


        int choice;

        do {
            System.out.println("\n===== IT HELPDESK TICKET MANAGEMENT SYSTEM =====");
            System.out.println("1. Customer Portal");
            System.out.println("2. Staff Login");
            System.out.println("3. Manager Module");
            System.out.println("4. Exit");

            choice = getValidInt(sc, "Choose role: ", 1, 4);
            
            switch (choice) {
                case 1 -> handleCustomerAccess(sc, service, customers,repo);

                case 2 -> {
                        System.out.println("\n===== Staff Login =====");
                        System.out.println("Demo Staff Username: staff01");
                        System.out.println("Demo Staff Password: 123456789");

                        String sName = service.getValidStringInput(sc, "Enter username (or 0 to return): ", "^[a-zA-Z0-9]+$", "Letters and numbers only.");
                        if (sName.equals("0")) break;

                        String sPass = service.getValidStringInput(sc, "Enter password: ", "^.+$", "Password cannot be empty.");

                        Staff foundStaff = null;
                        for (Staff s : staffList) {
                            if (s.getUsername().equals(sName) && s.getPassword().equals(sPass)) {
                                foundStaff = s;
                                break;
                            }
                        }

                            if (foundStaff != null) {
                                System.out.println("Login successful! Welcome " + foundStaff.getStaffID());
                                new StaffModule().run(sc, service, foundStaff, repo);
                            } else {
                                System.out.println("Invalid staff credentials!");
                            }
                    }
                
                case 3 -> {
                    System.out.println("\n===== Manager Login =====");
                    System.out.println("Demo Manager Username: manager01");
                    System.out.println("Demo Manager Password: 123456789");
                    
                    String mName = service.getValidStringInput(sc, "Enter username (or 0 to return): ", "^[a-zA-Z0-9]+$", "Invalid format.");
                    if (mName.equals("0")) break;
                    
                    String mPass = service.getValidStringInput(sc, "Enter password: ", "^.+$", "Invalid password.");
                    
                    if (mName.equals("manager01") && mPass.equals("123456789")) {
                        System.out.println("\nLogin Successful! Welcome, Manager.");
                        new ManagerModule().run(sc, service, repo, staffList);
                    } else {
                        System.out.println("Invalid Credentials.");
                    }
                }

                case 4 -> {
                    System.out.println("System exited.");
                        repo.saveCustomers(customers);
                        repo.saveTickets(service.getTickets());
                        repo.saveFeedback(service.getFeedbackList());
                        repo.saveStaffs(staffList);
                    System.out.println("System exited and data saved.");
                }

                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        sc.close();
    }

    public static void handleCustomerAccess(Scanner sc, TicketService service, ArrayList<Customer> customers,DataRepository repo) {
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

            System.out.println("\n===== Customer Portal =====");
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");

            customerChoice = getValidInt(sc, "Choice: ", 1, 3);

            switch (customerChoice) {
                case 1 -> {
                    System.out.println("\n===== Customer Sign In =====");
                    String username = service.getValidStringInput(sc, "Enter username (or 0 to return): ", "^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$", "Invalid format.");
                    if (username.equals("0")) return;
                    
                    String password = service.getValidStringInput(sc, "Enter password: ", "^.+$", "Cannot be empty.");

                    Customer existingCustomer = findCustomer(username, password, customers);

                    if (existingCustomer != null) {
                        System.out.println("Login successful!");
                        new CustomerModule().run(sc, service, existingCustomer, repo);
                    } else {
                        System.out.println("\nInvalid username or password. Please try again or sign up.\n");
                    }
                    }

                case 2 -> {
                    System.out.println("\n===== Customer Sign Up =====");
                    String signUpUsername = "";
                    
                    while(true){
                        String user = service.getValidStringInput(sc, "Enter username (6-12 chars): ", "^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$", "6-12 alphanumeric characters only.");
                        
                        if (user.equals("0")) break;
                        if (isUsernameTaken(user, customers)) {
                            System.out.println("Username already taken!"); continue;
                        }
                        
                        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
                        String email = service.getValidStringInput(sc, "Enter email: ", emailRegex, "Invalid email format!");
                        if (isEmailTaken(email, customers)) {
                            System.out.println("Email already registered!"); continue;
                        }
                        
                        String pass = service.getValidStringInput(sc, "Enter password (min 9 chars): ", "^.{9,}$", "Min. 9 characters required.");
                        
                        Customer newCustomer = new Customer(user, email, pass);
                        customers.add(newCustomer);
                        System.out.println("Account created! ID: " + newCustomer.getCustomerId());
                        repo.saveCustomers(customers);
                        
                        System.out.print("Press ENTER to enter the Customer Portal...");
                        sc.nextLine();
                        
                        new CustomerModule().run(sc, service, newCustomer, repo);
                        
                        break;
                    }
                }
                case 3 -> System.out.println("Exiting Customer Portal...");
                default -> System.out.println("Invalid choice.");
            }

        } while (customerChoice != 3);
    }

    public static Customer findCustomer(String user, String pass, ArrayList<Customer> customers) {
    for (Customer c : customers) {
        if (c.getUsername().trim().equalsIgnoreCase(user.trim()) && 
            c.getPassword().equals(pass)) {
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
    
    public static boolean isUsernameTaken(String username, ArrayList<Customer> customers) {
        for (Customer c : customers) {
            if (c.getUsername().equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    public static boolean isEmailTaken(String email, ArrayList<Customer> customers) {
        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }
    
    private static int getValidInt(Scanner sc, String prompt, int min, int max) {
            while (true) {
                System.out.print(prompt);
                String input = sc.nextLine().trim();
                try {
                    int val = Integer.parseInt(input);
                    if (val >= min && val <= max) return val;
                    System.out.println("Out of range! (" + min + "-" + max + ")");
                } catch (Exception e) {
                    System.out.println("Invalid number format.");
                }
            }
        }
    }
