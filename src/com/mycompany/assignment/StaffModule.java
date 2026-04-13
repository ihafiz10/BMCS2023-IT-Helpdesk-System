package Assignment;

import java.util.Scanner;

public class StaffModule {
    
    public static void pause(Scanner scanner) {
        System.out.print("\nPress Enter to return to the Staff Portal...");
        scanner.nextLine();
    }

    public void run(Scanner sc, TicketService service, Staff staff, DataRepository repo) {
        int choice;
        do {

            System.out.println("\n======================= Staff Guidelines ========================");
            System.out.println("1. View all tickets or only tickets assigned to you.");
            System.out.println("2. Use \"View My Assigned Tickets\" to focus on your assigned work.");
            System.out.println("3. Update ticket status as you process the issue.");
            System.out.println("4. Add responses to update customers on progress.");
            System.out.println("5. Closed tickets cannot be modified.");
            System.out.println("=================================================================\n");
            
            System.out.println("\n===== Staff Menu =====");
            System.out.println("\nWelcome, Staff [" + staff.getStaffID() + "]");
            System.out.println("1. View All Tickets      2. View My Assigned Tickets");
            System.out.println("3. Update Status         4. Add Response");
            System.out.println("5. Global Search         6. Filter by Priority");
            System.out.println("7. Exit");

            choice = service.getValidIntInput(sc, "Choice: ", 1, 7);

            switch (choice) {
                case 1 -> service.viewAllTicketsFiltered(sc, service.getTickets());

                case 2 -> {
                    service.viewTicketsByStaff(staff.getStaffID());
                    pause(sc);
                }

                case 3 -> {
                    while(true){
                        String tid = service.getValidStringInput(sc, 
                                "Enter Ticket ID to update (or 0 to return): ", 
                                "^[a-zA-Z0-9]+$", "Invalid ID format!");
                        if (tid.equals("0")) break;
                        
                    Ticket targetTicket = service.findTicketById(tid);
                    if (targetTicket == null) {
                        System.out.println("Ticket not found! Please check the ID."); continue;
                    }
                    
                    String assignedId = targetTicket.getStaffId(); 
                        if (assignedId != null && !assignedId.equalsIgnoreCase(staff.getStaffID())) {
                            System.out.println("You can only update tickets assigned to YOU (" + staff.getStaffID() + ").");
                            continue; 
                        }
                    
                    if (targetTicket.getStatus().equalsIgnoreCase("Closed")) {
                        System.out.println("Cannot update a closed ticket!"); continue; 
                    }

                    System.out.println("\n===== Select New Status =====");
                    System.out.println("1. In Progress");
                    System.out.println("2. Pending");
                    System.out.println("3. Resolved");
                    System.out.println("4. Cancel Update");
                    
                    int statusChoice = service.getValidIntInput(sc, "New Status: ", 1, 4);
                    if (statusChoice == 4) break;
        
                    if (statusChoice == 4) continue;

                    String newStatus = switch (statusChoice) {
                        case 1 -> "In Progress";
                        case 2 -> "Pending";
                        case 3 -> "Resolved";
                        default -> "";
                    };
                    
                    if (service.updateTicketStatus(tid, newStatus)) {
                        System.out.println("Ticket status has been updated to [" + newStatus + "].");  
                        repo.saveTickets(service.getTickets());
                    } else {
                        System.out.println("No changes were made. (The ticket is already " + newStatus + " or not found)");
                    }
                    System.out.println("\nPress ENTER to update another ticket...");
                    sc.nextLine();
                }
                }

                case 4 -> {
                    while (true) {
                        String rid = service.getValidStringInput(sc, 
                                "Enter Ticket ID to respond (or 0 to return): ", 
                                "^[a-zA-Z0-9]+$", "Invalid ID!");

                        if (rid.equals("0")) break;

                        Ticket targetTicket = service.findTicketById(rid);
                        
                        if (targetTicket == null) {
                            System.out.println("Ticket not found. Please try again.");continue;
                        }

                        if (targetTicket.getStatus().equalsIgnoreCase("Closed")) {
                            System.out.println("This ticket is [Closed]. You cannot add further responses.");continue;
                        }

                        String staffResponse = service.getValidStringInput(sc, 
                                "Your Response (min 5 chars): ", 
                                "^.{5,}$", "Response too short! (Min 5 chars)");

                        if (!staffResponse.equals("0")) {
                            service.addResponseToTicket(rid, staffResponse);
                            repo.saveTickets(service.getTickets());
                            System.out.println("Response added.");
                    }
                        pause(sc);
                        break;
                }
                }

                case 5 -> {
                        System.out.println("\n===== Global Search =====");
                        System.out.println("(Search by ID, Status, Priority, Customer ID, or Keyword)");
                        
                        String keyword = service.getValidStringInput(sc, 
                            "Enter search term (or 0 to return): ", 
                            "^[a-zA-Z0-9 ]+$", "Only letters and numbers allowed.");

                        if (!keyword.equals("0")) {
                        service.searchTickets(keyword);
                        pause(sc);
                    }
                }
                
                case 6 -> {
                    while (true) {
                        System.out.println("\n===== Filter Tickets by Priority =====");
                        System.out.println("1. Low Priority");
                        System.out.println("2. Medium Priority");
                        System.out.println("3. High Priority");
                        System.out.println("4. Return to Staff Portal");

                        int pChoice = service.getValidIntInput(sc, "Select priority level: ", 1, 4);
                        
                        if (pChoice == 4) {
                            System.out.println("Returning to Staff Portal...");
                            break; 
                        }
                        
                        if (pChoice != 4) {
                        String pStr = switch(pChoice) {
                            case 1 -> "Low";
                            case 2 -> "Medium";
                            case 3 -> "High";
                            default -> "";
                        };
                        
                        service.filterTicketsByPriority(pStr);
                        pause(sc);
                    }
                    }
                }
                case 7 -> System.out.println("Exiting Staff Module...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 7);
    }
}
