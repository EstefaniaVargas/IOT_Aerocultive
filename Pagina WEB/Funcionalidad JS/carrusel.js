// Funcion para cambiar las imagenes en el carrusel de la pagina principal
var slideIndex = 0;
showSlides();

// Se crea la funcion del carrusel de pagina principal 
function showSlides() {
        var i;
        var slides = document.getElementsByClassName("mySlides"); // Llama las imagenes que tiene la clase mySlides para mostrar en el carrusel
        // Funcion para recorrer todas las imagenes
        for (i = 0; i < slides.length; i++) { 
                slides[i].style.display = "none"; // Pone la imagen sin ningun estilo  
        }
        slideIndex++; //incremento el apuntador del vector slides
        // Se pregunta si ya llego al final
        if (slideIndex > slides.length) {
                slideIndex = 1 // Se reinicia el apuntador a la posicion inicial
        }    
        slides[slideIndex-1].style.display = "block";   
        setTimeout(showSlides, 3000); // Cambia la imagen cada 3 segundos 
}
       