-- usuarios
INSERT INTO Usuario(email, password, rol, activo, nombre, descripcion, ciudad, foto)
VALUES
    ('test@unlam.edu.ar', 'test', 'ADMIN', true, 'alguien', 'Me gusta mucho cocinar y compartir recetas con mis seres queridos', 'BuenosAires', 'default-user.png'),
   ('test2@unlam.edu.ar', 'test2', 'USER', true, 'alguien', 'Usuario prueba', 'BuenosAires', 'default-user.png');
-- recetas definidas
INSERT INTO receta (predefinida, calorias, comensales, descripcion, foto, nombre, tiempoPreparacion, calificacion, categoria, subcategoria, usuario_id, calificacionPromedio, guardada,tipoComida)
VALUES
    (true, 248, 5, '¡Te damos la mejor receta de todos los tiempos!', '/spring/imagenes/slides/slide1.jpeg', 'Pozole Rojo', 77, 5, 'carne', 'cerdo', 1, 0.0, false,'vegano'),
    (true, 247, 4, '¡Irresistible por donde lo veas!', '/spring/imagenes/slides/slide2.jpg', 'Pavo a la Hawaiana', 76, 4, 'caza', 'pollo', 1, 0.0, false,'vegano'),
    (true, 246, 3, 'Dulzura natural de las zanahorias', '/spring/imagenes/slides/slide3.jpg', 'Pan de zanahoria', 75, 3, 'pescado', 'salmon', 1, 0.0, false,'vegano'),
    (true, 244, 2, 'Plato tradicional de la cocina arequipeña.', '/spring/imagenes/slides/slide4.jpg', 'Ají de calabaza', 74, 2, 'pasta', 'espaguetis', 1, 0.0, false,'vegano'),
    (true, 243, 1, 'Receta con un toque especial al estilo mexicano.', '/spring/imagenes/slides/slide5.jpg', 'Bacalao a la vazcaína', 73, 1, 'cocteles', 'ponche', 1, 0.0, false,'vegano'),
    (true, 242, 5, 'Estofado japonés que combina múltiples sabores.', '/spring/imagenes/slides/slide6.jpg', 'Sukiyaki', 72, 5, 'pasta', 'pizza', 1, 0.0, false,'vegano');


