package pl.lodz.uni.math.contactapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

public class ContactImage extends AppCompatActivity {

    private DBTools dbTools = new DBTools(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_image);

        String contactId = getIntent().getStringExtra("contactId");
        String imagePatch = dbTools.getContactImagePatch(contactId);

        ImageView img = (ImageView)findViewById(R.id.ContactImageView);

        if(imagePatch.length() != 0) {

            img.setImageURI(Uri.parse(imagePatch));

        } else {

            Drawable drawable = getResources().getDrawable(R.drawable.ic_account_box_black_36dp);
            img.setImageDrawable(drawable);
        }
    }

    public void backToContacts(View view) {

        startActivity(new Intent(this, Contacts.class));
    }
}
