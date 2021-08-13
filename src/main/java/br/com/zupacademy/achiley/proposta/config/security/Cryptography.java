package br.com.zupacademy.achiley.proposta.config.security;

import org.springframework.security.crypto.encrypt.Encryptors;

public class Cryptography {
    private static String salt = "1945ce5c51afc0";

    public static String encrypt(String text){
        return Encryptors.queryableText("text", salt).encrypt(text);
    }

    public static String decrypt(String text){
        return Encryptors.queryableText("text", salt).decrypt(text);
    }
}
