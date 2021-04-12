<?php
//Este archivo envía los datos tomados del formulario a la base de datos de mysql

//Se importa el archivo que realiza la conexión entre mysql y la página web
require_once "conexion_config.php";

$nombre=$_POST['Nombre'];    //Nombre del usuario
$apellido=$_POST['Apellido'];//Apellido del usuario
$username=$_POST['username'];//Nombre de Usuario
$email=$_POST['email'];      //Correo del Usuario
$password=$_POST['password'];//Contraseña
$crypt_pass=md5($password);//Contraseña ENCRIPTADA (funciona para el login)

//Si los campos están llenos
if(!empty($_POST['Nombre']) && !empty($_POST['Apellido'])&& !empty($_POST['username'])&& !empty($_POST['email'])&& !empty($_POST['password']))
{
        //Se verifica la disponibilidad del Usuario y del Correo
        $check_user   = "SELECT * FROM aerocultive_users_db WHERE Usuario='$username'";
        $check_email  = "SELECT * FROM aerocultive_users_db WHERE Correo='$email'";
        $checku = mysqli_query($conn, $check_user);
        $checke = mysqli_query($conn, $check_email);

        if (mysqli_num_rows($checku) > 0) {
                $name_error = "Nombre de usuario no disponible"; 
        }else if(mysqli_num_rows($checke) > 0){
                $email_error = "El correo suministrado ya se encuentra registrado"; 	
        }else{
                $sql = "INSERT INTO aerocultive_users_db VALUES('','$nombre', '$apellido','$username','$email', '$crypt_pass')";
                $exito="Exito";
                $query = $conn->query($sql);          
        }

}

?>