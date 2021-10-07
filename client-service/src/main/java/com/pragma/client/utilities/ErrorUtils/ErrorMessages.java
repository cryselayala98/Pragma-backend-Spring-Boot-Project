package com.pragma.client.utilities.ErrorUtils;

public class ErrorMessages {

    private static final String IDENTIFICACION_CONTIENE_LETRAS = "la identificacion %s: %s contiene letras";
    private static final String IDENTIFICACION_NO_REGISTRADA = "la identificacion %s: %s no esta registrada";
    private static final String IDENTIFICACION_YA_REGISTRADA = "la identificacion %s: %s ya esta registrada";
    private static final String SIN_CLIENTES_POR_EDAD = "no existen clientes con edad mayor o igual a %d";

    public static String identificacionContieneLetras(String tipo, String numero) {
        return String.format(IDENTIFICACION_CONTIENE_LETRAS, tipo, numero);
    }

    public static String identificacionNoRegistrada(String tipo, String numero) {
        return String.format(IDENTIFICACION_NO_REGISTRADA, tipo, numero);
    }

    public static String identificacionYaRegistrada(String tipo, String numero) {
        return String.format(IDENTIFICACION_YA_REGISTRADA, tipo, numero);
    }

    public static String sinClientesPorEdad(Integer edad) {
        return String.format(SIN_CLIENTES_POR_EDAD, edad);
    }
}