package com.vetcare.persistencia.repositorio;

import com.vetcare.persistencia.entidad.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
}
