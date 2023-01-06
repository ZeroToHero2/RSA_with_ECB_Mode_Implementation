package RSA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Locale;

public class Encryption {
    private final BigInteger publicKey;
    private final BigInteger modulus;
    private String parsedString;
    private ArrayList<BigInteger> blockArray = new ArrayList<>();
    private boolean isOddLength = false; // Stealing checker

    public Encryption(BigInteger publicKey, BigInteger modulus, String plainText) {
        this.publicKey = publicKey;
        this.modulus = modulus;
        encrypt(plainText);
    }

    /**
     * Get given string block's ASCII Value
     */
    private BigInteger convertASCII(String block) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < block.length(); i++) {
            sb.append((int) block.charAt(i));
        }
        return new BigInteger(sb.toString());
    }

    /**
     * RSA encryption with ECB block mode
     */
    private BigInteger encrypt(String message) {
        BigInteger asciiRepresentation, textRepresentation;
        String blockString;
        String stealing = "", last = "", cipherString = "", chunkString = "", beforeLast = "";
        int index = 0, lengthOfPlaintext = message.length();

        if (lengthOfPlaintext % 2 != 0) isOddLength = true;

        while (index < lengthOfPlaintext) {
            if ((index == lengthOfPlaintext - 1) && (isOddLength)) {
                stealing = chunkString.substring(chunkString.length() - 8);
                blockString = message.substring(lengthOfPlaintext - 1);
                asciiRepresentation = convertASCII(blockString.toUpperCase(Locale.ENGLISH));
                String asciiBlock_string = asciiRepresentation.toString();
                asciiBlock_string = asciiBlock_string + stealing;
                asciiRepresentation = new BigInteger(asciiBlock_string);

                textRepresentation = asciiRepresentation.modPow(this.publicKey, this.modulus); //? RSA encryption

                blockArray.add(textRepresentation);
                chunkString = textRepresentation.toString();
                last = chunkString;
                index += 2;
                break;
            }
            blockString = message.substring(index, index + 2);
            asciiRepresentation = convertASCII(blockString.toUpperCase(Locale.ENGLISH));
            textRepresentation = asciiRepresentation.modPow(this.publicKey, this.modulus); //? RSA encryption

            //? Remove last 8-bits of the if there is stealing
            if (index == lengthOfPlaintext - 3 && isOddLength) {
                chunkString = textRepresentation.toString();
                blockArray.add(new BigInteger(textRepresentation.toString().substring(0, textRepresentation.toString().length() - 8)));
                beforeLast = textRepresentation.toString().substring(0, textRepresentation.toString().length() - 8);
                index += 2;
                continue;
            }
            blockArray.add(textRepresentation);
            chunkString = textRepresentation.toString();
            cipherString = cipherString + chunkString;
            // cipherString += textRepresentation.toString();
            index += 2;
        }

        if (isOddLength) { // Swap last 2 chunk
            cipherString = cipherString + last;
            cipherString = cipherString + beforeLast;
            // Swap after stealing
            Collections.swap(blockArray, blockArray.size() - 2, blockArray.size() - 1);
        }
        BigInteger cipherText = new BigInteger(cipherString);
        this.parsedString = Base64.getEncoder().encodeToString(cipherText.toByteArray());
        return cipherText;
    }

    public ArrayList<BigInteger> getBlockArray() {
        return blockArray;
    }

    public String getParsedString() {
        return parsedString;
    }

    public boolean checkIsOddLength() {
        return isOddLength;
    }
}
