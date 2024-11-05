document.addEventListener('DOMContentLoaded', () => {
    console.log('Página cargada y lista.');
});

//script carrusel
const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('.dot');
let currentSlide = 0;

function showSlide(index) {
    // asegura que el índice no este fuera de rango
    if (index >= slides.length) {
        currentSlide = 0;
    } else if (index < 0) {
        currentSlide = slides.length - 1;
    } else {
        currentSlide = index;
    }

    //esto elimina las clases "active" de todos los slides y dots
    slides.forEach(slide => slide.classList.remove('active'));
    dots.forEach(dot => dot.classList.remove('active'));

    //lo mismo pero las añade
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


document.addEventListener("DOMContentLoaded", () => {
    const verTipLink = document.getElementById('verTip');
    const tipDetalle = document.getElementById('tip-detalle');

    verTipLink.addEventListener("click", (event) => {
        event.preventDefault();

        if (tipDetalle.style.display === "none" || tipDetalle.style.display === "") {
            tipDetalle.style.display = "block";
            verTipLink.textContent = "Ocultar el tip";
        } else {
            tipDetalle.style.display = "none";
            verTipLink.textContent = "Ver el tip";
        }
    });
});