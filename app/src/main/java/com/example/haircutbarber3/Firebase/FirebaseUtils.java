package com.example.haircutbarber3.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    private static FirebaseAuth mAuth;
    private static FirebaseDatabase mDatabase;

    //Configuracion principal de Firebase Realtime database y Firebase Auth para todo el proyecto
    public static synchronized FirebaseAuth getFirebaseAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    public static synchronized FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance("https://haircutbarberdb-default-rtdb.europe-west1.firebasedatabase.app/");
        }
        return mDatabase;
    }

}
