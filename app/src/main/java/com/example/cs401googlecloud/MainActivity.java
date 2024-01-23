package com.example.cs401googlecloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    /**
     * db is instance of FirebaseFirestore will connect to
     */
    FirebaseFirestore db;

    Button buttonNewUser;

    EditText editTextEmail;
    EditText editTextName;

    String name;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //grab handle to new user button
        buttonNewUser = (Button) findViewById(R.id.buttonNewUser);
        //grab handle to the name & email textView objects
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextName = (EditText) findViewById(R.id.editTextName);

        //create FirebaseFirestore instnace
        db = FirebaseFirestore.getInstance();

        //create event handler for Button
        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // read in the name and email
                name = editTextName.getText().toString();
                email = editTextEmail.getText().toString();

                //test if email or name not entered
                //do not make new user and print message
                if(name ==null || email == null){
                   System.out.println("No email or name");
                   Toast.makeText(getApplicationContext(), "Both Name + Email must be entered", Toast.LENGTH_LONG).show();
                   return;
                }

                //now create hashmap of data
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("name", name);
                user.put("email", email);

                // Add a new document with a generated ID to collection users
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("Firestore:", "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(getApplicationContext(), "Successfull storing of new user "+ name +"," + email, Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Firestore:", "Error adding document", e);
                                Toast.makeText(getApplicationContext(), "FAILURE storing of new user "+ name +"," + email, Toast.LENGTH_LONG).show();
                            }
                        });


            }
        });
    }
}