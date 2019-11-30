package com.example.game.Game3;

import java.util.Random;

public class Game3HardStrategy implements Game3Strategy {
    private int[] enemyAttack = {9, 9, 10, 10, 10, 12, 12, 12, 13, 13, 13, 15};

    private int[] enemyDefend = {9, 9, 10, 10, 10, 12, 12, 12, 13, 13, 13, 15};

    @Override
    public int enemyAttack() {
        int damageIndex = new Random().nextInt(enemyAttack.length);
        return enemyAttack[damageIndex];
    }

    @Override
    public int enemyDefend() {
        int damageIndex = new Random().nextInt(enemyDefend.length);
        return enemyDefend[damageIndex];
    }

    @Override
    public int playerAttack() {
        return 12;
    }

    @Override
    public int playerDefend() {
        return 8;
    }
}
