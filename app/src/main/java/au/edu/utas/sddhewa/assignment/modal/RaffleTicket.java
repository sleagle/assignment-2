package au.edu.utas.sddhewa.assignment.modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.util.Date;

import au.edu.utas.sddhewa.assignment.util.Utility;

public class RaffleTicket implements Parcelable {

    private long RaffleTicketId;

    private long raffleId;

    private int numTickets;

    private float totalPrice;

    private Date purchasedDate;

    private long customerId;

    public RaffleTicket() {}

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

    private RaffleTicket(Parcel in) {
        RaffleTicketId = in.readLong();
        raffleId = in.readLong();
        numTickets = in.readInt();
        totalPrice = in.readFloat();
        customerId = in.readLong();
    }

    public static final Creator<RaffleTicket> CREATOR = new Creator<RaffleTicket>() {
        @Override
        public RaffleTicket createFromParcel(Parcel in) {
            return new RaffleTicket(in);
        }

        @Override
        public RaffleTicket[] newArray(int size) {
            return new RaffleTicket[size];
        }
    };

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

    public void setPurchasedDate(String purchasedDate) throws ParseException {
        this.purchasedDate = Utility.DATE_FORMAT.parse(purchasedDate);
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(RaffleTicketId);
        dest.writeLong(raffleId);
        dest.writeInt(numTickets);
        dest.writeFloat(totalPrice);
        dest.writeLong(customerId);
    }

    @Override
    public String toString() {
        return "RaffleTicket{" +
                "RaffleTicketId=" + RaffleTicketId +
                ", raffleId=" + raffleId +
                ", numTickets=" + numTickets +
                ", totalPrice=" + totalPrice +
                ", purchasedDate=" + purchasedDate +
                ", customerId=" + customerId +
                '}';
    }
}
