package com.vetcare.persistencia.repositorio;

import com.vetcare.persistencia.entidad.MascotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<MascotaEntity, Long> {
}
