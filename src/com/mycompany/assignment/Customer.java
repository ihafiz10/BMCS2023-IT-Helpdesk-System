package Assignment;

public class Customer extends User {

    private String customerId;
    private static int counter = 1;
    
    public Customer(){}

    public Customer(String username, String email, String password) {
        super(
                String.format("CUS%06d", counter),   // CUS000001
                username,
                email,
                password,
                "Customer",
                true,
                java.time.LocalDate.now().toString()
        );

        this.customerId = String.format("CUS%06d", counter);
        counter++;
}
    // Load customer from txt.
    public Customer(String customerId, String username, String email, String password, String dateJoined) {
        super(customerId, username, email, password, "Customer", true, dateJoined);
        this.customerId = customerId;
    }
    public String getCustomerId() {
        return customerId;
    }
    
    public static void setCounter(int newCounter) {
        counter = newCounter;
    }
}
