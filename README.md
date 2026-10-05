# VetCare

Sistema de gestión para una clínica veterinaria, diseñado con Java 17, Spring Boot 3 y una arquitectura orientada al dominio.

## Objetivo

Modelar el flujo clínico de una veterinaria con foco en:
- dominio rico y encapsulado
- reglas de negocio dentro del modelo
- composición y herencia para mascotas, propietarios, veterinarios y atenciones
- persistencia con JPA
- API REST para consultas, urgencias, tratamientos e historial médico

## Stack

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Maven
- JUnit 5

## Arquitectura

El proyecto sigue una organización por capas:

- dominio: modelos, enumeraciones, value objects, excepciones y reglas del negocio
- persistencia: entidades JPA y repositorios
- aplicación: DTOs, mappers, servicios y controladores REST
- configuración: perfiles de entorno y base de datos

## Patrones aplicados

- Factory: creación de atenciones y tipos clínicos
- Builder: preparación de entidades complejas
- Singleton: configuración centralizada de la clínica
- Strategy: reglas de asignación de veterinario; se pueden incorporar nuevas reglas como componentes que implementen `ReglaAsignacion`, sin modificar el servicio de urgencias.
- Observer: eventos de urgencias con notificaciones
- DTO + Mapper: desacople la capa REST de la persistencia

## Flujo principal

- Registro de propietario y mascota
- Programación de citas
- Reprogramación y cancelación de citas con comprobación de disponibilidad del veterinario
- Atención de consulta o urgencia
- Registro de consultas con cita o directamente vinculadas a una mascota
- Asignación de urgencias por especialidad, disponibilidad y menor carga activa
- Cambio de estado de urgencias con eventos de notificación
- Gestión de diagnóstico y tratamientos
- Registro automático en historial médico
- Remisión o cierre de urgencias según el estado clínico

## Operaciones principales

- `POST /api/citas`: agenda una cita futura y rechaza conflictos de horario.
- `POST /api/propietarios/{propietarioId}/mascotas/{mascotaId}` y `DELETE` en la misma ruta: asocia o desasocia mascotas de sus propietarios.
- `PUT /api/citas/{id}/reprogramar`: cambia la fecha de una cita programada.
- `POST /api/citas/{id}/cancelar`: cancela una cita programada.
- `POST /api/citas/{id}/atender`: marca la cita como atendida.
- `POST /api/urgencias`: registra una urgencia con `mascotaId` y `especialidadRequeridaId`; asigna al veterinario disponible con menor carga o deja el caso en espera.
- `PATCH /api/urgencias/{id}/estado`: valida y registra la transición de estado; para remitir, incluye `destinoRemision`.
- `PATCH /api/urgencias/{id}/veterinario`: reasigna a un veterinario activo y disponible de la especialidad requerida, liberando al anterior.
- `GET /api/historiales/mascota/{mascotaId}/atenciones`: filtra el historial opcionalmente por `tipo`, `desde`, `hasta`, `veterinarioId` y `diagnostico`.

Los cambios de estado de urgencias publican eventos para notificación. Un fallo del notificador se registra y no revierte el registro clínico.

## Ejecución

```bash
mvn clean test
mvn spring-boot:run
```

## Perfiles

- dev: desarrollo local
- test: entorno de pruebas

## Observación

El perfil `test` usa H2; el perfil `dev` requiere PostgreSQL disponible. Configura `DB_USERNAME` y `DB_PASSWORD` como variables de entorno para las credenciales de PostgreSQL (el usuario tiene como valor predeterminado `postgres`; la contraseña no tiene valor predeterminado).
