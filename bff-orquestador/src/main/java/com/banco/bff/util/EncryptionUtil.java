package com.banco.bff.util;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class EncryptionUtil {

    // Para que el BFF traduzca lo que viene de Postman
    public static String decrypt(String strEncrypted) {
        byte[] decodedBytes = Base64.getDecoder().decode(strEncrypted);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    // Método auxiliar por si quieres generar códigos encriptados para tus pruebas
    public static String encrypt(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }
}