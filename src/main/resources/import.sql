INSERT INTO `usuario` (`id`, `apellido`, `email`, `nombre`, `password`, `username`, `enabled`) VALUES (1,	'Martín',	'pepe@sincorreo.com',	'Pepe',	'$2a$10$PMDCjYqXJxGsVlnve1t9Jug2DkDDckvUDl8.vF4Dc6yg0FMjovsXO',	'pepe',1);
INSERT INTO `usuario` (`id`, `apellido`, `email`, `nombre`, `password`, `username`, `enabled`) VALUES (2,	'Sin Miedo',	'obijuan@sincorreo.es',	'Juan',	'$2a$10$PMDCjYqXJxGsVlnve1t9Jug2DkDDckvUDl8.vF4Dc6yg0FMjovsXO',	'obijuan',1);
INSERT INTO `usuario` (`id`, `username`, `nombre`, `email`, `password`, `apellido`, `enabled`) VALUES (3, 'juanito', 'Juanito', 'juan.perez@email.com', '$2a$10$PMDCjYqXJxGsVlnve1t9Jug2DkDDckvUDl8.vF4Dc6yg0FMjovsXO', 'pulgarcito',1);

INSERT INTO `alumno` (id, enabled, nombre, apellido, username, password, email) VALUES (1,1,'Jose Maria','Gomez Liñan','josema','dam2324','josema@gmail.com');
INSERT INTO `alumno` (id, enabled, nombre, apellido, username, password, email) VALUES (2,1,'Jaime','Gomez Liñan','jaime','conde123','jaime@gmail.com');
INSERT INTO `alumno` (id, enabled, nombre, apellido, username, password, email) VALUES (3,1,'Javier','Torres Gomez','javier','maya123','javier@gmail.com');
INSERT INTO `alumno` (id, enabled, nombre, apellido, username, password, email) VALUES (4,1,'Antonio','Sanchez Perez','toni','anto12','antonio@gmail.com');
INSERT INTO `alumno` (id, enabled, nombre, apellido, username, password, email) VALUES (5,1,'Pedro','Perez Albil','pedrito','pedro12','pedro@gmail.com');

INSERT INTO `profesor` (id, enabled, nombre, apellido, username, password, email) VALUES (1,1,'Santiago','Rodenas Herraiz','santi','srodenas12','srdodenas@gmail.com');
INSERT INTO `profesor` (id, enabled, nombre, apellido, username, password, email) VALUES (2,1,'Luis','Molina Perez','luis','luis12','luis@gmail.com');
INSERT INTO `profesor` (id, enabled, nombre, apellido, username, password, email) VALUES (3,1,'Juanjo','Martinez Soria','juanjo','juanjo12','juanjo@gmail.com');
INSERT INTO `profesor` (id, enabled, nombre, apellido, username, password, email) VALUES (4,1,'Juangu','Perez Martinez','juangu','juangu12','juangu@gmail.com');
INSERT INTO `profesor` (id, enabled, nombre, apellido, username, password, email) VALUES (5,1,'Maribel','Gomez Liñan','maribel','maribel12','maribel@gmail.com');

INSERT INTO `asignatura` (id, nombre, ciclo, curso) VALUES (1,'Acceso a Datos','Dam',2);
INSERT INTO `asignatura` (id, nombre, ciclo, curso) VALUES (2,'Programacion de servicios y procesos','Dam',2);
INSERT INTO `asignatura` (id, nombre, ciclo, curso) VALUES (3,'Programacion multimedia y dispositivos moviles','Dam',2);
INSERT INTO `asignatura` (id, nombre, ciclo, curso) VALUES (4,'Desarrollo de interfaces','Dam',2);
INSERT INTO `asignatura` (id, nombre, ciclo, curso) VALUES (5,'Sistema gestion empresarial','Dam',2);

INSERT INTO `telefono` (id, alumno_id, usuario_id, codigo_pais, numero) VALUES (1,1,1,34,222333444);
INSERT INTO `telefono` (id, alumno_id, usuario_id, codigo_pais, numero) VALUES (2,2,2,34,123456789);
INSERT INTO `telefono` (id, alumno_id, usuario_id, codigo_pais, numero) VALUES (3,3,3,34,987654321);

INSERT INTO `rol` (id, nombre) VALUES (1,'gestor');
INSERT INTO `rol` (id, nombre) VALUES (2,'alumnado');
INSERT INTO `rol` (id, nombre) VALUES (3,'profesorado');

INSERT INTO `usuario_roles` (roles_id, usuario_id) VALUES (1,1);
INSERT INTO `usuario_roles` (roles_id, usuario_id) VALUES (2,2);
INSERT INTO `usuario_roles` (roles_id, usuario_id) VALUES (3,3);

INSERT INTO `alumno_roles` (alumno_id, roles_id) VALUES (1,1);
INSERT INTO `alumno_roles` (alumno_id, roles_id) VALUES (2,2);
INSERT INTO `alumno_roles` (alumno_id, roles_id) VALUES (3,3);

INSERT INTO `profesor_roles` (profesor_id, roles_id) VALUES (1,1);
INSERT INTO `profesor_roles` (profesor_id, roles_id) VALUES (2,2);
INSERT INTO `profesor_roles` (profesor_id, roles_id) VALUES (3,3);
