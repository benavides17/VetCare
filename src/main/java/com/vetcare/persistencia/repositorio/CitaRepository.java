package com.vetcare.persistencia.repositorio;

import com.vetcare.persistencia.entidad.CitaEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<CitaEntity, Long> {

    @Query("""
            select case when count(c) > 0 then true else false end
            from CitaEntity c
            where c.veterinario.id = :veterinarioId
              and c.estado = com.vetcare.dominio.enumeraciones.EstadoCita.PROGRAMADA
              and c.fechaHora > :desde
              and c.fechaHora < :hasta
              and (:citaId is null or c.id <> :citaId)
            """)
    boolean existeConflictoHorario(@Param("veterinarioId") Long veterinarioId,
                                  @Param("desde") LocalDateTime desde,
                                  @Param("hasta") LocalDateTime hasta,
                                  @Param("citaId") Long citaId);
}
