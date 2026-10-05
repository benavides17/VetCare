package com.vetcare.aplicacion.servicio;

import com.vetcare.aplicacion.dto.MascotaDto;
import java.util.List;
import java.util.Optional;

public interface MascotaService {

    List<MascotaDto> listar();

    Optional<MascotaDto> buscarPorId(Long id);

    MascotaDto guardar(MascotaDto dto);

    void eliminar(Long id);
}
