public class Main {
  public static void main(String[] args) {
    TicketService service = new TicketService();

    Ticket t1 = new Ticket("T001", "C001", "Cannot log in to portal", "High", "2026-04-05");
    Ticket t2 = new Ticket("T002", "C002", "WiFi connection unstable", "Medium", "2026-04-05");

    service.createTicket(t1);
    service.createTicket(t2);

    service.viewAllTickets();

    service.assignTicketToStaff("T001", "S001");
    service.addResponseToTicket("T001", "Please reset your password and try again.");
    service.updateTicketStatus("T001", "Resolved");
    service.closeTicket("T001");

    Feedback f1 = new Feedback("F001", "T001", 5, "Very helpful support.");
    service.addFeedback(f1);

    service.viewAllFeedback();
    service.generateMonthlyReport();
  }
}
