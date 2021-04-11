package com.example.iot; // Referencia al ID de la aplicación

// Se importan las librerias necesarias para la aplicacion
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// Es el main de la interfaz principal
public class MainActivity extends AppCompatActivity {

    // Se le asigna a los dos botones de lainterfaz una variable tipo boton
    Button edtRegistrarse; 
    Button edtingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        edtRegistrarse=findViewById(R.id.edtRegistrarse); // se le asocia la variable creada al ID del boton registar de la interfaz
        edtingresar=findViewById(R.id.edtingresar); // se le asocia la variable creada al ID del boton ingresar de la interfaz
        
        edtingresar.setOnClickListener(new OnClickListener() { // Se crea la función que almacena las acciones del boton ingresar
            @Override
            public void onClick(View v) {
                Intent edtingresar = new Intent(MainActivity.this,Ingresar.class); //Se especifica que al hacer click pasa de la interfaz actual a la interfaz de ingresar
                startActivity(edtingresar); // Se realiza la acción del boton ingresar 
            }
        });
        
        edtRegistrarse.setOnClickListener(new OnClickListener() {// Se crea la función que almacena las acciones del boton registarse
            @Override
            public void onClick(View v) {
                Intent edtRegistrarse = new Intent(MainActivity.this,Registrarse.class);//Se especifica que al hacer click pasa de la interfaz actual a la interfaz de registro
                startActivity(edtRegistrarse);// Se realiza la acción del boton registrarse
            }
        });
    }
}