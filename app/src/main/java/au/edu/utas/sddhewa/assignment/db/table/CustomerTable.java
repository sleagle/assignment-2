package au.edu.utas.sddhewa.assignment.db.table;

import android.database.Cursor;

import java.text.ParseException;

import au.edu.utas.sddhewa.assignment.modal.Customer;
import au.edu.utas.sddhewa.assignment.util.FieldKey;

public class CustomerTable {

    public static final String TABLE_NAME = "customer";

    public static final String KEY_CUSTOMER_ID = "customer_id";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_MOBILE_NO = "mobile_no";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_SUBURB = "suburb";
    public static final String KEY_STATE = "state";
    public static final String KEY_POST_CODE = "post_code";

    public static final String CREATE_STATEMENT = new StringBuilder(FieldKey.CREATE_TABLE.value)
            .append(TABLE_NAME).append(FieldKey.CREATE_TABLE_OPEN.value)
            .append(KEY_CUSTOMER_ID).append(FieldKey.PK_AUTO_INCREMENT.value)
            .append(KEY_FIRST_NAME).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_LAST_NAME).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_MOBILE_NO).append(FieldKey.INT.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_EMAIL).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_ADDRESS).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_SUBURB).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_STATE).append(FieldKey.STRING.value).append(FieldKey.NOT_NULL.value)
            .append(KEY_POST_CODE).append(FieldKey.INT.value).append(FieldKey.NOT_NULL_WITHOUT.value)
            .append(FieldKey.CREATE_TABLE_CLOSE.value).toString();

    private static Customer createFromCursor(Cursor cursor) throws ParseException {

        if (cursor == null || cursor.isAfterLast() || cursor.isBeforeFirst()) {
            return null;
        }

        else {
            Customer customer = new Customer();
            customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(KEY_CUSTOMER_ID)));
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

