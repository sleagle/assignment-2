package au.edu.utas.sddhewa.assignment.modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.util.Date;

import au.edu.utas.sddhewa.assignment.util.RaffleType;
import au.edu.utas.sddhewa.assignment.util.Utility;

/**
 * Modal class of raffle object
 *
 * @author Sakna Hewa Galamulage
 * @date 05/05/2020
 * @updatedBy
 * @version 1
 */
public class Raffle implements Parcelable {

    /** id of the raffle */
    private long raffleId;

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

    private float prize;

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

    private byte[] raffleCover;

    public Raffle() {}

    public Raffle(String name, String description, RaffleType typeId, String startingDate,
                  String drawDate, float prize, boolean isActive, String location, float ticketPrice,
                  int noOfTickets, int ticketsSold, int maxTickets) throws ParseException {
        this.name = name;
        this.description = description;
        this.typeId = typeId;
        this.startingDate = Utility.DATE_FORMAT.parse(startingDate);
        this.drawDate = Utility.DATE_FORMAT.parse(drawDate);
        this.prize = prize;
        this.isActive = isActive;
        this.location = location;
        this.ticketPrice = ticketPrice;
        this.noOfTickets = noOfTickets;
        this.ticketsSold = ticketsSold;
        this.maxTickets = maxTickets;
    }

    protected Raffle(Parcel in) {
        raffleId = in.readInt();
        name = in.readString();
        description = in.readString();
        isActive = in.readByte() != 0;
        location = in.readString();
        ticketPrice = in.readFloat();
        prize = in.readFloat();
        noOfTickets = in.readInt();
        ticketsSold = in.readInt();
        maxTickets = in.readInt();
        raffleCover = in.createByteArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(raffleId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeString(location);
        dest.writeFloat(ticketPrice);
        dest.writeFloat(prize);
        dest.writeInt(noOfTickets);
        dest.writeInt(ticketsSold);
        dest.writeInt(maxTickets);
        dest.writeByteArray(raffleCover);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Raffle> CREATOR = new Creator<Raffle>() {
        @Override
        public Raffle createFromParcel(Parcel in) {
            return new Raffle(in);
        }

        @Override
        public Raffle[] newArray(int size) {
            return new Raffle[size];
        }
    };

    /**
     * get the raffle id
     *
     * @return raffle id
     */
    public long getRaffleId() {
        return raffleId;
    }

    /**
     * sets the raffle id
     *
     * @param raffleId - id
     */
    public void setRaffleId(long raffleId) {
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
    public void setTypeId(int typeId) {
        switch (typeId) {
            case 1:
                this.typeId =  RaffleType.NORMAL_RAFFLE;
                break;

            case 2:
                this.typeId = RaffleType.MARGIN_RAFFLE;
                break;

            default:
                break;
        }
    }

    public Date getStartingDate () {
        return startingDate;
    }
    /**
     * get the starting date of the raffle
     *
     * @return - starting date in string format
     */
    public String getStartingDateString() {
        return Utility.DATE_FORMAT.format(startingDate);
    }

    /**
     * sets the starting date of the raffle
     *
     * @param startingDate - starting date
     */
    public void setStartingDate(String startingDate) throws ParseException {
        this.startingDate = Utility.DATE_FORMAT.parse(startingDate);
    }

    /**
     * get the draw date for a raffle
     *
     * @return - draw date in string format
     */
    public String getDrawDate() {
        return Utility.DATE_FORMAT.format(drawDate);
    }

    /**
     * sets the draw date for a raffle
     *
     * @param drawDate - draw date
     */
    public void setDrawDate(String drawDate) throws ParseException {
        this.drawDate = Utility.DATE_FORMAT.parse(drawDate);
    }

    public float getPrize() {
        return prize;
    }

    public void setPrize(float prize) {
        this.prize = prize;
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
    public void setActive(int active) {
        isActive = active == 1;
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

    public byte[] getRaffleCover() {
        return raffleCover;
    }

    public void setRaffleCover(byte[] raffleCover) {
        this.raffleCover = raffleCover;
    }

    public String getTicketPriceString() {

        return Utility.getFormattedPrice(getTicketPrice());
    }

    public String getRafflePrizeString() {
        return Utility.getFormattedPrice(getPrize());
    }

    public String getTicketsSoldStringForList() {
        return  getTicketsSold() + "/" + getNoOfTickets();
    }
    /**
     * gets a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {

        return getName() + " - Starts: " + getStartingDateString();
        /*return new StringBuilder("Raffle { raffleId: ").append(raffleId)
                .append(", name: ").append(name).append(", description: ").append(description)
                .append(", typeId: ").append(typeId.toString()).append(", startingDate: ").append(startingDate)
                .append(", drawDate: ").append(drawDate).append(", isActive: ").append(isActive)
                .append(", location: ").append(location).append(", ticketPrice: ").append(ticketPrice)
                .append(", noOfTickets: ").append(noOfTickets).append(", ticketsSold: ").append(ticketsSold)
                .append(", maxTickets:").append(maxTickets).append(" }").toString();*/
    }
}
