package android.com.sirioibanes.presenters;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.dtos.Event;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CheckPresenter {

    public void getRegistros() {


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child("VaenKWCCNQQoIHNY2eLEX5ySzr43");
        Log.wtf("LOG PROGRAMA", "voy a intentar obtener los datos");

        final ValueEventListener eventListener = new ValueEventListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                String mensajeCompleto = "se cargo con exito y encontramos estos datos: \n " + dataSnapshot.toString();
                Log.wtf("LOG PROGRAMA", mensajeCompleto);
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.wtf("LOG PROGRAMA", "Hubo un error");
            }
        };

        myRef.addListenerForSingleValueEvent(eventListener);
    }

}
