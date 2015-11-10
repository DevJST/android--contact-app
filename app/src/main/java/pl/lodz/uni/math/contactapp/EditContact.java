package pl.lodz.uni.math.contactapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class EditContact extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText emailAddress;

    private DBTools dbTools = new DBTools(this);
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        emailAddress = (EditText) findViewById(R.id.emailAddress);

        contactId = getIntent().getStringExtra("contactId");
        HashMap<String, String> contactInfo = dbTools.getContactInfo(contactId);


        firstName.setText(contactInfo.get("firstName"), TextView.BufferType.EDITABLE);
        lastName.setText(contactInfo.get("lastName"), TextView.BufferType.EDITABLE);
        phoneNumber.setText(contactInfo.get("phoneNumber"), TextView.BufferType.EDITABLE);
        emailAddress.setText(contactInfo.get("emailAddress"), TextView.BufferType.EDITABLE);
    }

    public void editContactNext(View view) {

        // Will hold the HashMap of values

        HashMap<String, String> queryValuesMap =  new HashMap<String, String>();

        // Get the values from the EditText boxes

        queryValuesMap.put("contactId", contactId);
        queryValuesMap.put("firstName", firstName.getText().toString());
        queryValuesMap.put("lastName", lastName.getText().toString());
        queryValuesMap.put("phoneNumber", phoneNumber.getText().toString());
        queryValuesMap.put("emailAddress", emailAddress.getText().toString());

        // Call for the HashMap to be added to the database

        Intent theIntent = new Intent(getApplication(), EditContactIM.class);
        theIntent.putExtra("personData", queryValuesMap);
        startActivity(theIntent);
    }


    public void editContactCancel(View view) {

        final Context context = this;

        new AlertDialog.Builder(this)
                .setTitle("Cancel")
                .setMessage("Are you sure you want to cancel without saving data?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(context, Contacts.class));
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
