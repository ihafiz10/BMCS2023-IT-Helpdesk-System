/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment;


/**
 *
 * @author User
 */
import java.util.ArrayList;

public class Database {
    private ArrayList<Ticket> tickets;

    public Database() {
        tickets = new ArrayList<>();
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public boolean addTicket(Ticket newTicket) {
        if (newTicket == null) {
            System.out.println("Ticket is null!");
            return false;
        }

        for (Ticket t : tickets) {
            if (t.getTicketID().equals(newTicket.getTicketID())) {
                System.out.println("Duplicate Ticket ID!");
                return false;
            }
        }

        tickets.add(newTicket);
        return true;
    }

    public Ticket findTicketByID(String id) {
        for (Ticket t : tickets) {
            if (t.getTicketID().equals(id)) {
                return t;
            }
        }
        return null;
    }
}