package pl.lodz.uni.math.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBTools extends SQLiteOpenHelper {

    public DBTools(Context applicationcontext) {


        super(applicationcontext, "contactbook.db", null, 2);

    }

    public void onCreate(SQLiteDatabase database) {

        // Make sure you don't put a ; at the end of the query

        String query = "CREATE TABLE contacts ( contactId INTEGER PRIMARY KEY, firstName TEXT, " +
                "lastName TEXT, phoneNumber TEXT, emailAddress TEXT, GG TEXT, webEx TEXT, skype TEXT, imageUri TEXT)";

        // Executes the query provided as long as the query isn't a select
        // or if the query doesn't return any data

        database.execSQL(query);

    }

    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {

        String query = "DROP TABLE IF EXISTS contacts";

        // Executes the query provided as long as the query isn't a select
        // or if the query doesn't return any data

        database.execSQL(query);
        onCreate(database);
    }

    public void insertContact(HashMap<String, String> queryValues) {

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Stores key value pairs being the column name and the data
        // ContentValues data type is needed because the database
        // requires its data type to be passed

        ContentValues values = new ContentValues();

        values.put("firstName", queryValues.get("firstName"));
        values.put("lastName", queryValues.get("lastName"));
        values.put("phoneNumber", queryValues.get("phoneNumber"));
        values.put("emailAddress", queryValues.get("emailAddress"));
        values.put("GG", queryValues.get("GG"));
        values.put("webEx", queryValues.get("webEx"));
        values.put("skype", queryValues.get("skype"));
        values.put("imageUri", queryValues.get("imageUri"));

        // Inserts the data in the form of ContentValues into the
        // table name provided

        database.insert("contacts", null, values);

        // Release the reference to the SQLiteDatabase object

        database.close();
    }

    public int updateContact(HashMap<String, String> queryValues) {

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Stores key value pairs being the column name and the data

        ContentValues values = new ContentValues();

        values.put("firstName", queryValues.get("firstName"));
        values.put("lastName", queryValues.get("lastName"));
        values.put("phoneNumber", queryValues.get("phoneNumber"));
        values.put("emailAddress", queryValues.get("emailAddress"));
        values.put("GG", queryValues.get("GG"));
        values.put("webEx", queryValues.get("webEx"));
        values.put("skype", queryValues.get("skype"));
        values.put("imageUri", queryValues.get("imageUri"));

        // update(TableName, ContentValueForTable, WhereClause, ArgumentForWhereClause)


        return database.update("contacts", values, "contactId" + " = ?", new String[] { queryValues.get("contactId") });
    }

    public void deleteContact(String id) {

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM  contacts where contactId='"+ id +"'";

        // Executes the query provided as long as the query isn't a select
        // or if the query doesn't return any data

        database.execSQL(deleteQuery);
    }

    public ArrayList<HashMap<String, String>> getAllContacts() {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> contactArrayList;

        contactArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT  * FROM contacts";

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Cursor provides read and write access for the
        // data returned from a database query

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contactMap = new HashMap<String, String>();

                // Store the key / value pairs in a HashMap
                // Access the Cursor data by index that is in the same order
                // as used when creating the table

                contactMap.put("contactId", cursor.getString(0));
                contactMap.put("firstName", cursor.getString(1));
                contactMap.put("lastName", cursor.getString(2));
                contactMap.put("phoneNumber", cursor.getString(3));
                contactMap.put("emailAddress", cursor.getString(4));
                contactMap.put("GG", cursor.getString(5));
                contactMap.put("webEx", cursor.getString(6));
                contactMap.put("skype", cursor.getString(7));
                contactMap.put("imageUri", cursor.getString(8));

                contactArrayList.add(contactMap);

            } while (cursor.moveToNext()); // Move Cursor to the next row
        }

        // return contact list
        return contactArrayList;
    }

    public HashMap<String, String> getContactInfo(String id) {
        HashMap<String, String> contactMap = new HashMap<String, String>();

        // Open a database for reading only

        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM contacts where contactId='"+id+"'";

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                contactMap.put("firstName", cursor.getString(1));
                contactMap.put("lastName", cursor.getString(2));
                contactMap.put("phoneNumber", cursor.getString(3));
                contactMap.put("emailAddress", cursor.getString(4));
                contactMap.put("GG", cursor.getString(5));
                contactMap.put("webEx", cursor.getString(6));
                contactMap.put("skype", cursor.getString(7));
                contactMap.put("imageUri", cursor.getString(8));

            } while (cursor.moveToNext());
        }
        return contactMap;
    }

    public ArrayList<HashMap<String, String>> getContactsNamesWithId() {


        ArrayList<HashMap<String, String>> contactArrayList;

        contactArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT contactId, firstName || ' ' || lastName AS 'name', imageUri FROM contacts";


        SQLiteDatabase database = this.getWritableDatabase();

        // Cursor provides read and write access for the
        // data returned from a database query

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row

        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> contactMap = new HashMap<String, String>();

                contactMap.put("contactId", cursor.getString(0));
                contactMap.put("name", cursor.getString(1));
                contactMap.put("imageUri", cursor.getString(2));

                contactArrayList.add(contactMap);

            } while (cursor.moveToNext()); // Move Cursor to the next row
        }

        // return contact list
        return contactArrayList;
    }

    public String getContactImagePatch(String id) {

        String contactImagePatch = "";

        String query = "SELECT imageUri FROM contacts where contactId='"+ id +"'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                contactImagePatch = cursor.getString(0);

            } while (cursor.moveToNext()); // Move Cursor to the next row
        }

        return contactImagePatch;
    }
}
