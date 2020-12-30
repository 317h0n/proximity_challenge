/* Populate items */
INSERT INTO items (codigo, nombre, descripcion, precio) VALUES('G0001', 'Tentacion Chocolate', 'Galleta sabor a chocolate, bolsa de 8 unidades', 0.60);
INSERT INTO items (codigo, nombre, descripcion, precio) VALUES('S0001', 'Doritos', 'Bolsa de snack salados, bolsa de 100g', 1.50);

/* Populate Expendedora tipo */
INSERT INTO expendedoras_tipo (codigo, forma_pago) VALUES ('XYZ1','E');
INSERT INTO expendedoras_tipo (codigo, forma_pago) VALUES ('XYZ2','ET');

/* Populate Expendedoras */
INSERT INTO expendedoras (codigo, tipo_id, clave, recolectar_dinero) VALUES ('ME0001', 1, '12345', 0);
INSERT INTO expendedoras (codigo, tipo_id, clave, recolectar_dinero) VALUES ('ME0002', 1, '12345', 0);
INSERT INTO expendedoras (codigo, tipo_id, clave, recolectar_dinero) VALUES ('ME0003', 2, '12345', 0);

/* Populate Ventas */
INSERT INTO ventas (fecha, expendedora_id, forma_pago, pagada) VALUES ('2020-12-27', 1, 'E', 0);
INSERT INTO ventas (fecha, expendedora_id, forma_pago, pagada) VALUES ('2020-12-28', 1, 'E', 1);
INSERT INTO ventas (fecha, expendedora_id, forma_pago, pagada) VALUES ('2020-12-28', 2, 'E', 1);
INSERT INTO ventas (fecha, expendedora_id, forma_pago, pagada) VALUES ('2020-12-28', 3, 'ET', 0);
INSERT INTO ventas (fecha, expendedora_id, forma_pago, pagada) VALUES ('2020-12-28', 3, 'E', 1);

/* Populate Ventas Items */
INSERT INTO ventas_items (venta_id, item_id, cantidad) VALUES (1, 1, 4);
INSERT INTO ventas_items (venta_id, item_id, cantidad) VALUES (2, 2, 2);
INSERT INTO ventas_items (venta_id, item_id, cantidad) VALUES (2, 1, 1);
INSERT INTO ventas_items (venta_id, item_id, cantidad) VALUES (3, 1, 1);
INSERT INTO ventas_items (venta_id, item_id, cantidad) VALUES (4, 2, 2);
INSERT INTO ventas_items (venta_id, item_id, cantidad) VALUES (5, 1, 5);

/* Populate Tecnicos */
INSERT INTO tecnicos (codigo, nombre) VALUES ('T0003','Roberto Martinez');
INSERT INTO tecnicos (codigo, nombre) VALUES ('T0004','Waldir Saenz');

/* Populate Retiros */
INSERT INTO retiros (fecha, expendedora_id, monto, tecnico_id) VALUES ('2020-12-29', 1, 1, 1);
INSERT INTO retiros (fecha, expendedora_id, monto, tecnico_id) VALUES ('2020-12-29', 2, 0.50, 2);