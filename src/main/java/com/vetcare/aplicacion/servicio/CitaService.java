package com.vetcare.aplicacion.servicio;

import com.vetcare.aplicacion.dto.CitaDto;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface CitaService {

    List<CitaDto> listar();

    Optional<CitaDto> buscarPorId(Long id);

    CitaDto guardar(CitaDto dto);

    CitaDto reprogramar(Long id, LocalDateTime nuevaFechaHora);

    CitaDto cancelar(Long id);

    CitaDto marcarAtendida(Long id);

    void eliminar(Long id);
}
