package au.edu.utas.sddhewa.assignment.modal;

import java.text.SimpleDateFormat;
import java.util.Date;

import au.edu.utas.sddhewa.assignment.util.RaffleType;

/**
 * Modal class of raffle object
 *
 * @author Sakna Hewa Galamulage
 * @date 05/05/2020
 * @updatedBy
 * @version 1
 */
public class Raffle {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /** id of the raffle */
    private int raffleId;

    /** name of the raffle */
    private String name;

    /** description/details of the raffle */
    private String description;

    /** type of the raffle either normal/margin */
    private RaffleType typeId;

    /** starting date of the raffle */
    private Date startingDate;

    /** draw date of the raffle */
    private Date drawDate;

    /** if the raffle is current or past */
    private boolean isActive;

    /** location where the raffle would be drawn */
    private String location;

    /** price of the raffle ticket */
    private float ticketPrice;

    /** number of tickets allowed for the raffle */
    private int noOfTickets;

    /** number of tickets sold so far */
    private int ticketsSold;

    /** number of tickets allowed per a person */
    private int maxTickets;

    /**
     * get the raffle id
     *
     * @return raffle id
     */
    public int getRaffleId() {
        return raffleId;
    }

    /**
     * sets the raffle id
     *
     * @param raffleId - id
     */
    public void setRaffleId(int raffleId) {
        this.raffleId = raffleId;
    }

    /**
     * get the raffle name
     *
     * @return - raffle name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the raffle name
     *
     * @param name - raffle name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the raffle description/detail
     *
     * @return raffle description/detail
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the raffle description/detail
     *
     * @param description - raffle description/detail
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get the type of the raffle either normal/margin
     *
     * @return - raffle type
     */
    public RaffleType getTypeId() {
        return typeId;
    }

    /**
     * sets type of the raffle either normal/margin
     *
     * @param typeId - raffle type
     */
    public void setTypeId(RaffleType typeId) {
        this.typeId = typeId;
    }

    /**
     * get the starting date of the raffle
     *
     * @return - starting date in string format
     */
    public String getStartingDate() {
        return dateFormat.format(startingDate);
    }

    /**
     * sets the starting date of the raffle
     *
     * @param startingDate - starting date
     */
    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * get the draw date for a raffle
     *
     * @return - draw date in string format
     */
    public String getDrawDate() {
        return dateFormat.format(drawDate);
    }

    /**
     * sets the draw date for a raffle
     *
     * @param drawDate - draw date
     */
    public void setDrawDate(Date drawDate) {
        this.drawDate = drawDate;
    }

    /**
     * gets if the raffle is active or not
     *
     * @return true if active else false
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * sets if the raffle is active or not
     *
     * @param active - state
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * gets the location where the raffle draw will take place
     *
     * @return location of the raffle draw
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the location where the raffle draw will take place
     *
     * @param location - location of the raffle draw
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * get the price of a raffle ticket
     *
     * @return price of a ticket
     */
    public float getTicketPrice() {
        return ticketPrice;
    }

    /**
     * sets the price of a raffle ticket
     *
     * @param ticketPrice - ticket price
     */
    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * get the number of tickets allocated for the raffle
     *
     * @return number of tickets
     */
    public int getNoOfTickets() {
        return noOfTickets;
    }

    /**
     * set the number of tickets allocated for the raffle
     *
     * @param noOfTickets - number of tickets
     */
    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    /**
     * get the number of tickets sold so far
     *
     * @return - tickets sold
     */
    public int getTicketsSold() {
        return ticketsSold;
    }

    /**
     * sets the number of tickets sold so far
     *
     * @param ticketsSold - number of tickets sold
     */
    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    /**
     * gets the max number of tickets allowed for a user
     *
     * @return - number of tickets
     */
    public int getMaxTickets() {
        return maxTickets;
    }

    /**
     * sets the max number of tickets
     *
     * @param maxTickets - number of tickets
     */
    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    /**
     * gets a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {

        return new StringBuilder("Raffle { raffleId: ").append(raffleId)
                .append(", name: ").append(name).append(", description: ").append(description)
                .append(", typeId: ").append(typeId.toString()).append(", startingDate: ").append(startingDate)
                .append(", drawDate: ").append(drawDate).append(", isActive: ").append(isActive)
                .append(", location: ").append(location).append(", ticketPrice: ").append(ticketPrice)
                .append(", noOfTickets: ").append(noOfTickets).append(", ticketsSold: ").append(ticketsSold)
                .append(", maxTickets:").append(maxTickets).append(" }").toString();
    }
}
