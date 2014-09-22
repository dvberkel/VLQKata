package nl.dvberkel.kata;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class VLQEncodeTest {
    @Test
    public void shouldEncodeCorrectly() {
        assertThat(new Kata().encode(0), is(new byte[]{0b0}));
    }
}
