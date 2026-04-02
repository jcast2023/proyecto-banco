INSERT INTO clientes (codigo_unico, nombres, apellidos, tipo_documento, numero_documento)
VALUES ('CLI-001', 'Julio', 'Castillo', 'DNI', '77777777')
    ON CONFLICT (codigo_unico) DO NOTHING;