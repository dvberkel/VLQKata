package nl.dvberkel.kata;


import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kata implements VLQEncoder, VLQDecoder {
    private static final BigInteger base = BigInteger.valueOf(128);
    private static final byte continueBit = (byte) 0b10000000;
    private static final byte mask = 0b01111111;

    @Override
    public byte[] encode(BigInteger n) {
        BigInteger[] result = n.divideAndRemainder(base);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(leastSignificantBits(result[1]));
        while (greaterThanZero(result[0])) {
            output.write(leastSignificantBits(result[0]) | 0b10000000);
            result = result[0].divideAndRemainder(base);
        }
        return reverse(output);
    }

    private boolean greaterThanZero(BigInteger n) {
        return n.compareTo(BigInteger.ZERO) > 0;
    }

    private byte leastSignificantBits(BigInteger n) {
        return n.mod(base).toByteArray()[0];
    }

    private byte[] reverse(ByteArrayOutputStream output) {
        byte[] out = output.toByteArray();
        ArrayUtils.reverse(out);
        return out;
    }

    @Override
    public List<BigInteger> decode(byte[] source) {
        List<BigInteger> result = new ArrayList<>();
        for (int index = 0; index < source.length; index++) {
            BigInteger n = BigInteger.valueOf(source[index] & mask);
            while ((source[index] & continueBit) != 0) {
                index++;
                n = n.multiply(base).add(BigInteger.valueOf(source[index] & mask));
            }
            result.add(n);
        }
        return result;
    }
}
