package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.adapters.RvAdapter;
import android.com.sirioibanes.dtos.Registro;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class CheckActivity extends AppCompatActivity {

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        Registro registro1 = new Registro("hola",123);
        Registro registro2 = new Registro("chau",1233);
        Registro registro3 = new Registro("como va",1243);
        List <Registro> registerList = Arrays.asList(registro1,registro2,registro3);

        RvAdapter adaptador = new RvAdapter(registerList);
        rv.setAdapter(adaptador);

    }

}
