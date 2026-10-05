package com.vetcare.persistencia.repositorio;

import com.vetcare.persistencia.entidad.TratamientoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TratamientoRepository extends JpaRepository<TratamientoEntity, Long> {

    List<TratamientoEntity> findByAtencionId(Long atencionId);
}
