document.addEventListener('DOMContentLoaded', () => {
    console.log('Página cargada y lista.');
});

//script filtrar recetas
function filtrarRecetas(categoria) {
    const recetas = document.querySelectorAll('.tarjeta-receta');
    
    recetas.forEach(receta => {
        // oculta todas las recetas primero
        receta.style.display = 'none';

        // verifica si la receta pertenece a la categoría seleccionada
        if (receta.querySelector('.subcategoria').textContent.toLowerCase().includes(categoria)) {
            receta.style.display = 'block';  // Mostramos la receta si coincide
        }
    });
}

//script carrusel
const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('.dot');
let currentSlide = 0;

function showSlide(index) {
    // asegura que el índice no esté fuera de rango
    if (index >= slides.length) {
        currentSlide = 0;
    } else if (index < 0) {
        currentSlide = slides.length - 1;
    } else {
        currentSlide = index;
    }

    // eliminar clases "active" de todos los slides y dots
    slides.forEach(slide => slide.classList.remove('active'));
    dots.forEach(dot => dot.classList.remove('active'));

    // añadir la clase "active" al slide y dot actuales
    slides[currentSlide].classList.add('active');
    dots[currentSlide].classList.add('active');
}

function nextSlide() {
    showSlide(currentSlide + 1);
}

function prevSlide() {
    showSlide(currentSlide - 1);
}

document.querySelector('.next').addEventListener('click', nextSlide);
document.querySelector('.prev').addEventListener('click', prevSlide);

dots.forEach((dot, index) => {
    dot.addEventListener('click', () => {
        showSlide(index);
    });
});

// cambio automático
setInterval(nextSlide, 4000);
