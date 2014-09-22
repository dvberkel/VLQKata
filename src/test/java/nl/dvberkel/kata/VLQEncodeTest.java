package nl.dvberkel.kata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static nl.dvberkel.kata.VLQEncodeTestCase.verifyThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class VLQEncodeTest {
    private final BigInteger source;
    private final byte[] destination;

    public VLQEncodeTest(VLQEncodeTestCase testCase) {
        this.source = testCase.getSource();
        this.destination = testCase.getDestination();
    }

    @Test
    public void shouldEncodeCorrectly() {
        assertThat(new Kata().encode(source), is(destination));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        List<Object[]> testCases = new ArrayList<Object[]>();
        testCases.add(verifyThat(0).encodesAs(0b0));
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
}