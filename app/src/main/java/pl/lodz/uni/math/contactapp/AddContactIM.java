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

import java.util.HashMap;

public class AddContactIM extends AppCompatActivity {

    private EditText GG;
    private EditText webEX;
    private EditText skype;

    private HashMap<String, String> personDataWithIM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_im);

        GG = (EditText) findViewById(R.id.GG);
        webEX = (EditText) findViewById(R.id.webEx);
        skype = (EditText) findViewById(R.id.skype);

        personDataWithIM = (HashMap<String, String>) getIntent().getSerializableExtra("personData");
    }

    public void addContactIMCancel(View view) {

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

    public void addContactIMNext(View view) {

        personDataWithIM.put("GG", GG.getText().toString());
        personDataWithIM.put("webEx", webEX.getText().toString());
        personDataWithIM.put("skype", skype.getText().toString());

        Intent theIntent = new Intent(getApplication(), AddContactImage.class);
        theIntent.putExtra("personDataWithIM", personDataWithIM);
        startActivity(theIntent);
    }
}
