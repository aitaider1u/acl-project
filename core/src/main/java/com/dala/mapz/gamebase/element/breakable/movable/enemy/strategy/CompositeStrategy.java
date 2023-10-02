package com.dala.mapz.gamebase.element.breakable.movable.enemy.strategy;
import com.dala.mapz.gamebase.element.breakable.movable.Enemy;

import java.util.concurrent.Callable;

public class CompositeStrategy extends Strategy{
    private Strategy firstStrat;
    private Strategy secondStrat;
    private Callable<Boolean> condition;
    public CompositeStrategy(Enemy enemy, Callable<Boolean> callable, Strategy firstStrat, Strategy secondStrat) {
        super(enemy);
        this.firstStrat = firstStrat;
        this.secondStrat = secondStrat;
        this.condition = callable;
    }

    @Override
    public void update() {
        try {
            if (condition.call()) firstStrat.update();
            else secondStrat.update();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
