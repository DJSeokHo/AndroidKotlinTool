package com.swein.androidkotlintool.main.examples.instagreamlike.ig

class InvalidMaxSpansException(maxSpanSize: Int) :
    RuntimeException("Invalid layout spans: $maxSpanSize. Span size must be at least 1.")