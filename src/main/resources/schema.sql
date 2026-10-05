CREATE TABLE IF NOT EXISTS especialidades (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(500),
    activa BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS propietarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    tipo_documento VARCHAR(30) NOT NULL,
    numero_documento VARCHAR(30) NOT NULL,
    telefono VARCHAR(50),
    email VARCHAR(150),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS mascotas (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(100) NOT NULL,
    raza VARCHAR(100),
    sexo VARCHAR(20) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    color VARCHAR(80),
    peso NUMERIC(6,2),
    observaciones VARCHAR(500),
    activa BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS propietario_mascota (
    propietario_id BIGINT NOT NULL,
    mascota_id BIGINT NOT NULL,
    PRIMARY KEY (propietario_id, mascota_id),
    CONSTRAINT fk_propietario_mascota_propietario FOREIGN KEY (propietario_id) REFERENCES propietarios (id),
    CONSTRAINT fk_propietario_mascota_mascota FOREIGN KEY (mascota_id) REFERENCES mascotas (id)
);

CREATE TABLE IF NOT EXISTS veterinarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    tipo_documento VARCHAR(30) NOT NULL,
    numero_documento VARCHAR(30) NOT NULL,
    telefono VARCHAR(50),
    email VARCHAR(150),
    licencia VARCHAR(50) NOT NULL,
    disponibilidad VARCHAR(25) NOT NULL DEFAULT 'DISPONIBLE',
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    especialidad_id BIGINT NOT NULL,
    CONSTRAINT fk_veterinario_especialidad FOREIGN KEY (especialidad_id) REFERENCES especialidades (id)
);

CREATE TABLE IF NOT EXISTS citas (
    id BIGSERIAL PRIMARY KEY,
    fecha_hora TIMESTAMP NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    estado VARCHAR(25) NOT NULL DEFAULT 'PROGRAMADA',
    mascota_id BIGINT NOT NULL,
    veterinario_id BIGINT NOT NULL,
    CONSTRAINT fk_cita_mascota FOREIGN KEY (mascota_id) REFERENCES mascotas (id),
    CONSTRAINT fk_cita_veterinario FOREIGN KEY (veterinario_id) REFERENCES veterinarios (id)
);

CREATE TABLE IF NOT EXISTS historiales_medicos (
    id BIGSERIAL PRIMARY KEY,
    mascota_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_historial_mascota FOREIGN KEY (mascota_id) REFERENCES mascotas (id)
);

CREATE TABLE IF NOT EXISTS atenciones (
    id BIGSERIAL PRIMARY KEY,
    tipo_atencion VARCHAR(20) NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    sintomas VARCHAR(1000),
    diagnostico VARCHAR(1000),
    historial_medico_id BIGINT NOT NULL,
    cita_id BIGINT,
    observaciones VARCHAR(1000),
    hora_llegada TIMESTAMP,
    prioridad VARCHAR(25),
    estado VARCHAR(25),
    veterinario_asignado_id BIGINT,
    destino_remision VARCHAR(255),
    especialidad_requerida_id BIGINT,
    mascota_id BIGINT,
    CONSTRAINT fk_atencion_historial FOREIGN KEY (historial_medico_id) REFERENCES historiales_medicos (id),
    CONSTRAINT fk_atencion_cita FOREIGN KEY (cita_id) REFERENCES citas (id),
    CONSTRAINT fk_atencion_veterinario FOREIGN KEY (veterinario_asignado_id) REFERENCES veterinarios (id),
    CONSTRAINT fk_atencion_mascota FOREIGN KEY (mascota_id) REFERENCES mascotas (id)
);

ALTER TABLE atenciones ADD COLUMN IF NOT EXISTS especialidad_requerida_id BIGINT;

CREATE TABLE IF NOT EXISTS atencion_procedimientos (
    atencion_id BIGINT NOT NULL,
    procedimiento VARCHAR(255) NOT NULL,
    CONSTRAINT fk_atencion_procedimiento_atencion FOREIGN KEY (atencion_id) REFERENCES atenciones (id)
);

CREATE TABLE IF NOT EXISTS tratamientos (
    id BIGSERIAL PRIMARY KEY,
    medicamento VARCHAR(150) NOT NULL,
    dosis_cantidad NUMERIC(10,2) NOT NULL,
    unidad VARCHAR(50) NOT NULL,
    frecuencia VARCHAR(50) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    indicaciones VARCHAR(500),
    estado VARCHAR(25) NOT NULL DEFAULT 'ACTIVO',
    atencion_id BIGINT NOT NULL,
    CONSTRAINT fk_tratamiento_atencion FOREIGN KEY (atencion_id) REFERENCES atenciones (id)
);
