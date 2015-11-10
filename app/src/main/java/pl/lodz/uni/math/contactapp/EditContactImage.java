package pl.lodz.uni.math.contactapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.HashMap;

public class EditContactImage extends AppCompatActivity {

    private HashMap<String, String> personDataWithIM;
    private DBTools dbTools = new DBTools(this);

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath = "";
    private ImageButton img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact_image);

        personDataWithIM = (HashMap<String, String>) getIntent().getSerializableExtra("personDataWithIM");
        String imagePatch = dbTools.getContactImagePatch(personDataWithIM.get("contactId"));

        img = (ImageButton)findViewById(R.id.editImageButton);



        if(imagePatch.length() != 0) {

            img.setImageURI(Uri.parse(imagePatch));

        } else {

            Drawable drawable = getResources().getDrawable(R.drawable.ic_wallpaper_black_48dp);
            img.setImageDrawable(drawable);
        }

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                img.setImageDrawable(null);
                img.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(uri,
                projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void editContactSave(View view) {

        personDataWithIM.put("imageUri", selectedImagePath);
        dbTools.updateContact(personDataWithIM);
        startActivity(new Intent(this, Contacts.class));
    }

    public void editContactImageCancel(View view) {

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
