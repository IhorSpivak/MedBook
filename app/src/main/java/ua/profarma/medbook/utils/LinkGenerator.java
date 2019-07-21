package ua.profarma.medbook.utils;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public final class LinkGenerator {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnoprstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final String ENCRYPTION_KEY = "us0zwmCe62Xaklc3UH8uyCkfQ1aoV6Idr7wrA3FckEsRxSRqhpj0S8sKgPHTCY8HzkKe1hDmLVbaJpJs7RDmrGAdXSqCeAnZY7nxX6FUJJAy2iOLvhvlBF4X7pNrPjca";
    public static final String MEDBOOK_LINK_PAYOUT = "https://tech.medbook.mobi/docflow?key=%s&page=short-exchange-points";
    public static final String MEDBOOK_LINK_BRING_FRIEND = "https://tech.medbook.mobi/docflow?key=%s&page=bring-a-friend";


    public static final String MEDBOOK_LINK_LOAYALTY_RULES = "https://tech.medbook.mobi/docflow?key=%s=&page=loyalty-program";
    public static final String MEDBOOK_LINK_LOAYALTY_INSTRUCTIONS = "https://tech.medbook.mobi/docflow?key=%s=&page=fishka";


    public static String newUrlRules() {
        String generatedString = ENCRYPTION_KEY + randomString32();

        byte[] data = new byte[0];
        try {
            data = generatedString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        String base64 = Base64.encodeToString(data, Base64.DEFAULT);

        return String.format(MEDBOOK_LINK_LOAYALTY_RULES, base64);
    }

    public static String newUrlInstructions() {
        String generatedString = ENCRYPTION_KEY + randomString32();

        byte[] data = new byte[0];
        try {
            data = generatedString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        String base64 = Base64.encodeToString(data, Base64.DEFAULT);

        return String.format(MEDBOOK_LINK_LOAYALTY_INSTRUCTIONS, base64);
    }

   public static String newUrlBringFriend() {
        String generatedString = ENCRYPTION_KEY + randomString32();

        byte[] data = new byte[0];
        try {
            data = generatedString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        String base64 = Base64.encodeToString(data, Base64.DEFAULT);

        return String.format(MEDBOOK_LINK_BRING_FRIEND, base64);
    }

    public static String newUrlPayout() {
        String generatedString = ENCRYPTION_KEY + randomString32();

        byte[] data = new byte[0];
        try {
            data = generatedString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        String base64 = Base64.encodeToString(data, Base64.DEFAULT);

        return String.format(MEDBOOK_LINK_PAYOUT, base64);
    }

    private static String randomString32() {
        Random r = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.length() < 32) {
            int i = r.nextInt(ALPHA_NUMERIC_STRING.length());
            stringBuilder.append(ALPHA_NUMERIC_STRING.charAt(i));
        }

        return stringBuilder.toString();
    }
}
