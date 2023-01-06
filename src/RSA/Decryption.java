package RSA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Decryption {
    private final BigInteger privateKey;
    private final BigInteger modulus;
    private final String decryptedText;
    private final boolean issOddNumber;

    public Decryption(BigInteger privateKey, BigInteger modulus, ArrayList<BigInteger> blockChunk, boolean issOddNumber) {
        this.privateKey = privateKey;
        this.modulus = modulus;
        this.issOddNumber = issOddNumber;
        this.decryptedText = decrypt(blockChunk);
    }

    /**
     * Get given string ASCII Value
     */
    private String convertASCIItoString(String cipherText) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i += 2) {
            result.append((char) Integer.parseInt(cipherText.substring(i, i + 2)));
        }
        return result.toString();
    }

    /**
     * RSA decryption with ECB block mode
     */
    private String decrypt(ArrayList<BigInteger> ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        String decrypted = "", stealingTemp = "";

        if (issOddNumber) {
            Collections.swap(ciphertext, ciphertext.size() - 2, ciphertext.size() - 1);
            decrypted = ciphertext.get(ciphertext.size() - 1).modPow(privateKey, modulus).toString();
            stealingTemp = decrypted.substring(decrypted.length() - 8);
            String secondLast = ciphertext.get(ciphertext.size() - 2).toString();
            secondLast = secondLast + stealingTemp;
            ciphertext.set(ciphertext.size() - 2, new BigInteger(secondLast));
        }
        //? Decrypt ciphertext in ECB Mode
        for (BigInteger bigInteger : ciphertext) {
            decrypted = bigInteger.modPow(privateKey, modulus).toString();
            if (issOddNumber && (Objects.equals(ciphertext.get(ciphertext.size() - 1), bigInteger))) {
                decrypted = decrypted.substring(0, 2);
            }
            plaintext.append(decrypted);
        }
        plaintext = new StringBuilder(convertASCIItoString(plaintext.toString()));
        return plaintext.toString();
    }

    public String getDecryptedText() {
        return decryptedText;
    }
}
