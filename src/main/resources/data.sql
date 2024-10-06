INSERT INTO Usuario(id, email, password, rol, activo, nombre, descripcion, ciudad, foto) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true, 'alguien', 'Me gusta mucho cocinar y compartir recetas con mis seres queridos', 'BuenosAires', 'default-user.png' );

INSERT INTO receta (id, calorias, comensales, descripcion, foto, nombre, tiempoPreparacion, calificacion, categoria, subcategoria, usuario_id)
VALUES

        --slides (sujeto a modificacion (crear tabla especifica para slides))
       (1, 248, 5, '¡Te damos la mejor receta de todos los tiempos!', '/spring/imagenes/slides/slide1.jpeg','Pozole Rojo', 77, 5, 'carne', 'cerdo', null),
       (2, 247, 4, '¡Irresistible por donde lo veas!', '/spring/imagenes/slides/slide2.jpg', 'Pavo a la Hawaiana', 76, 4, 'caza', 'pollo', null),
       (3, 246, 3, 'Dulzura natural de las zanahorias', '/spring/imagenes/slides/slide3.jpg', 'Pan de zanahoria', 75, 3, 'pescado', 'salmon', null),
       (4, 244, 2, 'Plato tradicional de la cocina arequipeña.', '/spring/imagenes/slides/slide4.jpg', 'Ají de calabaza', 74, 2, 'pasta', 'espaguetis', null),
       (5, 243, 1, 'Receta con un toque especial al estilo mexicano.', '/spring/imagenes/slides/slide5.jpg', 'Bacalao a la vazcaína', 73, 1, 'cocteles', 'ponche',  null),
       (6, 242, 5, 'Estofado japonés que combina múltiples sabores.', '/spring/imagenes/slides/slide6.jpg', 'Sukiyaki', 72, 5, 'pasta', 'pizza', null);
       ---------------------------------