package nl.dvberkel.kata;

import java.math.BigInteger;

public interface VLQEncoder {
    public byte[] encode(BigInteger n);
}
