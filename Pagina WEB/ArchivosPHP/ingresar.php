<?php  
  
//Este archivo envía los datos tomados del formulario de ingreso a la base de datos de mysql

require_once "conexion_config.php";

//Si todos los campos están llenos
if(!empty($_POST['username']) && !empty($_POST['password'])) {  
        $user=$_POST['username']; // Asigna  a la variable user el usuario ingresado
        $pass=$_POST['password'];  //Asigna  a la variable pass la contraseña in gresada
        
        // Crea una cadena SQL real
        $username = mysqli_real_escape_string($conn,$_POST['username']);
        $password = mysqli_real_escape_string($conn,$_POST['password']);
        $crypt_pass=md5($password); // Se encripta la contraseña
        
        //Se verifican la contraseña y el usuario
        $sql = "SELECT * FROM aerocultive_users_db WHERE Usuario = '$username' and Contrasena = '$crypt_pass'"; //Se revisa los usuarios y contraseñas de la base de datos
        $check = mysqli_query($conn,$sql); // funcion para comparar los datos ingresados y los existentes en la base de datos
        $row = mysqli_fetch_array($check,MYSQLI_ASSOC); // Se extrae toda la columna de usuario y contraseña 
        $contador = mysqli_num_rows($check); //Booleano que al coincidir se vuelve 1 
        if($contador == 1) {//Si el usuario y la contraseña son correctos
                $ingreso_exitoso="Exito";
         
        }else {//Si el usuario y la contraseña son incorrectos
                $error="Error";
        }
        
} else {  
        
}  
?>  