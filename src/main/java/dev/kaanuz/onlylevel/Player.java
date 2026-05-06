package dev.kaanuz.onlylevel;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.event.KeyEvent;

public class Player {
    private double x;
    private double y;
    private double width;
    private double height;
    private double velocityY;
    private boolean onGround;
    private int deaths;
    private boolean facingRight;
    private boolean justBounced = false;
    private double currentVX;

    public Player(double x, double y) {
        this.width = 20;
        this.height = 20;
        this.x = x;
        this.y = y;
        this.velocityY = 0;
        this.onGround = false;
        this.deaths = 0;
        this.facingRight = true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getDeaths() {
        return deaths;
    }

    public void incrementDeaths() {
        deaths++;
    }

    public void setPosition(double x, double y, Map map) {
        this.x = x;
        this.y = y;
        this.velocityY = 0;
        this.onGround = map.checkCollision(x, y - 1, map.getObstacles()); // check if in ground
        this.justBounced = false;
    }

    public void move(char direction, Stage stage) {
        double vx = stage.getVelocityX();
        double nextX;

        // Stage 5 - icy floor
        if (stage.getStageNumber() == 5) {
            if (direction == 'r') {
                currentVX += vx * 0.2;
            } else if (direction == 'l') {
                currentVX -= vx * 0.2;
            }

            // friction effect
            currentVX *= 0.95;

            nextX = x + currentVX;

            if (!stage.getMap().checkCollision(nextX, y, stage.getMap().getObstacles())) {
                x = nextX;
            }

            if (currentVX > 0.1) {
                facingRight = true;
            } else if (currentVX < -0.1) {
                facingRight = false;
            }
        }

        // normal floor
        else {
            if (direction == 'r') {
                nextX = x + vx;

                if (stage.getKeyCodes()[0] == KeyEvent.VK_LEFT) {
                    facingRight = false;
                } else {
                    facingRight = true;
                }
            } else if (direction == 'l') {
                nextX = x - vx;

                if (stage.getKeyCodes()[1] == KeyEvent.VK_RIGHT) {
                    facingRight = true;
                } else {
                    facingRight = false;
                }
            } else {
                nextX = x;
            }

            if (direction == 'r' || direction == 'l') {
                if (!stage.getMap().checkCollision(nextX, y, stage.getMap().getObstacles())) {
                    x = nextX;
                }
            }
        }

        // jump
        if (direction == 'u' && onGround && stage.getKeyCodes()[2] != -1) {
            velocityY = stage.getVelocityY();
            onGround = false;
        }
    }


    public void applyGravity(Stage stage, Map map) {
        double gravity = stage.getGravity();
        velocityY += gravity;
        double maxY = 600;
        double step;
        if (velocityY > 0) {
            step = 1;
        }else if ((velocityY < 0)){
            step = -1;
        }else {
            step = 0;
        }

        int steps = (int) Math.abs(velocityY);

        for (int i = 0; i < steps; i++) {
            double nextY = y + step;

            if (nextY > maxY) {
                velocityY = 0;
                onGround = false;
                return;
            }
            if (!map.checkCollision(x, nextY, map.getObstacles())) {
                y = nextY;
                onGround = false;
                justBounced = false;
            } else {
                velocityY = 0;
                onGround = true;

                if (stage.getUpCode() == -1 && !justBounced) {
                    velocityY = stage.getVelocityY();
                    justBounced = true;
                }
                return;
            }
        }

    }

    public void draw() {
        String img;
        if(facingRight){
            img = "misc/ElephantRight.png";
        } else {
            img = "misc/ElephantLeft.png";
        }
        StdDraw.picture(this.x , this.y , img, this.width, this.height);
    }
}
