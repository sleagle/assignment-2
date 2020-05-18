package au.edu.utas.sddhewa.assignment.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;

import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.util.FieldKey;

/**
 *
 */
public class RaffleTable {

    public static final String TABLE_NAME = "raffle";

    private static final String KEY_RAFFLE_ID = "raffle_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TYPE = "type_id";
    private static final String KEY_STARTING_DATE = "starting_date";
    private static final String KEY_DRAW_DATE = "draw_date";
    private static final String KEY_IS_ACTIVE = "is_active";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_TICKET_PRICE = "ticket_price";
    private static final String KEY_NUM_TICKETS = "num_tickets";
    private static final String KEY_MAX_TICKETS = "max_tickets";
    private static final String KEY_TICKETS_SOLD = "tickets_sold";
    private static final String KEY_RAFFLE_COVER = "raffle_cover";

    public static final String CREATE_STATEMENT = new StringBuilder(FieldKey.CREATE_TABLE.value)
            .append(TABLE_NAME).append(FieldKey.CREATE_TABLE_OPEN.value)
            .append(KEY_RAFFLE_ID).append(FieldKey.PK_AUTO_INCREMENT.value)
            .append(KEY_NAME).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_DESCRIPTION).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_TYPE).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_STARTING_DATE).append(FieldKey.DATE.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_DRAW_DATE).append(FieldKey.DATE.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_IS_ACTIVE).append(FieldKey.BOOLEAN.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_LOCATION).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_TICKET_PRICE).append(FieldKey.FLOAT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_NUM_TICKETS).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_MAX_TICKETS).append(FieldKey.INT.value).append(FieldKey.COMMA.value)
            .append(KEY_TICKETS_SOLD).append(FieldKey.INT.value).append(FieldKey.COMMA.value)
            .append(KEY_RAFFLE_COVER).append(FieldKey.BLOB.value)
            .append(FieldKey.CREATE_TABLE_CLOSE.value).toString();

    public static long insert(SQLiteDatabase db, Raffle raffle) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, raffle.getName());
        values.put(KEY_DESCRIPTION, raffle.getDescription());
        values.put(KEY_TYPE, raffle.getTypeId().id);
        values.put(KEY_STARTING_DATE, raffle.getStartingDate());
        values.put(KEY_DRAW_DATE, raffle.getDrawDate());
        values.put(KEY_IS_ACTIVE, raffle.isActive());
        values.put(KEY_LOCATION, raffle.getLocation());
        values.put(KEY_TICKET_PRICE, raffle.getTicketPrice());
        values.put(KEY_NUM_TICKETS, raffle.getNoOfTickets());
        values.put(KEY_MAX_TICKETS, raffle.getMaxTickets());
        values.put(KEY_TICKETS_SOLD, raffle.getTicketsSold());

        return db.insert(TABLE_NAME, null, values);
    }

    public static ArrayList<Raffle> selectAll(SQLiteDatabase db) throws ParseException {

        ArrayList<Raffle> raffles = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null,
                null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                raffles.add(createFromCursor(c));

                c.moveToNext();
            }
        }

        return raffles;
    }

    public static ArrayList<Raffle> selectCurrentRaffles(SQLiteDatabase db) throws ParseException {

        ArrayList<Raffle> raffles = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, KEY_IS_ACTIVE+"=?", new String[] { "1" },
                null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                raffles.add(createFromCursor(c));

                c.moveToNext();
            }
        }

        return raffles;
    }

    public static ArrayList<Raffle> selectPastRaffles(SQLiteDatabase db) throws ParseException {

        ArrayList<Raffle> raffles = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, KEY_IS_ACTIVE+"=?", new String[] { "0" },
                null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                raffles.add(createFromCursor(c));

                c.moveToNext();
            }
        }

        return raffles;
    }

    private static Raffle createFromCursor(Cursor cursor) throws ParseException {

        if (cursor == null || cursor.isAfterLast() || cursor.isBeforeFirst()) {
            return null;
        }

        else {
            Raffle raffle = new Raffle();
            raffle.setRaffleId(cursor.getInt(cursor.getColumnIndex(KEY_RAFFLE_ID)));
            raffle.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            raffle.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
            raffle.setTypeId(cursor.getInt(cursor.getColumnIndex(KEY_TYPE)));
            raffle.setStartingDate(cursor.getString(cursor.getColumnIndex(KEY_STARTING_DATE)));
            raffle.setDrawDate(cursor.getString(cursor.getColumnIndex(KEY_DRAW_DATE)));
            raffle.setActive(cursor.getInt(cursor.getColumnIndex(KEY_IS_ACTIVE)));
            raffle.setLocation(cursor.getString(cursor.getColumnIndex(KEY_LOCATION)));
            raffle.setTicketPrice(cursor.getFloat(cursor.getColumnIndex(KEY_TICKET_PRICE)));
            raffle.setNoOfTickets(cursor.getInt(cursor.getColumnIndex(KEY_NUM_TICKETS)));
            raffle.setTicketsSold(cursor.getInt(cursor.getColumnIndex(KEY_TICKETS_SOLD)));
            raffle.setMaxTickets(cursor.getInt(cursor.getColumnIndex(KEY_MAX_TICKETS)));
            //TODO raffle.setRaffleCover(cursor.getBlob(cursor.getColumnIndex(KEY_RAFFLE_COVER)));
            return raffle;
        }
    }
}
