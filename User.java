package com.mycompany.assignment;

public class User {
    private String userId;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean isActive;
    private String dateJoined;

    public User() {
        setUserId("ABC1234567");
        setUsername("Guest01");
        setEmail("none@example.com");
        setPassword("123456789");
        setRole("Customer");
        setActive(true);
        this.dateJoined = java.time.LocalDate.now().toString();
    }

    public User(String userId, String username, String email, String password,
                String role, boolean isActive, String dateJoined) {
        setUserId(userId);
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setRole(role);
        setActive(isActive);
        this.dateJoined = dateJoined;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId != null && userId.toUpperCase().matches("^[a-zA-Z0-9]{9,}$")) {
            this.userId = userId.toUpperCase();
        } else {
            this.userId = "INVALID_ID";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null && username.matches("^[a-zA-Z0-9_]{6,}$")) {
            this.username = username;
        } else {
            this.username = "INVALID_USERNAME";
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            this.email = "INVALID_EMAIL";
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null && password.length() >= 9) {
            this.password = password;
        } else {
            this.password = "INVALID_PASSWORD";
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role != null && (role.equals("Customer") || role.equals("Staff") || role.equals("Manager"))) {
            this.role = role;
        } else {
            this.role = "Customer";
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public boolean validateLogin(String inputUser, String inputPass) {
        return this.username.equals(inputUser)
                && this.password.equals(inputPass)
                && this.isActive;
    }

    public boolean validateManagerLogin(String inputUser, String inputPass) {
        if (validateLogin(inputUser, inputPass)) {
            if (this.role.equalsIgnoreCase("Manager")) {
                System.out.println("[Login] Welcome, Manager " + this.username);
                return true;
            } else {
                System.out.println("[Access Denied] You do not have Manager privileges.");
                return false;
            }
        }

        System.out.println("[Error] Invalid username or password.");
        return false;
    }
}
