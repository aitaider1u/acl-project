package com.dala.mapz.gamebase.factories;

import java.util.Random;

public abstract class AbstractFactory
{
    protected Random random = new Random();

    public void setSeed(long seed) {
        this.random = new Random(seed);
    }
}
