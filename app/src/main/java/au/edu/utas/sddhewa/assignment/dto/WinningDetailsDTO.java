package au.edu.utas.sddhewa.assignment.dto;

public class WinningDetailsDTO {

    private String ticketNumber;

    private String ticketOwner;

    public WinningDetailsDTO() {
    }

    public WinningDetailsDTO(String ticketNumber, String ticketOwner) {
        this.ticketNumber = ticketNumber;
        this.ticketOwner = ticketOwner;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTicketOwner() {
        return ticketOwner;
    }

    public void setTicketOwner(String ticketOwner) {
        this.ticketOwner = ticketOwner;
    }
}
