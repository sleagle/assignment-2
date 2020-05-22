package au.edu.utas.sddhewa.assignment.modal;

import android.os.Parcel;
import android.os.Parcelable;

public class Ticket implements Parcelable {

    private String ticketNumber;

    private long RaffleTicketId;

    public Ticket(String ticketNumber, long raffleTicketId) {
        this.ticketNumber = ticketNumber;
        RaffleTicketId = raffleTicketId;
    }

    private Ticket(Parcel in) {
        ticketNumber = in.readString();
        RaffleTicketId = in.readLong();
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ticketNumber);
        dest.writeLong(RaffleTicketId);
    }
}
