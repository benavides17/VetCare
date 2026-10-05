package com.vetcare.aplicacion.servicio;

import com.vetcare.aplicacion.dto.PropietarioDto;
import java.util.List;
import java.util.Optional;

public interface PropietarioService {

    List<PropietarioDto> listar();

    Optional<PropietarioDto> buscarPorId(Long id);

    Optional<PropietarioDto> buscarPorDocumento(String numeroDocumento);

    PropietarioDto guardar(PropietarioDto dto);

    void asociarMascota(Long propietarioId, Long mascotaId);

    void desasociarMascota(Long propietarioId, Long mascotaId);

    void eliminar(Long id);
}
