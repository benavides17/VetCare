package com.vetcare.persistencia.repositorio;

import com.vetcare.persistencia.entidad.VeterinarioEntity;
import com.vetcare.dominio.enumeraciones.DisponibilidadVeterinario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<VeterinarioEntity, Long> {

    Optional<VeterinarioEntity> findByNumeroDocumento(String numeroDocumento);

    List<VeterinarioEntity> findByEspecialidad_IdAndActivoTrueAndDisponibilidad(
            Long especialidadId, DisponibilidadVeterinario disponibilidad);
}
