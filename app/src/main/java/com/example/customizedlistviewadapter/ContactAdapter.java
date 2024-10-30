package com.example.customizedlistviewadapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {
    Context context;
    public ContactAdapter(@NonNull Context context, @NonNull ArrayList<Contact> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v==null){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_item_contact_design, parent, false);
        }

        TextView tvName = v.findViewById(R.id.tvName);
        TextView tvPhone = v.findViewById(R.id.tvContact);
        ImageView ivDelete = v.findViewById(R.id.ivDelete);

        // this will give the data to the view on specified position.
        // Another method would be to ArrayList<Contact> contacts, and then making it equal to objects in contactAdapter method
        // then we would had the access to the position

        Contact contact = getItem(position);

//        The getView(int position, View convertView, ViewGroup parent) method is called by the ListView when it needs to display a new row. Here's how it works step-by-step:
//         When the ListView is Populated:
//          When the ListView is initially created and populated, the Adapter calls getView() for each position that needs to be displayed.
//          For example, if you have 10 items in your ArrayList, getView() will be called 10 times (once for each item).
//          Position Parameter:
//          The position parameter in getView() indicates which item in your dataset the ListView is trying to display at that moment.
//          If position is 0, getItem(0) will retrieve the first contact from the ArrayList, and so on.
//          Retrieving Data:
//          Inside getView(), when you call getItem(position), you are asking for the data that corresponds to that position in the ArrayList.
//          This happens before the view is fully populated in the ListView, which is why it can retrieve the data: the adapter has direct access to the ArrayList and can provide the appropriate Contact object for the given position.
        assert contact != null;
        tvName.setText(contact.getName());
        tvPhone.setText(contact.getPhone());

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder delDialog = new AlertDialog.Builder(context);
                delDialog.setTitle("Confirmation");
                delDialog.setMessage("Do you really want to delete this contact?");
                delDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        remove(contact);
                        notifyDataSetChanged();
                    }
                });
                delDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                delDialog.show();
            }
        });

        //Update the record
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder updateDialog = new AlertDialog.Builder(context);
                updateDialog.setTitle("Update Record");
                View view1 = LayoutInflater.from(context).inflate(R.layout.add_contact_form, null, false);
                updateDialog.setView(view1);

                EditText etName = view1.findViewById(R.id.etName);
                EditText etPhone = view1.findViewById(R.id.etPhone);
                etName.setText(contact.getName());
                etPhone.setText(contact.getPhone());

                updateDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = etName.getText().toString().trim();
                        String phone = etPhone.getText().toString();
                        if(name.isEmpty() || phone.isEmpty()){
                            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                        }else{
                            String oldName = contact.getName();
                            contact.setName(name);
                            contact.setPhone(phone);
                            notifyDataSetChanged();
                            Toast.makeText(context, oldName + " has been updated", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                updateDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                updateDialog.show();
                return false;
            }
        });

        return v;
    }
}
