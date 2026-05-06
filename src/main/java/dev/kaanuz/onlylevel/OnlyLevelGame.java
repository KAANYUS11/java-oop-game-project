package dev.kaanuz.onlylevel;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class OnlyLevelGame {
    public static void main(String[] args) {
        // StdDraw screen settings
        StdDraw.setCanvasSize(800, 600);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 600);

        // Stages List
        ArrayList<Stage> stages = new ArrayList<>();

        // Stage 1: Normal controls
        stages.add(new Stage(-0.45, 3.65, 10, 1,
                KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Arrow keys are required", "Arrow keys move player ,press button and enter the second pipe"));

        // Stage 2: Reversed directions
        stages.add(new Stage(-0.45, 3.65, 10, 2,
                KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP,
                "Not always straight forward", "Right and left buttons reversed"));

        // Stage 3: Constantly jumping
        stages.add(new Stage(-2, 3.65, 24, 3,
                KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, -1,
                "A bit bouncy here", "You jump constantly"));

        // Stage 4: press the button 5 times
        stages.add(new Stage(-0.45, 3.65, 10, 4,
                KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Never gonna give you up", "Press button 5 times"));

        // Stage 5: icy floor
        stages.add(new Stage(
                -0.45, 3.65, 10, 5,
                KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "It's slippery!", "Tap the keys gently. The floor is icy."));

        // start the game
        Game game = new Game(stages);
        game.play();

    }
}
