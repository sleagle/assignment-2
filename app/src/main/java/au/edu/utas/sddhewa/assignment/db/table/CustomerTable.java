package au.edu.utas.sddhewa.assignment.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;

import au.edu.utas.sddhewa.assignment.modal.Customer;
import au.edu.utas.sddhewa.assignment.util.FieldKey;

public class CustomerTable {

    public static final String LOG_TAG = "Raffle_Customer_Table";

    public static final String TABLE_NAME = "customer";

    private static final String KEY_CUSTOMER_ID = "customer_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_MOBILE_NO = "mobile_no";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_SUBURB = "suburb";
    private static final String KEY_STATE = "state";
    private static final String KEY_POST_CODE = "post_code";

    public static final String CREATE_STATEMENT = new StringBuilder(FieldKey.CREATE_TABLE.value)
            .append(TABLE_NAME).append(FieldKey.CREATE_TABLE_OPEN.value)
            .append(KEY_CUSTOMER_ID).append(FieldKey.PK_AUTO_INCREMENT.value)
            .append(KEY_TITLE).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_FIRST_NAME).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_LAST_NAME).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_MOBILE_NO).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_EMAIL).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_ADDRESS).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_SUBURB).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_STATE).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_POST_CODE).append(FieldKey.INT.value).append(FieldKey.NOT_NULL_WITHOUT.value)
            .append(FieldKey.CREATE_TABLE_CLOSE.value).toString();

    public static long insert(SQLiteDatabase db, Customer customer) {

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, customer.getTitle());
        values.put(KEY_FIRST_NAME, customer.getFirstName());
        values.put(KEY_LAST_NAME, customer.getLastName());
        values.put(KEY_MOBILE_NO, customer.getMobileNo());
        values.put(KEY_EMAIL, customer.getEmail());
        values.put(KEY_ADDRESS, customer.getAddress());
        values.put(KEY_SUBURB, customer.getSuburb());
        values.put(KEY_STATE, customer.getState());
        values.put(KEY_POST_CODE, customer.getPostCode());

        return db.insert(TABLE_NAME, null, values);
    }

    public static ArrayList<Customer> selectAll(SQLiteDatabase db) throws ParseException {

        ArrayList<Customer> customers = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null,
                null, null, null);

        if (c != null) {
            c.moveToFirst();

            while (!c.isAfterLast()) {
                customers.add(createFromCursor(c));

                c.moveToNext();
            }
        }

        return customers;
    }

    private static Customer createFromCursor(Cursor cursor) throws ParseException {

        if (cursor == null || cursor.isAfterLast() || cursor.isBeforeFirst()) {
            return null;
        }

        else {
            Customer customer = new Customer();
            customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(KEY_CUSTOMER_ID)));
            customer.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
            customer.setFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
            customer.setLastName(cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
            customer.setMobileNo(cursor.getInt(cursor.getColumnIndex(KEY_MOBILE_NO)));
            customer.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            customer.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
            customer.setSuburb(cursor.getString(cursor.getColumnIndex(KEY_SUBURB)));
            customer.setState(cursor.getString(cursor.getColumnIndex(KEY_STATE)));
            customer.setPostCode(cursor.getInt(cursor.getColumnIndex(KEY_POST_CODE)));

            return customer;
        }
    }
}

