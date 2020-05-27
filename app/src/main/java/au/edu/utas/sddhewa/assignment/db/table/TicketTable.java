package au.edu.utas.sddhewa.assignment.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.edu.utas.sddhewa.assignment.modal.RaffleTicket;
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

    public static Ticket getTicketByTicketNumber(SQLiteDatabase db, String ticketNumber) throws ParseException {

        Ticket ticket = null;

        Cursor c = db.query(TABLE_NAME, null, KEY_TICKET_NUMBER+"=?",
                new String[] { ticketNumber }, null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                ticket = createFromCursor(c);

                c.moveToNext();
            }
        }

        return ticket;
    }

    public static ArrayList<Ticket> getTicketsByRaffleTicket(SQLiteDatabase db, long raffleTicketId) throws ParseException {
        ArrayList<Ticket> tickets = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, KEY_RAFFLE_TICKET_ID+"=?",
                new String[] { String.valueOf(raffleTicketId) }, null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                tickets.add(createFromCursor(c));

                c.moveToNext();
            }
        }

        return tickets;
    }

    public static ArrayList<String> getTicketIdsByRaffleTicketList(SQLiteDatabase db, Long[] raffleTicketIds) throws ParseException {
        ArrayList<String> tickets = new ArrayList<>();
        String inList = Arrays.toString(raffleTicketIds);
        inList = inList.replace("[", "(");
        inList = inList.replace("]", ")");
        Log.d("#### array", inList);
        Cursor c = db.query(TABLE_NAME,null, KEY_RAFFLE_TICKET_ID+" IN "+inList,
                null, null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                tickets.add(createFromCursor(c).getTicketNumber());

                c.moveToNext();
            }
        }

        return tickets;
    }

    private static Ticket createFromCursor(Cursor cursor) throws ParseException {

        if (cursor == null || cursor.isAfterLast() || cursor.isBeforeFirst()) {
            return null;
        }

        else {
            Ticket ticket = new Ticket();

            ticket.setTicketNumber(cursor.getString(cursor.getColumnIndex(KEY_TICKET_NUMBER)));
            ticket.setRaffleTicketId(cursor.getInt(cursor.getColumnIndex(KEY_RAFFLE_TICKET_ID)));

            return ticket;
        }
    }
}
