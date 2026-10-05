package com.vetcare.persistencia.repositorio;

import com.vetcare.persistencia.entidad.AtencionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtencionRepository extends JpaRepository<AtencionEntity, Long> {
}
