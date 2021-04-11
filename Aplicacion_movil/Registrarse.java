package com.example.iot;// Referencia al ID de la aplicación

// Se importan las librerias necesarias para la aplicacion
import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Hashtable;
import java.util.Map;

// Es el main de la interfaz de registro de usuarios
public class Registrarse extends AppCompatActivity {

    // Se crean las variables tipo texto de usuario y contraseña
    private EditText etNombre, etApellido, etUsuarioID, etEmail, etContrasena;

    // Se crea la variables tipo boton de registrarse y regresar
    Button regresar;
    Button registarse;

    // Se crea una variable private que representa la clase FirebaseAuth
    private FirebaseAuth firebaseAuth;//Comandos dados por Firebase

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        firebaseAuth = FirebaseAuth.getInstance(); //Se inicia la conexion de la aplicacion y firebase

        etEmail = findViewById(R.id.editTextTextEmailAddress);// Se le asigna a la variable el ID de la casilla de la interfaz de texto de Email
        etContrasena =(EditText)findViewById(R.id.contrasenaregistro);// Se le asigna a la variable el ID de la casilla de la interfaz de texto de contraseña
        regresar = findViewById(R.id.regresarregistro);// Se le asigna a la variable el ID del boton regresar a la pagina principal de la interfaz
        registarse = findViewById(R.id.registarser);// Se le asigna a la variable el ID del boton registrarse

        regresar.setOnClickListener(new View.OnClickListener() {// Se crea la función que almacena las acciones del boton regresar
            @Override
            public void onClick(View v) {
                Intent regresar = new Intent(Registrarse.this, MainActivity.class);//Se especifica que al hacer click en el boton regresar debe devolverse a la pagina principal
                startActivity(regresar);// Se realiza la accion
            }
        });
        registarse.setOnClickListener(new View.OnClickListener() // Se crea la función que almacena las acciones del boton registarse
            @Override
            public void onClick(View v) {
            RegistroUser();// se llama la funcion RegistroUser
            }
        });
    }

    // Funcion que realiza la comunicacion entre firebase y la aplicación
    private void RegistroUser(){

        //Se definen dos variables de usuario y contraseña las cuales almacenaran los datos ingresados en la applicacion
        String usuario1 = etEmail.getText().toString().trim();
        String password = etContrasena.getText().toString().trim();

        // Verificacion de datos 
        if(TextUtils.isEmpty(usuario1)){ //verifica si no se escribio el usuario y muestra el mensaje
            Toast.makeText(this, "Debe ingresar un usuario", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){//verifica si no se escribio la contraseña y muestra el mensaje
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        //Funcion predefinida de Firebase para hacer registro de usuarios 
        firebaseAuth.createUserWithEmailAndPassword(usuario1, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {// Si el registro es exitoso muestra un mensaje al usuario
                            Toast.makeText(Registrarse.this, "Registro exitoso"+etEmail.getText(), Toast.LENGTH_LONG).show();
                        } else { // Si el usuario ya existe en la base de datos
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(Registrarse.this, "Ese usuario ya existe", Toast.LENGTH_LONG).show();
                            }else { // cuando se ingresa un usuario que no es uun email se muestra un mensaje de error
                                Toast.makeText(Registrarse.this, "No se puede registrar el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


}