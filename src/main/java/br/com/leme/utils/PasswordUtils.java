package br.com.leme.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordUtils {
    private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    public static String generateHash (char[] senha) {
        try {
            return argon2.hash(3, 65536, 1, senha);
        } finally {
            argon2.wipeArray(senha);
        }
    }

    public static boolean verifyHash (String senha, String senhaHashed) {
        try {
            return argon2.verify(senhaHashed, senha.toCharArray());
        } finally {
            argon2.wipeArray(senha.toCharArray());
        }
    }
}
