package com.mycompany.assignment;

public class Customer extends User {

    private String customerId;
    private static int counter = 1;

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

    public String getCustomerId() {
        return customerId;
    }
}
