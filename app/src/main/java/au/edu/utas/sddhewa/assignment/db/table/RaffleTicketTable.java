package au.edu.utas.sddhewa.assignment.db.table;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import au.edu.utas.sddhewa.assignment.modal.RaffleTicket;
import au.edu.utas.sddhewa.assignment.util.FieldKey;

public class RaffleTicketTable {

    public static final String TABLE_NAME = "raffle_ticket";

    private static final String KEY_TICKET_ID = "raffle_ticket_id";
    private static final String KEY_RAFFLE_ID = "raffle_id";
    private static final String KEY_NUM_TICKETS = "num_tickets";
    private static final String KEY_CUSTOMER_ID = "customer_id";
    private static final String KEY_PURCHASED_DATE = "purchased_date";

    public static final String CREATE_STATEMENT = new StringBuilder(FieldKey.CREATE_TABLE.value)
            .append(TABLE_NAME).append(FieldKey.CREATE_TABLE_OPEN.value)
            .append(KEY_TICKET_ID).append(FieldKey.PK_AUTO_INCREMENT.value)
            .append(KEY_RAFFLE_ID).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_NUM_TICKETS).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_CUSTOMER_ID).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_PURCHASED_DATE).append(FieldKey.DATE_TIME.value).append(FieldKey.NOT_NULL.value)
            .append(FieldKey.FOREIGN_KEY.value).append(KEY_RAFFLE_ID)
            .append(FieldKey.REFERENCES.value).append(RaffleTable.TABLE_NAME)
            .append(FieldKey.BRACKETS_OPEN.value).append(KEY_RAFFLE_ID).append(FieldKey.BRACKETS_CLOSE.value).append(FieldKey.COMMA.value)
            .append(FieldKey.FOREIGN_KEY.value).append(KEY_CUSTOMER_ID)
            .append(FieldKey.REFERENCES.value).append(CustomerTable.TABLE_NAME)
            .append(FieldKey.BRACKETS_OPEN.value).append(KEY_CUSTOMER_ID).append(FieldKey.BRACKETS_CLOSE.value)
            .append(FieldKey.CREATE_TABLE_CLOSE.value).toString();

    public static long insert(SQLiteDatabase db, RaffleTicket raffleTicket) {

        ContentValues values = new ContentValues();
        values.put(KEY_RAFFLE_ID, raffleTicket.getRaffleId());
        values.put(KEY_NUM_TICKETS, raffleTicket.getNumTickets());
        values.put(KEY_CUSTOMER_ID, raffleTicket.getCustomerId());
        values.put(KEY_PURCHASED_DATE, raffleTicket.getPurchasedDate());

        return db.insert(TABLE_NAME, null, values);
    }
}
