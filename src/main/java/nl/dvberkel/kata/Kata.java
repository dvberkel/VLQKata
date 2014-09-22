package nl.dvberkel.kata;


import java.math.BigInteger;

public class Kata {
    public byte[] encode(int n) {
        return encode(BigInteger.valueOf(n));
    }

    public byte[] encode(BigInteger n) {
        return new byte[] { 0b0 };
    }

}
