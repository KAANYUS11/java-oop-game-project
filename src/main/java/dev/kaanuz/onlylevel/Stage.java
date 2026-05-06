package dev.kaanuz.onlylevel;

import java.awt.Color;
import java.util.Random;

public class Stage {

    // Data Fields
    private int stageNumber;
    private double gravity;
    private double velocityX;
    private double velocityY;
    private int rightCode;
    private int leftCode;
    private int upCode;
    private String clue;
    private String help;
    private Color color;
    private Map map;

    // Constructor
    public Stage(double gravity, double velocityX, double velocityY, int stageNumber,
                 int rightCode, int leftCode, int upCode, String clue, String help) {
        this.gravity = gravity;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.stageNumber = stageNumber;
        this.rightCode = rightCode;
        this.leftCode = leftCode;
        this.upCode = upCode;
        this.clue = clue;
        this.help = help;

        Random rand = new Random();
        this.color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    // Getters and Setters
    public int getStageNumber() {
        return stageNumber;
    }
    public void setMap(Map map){
        this.map = map;
    }
    public Map getMap(){
        return map;
    }

    public double getGravity() {
        return gravity;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public int[] getKeyCodes() {
        return new int[]{rightCode, leftCode, upCode};
    }

    public String getClue() {
        return clue;
    }

    public String getHelp() {
        return help;
    }

    public Color getColor() {
        return color;
    }
    public int getUpCode() {
        return upCode;
    }

}
