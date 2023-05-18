package com.example.haircutbarber3.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FirebaseUtils {
    private static FirebaseAuth mAuth;
    private static FirebaseDatabase mDatabase;

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

    public void obtenerCorreosElectronicosUsuarios(final OnEmailsObtenidosListener listener) {

    }

    public interface OnEmailsObtenidosListener {
        void onEmailsObtenidos(List<String> emails);
    }
}
