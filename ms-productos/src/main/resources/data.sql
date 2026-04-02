INSERT INTO productos (codigo_unico, nombre_producto, tipo_producto, saldo, codigo_cliente)
VALUES ('PROD-001', 'Cuenta de Ahorros', 'AHORRO', 1500.0, 'CLI-001')
    ON CONFLICT (codigo_unico) DO NOTHING; -- Se especificó la columna del conflicto