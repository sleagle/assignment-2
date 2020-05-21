package au.edu.utas.sddhewa.assignment.modal;

import java.util.Date;

import au.edu.utas.sddhewa.assignment.util.Utility;

public class RaffleTicket {

    private long RaffleTicketId;

    private long raffleId;

    private int numTickets;

    private float totalPrice;

    private Date purchasedDate;

    private long customerId;

    public RaffleTicket(long raffleId, int numTickets, float totalPrice, Date purchasedDate) {
        this.raffleId = raffleId;
        this.numTickets = numTickets;
        this.totalPrice = totalPrice;
        this.purchasedDate = purchasedDate;
    }

    public RaffleTicket(long raffleId, int numTickets, float totalPrice, Date purchasedDate, long customerId) {
        this.raffleId = raffleId;
        this.numTickets = numTickets;
        this.totalPrice = totalPrice;
        this.purchasedDate = purchasedDate;
        this.customerId = customerId;
    }

    public long getRaffleTicketId() {
        return RaffleTicketId;
    }

    public void setRaffleTicketId(long raffleTicketId) {
        RaffleTicketId = raffleTicketId;
    }

    public long getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(long raffleId) {
        this.raffleId = raffleId;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float price) {
        this.totalPrice = price;
    }

    public String getPurchasedDate() {
        return Utility.DATE_TIME_FORMAT.format(purchasedDate);
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

}
