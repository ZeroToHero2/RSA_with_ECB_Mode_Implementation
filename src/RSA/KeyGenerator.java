package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyGenerator {
    private final static BigInteger one = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();
    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;
    private BigInteger p, q;
    private int N = 1024;

    /**
     * Generate an N-bit (roughly) public and private key
     */
    public KeyGenerator() {
        /**
         * Generate two large prime numbers "p" and "q" with N bits
         */
        this.p = selectPrimeNumber();
        this.q = selectPrimeNumber();

        /**
         * Compute "n" and "phi(n)"
         */
        this.modulus = p.multiply(q);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        /**
         * Choose a public key e such that 1 < e < phi(n) and gcd(e, phi(n)) = 1
         */
        publicKey = selectPublicKey(modulus, phi);
        while (phi.gcd(publicKey).compareTo(one) > 0 && publicKey.compareTo(phi) < 0) {
            publicKey = publicKey.add(one);
        }
        /**
         * Compute the Private key d such that d = e^(-1) mod phi(n)
         */
        this.privateKey = publicKey.modInverse(phi);
    }

    /**
     * Create p and q which are prime
     */
    private BigInteger selectPrimeNumber() {
        BigInteger candidatePrime;
        boolean isPrime;
        while (true) {
            candidatePrime = BigInteger.probablePrime(N, random);
            isPrime = isProbablyPrime(candidatePrime);
            if (isPrime) break;
        }
        return candidatePrime;
    }

    /**
     * Returns true if the given number is probably prime, false if it's definitely composite.
     */
    public boolean isProbablyPrime(BigInteger n) {
        // Check for small prime numbers
        if (n.equals(BigInteger.TWO)) {
            return true;
        }
        if (n.compareTo(BigInteger.TWO) < 0 || n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return false;
        }

        // Apply the test
        int ROUNDCOUNT = 20;
        for (int i = 0; i < ROUNDCOUNT; i++) {
            BigInteger a = getRandomBigInteger(n.subtract(one));
            if (!a.modPow(n.subtract(one), n).equals(one)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a random BigInteger that is greater than or equal to min and less than max.
     */
    private static BigInteger getRandomBigInteger(BigInteger max) {
        BigInteger result;
        do {
            result = new BigInteger(max.bitLength(), random);
        } while (result.compareTo(max) >= 0);
        return result;
    }

    private BigInteger selectPublicKey(BigInteger n, BigInteger phi) {
        BigInteger candidatePublicKey = new BigInteger(N, random);

        while (true) {
            if (candidatePublicKey.compareTo(one) < 0 && phi.compareTo(candidatePublicKey) < 0) {
                candidatePublicKey = new BigInteger(N, random);
                continue;
            } else if (!(candidatePublicKey.gcd(n).equals(one))) {
                candidatePublicKey = new BigInteger(N, random);
                continue;
            } else if (!(candidatePublicKey.gcd(phi).equals(one))) {
                candidatePublicKey = new BigInteger(N, random);
                continue;
            }
            break;
        }
        return candidatePublicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getModulus() {
        return modulus;
    }
}
