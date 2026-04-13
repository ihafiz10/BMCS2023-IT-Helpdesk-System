package Assignment;

public class Feedback {
    private String feedbackId;
    private String ticketId;
    private int rating;
    private String comment;
    
    public Feedback(){};

    public Feedback(String feedbackId, String ticketId, int rating, String comment) {

        this.feedbackId = feedbackId;
        this.ticketId = ticketId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getFeedbackId() { return feedbackId; }
    public String getTicketId() { return ticketId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    @Override
    public String toString() {
        return "Feedback ID: " + feedbackId +
                "\nTicket ID: " + ticketId +
                "\nRating: " + rating +
                "\nComment: " + comment;
    }
}
