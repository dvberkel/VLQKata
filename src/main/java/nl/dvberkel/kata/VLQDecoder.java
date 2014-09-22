package nl.dvberkel.kata;

import java.math.BigInteger;
import java.util.List;

public interface VLQDecoder {
    public List<BigInteger> decode(byte[] source);
}
