package com.mycompany.assignment;

import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> userList;

    public UserManager() {
        this.userList = new ArrayList<>();
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        if (user.getUserId().equals("INVALID_ID")) {
            System.out.println("Invalid user data.");
            return;
        }

        userList.add(user);
        System.out.println("User added: " + user.getUsername());
    }

    public User findUserById(String id) {
        for (User u : userList) {
            if (u.getUserId().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null;
    }

    public void deactivateUser(String id) {
        User u = findUserById(id);

        if (u != null) {
            u.setActive(false);
            System.out.println("User deactivated.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void displayUsersByRole(String role) {
        System.out.println("\n--- Users ---");

        for (User u : userList) {
            if (role.equalsIgnoreCase("All") ||
                    u.getRole().equalsIgnoreCase(role)) {

                System.out.println(u.getUserId() + " | " +
                        u.getUsername() + " | " +
                        u.getRole());
            }
        }
    }

    public boolean loginAsManager(String username, String password) {
        for (User u : userList) {
            if (u.getUsername().equals(username)) {
                return u.validateManagerLogin(username, password);
            }
        }
        return false;
    }
}
