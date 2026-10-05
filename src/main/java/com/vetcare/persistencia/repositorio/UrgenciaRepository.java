package com.vetcare.persistencia.repositorio;

import com.vetcare.persistencia.entidad.UrgenciaEntity;
import com.vetcare.dominio.enumeraciones.EstadoUrgencia;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrgenciaRepository extends JpaRepository<UrgenciaEntity, Long> {

    long countByVeterinarioAsignadoIdAndEstadoIn(Long veterinarioId, Collection<EstadoUrgencia> estados);
}
