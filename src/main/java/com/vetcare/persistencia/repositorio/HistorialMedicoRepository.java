package com.vetcare.persistencia.repositorio;

import com.vetcare.persistencia.entidad.HistorialMedicoEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialMedicoRepository extends JpaRepository<HistorialMedicoEntity, Long> {

    Optional<HistorialMedicoEntity> findByMascotaId(Long mascotaId);
}
