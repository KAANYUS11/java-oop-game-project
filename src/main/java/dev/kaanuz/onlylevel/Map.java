package dev.kaanuz.onlylevel;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;

public class Map {
    private double doorSlideAmount = 0;
    private final double doorSlideSpeed = 2;
    private boolean doorFullyOpened = false;
    private String timerText = "";
    private boolean showHelp = false;
    private Stage stage;
    private Player player;
    private boolean wasOnButton = false;

    // Obstacle and map elements
    private int[][] obstacles = {
            {0, 120, 120, 270}, {0, 270, 168, 330}, {0, 330, 30, 480}, {0, 480, 180, 600},
            {180, 570, 680, 600}, {270, 540, 300, 570}, {590, 540, 620, 570}, {680, 510, 800, 600},
            {710, 450, 800, 510}, {740, 420, 800, 450}, {770, 300, 800, 420}, {680, 240, 800, 300},
            {680, 300, 710, 330}, {770, 180, 800, 240}, {0, 120, 800, 150}, {560, 150, 800, 180},
            {530, 180, 590, 210}, {530, 210, 560, 240}, {320, 150, 440, 210}, {350, 210, 440, 270},
            {220, 270, 310, 300}, {360, 360, 480, 390}, {530, 310, 590, 340}, {560, 400, 620, 430}
    };
    private int[] button = {400, 390, 470, 410};
    private int[] buttonFloor = {400, 390, 470, 400};
    private int[][] startPipe = { {115, 450, 145, 480}, {110, 430, 150, 450} };
    private int[][] exitPipe = { {720, 175, 740, 215}, {740, 180, 770, 210} };
    private int[][] spikes = {
            {30, 333, 50, 423, 90}, {121, 150, 207, 170, 180}, {441, 150, 557, 170, 180},
            {591, 180, 621, 200, 180}, {750, 301, 769, 419, 270}, {680, 490, 710, 510, 0},
            {401, 550, 521, 570, 0}
    };
    private int[] door = {685, 180, 700, 240};

    private int buttonPressNum = 0;
    private boolean isDoorOpen = false;

    public Map(Stage stage, Player player) {
        this.stage = stage;
        this.player = player;
        this.player.setPosition(startPipe[0][0], startPipe[0][1], this);
    }

    public void movePlayer(char direction) {
        player.move(direction, stage);
    }

    public void applyGravity() {
        player.applyGravity(stage, this);
    }

    public boolean checkCollision(double x, double y, int[][] boxes) {
        // check obstacle collision
        for (int[] box : boxes) {
            double playerLeft = x - player.getWidth() / 2;
            double playerRight = x + player.getWidth() / 2;
            double playerBottom = y - player.getHeight() / 2;
            double playerTop = y + player.getHeight() / 2;

            double boxLeft = box[0];
            double boxRight = box[2];
            double boxBottom = box[1];
            double boxTop = box[3];

            boolean horizontalOverlap = playerRight > boxLeft && playerLeft < boxRight;
            boolean verticalOverlap = playerTop > boxBottom && playerBottom < boxTop;

            if (horizontalOverlap && verticalOverlap) {
                return true;
            }
        }

        // check door collision
        if (!isDoorOpen) {
            double playerLeft = x - player.getWidth() / 2;
            double playerRight = x + player.getWidth() / 2;
            double playerBottom = y - player.getHeight() / 2;
            double playerTop = y + player.getHeight() / 2;

            double doorLeft = door[0];
            double doorRight = door[2];
            double doorBottom = door[1];
            double doorTop = door[3];

            boolean horizontalOverlap = playerRight > doorLeft && playerLeft < doorRight;
            boolean verticalOverlap = playerTop > doorBottom && playerBottom < doorTop;

            if (horizontalOverlap && verticalOverlap) {
                return true;
            }
        }

        return false;
    }


    public boolean isOnSpike() {
        return checkCollision(player.getX(), player.getY(), spikes);
    }

    public boolean changeStage() {
        if (!isDoorOpen) return false;
        return checkCollision(player.getX(), player.getY(), exitPipe);
    }


    public void pressButton() {
        boolean isOnButtonNow = checkCollision(player.getX(), player.getY(), new int[][]{button});

        if (isOnButtonNow && !wasOnButton) {
            buttonPressNum++;
        }

        if (stage.getStageNumber() == 4) {
            if (buttonPressNum >= 5) {
                isDoorOpen = true;
            }
        } else if (isOnButtonNow) {
            isDoorOpen = true;
        }

        wasOnButton = isOnButtonNow;
    }



