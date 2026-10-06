package com.vetcare.aplicacion.servicio.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vetcare.dominio.enumeraciones.Sexo;
import com.vetcare.dominio.enumeraciones.TipoDocumento;
import com.vetcare.exception.MascotaNoPerteneceAPropietarioException;
import com.vetcare.persistencia.entidad.MascotaEntity;
import com.vetcare.persistencia.entidad.PropietarioEntity;
import com.vetcare.persistencia.repositorio.MascotaRepository;
import com.vetcare.persistencia.repositorio.PropietarioRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropietarioServiceImplTest {

    @Mock
    private PropietarioRepository propietarioRepository;
    @Mock
    private MascotaRepository mascotaRepository;
    @InjectMocks
    private PropietarioServiceImpl servicio;

    @Test
    void asociaYDesasociaUnaMascotaDelPropietario() {
        PropietarioEntity propietario = new PropietarioEntity("Ana", "García",
                TipoDocumento.CC, "123456", "3001234567", "ana@example.com");
        MascotaEntity mascota = mascota();
        when(propietarioRepository.findById(1L)).thenReturn(Optional.of(propietario));
        when(mascotaRepository.findById(2L)).thenReturn(Optional.of(mascota));

        servicio.asociarMascota(1L, 2L);
        assertEquals(1, propietario.getMascotas().size());
        verify(propietarioRepository).save(propietario);

        servicio.desasociarMascota(1L, 2L);
        assertEquals(0, propietario.getMascotas().size());
        verify(propietarioRepository, org.mockito.Mockito.times(2)).save(propietario);
    }

    @Test
    void noDesasociaUnaMascotaQueNoPerteneceAlPropietario() {
        PropietarioEntity propietario = new PropietarioEntity("Ana", "García",
                TipoDocumento.CC, "123456", "3001234567", "ana@example.com");
        when(propietarioRepository.findById(1L)).thenReturn(Optional.of(propietario));
        when(mascotaRepository.findById(2L)).thenReturn(Optional.of(mascota()));

        assertEquals("La mascota no está asociada a este propietario.",
                assertThrows(MascotaNoPerteneceAPropietarioException.class,
                        () -> servicio.desasociarMascota(1L, 2L)).getMessage());
    }

    private MascotaEntity mascota() {
        MascotaEntity mascota = new MascotaEntity("Milo", "Perro", "Mestizo", Sexo.MACHO,
                LocalDate.of(2021, 1, 1), "Negro", 12.0, null);
        mascota.setId(2L);
        return mascota;
    }
}
