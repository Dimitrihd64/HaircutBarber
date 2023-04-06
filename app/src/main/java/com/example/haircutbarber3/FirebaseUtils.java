package com.example.haircutbarber3;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseUtils {
    private static FirebaseAuth mAuth;

    public static synchronized FirebaseAuth getFirebaseAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }
}
