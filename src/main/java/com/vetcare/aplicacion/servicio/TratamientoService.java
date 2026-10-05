package com.vetcare.aplicacion.servicio;

import com.vetcare.aplicacion.dto.TratamientoDto;
import java.util.List;
import java.util.Optional;

public interface TratamientoService {

    List<TratamientoDto> listar();

    Optional<TratamientoDto> buscarPorId(Long id);

    List<TratamientoDto> buscarPorAtencionId(Long atencionId);

    TratamientoDto guardar(TratamientoDto dto);

    void eliminar(Long id);
}
