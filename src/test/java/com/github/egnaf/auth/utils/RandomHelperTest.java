package com.github.egnaf.auth.utils;

import com.github.egnaf.auth.utils.helpers.RandomHelper;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomHelperTest {

    @Test
    public void generate() {
        assertEquals(RandomHelper.generate().getClass(), String.class);
    }

    @Test
    public void generateWithKey() {
        assertEquals(RandomHelper.generate("test"), "098f6bcd462133738ade4e832627b4f6");
    }
}
