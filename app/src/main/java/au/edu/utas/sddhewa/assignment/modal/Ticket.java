package au.edu.utas.sddhewa.assignment.modal;

public class Ticket {

    private String ticketNumber;

    private long RaffleTicketId;

    public Ticket(String ticketNumber, long raffleTicketId) {
        this.ticketNumber = ticketNumber;
        RaffleTicketId = raffleTicketId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public long getRaffleTicketId() {
        return RaffleTicketId;
    }

    public void setRaffleTicketId(int raffleTicketId) {
        RaffleTicketId = raffleTicketId;
    }
}
