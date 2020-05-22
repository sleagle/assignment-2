package au.edu.utas.sddhewa.assignment.dto;

import au.edu.utas.sddhewa.assignment.modal.Customer;
import au.edu.utas.sddhewa.assignment.modal.RaffleTicket;

public class TicketsSoldDTO {

    private RaffleTicket raffleTicket;

    private Customer customer;

    public TicketsSoldDTO(RaffleTicket raffleTicket, Customer customer) {
        this.raffleTicket = raffleTicket;
        this.customer = customer;
    }

    public RaffleTicket getRaffleTicket() {
        return raffleTicket;
    }

    public void setRaffleTicket(RaffleTicket raffleTicket) {
        this.raffleTicket = raffleTicket;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
