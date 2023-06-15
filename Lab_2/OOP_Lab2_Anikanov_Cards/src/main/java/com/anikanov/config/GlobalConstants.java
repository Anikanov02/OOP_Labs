package com.anikanov.config;

import java.math.MathContext;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

public class GlobalConstants {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final MathContext MATH_CONTEXT = new MathContext(2, RoundingMode.DOWN);
}
