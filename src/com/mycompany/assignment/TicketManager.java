package Assignment;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

public class TicketManager {

    private ArrayList<Ticket> ticketList;
    private ArrayList<User> userList;

    public TicketManager() {
        this.ticketList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }

    public void setTicketList(ArrayList<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    // Find ticket
    public Ticket findTicketById(String ticketId) {
        for (Ticket t : ticketList) {
            if (t.getTicketId().equalsIgnoreCase(ticketId)) {
                return t;
            }
        }
        return null;
    }

    // Find staff with least workload
    public String getMostAvailableStaff() {
        String bestStaff = "NONE";
        int minTickets = Integer.MAX_VALUE;

        for (User u : userList) {
            if (u.getRole().equalsIgnoreCase("Staff") && u.isActive()) {

                int workload = 0;

                for (Ticket t : ticketList) {
                    if (t.getStaffId() != null &&
                            t.getStaffId().equals(u.getUserId()) &&
                            !t.getStatus().equalsIgnoreCase("Resolved")
                            && !t.getStatus().equalsIgnoreCase("Closed")) {
                        workload++;
                    }
                }

                if (workload < minTickets) {
                    minTickets = workload;
                    bestStaff = u.getUserId();
                }
            }
        }
        return bestStaff;
    }

    // Critical tickets
    public void identifyCriticalTickets() {
        System.out.println("\n================================================================");
        System.out.println("               CRITICAL TICKETS DASHBOARD ");
        System.out.println("================================================================");
        System.out.printf("%-10s | %-10s | %-12s | %-15s\n", "ID", "PRIORITY", "WAIT TIME", "STATUS");
        System.out.println("----------------------------------------------------------------");

        LocalDateTime now = LocalDateTime.now();

        List<Ticket> criticalOnes = ticketList.stream()
            .filter(t -> {
                boolean high = t.getPriority().equalsIgnoreCase("High");
                boolean notResolved = !t.getStatus().equalsIgnoreCase("Resolved") 
                                   && !t.getStatus().equalsIgnoreCase("Closed");
                long hours = Duration.between(t.getCreationTime(), now).toHours();
                return notResolved && (high || hours >= 24);
            })

            .sorted((t1, t2) -> {
                if (t1.getPriority().equalsIgnoreCase("High") && !t2.getPriority().equalsIgnoreCase("High")) return -1;
                if (!t1.getPriority().equalsIgnoreCase("High") && t2.getPriority().equalsIgnoreCase("High")) return 1;
                return t2.getCreationTime().compareTo(t1.getCreationTime());
            })
            .toList();


        if (criticalOnes.isEmpty()) {
            System.out.println("            Excellent! No critical tickets found.");
        } else {
            for (Ticket t : criticalOnes) {
                long totalHours = Duration.between(t.getCreationTime(), now).toHours();
                String waitTimeDisplay;


                if (totalHours >= 24) {
                    waitTimeDisplay = (totalHours / 24) + " days ago";
                } else {
                    waitTimeDisplay = totalHours + " hours ago";
                }


                String prefix = (t.getPriority().equalsIgnoreCase("High") && totalHours >= 24) ? "!" : " ";

                System.out.printf("%s%-9s | %-10s | %-12s | %-15s\n", 
                                  prefix, t.getTicketId(), t.getPriority(), waitTimeDisplay, t.getStatus());
            }
        }

        System.out.println("================================================================\n");
    }

    // Monitoring
    public void monitorTicketStatus() {
        int open = 0;
        int pending = 0;
        int inProgress = 0;
        int resolved = 0;
        int closed = 0;

        for (Ticket t : ticketList) {
            switch (t.getStatus().toLowerCase()) {
                case "open" -> open++;
                case "pending" -> pending++;
                case "in progress" -> inProgress++;
                case "resolved" -> resolved++;
                case "closed" -> closed++;
            }
        }

        System.out.println("\n==================== Ticket Monitoring ====================\n");
        System.out.println("Open       : " + open);
        System.out.println("Pending    : " + pending);
        System.out.println("In Progress: " + inProgress);
        System.out.println("Resolved   : " + resolved);
        System.out.println("Closed     : " + closed);
        System.out.println("Total      : " + ticketList.size());
        System.out.println("\n===========================================================");
    }

    // Monthly report
    public void generateMonthlyTicketReport(int year, int month) {
    int total = 0;
    int resolved = 0;
    int hardwareCount = 0, softwareCount = 0, networkCount = 0, loginCount = 0, othersCount = 0;

    for (Ticket t : ticketList) {
        LocalDateTime time = t.getCreationTime();

        if (time.getYear() == year && time.getMonthValue() == month) {
            total++;
            
            if (t.getStatus().equalsIgnoreCase("Resolved") || t.getStatus().equalsIgnoreCase("Closed")) {
                resolved++;
            }

            String desc = t.getDescription().toLowerCase();
            if (desc.contains("[hardware]")) hardwareCount++;
            else if (desc.contains("[software]")) softwareCount++;
            else if (desc.contains("[network/wi-fi]")) networkCount++;
            else if (desc.contains("[account/login]")) loginCount++;
            else othersCount++;
        }
    }

    System.out.println("\n===================================================================");
    System.out.printf("           PERFORMANCE REPORT - %02d/%d\n", month, year);
    System.out.println("===================================================================");
    
    if (total == 0) {
        System.out.println("          No data available for this period.");
    } else {
        double rate = ((double) resolved / total) * 100;
        System.out.printf("%-25s : %d\n", "Total Tickets Created", total);
        System.out.printf("%-25s : %d\n", "Successfully Resolved", resolved);
        System.out.printf("%-25s : %d\n", "Still Outstanding", (total - resolved));
        System.out.printf("%-25s : %.1f%%\n", "Monthly Resolution Rate", rate);
        
        System.out.println("------------------------------------------------------------------");

        System.out.println("CATEGORY BREAKDOWN:");
        System.out.printf("  - Hardware        : %d\n", hardwareCount);
        System.out.printf("  - Software        : %d\n", softwareCount);
        System.out.printf("  - Network/Wi-Fi   : %d\n", networkCount);
        System.out.printf("  - Account/Login   : %d\n", loginCount);
        System.out.printf("  - Others          : %d\n", othersCount);
    }
    
    System.out.println("==================================================================\n");
}

    // Performance
    public void displayStaffPerformance(int targetYear, int targetMonth, ArrayList<Staff> staffList) {
    System.out.println("\n=====================================================================================");
    System.out.println("              STAFF PERFORMANCE ANALYSIS ");
    System.out.println("=====================================================================================");
    System.out.printf("%-10s | %-20s | %-10s | %-18s | %-12s\n", 
                  "STAFF ID", "NAME", "TICKETS", "AVG RESP TIME", "EFFICIENCY");
    System.out.println("------------------------------------------------------------------------------------");


    for (Staff staff : staffList) {
        String sid = staff.getStaffID();
        String sName = staff.getUsername();

        long totalMinutes = 0;
        int resolvedCount = 0;
        int totalAssigned = 0;

        for (Ticket t : ticketList) {
            
            boolean isStaffMatch = t.getAssignedStaffId() != null && t.getAssignedStaffId().equalsIgnoreCase(sid);
            boolean isYearMatch = t.getCreationTime().getYear() == targetYear;
            boolean isMonthMatch = t.getCreationTime().getMonthValue() == targetMonth;
            
            if (isStaffMatch && isYearMatch && isMonthMatch) {
                totalAssigned++;
            
            if (t.getResolutionTime() != null) {
                    Duration d = Duration.between(t.getCreationTime(), t.getResolutionTime());
                    totalMinutes += d.toMinutes();
                    resolvedCount++;
                }
            }
        }

        if (totalAssigned > 0) {
            String avgTimeStr;
            if (resolvedCount > 0) {
                long avgMins = totalMinutes / resolvedCount;
                long d = avgMins / 1440;
                long h = (avgMins % 1440) / 60;
                long m = avgMins % 60;
                avgTimeStr = (d > 0 ? d + "d " : "") + (h > 0 || d > 0 ? h + "h " : "") + m + "m";
            } else {
                avgTimeStr = "Pending";
            }

            double solveRate = ((double) resolvedCount / totalAssigned) * 100;
            System.out.printf("%-10s | %-20s | %-10s | %-18s | %-12s\n", 
                              sid, sName, totalAssigned, avgTimeStr, solveRate);
        } else {
            System.out.printf("%-10s | %-20s | %-10s | %-18s | %-12s\n", 
                              sid, sName, "0", "No Task", "0.0%");
        }
    }
    System.out.println("====================================================================================\n");
}

    // Feedback view
    public void viewAllFeedback(ArrayList<Feedback> feedbackList) {
        System.out.println("\n--- Feedback ---");

        if (feedbackList.isEmpty()) {
            System.out.println("No feedback available.");
            return;
        }

        for (Feedback f : feedbackList) {
            System.out.println("Ticket: " + f.getTicketId() +
                    " | Rating: " + f.getRating() +
                    " | Comment: " + f.getComment());
        }
    }

    // TicketManager.java
    public boolean assignTicketToStaff(String tid, String sid) {
        for (Ticket t : ticketList) {
            if (t.getTicketId().equalsIgnoreCase(tid)) {
                if (t.getStatus().equalsIgnoreCase("Closed")) {
                    return false;
                }


                t.setStaffId(sid.toUpperCase()); 

                return true;
            }
        }
        return false; 
    }
}