    public void restartStage() {
        this.player.setPosition(startPipe[0][0], startPipe[0][1], this);
        buttonPressNum = 0;
        isDoorOpen = false;
        doorSlideAmount = 0;
        doorFullyOpened = false;
        wasOnButton = false;
    }


    public Stage getStage() {
        return stage;
    }

    public Player getPlayer() {
        return player;
    }


    public int[][] getObstacles() {
        return obstacles;
    }
    public void setTimerText(String t) {
        this.timerText = t;
    }


    public void draw() {
        // Draw timer background
        StdDraw.setPenColor(new Color(56, 93, 172));
        StdDraw.filledRectangle(400, 60, 400, 60);

        //Draw timer
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(100, 50, timerText);

        // Draw Texts and Buttons
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(250, 85, "Help");
        StdDraw.rectangle(250, 85, 40, 15);
        StdDraw.text(550, 85, "Restart");
        StdDraw.rectangle(550, 85, 40, 15);
        StdDraw.text(400, 20, "RESET THE GAME");
        StdDraw.rectangle(400, 20, 80, 15);
        StdDraw.text(700, 75, "Deaths: " + player.getDeaths());
        StdDraw.text(700, 50, "Stage: " + stage.getStageNumber());
        StdDraw.text(100, 75, "Level: 1");
        if (showHelp) {
            StdDraw.text(400, 85, "Help:");
            StdDraw.text(400, 55, stage.getHelp());
        } else {
            StdDraw.text(400, 85, "Clue:");
            StdDraw.text(400, 55, stage.getClue());
        }

        //Draw door
        if (isDoorOpen && !doorFullyOpened) {
            doorSlideAmount -= doorSlideSpeed;
            if (doorSlideAmount <= -(door[3] - door[1])) {
                doorFullyOpened = true;
            }
        }

        if (!doorFullyOpened) {
            double centerX = (door[0] + door[2]) / 2.0;
            double centerY = ((door[1] + door[3]) / 2.0) + doorSlideAmount;
            double width = (door[2] - door[0]) / 2.0;
            double height = (door[3] - door[1]) / 2.0;

            StdDraw.setPenColor(Color.GREEN);
            StdDraw.filledRectangle(centerX, centerY, width, height);
        }
        // Draw player
        player.draw();
        // Draw obstacles
        StdDraw.setPenColor(stage.getColor());
        for (int[] o : obstacles) {
            StdDraw.filledRectangle((o[0] + o[2]) / 2.0, (o[1] + o[3]) / 2.0,
                    (o[2] - o[0]) / 2.0, (o[3] - o[1]) / 2.0);
        }

        // Draw button
        boolean isPlayerOnButton = checkCollision(player.getX(), player.getY(), new int[][]{button});

        // red part of button
        if (!isPlayerOnButton) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.filledRectangle((button[0] + button[2]) / 2.0,
                    (button[1] + button[3]) / 2.0,
                    (button[2] - button[0]) / 2.0,
                    (button[3] - button[1]) / 2.0);
        }

        // grey part of button
        StdDraw.setPenColor(Color.GRAY);
        StdDraw.filledRectangle((buttonFloor[0] + buttonFloor[2]) / 2.0,
                (buttonFloor[1] + buttonFloor[3]) / 2.0,
                (buttonFloor[2] - buttonFloor[0]) / 2.0,
                (buttonFloor[3] - buttonFloor[1]) / 2.0);



        //draw spikes
        for (int[] spike : spikes) {
            double x = (spike[0] + spike[2]) / 2.0;
            double y = (spike[1] + spike[3]) / 2.0;
            double w = spike[2] - spike[0];
            double h = spike[3] - spike[1];
            double angle = spike[4];

            if (angle == 90 || angle == 270) {
                double temp = w;
                w = h;
                h = temp;
            }

            StdDraw.picture(x, y, "misc/Spikes.png", w, h, angle);
        }

        // Draw pipes
        StdDraw.setPenColor(Color.ORANGE);
        for (int[] p : startPipe) {
            StdDraw.filledRectangle((p[0] + p[2]) / 2.0, (p[1] + p[3]) / 2.0,
                    (p[2] - p[0]) / 2.0, (p[3] - p[1]) / 2.0);
        }
        for (int[] p : exitPipe) {
            StdDraw.filledRectangle((p[0] + p[2]) / 2.0, (p[1] + p[3]) / 2.0,
                    (p[2] - p[0]) / 2.0, (p[3] - p[1]) / 2.0);
        }
    }
    public void enableHelpText() {
        this.showHelp = true;
    }

}
