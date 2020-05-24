package au.edu.utas.sddhewa.assignment.dto;

public class WinningDetailsDTO {

    private String ticketNumber;

    private long ticketOwnerId;

    public WinningDetailsDTO() {
    }

    public WinningDetailsDTO(String ticketNumber, long ticketOwnerId) {
        this.ticketNumber = ticketNumber;
        this.ticketOwnerId = ticketOwnerId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public long getTicketOwnerId() {
        return ticketOwnerId;
    }

    public void setTicketOwnerId(long ticketOwnerId) {
        this.ticketOwnerId = ticketOwnerId;
    }
}
