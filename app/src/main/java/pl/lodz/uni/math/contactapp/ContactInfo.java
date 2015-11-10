package pl.lodz.uni.math.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class ContactInfo extends AppCompatActivity {

    private DBTools dbTools = new DBTools(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        String contactId = getIntent().getStringExtra("contactId");
        HashMap<String, String> contactInfo = dbTools.getContactInfo(contactId);

        ((TextView) findViewById(R.id.firstName)).setText(contactInfo.get("firstName"));
        ((TextView) findViewById(R.id.lastName)).setText(contactInfo.get("lastName"));
        ((TextView) findViewById(R.id.phoneNumber)).setText(contactInfo.get("phoneNumber"));
        ((TextView) findViewById(R.id.emailAddress)).setText(contactInfo.get("emailAddress"));
        ((TextView) findViewById(R.id.GG)).setText(contactInfo.get("GG"));
        ((TextView) findViewById(R.id.webEx)).setText(contactInfo.get("webEx"));
        ((TextView) findViewById(R.id.skype)).setText(contactInfo.get("skype"));

    }

    public void backToContacts(View view) {

        startActivity(new Intent(this, Contacts.class));
    }
}
