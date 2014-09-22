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

import static nl.dvberkel.kata.VLQDecodeTestCase.verifyThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class VLQDecodeTest {
    private final byte[] source;
    private final List<BigInteger> destination;
    private VLQDecoder kata;

    public VLQDecodeTest(VLQDecodeTestCase testCase) {
        this.source = testCase.getSource();
        this.destination = testCase.getDestination();
    }

    @Before
    public void createKata() {
        kata = new Kata();
    }

    @Test
    public void shouldEncodeCorrectly() {
        assertThat(kata.decode(source), is(destination));
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        List<Object[]> testCases = new ArrayList<Object[]>();
        testCases.add(verifyThat(0b00000000).decodesAs(0));
        testCases.add(verifyThat(0b01111111).decodesAs(127));
        testCases.add(verifyThat(0b00000000, 0b00000000).decodesAs(0, 0));
        testCases.add(verifyThat(0b10000001, 0b00000000).decodesAs(128));
        testCases.add(verifyThat(0b10000001, 0b10000000, 0b00000000).decodesAs(16384));
        testCases.add(verifyThat(0b10000001, 0b10000000, 0b00000000, 0b00000001).decodesAs(16384, 1));
        return testCases;
    }
}

class VLQDecodeTestCase {
    private final byte[] source;
    private List<BigInteger> destination;

    public static VLQDecodeTestCase verifyThat(int... source) {
        byte[] destination = new byte[source.length];
        for (int index = 0; index < source.length; index++) {
            destination[index] = (byte) source[index];
        }
        return verifyThat(destination);
    }


    public static VLQDecodeTestCase verifyThat(byte[] source) {
        return new VLQDecodeTestCase(source);
    }

    private VLQDecodeTestCase(byte[] source) {
        this.source = source;
    }

    public Object[] decodesAs(int... destination) {
        BigInteger[] copy = new BigInteger[destination.length];
        for (int index = 0; index < destination.length; index++) {
            copy[index] = BigInteger.valueOf(destination[index]);
        }
        return decodesAs(copy);
    }

    private Object[] decodesAs(BigInteger... destination) {
        this.destination = Arrays.asList(destination);
        return new Object[] { this };
    }

    public byte[] getSource() {
        return source;
    }

    public List<BigInteger> getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return String.format("decode(%s) should be %s", Arrays.toString(source), destination);
    }
}