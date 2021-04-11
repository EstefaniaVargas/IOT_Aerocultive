package com.example.iot;// Referencia al ID de la aplicación

// Se importan las librerias necesarias para la aplicacion
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

// Es el main de la interfaz ingresar
public class Ingresar extends AppCompatActivity {

    // Se crean las variables tipo texto de usuario y contraseña
    EditText UsuarioIdLogin, password1;

    // Se crea la variables tipo boton de ingresar y regresar
    Button ingresarlogin;
    Button regresarl;

    // Se crea una variable private que representa la clase FirebaseAuth
    private FirebaseAuth mAuth; //Comandos dados por Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);

        mAuth = FirebaseAuth.getInstance(); // Se inicia la conexion de la aplicacion y firebase
        UsuarioIdLogin=findViewById(R.id.UsuarioIdLogin); // Se le asigna a la variable el ID de la casilla de la interfaz de texto de usuario
        password1=findViewById(R.id.password1);// Se le asigna a la variable el ID de la casilla de la interfaz de texto de contraseña
        ingresarlogin=findViewById(R.id.ingresarl);// Se le asigna a la variable el ID del boton ingresar de la interfaz
        regresarl=findViewById(R.id.regresarl);// Se le asigna a la variable el ID del boton regresar a la pagina principal de la interfaz

        regresarl.setOnClickListener(new OnClickListener() {// Se crea la función que almacena las acciones del boton regresar
            @Override
            public void onClick(View v) {
                Intent regresarl = new Intent(Ingresar.this,MainActivity.class);//Se especifica que al hacer click en el boton regresar debe devolverse a la pagina principal
                startActivity(regresarl); // Se realiza la accion
            }
        });
        ingresarlogin.setOnClickListener(new OnClickListener() {// Se crea la función que almacena las acciones del boton ingresar
            @Override
            public void onClick(View v) {
                Iniciar();// Se llama a la función iniciar
            }
        });

    }

    // Funcion que realiza la comunicacion entre firebase y la aplicación
    private void Iniciar(){

        //Se definen dos variables de usuario y contraseña las cuales almacenaran los datos ingresados en la applicacion
        final String usuario = UsuarioIdLogin.getText().toString().trim();
        final String contraseña = password1.getText().toString().trim();

        // Verificacion de datos 
        if(TextUtils.isEmpty(usuario)){ //verifica si no se escribio el usuario y muestra el mensaje
            Toast.makeText(this, "Debe ingresar un usuario", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(contraseña)){//verifica si no se escribio la contraseña y muestra el mensaje
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        //Funcion predefinida de Firebase para hacer autenticacion de usuarios 
        mAuth.signInWithEmailAndPassword(usuario, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { 

                        // Se verifica que el usuario exista 
                        if (task.isSuccessful()) {
                            int pos = usuario.indexOf("@"); // el usuario ingresado debe tener el signo @ ya que es un email 
                            String users= usuario.substring(0, pos);
                            Toast.makeText(Ingresar.this, "Bienvenido"+ UsuarioIdLogin.getText(), Toast.LENGTH_LONG).show();// Si el usuario es correcto se muestra el mensaje de bienvenido
                            Intent intencion = new Intent(getApplication(),datos.class); //Al hacer click en ingresar se especifica que debe redirigir a la pagina de datos
                            startActivity(intencion);//Se realiza la redigiración
                            finish();// se finaliza la comunicacion con firebase

                        } else {
                            if(contraseña.length() < 6){ // Si la contraseña ingresada es menos a 6 caracteres
                                password1.setError("Error. La contraseña debe ser minimo de 6 caracteres");// se muestra un mensaje recordando que las contraseñas son minimo de 6 caracteres
                            }else {
                                Toast.makeText(Ingresar.this, "Fallo la autenticacion", Toast.LENGTH_SHORT).show();// cuando se ingresa en usuario que no es un email se muestra error 
                            }
                        }
                    }
                });
    }
}