package au.edu.utas.sddhewa.assignment.dto;

import android.os.Parcel;
import android.os.Parcelable;

import au.edu.utas.sddhewa.assignment.modal.Customer;
import au.edu.utas.sddhewa.assignment.modal.RaffleTicket;

public class TicketsSoldDTO implements Parcelable {

    private RaffleTicket raffleTicket;

    private Customer customer;

    public TicketsSoldDTO(RaffleTicket raffleTicket, Customer customer) {
        this.raffleTicket = raffleTicket;
        this.customer = customer;
    }

    protected TicketsSoldDTO(Parcel in) {
        raffleTicket = in.readParcelable(RaffleTicket.class.getClassLoader());
        customer = in.readParcelable(Customer.class.getClassLoader());
    }

    public static final Creator<TicketsSoldDTO> CREATOR = new Creator<TicketsSoldDTO>() {
        @Override
        public TicketsSoldDTO createFromParcel(Parcel in) {
            return new TicketsSoldDTO(in);
        }

        @Override
        public TicketsSoldDTO[] newArray(int size) {
            return new TicketsSoldDTO[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(raffleTicket, flags);
        dest.writeParcelable(customer, flags);
    }
}
