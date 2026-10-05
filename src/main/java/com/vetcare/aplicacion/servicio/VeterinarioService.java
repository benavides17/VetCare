package com.vetcare.aplicacion.servicio;

import com.vetcare.aplicacion.dto.VeterinarioDto;
import java.util.List;
import java.util.Optional;

public interface VeterinarioService {

    List<VeterinarioDto> listar();

    Optional<VeterinarioDto> buscarPorId(Long id);

    Optional<VeterinarioDto> buscarPorDocumento(String numeroDocumento);

    VeterinarioDto guardar(VeterinarioDto dto);

    void eliminar(Long id);
}
