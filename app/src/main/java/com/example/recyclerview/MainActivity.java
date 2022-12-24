package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    DatabaseReference DBreference;
    RecyclerView recyclerView;
    ArrayList<Post> list;
    Adapter adapter;
    Button buttonAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBreference= FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.RCview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list=new ArrayList<>();
        buttonAdd = findViewById(R.id.Addbutton);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(MainActivity.this);

            }
        });
        readData();
    }
    private void readData() {
      DBreference.child("users").orderByChild("FirstName").addValueEventListener(new ValueEventListener() {
          @SuppressLint("NotifyDataSetChanged")
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
             for(DataSnapshot dataSnapshot :snapshot.getChildren()){

                 final String getF= dataSnapshot.child("first name").getValue(String.class);
                 final  String getid= dataSnapshot.child("id").getValue(String.class);
                 final  String getL= dataSnapshot.child("last name").getValue(String.class);

                 list.add(new Post(getid,getF,getL));
             }
              adapter = new Adapter(MainActivity.this, list);
              recyclerView.setAdapter(adapter);
              adapter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
              Toast.makeText(MainActivity.this, "eroor de lire ", Toast.LENGTH_SHORT).show();
          }
      });

    }

    public class ViewDialogAdd {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_add);

            EditText textFname = dialog.findViewById(R.id.textFname);
            EditText textLname= dialog.findViewById(R.id.textLname);

            Button buttonAdd = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);


            buttonAdd.setText("ADD");
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


           buttonAdd.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String id = "user" + new Date().getTime();
                   String Fname = textFname.getText().toString();
                   String Lname = textLname.getText().toString();
                   
                   if (Fname.isEmpty() || Lname.isEmpty()){
                       Toast.makeText(context, "please retry", Toast.LENGTH_SHORT).show();
                   }else {
                       DBreference.child("users").child(id).child("first name").setValue(Fname);
                        DBreference.child("users").child(id).child("id").setValue(id);
                       DBreference.child("users").child(id).child("last name").setValue(Lname);


                       Toast.makeText(context, "sucsussfull", Toast.LENGTH_SHORT).show();
                       dialog.dismiss();

                   }
               }
           });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }

    }

}