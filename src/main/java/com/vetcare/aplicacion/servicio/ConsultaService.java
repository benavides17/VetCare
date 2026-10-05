package com.vetcare.aplicacion.servicio;

import com.vetcare.aplicacion.dto.ConsultaDto;
import java.util.List;
import java.util.Optional;

public interface ConsultaService {

    List<ConsultaDto> listar();

    Optional<ConsultaDto> buscarPorId(Long id);

    ConsultaDto guardar(ConsultaDto dto);

    void eliminar(Long id);
}
