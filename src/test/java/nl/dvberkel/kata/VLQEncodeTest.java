package nl.dvberkel.kata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static nl.dvberkel.kata.VLQEncodeTestCase.verifyThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class VLQEncodeTest {
    private final BigInteger source;
    private final byte[] destination;
    private Kata kata;

    public VLQEncodeTest(VLQEncodeTestCase testCase) {
        this.source = testCase.getSource();
        this.destination = testCase.getDestination();
    }

    @Before
    public void createKata() {
        kata = new Kata();
    }

    @Test
    public void shouldEncodeCorrectly() {
        assertThat(kata.encode(source), is(destination));
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        List<Object[]> testCases = new ArrayList<Object[]>();
        for (int b0 = 0; b0 < 128; b0++) {
            testCases.add(verifyThat(b0).encodesAs(b0));
        }
        for (int b1 = 1; b1 < 128; b1++) {
            for (int b0 = 0; b0 < 128; b0++) {
                testCases.add(verifyThat(128 * b1 + b0).encodesAs(b1 | 0b10000000, b0));
            }
        }
        testCases.add(verifyThat(128*128 * 1 + 128 * 0 + 0).encodesAs(1 | 0b10000000, 0 | 0b10000000, 0));
        return testCases;
    }
}

class VLQEncodeTestCase {
    private final BigInteger source;
    private byte[] destination;

    public static VLQEncodeTestCase verifyThat(int source) {
        return verifyThat(BigInteger.valueOf(source));
    }


    public static VLQEncodeTestCase verifyThat(BigInteger source) {
        return new VLQEncodeTestCase(source);
    }

    private VLQEncodeTestCase(BigInteger source) {
        this.source = source;
    }

    public Object[] encodesAs(int... destination) {
        this.destination = new byte[destination.length];
        for (int index = 0; index < destination.length; index++) {
            this.destination[index] = (byte) destination[index];
        }
        return new Object[]{this};
    }

    public BigInteger getSource() {
        return source;
    }

    public byte[] getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return String.format("encode(%s) should be %s", source, Arrays.toString(destination));
    }
}