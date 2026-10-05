package com.vetcare.aplicacion.servicio;

import com.vetcare.aplicacion.dto.EspecialidadDto;
import java.util.List;
import java.util.Optional;

public interface EspecialidadService {

    List<EspecialidadDto> listar();

    Optional<EspecialidadDto> buscarPorId(Long id);

    EspecialidadDto guardar(EspecialidadDto dto);

    void eliminar(Long id);
}
