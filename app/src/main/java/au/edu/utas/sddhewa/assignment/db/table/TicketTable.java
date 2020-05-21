package au.edu.utas.sddhewa.assignment.db.table;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import au.edu.utas.sddhewa.assignment.modal.Ticket;
import au.edu.utas.sddhewa.assignment.util.FieldKey;

public class TicketTable {

    public static final String TABLE_NAME = "ticket";

    private static final String KEY_TICKET_NUMBER = "ticket_number";
    private static final String KEY_RAFFLE_TICKET_ID = "raffle_ticket_id";

    public static String CREATE_STATEMENT = new StringBuilder(FieldKey.CREATE_TABLE.value)
            .append(TABLE_NAME).append(FieldKey.CREATE_TABLE_OPEN.value)
            .append(KEY_TICKET_NUMBER).append(FieldKey.STRING.value).append(FieldKey.PK.value)
            .append(KEY_RAFFLE_TICKET_ID).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(FieldKey.FOREIGN_KEY.value).append(KEY_RAFFLE_TICKET_ID)
            .append(FieldKey.REFERENCES.value).append(RaffleTicketTable.TABLE_NAME)
            .append(FieldKey.BRACKETS_OPEN.value).append(KEY_RAFFLE_TICKET_ID).append(FieldKey.BRACKETS_CLOSE.value)
            .append(FieldKey.CREATE_TABLE_CLOSE.value).toString();

    public static long insert(SQLiteDatabase db, Ticket ticket) {

        ContentValues values = new ContentValues();
        values.put(KEY_TICKET_NUMBER, ticket.getTicketNumber());
        values.put(KEY_RAFFLE_TICKET_ID, ticket.getRaffleTicketId());

        return db.insert(TABLE_NAME, null, values);
    }
}
