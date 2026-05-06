# Only Level

Only Level is a small Java platformer where the player guides an elephant through five variations of the same level. Each stage changes the movement rules or objective, turning a simple map into a puzzle about controls, timing, and state.

This project was built for **CMPE160: Introduction to Object-Oriented Programming** as a course project focused on Java classes, object interaction, event handling, and simple game loops.

## Features

- Five-stage platformer progression with changing rules.
- Keyboard-driven movement and jumping.
- Stage-specific mechanics: reversed controls, automatic jumping, repeated button presses, and slippery movement.
- Spike collision, stage restart, full game reset, death counter, and timer.
- In-game clue/help text and clickable UI controls.
- Sprite-based player and spike rendering with Princeton `StdDraw`.

## Tech Stack

- Java 17
- Maven
- Princeton Java StdLib / `StdDraw`
- Java AWT keyboard and mouse events

## Controls and Flow

- `Right Arrow`: move right.
- `Left Arrow`: move left.
- `Up Arrow`: jump when jumping is enabled for the current stage.
- `Help`: click the in-game Help button to reveal the stage hint.
- `Restart`: click the in-game Restart button to restart the current stage.
- `RESET THE GAME`: click to return to stage 1 and reset timer/death count.
- End screen: press `A` to play again or `Q` to quit.

Some stages intentionally change these controls. For example, one stage reverses left/right movement, another makes the player jump automatically, and the final stage adds icy movement.

## How to Run

Requirements:

- JDK 17 or newer
- Maven 3.8 or newer

Build the project:

```bash
mvn clean compile
```

Run the game:

```bash
mvn exec:java
```

The game loads image assets from the `misc/` directory, so run the command from the repository root.

## Project Structure

```text
.
├── misc/                         # Game image assets
├── src/main/java/dev/kaanuz/onlylevel/
│   ├── OnlyLevelGame.java         # Application entry point and stage setup
│   ├── Game.java                  # Main game loop and input handling
│   ├── Map.java                   # Level geometry, collisions, drawing, UI
│   ├── Player.java                # Player movement, gravity, sprite rendering
│   └── Stage.java                 # Stage configuration and rule data
├── pom.xml                        # Maven build and dependency configuration
└── README.md
```

The original course submission PDF is kept locally under `_archive/` and ignored by Git.

## Skills Gained/Practised

- Object-oriented decomposition into game, map, player, and stage models.
- Real-time game loop design with frame updates.
- Collision detection with rectangular hitboxes.
- Keyboard and mouse input handling.
- State management across stages, restarts, timers, and deaths.
- Dependency management with Maven.


