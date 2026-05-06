package dev.kaanuz.onlylevel;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.event.KeyEvent;
import java.util.List;

public class Game {
    private int stageIndex;
    private final List<Stage> stages;
    private int deathNumber;
    private double gameTime;
    private double resetTime;
    private boolean resetGame;
    private boolean wasMousePressed = false;
    private Map map;
    private Player player;
    public Game(List<Stage> stages) {
        this.stageIndex = 0;
        this.stages = stages;
        this.deathNumber = 0;
        this.gameTime = 0;
        this.resetTime = System.currentTimeMillis();
        this.resetGame = false;
    }

    public void play() {
        wasMousePressed = false;
        while (stageIndex < stages.size()) {
            Stage currentStage = stages.get(stageIndex);
            player = new Player(115, 450);
            map = new Map(currentStage, player);
            currentStage.setMap(map);

            boolean stagePassed = false;

            while (!stagePassed) {
                if (resetGame) {
                    stageIndex = 0;
                    deathNumber = 0;
                    gameTime = 0;
                    resetTime = System.currentTimeMillis();

                    player = new Player(115, 450);
                    map = new Map(stages.get(stageIndex), player);
                    resetGame = false;
                    break;
                }

                StdDraw.clear();
                map.applyGravity();
                handleInput(map);

                if (map.isOnSpike()) {
                    player.incrementDeaths();
                    deathNumber++;
                    map.restartStage();
                }

                map.pressButton();

                if (map.changeStage()) {
                    if (stageIndex != 4) {
                        StdDraw.setPenColor(StdDraw.GREEN);
                        StdDraw.filledRectangle(400, 300, 300, 50);
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.text(400, 300, "You passed the stage. But is the level over?!");
                        StdDraw.show(2000);
                    }
                    stagePassed = true;
                }

                map.draw();

                // Timer update
                long now = System.currentTimeMillis();
                gameTime = (now - resetTime) / 1000.0;
                int minutes = (int) (gameTime / 60);
                int seconds = (int) (gameTime % 60);
                int millis = (int) ((gameTime - minutes * 60 - seconds) * 1000);
                String timerFormatted = String.format("%02d:%02d:%03d", minutes, seconds, millis);
                map.setTimerText(timerFormatted);

                StdDraw.show(16); // ~60 FPS
            }

            if (stagePassed) {
                stageIndex++;
            }
        }
        showEndScreen();
    }

    private void showEndScreen() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle(400, 300, 350, 100);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(400, 320, "CONGRATULATIONS! YOU FINISHED THE LEVEL");
        StdDraw.text(400, 280, "You finished with " + deathNumber + " deaths in " + formatTime(gameTime));
        StdDraw.text(400, 240, "Press 'A' to play again or 'Q' to quit");

        StdDraw.show();

        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
                stageIndex = 0;
                deathNumber = 0;
                resetTime = System.currentTimeMillis();
                play();
                return;
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
                System.exit(0);
            }
            StdDraw.show(100);
        }
    }

    private void handleInput(Map map) {
        Stage s = map.getStage();
        int[] keys = s.getKeyCodes();

        if (keys[0] != -1 && StdDraw.isKeyPressed(keys[0])) {
            map.movePlayer('r');
        }
        if (keys[1] != -1 && StdDraw.isKeyPressed(keys[1])) {
            map.movePlayer('l');
        }
        if (keys[2] != -1 && StdDraw.isKeyPressed(keys[2])) {
            map.movePlayer('u');
        }

        // Mouse controls
        double mx = StdDraw.mouseX();
        double my = StdDraw.mouseY();
        boolean isMousePressedNow = StdDraw.mousePressed();

        boolean isInsideRestart = (mx >= 510 && mx <= 590 && my >= 70 && my <= 100);
        if (isMousePressedNow && !wasMousePressed && isInsideRestart) {
            map.getPlayer().incrementDeaths();
            map.restartStage();
        }

        boolean isInsideReset = (mx >= 320 && mx <= 480 && my >= 5 && my <= 35);
        if (isMousePressedNow && !wasMousePressed && isInsideReset) {
            stageIndex = 0;
            deathNumber = 0;
            gameTime = 0;
            resetTime = System.currentTimeMillis();
            resetGame = true;
            return;
        }
        boolean isInsideHelp = (mx >= 210 && mx <= 290 && my >= 70 && my <= 100);
        if (isMousePressedNow && !wasMousePressed && isInsideHelp) {
            map.enableHelpText();
        }
        wasMousePressed = isMousePressedNow;
    }

    private String formatTime(double totalSeconds) {
        int minutes = (int) (totalSeconds / 60);
        int seconds = (int) (totalSeconds % 60);
        int milliseconds = (int) ((totalSeconds - minutes * 60 - seconds) * 1000);
        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }
}
