package id.ic.aims.console;

import id.ic.aims.manager.EncryptionManager;

public class Console {

    public static void main(String[] args) {
        System.out.println("Password: " + EncryptionManager.getInstance().encrypt("aims_user"));
        System.out.println("Decrypt: " + EncryptionManager.getInstance().decrypt("31QTmAGMdFCbuRXzxHUGNfIEWPri/Kxpx4Yn/VjbMW4="));


        String salt = EncryptionManager.getInstance().generateRandomString(6);
        String hashedPassword = EncryptionManager.getInstance().hash(salt, "P@ssw0rd");

        System.out.println(salt);
        System.out.println(hashedPassword);
    }
}
