package nl.dvberkel.kata;


import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

public class Kata {
    private static final BigInteger base = BigInteger.valueOf(128);

    public byte[] encode(int n) {
        return encode(BigInteger.valueOf(n));
    }

    public byte[] encode(BigInteger n) {
        BigInteger[] result  = n.divideAndRemainder(base);
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

}
