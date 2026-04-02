CREATE TABLE IF NOT EXISTS productos (
    id SERIAL PRIMARY KEY,
    codigo_unico VARCHAR(20) UNIQUE, -- Se agregó UNIQUE aquí
    nombre_producto VARCHAR(100),
    tipo_producto VARCHAR(50),
    saldo NUMERIC(18,2),
    codigo_cliente VARCHAR(50),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );