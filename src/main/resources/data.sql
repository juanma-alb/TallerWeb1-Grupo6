-- usuarios
INSERT INTO Usuario(email, password, rol, activo, nombre, descripcion, ciudad, foto)
VALUES
    ('test@unlam.edu.ar', 'test', 'ADMIN', true, 'alguien', 'Me gusta mucho cocinar y compartir recetas con mis seres queridos', 'BuenosAires', 'default-user.png'),
   ('test2@unlam.edu.ar', 'test2', 'USER', true, 'alguien', 'Usuario prueba', 'BuenosAires', 'default-user.png');
-- recetas definidas
INSERT INTO receta (predefinida, calorias, comensales, descripcion, foto, nombre, tiempoPreparacion, calificacion, categoria, subcategoria, usuario_id, calificacionPromedio, guardada, tipoComida)
VALUES
    (true, 248, 5, '¡Te damos la mejor receta de todos los tiempos!', 'slide1.jpeg', 'Pozole Rojo', 77, 5, 'carne', 'cerdo', null, 0.0, false,'vegano'),
    (true, 247, 4, '¡Irresistible por donde lo veas!', 'slide2.jpg', 'Pavo a la Hawaiana', 76, 4, 'caza', 'pollo', null, 0.0, false,'Flexitariano'),
    (true, 246, 3, 'Dulzura natural de las zanahorias', 'slide3.jpg', 'Pan de zanahoria', 75, 3, 'pescado', 'salmon', null, 0.0, false,'vegano'),
    (true, 244, 2, 'Plato tradicional de la cocina arequipeña.', 'slide4.jpg', 'Ají de calabaza', 74, 2, 'pasta', 'espaguetis', null, 0.0, false,'Ovolactovegetariano'),
    (true, 243, 1, 'Receta con un toque especial al estilo mexicano.', 'slide5.jpg', 'Bacalao a la vazcaína', 73, 1, 'cocteles', 'ponche', null, 0.0, false,'Flexitariano'),
    (true, 242, 5, 'Estofado japonés que combina múltiples sabores.', 'slide6.jpg', 'Sukiyaki', 72, 5, 'pasta', 'pizza', null, 0.0, false,'Ovolactovegetariano'),
    (true, 241, 5, 'Tacos de Pescado, una receta muy rica para compartir entre amigos.', 'slide7.jpg', 'Tacos de Pescado', 72, 4, 'carne', 'cerdo', null, 0.0, false,'Básica'),
    (true, 240, 5, 'Brownies caseros muy ricos.', 'slide8.jpg', 'Brownie', 50, 5, 'pasta', 'espaguetis', null, 4, false,'Básica'),
    (true, 239, 5, 'Tacos Veganos de Garbanzos.', 'slide9.jpeg', 'Tacos Veganos de Garbanzos', 72, 3, 'pasta', 'pizza', null, 0.0, false,'vegano'),
    (true, 239, 2, 'Hamburguesas de Lentejas.', 'slide10.jpg', 'Hamburguesas de Lentejas', 72, 3, 'pasta', 'pizza', null, 0.0, false,'vegano');