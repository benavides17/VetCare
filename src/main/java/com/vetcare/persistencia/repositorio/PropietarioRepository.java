package com.vetcare.persistencia.repositorio;

import com.vetcare.persistencia.entidad.PropietarioEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropietarioRepository extends JpaRepository<PropietarioEntity, Long> {

    Optional<PropietarioEntity> findByNumeroDocumento(String numeroDocumento);
}
