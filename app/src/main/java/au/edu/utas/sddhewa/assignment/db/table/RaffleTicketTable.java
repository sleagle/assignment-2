package au.edu.utas.sddhewa.assignment.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;

import au.edu.utas.sddhewa.assignment.modal.RaffleTicket;
import au.edu.utas.sddhewa.assignment.util.FieldKey;

public class RaffleTicketTable {

    public static final String TABLE_NAME = "raffle_ticket";

    private static final String KEY_RAFFLE_TICKET_ID = "raffle_ticket_id";
    private static final String KEY_RAFFLE_ID = "raffle_id";
    private static final String KEY_NUM_TICKETS = "num_tickets";
    private static final String KEY_CUSTOMER_ID = "customer_id";
    private static final String KEY_PURCHASED_DATE = "purchased_date";
    private static final String KEY_TOTAL_PRICE = "total_price";

    public static final String CREATE_STATEMENT = new StringBuilder(FieldKey.CREATE_TABLE.value)
            .append(TABLE_NAME).append(FieldKey.CREATE_TABLE_OPEN.value)
            .append(KEY_RAFFLE_TICKET_ID).append(FieldKey.PK_AUTO_INCREMENT.value)
            .append(KEY_RAFFLE_ID).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_NUM_TICKETS).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_CUSTOMER_ID).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_PURCHASED_DATE).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_TOTAL_PRICE).append(FieldKey.FLOAT.value).append(FieldKey.NOT_NULL.value)
            .append(FieldKey.FOREIGN_KEY.value).append(KEY_RAFFLE_ID)
            .append(FieldKey.REFERENCES.value).append(RaffleTable.TABLE_NAME)
            .append(FieldKey.BRACKETS_OPEN.value).append(KEY_RAFFLE_ID).append(FieldKey.BRACKETS_CLOSE.value).append(FieldKey.COMMA.value)
            .append(FieldKey.FOREIGN_KEY.value).append(KEY_CUSTOMER_ID)
            .append(FieldKey.REFERENCES.value).append(CustomerTable.TABLE_NAME)
            .append(FieldKey.BRACKETS_OPEN.value).append(KEY_CUSTOMER_ID).append(FieldKey.BRACKETS_CLOSE.value)
            .append(FieldKey.CREATE_TABLE_CLOSE.value).toString();

    public static long insert(SQLiteDatabase db, RaffleTicket raffleTicket) {

        Log.d("### purch Date:", raffleTicket.getPurchasedDate());
        ContentValues values = new ContentValues();
        values.put(KEY_RAFFLE_ID, raffleTicket.getRaffleId());
        values.put(KEY_NUM_TICKETS, raffleTicket.getNumTickets());
        values.put(KEY_CUSTOMER_ID, raffleTicket.getCustomerId());
        values.put(KEY_PURCHASED_DATE, raffleTicket.getPurchasedDate());
        values.put(KEY_TOTAL_PRICE, raffleTicket.getTotalPrice());

        return db.insert(TABLE_NAME, null, values);
    }

    public static ArrayList<RaffleTicket> selectAll(SQLiteDatabase db) throws ParseException {

        ArrayList<RaffleTicket> raffleTickets = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null,
                null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                raffleTickets.add(createFromCursor(c));

                c.moveToNext();
            }
        }

        return raffleTickets;
    }

    public static ArrayList<RaffleTicket> selectAllByRaffleId(SQLiteDatabase db, long raffleId) throws ParseException {

        ArrayList<RaffleTicket> raffleTickets = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, KEY_RAFFLE_ID+"=?",
                new String[] { String.valueOf(raffleId) }, null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                raffleTickets.add(createFromCursor(c));

                c.moveToNext();
            }
        }

        return raffleTickets;
    }

    public static ArrayList<Long> selectAllRaffleTicketIdsByRaffleId(SQLiteDatabase db, long raffleId) throws ParseException {

        ArrayList<Long> raffleTickets = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, KEY_RAFFLE_ID+"=?",
                new String[] { String.valueOf(raffleId) }, null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                raffleTickets.add(createFromCursor(c).getRaffleTicketId());

                c.moveToNext();
            }
        }

        return raffleTickets;
    }

    public static ArrayList<RaffleTicket> selectAllByCustomerId(SQLiteDatabase db, long customerId) throws ParseException {

        ArrayList<RaffleTicket> raffleTickets = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, KEY_CUSTOMER_ID+"=?",
                new String[] { String.valueOf(customerId) }, null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                raffleTickets.add(createFromCursor(c));

                c.moveToNext();
            }
        }

        return raffleTickets;
    }

    public static ArrayList<RaffleTicket> selectAllByRaffleAndCustomerId(
            SQLiteDatabase db, long raffleId, long customerId) throws ParseException {

        ArrayList<RaffleTicket> raffleTickets = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null,  KEY_RAFFLE_ID+"=? and " +KEY_CUSTOMER_ID+"=?",
                new String[] { String.valueOf(raffleId) , String.valueOf(customerId) },
                null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                raffleTickets.add(createFromCursor(c));

                c.moveToNext();
            }
        }

        return raffleTickets;
    }

    public static RaffleTicket selectByRaffleTicketId(SQLiteDatabase db, long raffleTicketId) throws ParseException {

        RaffleTicket raffleTicket = new RaffleTicket();

        Cursor c = db.query(TABLE_NAME, null,  KEY_RAFFLE_TICKET_ID+"=?",
                new String[] { String.valueOf(raffleTicketId) },
                null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                raffleTicket = createFromCursor(c);

                c.moveToNext();
            }
        }
        return raffleTicket;
    }

    private static RaffleTicket createFromCursor(Cursor cursor) throws ParseException {

        if (cursor == null || cursor.isAfterLast() || cursor.isBeforeFirst()) {
            return null;
        }

        else {
            RaffleTicket raffleTicket = new RaffleTicket();

            raffleTicket.setRaffleTicketId(cursor.getInt(cursor.getColumnIndex(KEY_RAFFLE_TICKET_ID)));
            raffleTicket.setRaffleId(cursor.getInt(cursor.getColumnIndex(KEY_RAFFLE_ID)));
            raffleTicket.setNumTickets(cursor.getInt(cursor.getColumnIndex(KEY_NUM_TICKETS)));
            raffleTicket.setPurchasedDate(cursor.getString(cursor.getColumnIndex(KEY_PURCHASED_DATE)));
            raffleTicket.setTotalPrice(cursor.getFloat(cursor.getColumnIndex(KEY_TOTAL_PRICE)));
            raffleTicket.setCustomerId(cursor.getInt(cursor.getColumnIndex(KEY_CUSTOMER_ID)));

            return raffleTicket;
        }
    }
}
