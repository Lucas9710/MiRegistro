package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.adapters.RvAdapter;
import android.com.sirioibanes.dtos.Registro;
import android.com.sirioibanes.dtos.Usuario;
import android.com.sirioibanes.presenters.CheckPresenter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class CheckActivity extends AppCompatActivity {

    //aca van las propiedades
    RecyclerView rv;
    CheckPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.wtf("LOG PROGRAMA", "Se inicializo el programa");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);


        //ACA CREO EL PRESENTER E INTENTO IR A BUSCAR LOS DATOS
        getPresenterData();

        //ACA CONFIGURO EL RECYCLER VIEW PARA QUE FUNCIONE CON DATOS DE REGISTRO
        rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        //ACA CREO ALGUNAS VARIABLES PARA SIMULAR LOS DATOS DEL SERVIDOR
        Usuario empleado = new Usuario ("Lucas","Nuñez", "1554033249","Lucascapo", "Lucas.9710@hotmail.com");
        Registro registro1 = new Registro("hola",123);
        Registro registro2 = new Registro("chau",1233);
        Registro registro3 = new Registro("como va",1243);
        List <Registro> registerList = Arrays.asList(registro1,registro2,registro3);



        Log.wtf("LOG PROGRAMA", "Aca fue creado el objeto empleado");

        RvAdapter adaptador = new RvAdapter(registerList);
        rv.setAdapter(adaptador);

    }

    private void getPresenterData() {
        Log.wtf("LOG PROGRAMA", "se esta por cargar el get registros");
        presenter = new CheckPresenter();
        presenter.getRegistros();
    }
}
