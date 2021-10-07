package com.pragma.photo.utilities.ErrorUtils;

public class ErrorMessages {

    private static final String PHOTO_IS_NOT_REGISTERED = "la foto con id : %s no existe";

    public static String photoDoesNotExist(String id) {
        return String.format(PHOTO_IS_NOT_REGISTERED, id);
    }
}