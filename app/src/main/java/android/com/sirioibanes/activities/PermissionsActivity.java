package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PermissionsActivity extends AppCompatActivity {

    //VARIABLES GLOBALES

    private Button buttonContinuar;
    private Button buttonActivar;

    //METODOS


    //METODO 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        buttonActivar = (Button)findViewById(R.id.button_activar);
        buttonContinuar = (Button)findViewById(R.id.button_acceder);
        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonContinuar();
            }
        });
        buttonActivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonActivar();
            }
        });

    }

    private void setButtonContinuar() {
        Intent myIntent = new Intent(PermissionsActivity.this, ScannerActivity.class);
        PermissionsActivity.this.startActivity(myIntent);

    }

    private void setButtonActivar() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, 100);
        } else {
            startActivity(new Intent(PermissionsActivity.this, ScannerActivity.class));
        }
    }

    private void goToSettings () {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(PermissionsActivity.this, ScannerActivity.class));
        } else {
            goToSettings();
        }
    }



}



