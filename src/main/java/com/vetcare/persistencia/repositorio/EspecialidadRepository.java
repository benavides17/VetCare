package com.vetcare.persistencia.repositorio;

import com.vetcare.persistencia.entidad.EspecialidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Long> {
}
