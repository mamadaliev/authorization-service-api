package com.github.egnaf.auth.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomIdentifierTest {

    @Test
    public void generate() {
        assertEquals(RandomIdentifier.generate().getClass(), String.class);
    }

    @Test
    public void generateWithKey() {
        assertEquals(RandomIdentifier.generate("test"), "098f6bcd462133738ade4e832627b4f6");
    }
}
