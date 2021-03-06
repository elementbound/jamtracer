package com.github.elementbound.jamtracer;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JamtracerTest {
    @Test
    public void shouldFail() {
        assertThat(true, is(true));
    }
}