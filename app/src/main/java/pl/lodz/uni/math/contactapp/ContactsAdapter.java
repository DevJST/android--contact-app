package pl.lodz.uni.math.contactapp;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;

import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;


import java.util.List;
import java.util.Map;

public class ContactsAdapter extends SimpleAdapter {

    private Context context;

    public ContactsAdapter(Context context, List<? extends Map<String, ?>> values, int resource, String[] from, int[] to ){


        super(context, values, resource, from, to);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final DBTools dbTools = new DBTools(context);

        final View view = super.getView(position, convertView, parent);

        ImageView image = (ImageView)view.findViewById(R.id.contactImageIcon);
        TextView name = (TextView)view.findViewById(R.id.name);
        ImageView editImageView = (ImageView)view.findViewById(R.id.editIcon);
        ImageView deleteImageView = (ImageView)view.findViewById(R.id.delIcon);

        image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView contactIdTextView = (TextView)view.findViewById(R.id.contactId);
                String contactIdVal = contactIdTextView.getText().toString();

                Intent theIntent = new Intent(context, ContactImage.class);
                theIntent.putExtra("contactId", contactIdVal);

                context.startActivity(theIntent);

            }
        });

        name.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView contactIdTextView = (TextView)view.findViewById(R.id.contactId);
                String contactIdVal = contactIdTextView.getText().toString();

                Intent theIntent = new Intent(context, ContactInfo.class);
                theIntent.putExtra("contactId", contactIdVal);

                context.startActivity(theIntent);
            }
        });

        editImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView contactIdTextView = (TextView) view.findViewById(R.id.contactId);
                String contactIdVal = contactIdTextView.getText().toString();

                Intent theIntent = new Intent(context, EditContact.class);
                theIntent.putExtra("contactId", contactIdVal);

                context.startActivity(theIntent);
            }
        });

        deleteImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView contactIdTextView = (TextView)view.findViewById(R.id.contactId);
                final String contactIdVal = contactIdTextView.getText().toString();

                new AlertDialog.Builder(context)
                        .setTitle("Cancel")
                        .setMessage("This contact will be removed")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(context, "deleting row", Toast.LENGTH_SHORT).show();
                                dbTools.deleteContact(contactIdVal);
                                ((Contacts)context).listContacts();
                                //((Contacts)context).finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();


            }
        });

        return view;
    }
}
