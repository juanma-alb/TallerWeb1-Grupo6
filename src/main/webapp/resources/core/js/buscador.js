document.getElementById('buscador').addEventListener('keyup', function() {
    let query = this.value.toLowerCase();
    let recetas = document.querySelectorAll('.recetasDelDia .item');

    recetas.forEach(function(receta) {
        let nombre = receta.querySelector('.nombreReceta').textContent.toLowerCase();
        if (nombre.includes(query)) {
            receta.style.display = '';
        } else {
            receta.style.display = 'none';
        }
    });
});