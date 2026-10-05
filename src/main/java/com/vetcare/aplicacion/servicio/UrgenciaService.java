package com.vetcare.aplicacion.servicio;

import com.vetcare.aplicacion.dto.UrgenciaDto;
import com.vetcare.dominio.enumeraciones.EstadoUrgencia;
import java.util.List;
import java.util.Optional;

public interface UrgenciaService {

    List<UrgenciaDto> listar();

    Optional<UrgenciaDto> buscarPorId(Long id);

    UrgenciaDto guardar(UrgenciaDto dto);

    UrgenciaDto reasignarVeterinario(Long urgenciaId, Long veterinarioId);

    UrgenciaDto cambiarEstado(Long id, EstadoUrgencia nuevoEstado, String destinoRemision);

    void eliminar(Long id);
}
