package au.edu.utas.sddhewa.assignment.db.table;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

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

    public static final String CREATE_STATEMENT = new StringBuilder(FieldKey.CREATE_TABLE.value)
            .append(TABLE_NAME).append(FieldKey.CREATE_TABLE_OPEN.value)
            .append(KEY_RAFFLE_ID).append(FieldKey.PK_AUTO_INCREMENT)
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
            .append(KEY_TICKETS_SOLD).append(FieldKey.INT.value)
            .append(FieldKey.CREATE_TABLE_CLOSE.value).toString();

    public static void insert(SQLiteDatabase db, Raffle raffle) {

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

        db.insert(TABLE_NAME, null, values);
    }
}
