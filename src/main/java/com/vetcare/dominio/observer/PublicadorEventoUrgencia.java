package com.vetcare.dominio.observer;

import com.vetcare.dominio.valores.EventoUrgencia;

public interface PublicadorEventoUrgencia {

    void publicar(EventoUrgencia evento);
}
