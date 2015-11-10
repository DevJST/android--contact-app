package pl.lodz.uni.math.contactapp;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Contacts extends ListActivity {

    DBTools dbTools = new DBTools(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listContacts();
    }

    public void listContacts() {

        ArrayList<HashMap<String, String>> contactList =  dbTools.getContactsNamesWithId();

        /*Integer noImageIconID = R.drawable.ic_account_box_black_36dp;
        Uri noImageIconUri = Uri.parse(noImageIconID.toString());
        String noImageIconPath = getPath(noImageIconUri); */

        for (int i = 0; i < contactList.size(); i++) {

            String imageUri = contactList.get(i).get("imageUri");
            if (imageUri == "" || imageUri==null) {

                contactList.get(i).put("imageUri", null);
            }

        }

        if(contactList.size()!=0) {

            ListAdapter adapter = new ContactsAdapter( Contacts.this, contactList, R.layout.activity_contact_entry, new String[] { "contactId", "name", "imageUri" }, new int[] {R.id.contactId, R.id.name, R.id.contactImageIcon});

            // setListAdapter provides the Cursor for the ListView
            // The Cursor provides access to the database data

            setListAdapter(adapter);

        } else {

            setListAdapter(null);
        }

    }

    public void addContact(View view) {

        Intent intent = new Intent(this, AddNewContact.class);
        startActivity(intent);
    }
    public void closeApp(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

    
