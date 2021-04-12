<?php
//Este archivo crea la conexión entre la página web y la base de datos en mysql

$servidor="fdb27.125mb.com"; // Se da el nombre del servidor 
$usuario="3786402_aerocultiveusers"; // se da el usuario de la base de datos
$password="eyd102218"; //Se da la contraseña de la base de datos 
$db="3786402_aerocultiveusers"; // Se da el nombre de la base de datos 
		
$conn=mysqli_connect($servidor,$usuario,$password,$db); //Conectar a la base de datos

if($conn->connect_error){//Si hay un error en la conexion
        die("Conexión a la base de datos fallida:" . mysqli_connect_error()); // Muestra el error
}

?>