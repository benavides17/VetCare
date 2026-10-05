/**
 * Valor inmutable que valida el tipo y número del documento.
 */
package com.vetcare.dominio.valores;

import com.vetcare.dominio.enumeraciones.TipoDocumento;

public record Documento(TipoDocumento tipo, String numero) {

    public Documento {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de documento es obligatorio.");
        }
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número de documento es obligatorio.");
        }
        numero = numero.trim();
    }
}
