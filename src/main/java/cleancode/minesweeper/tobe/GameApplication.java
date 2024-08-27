package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.Beginner;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class GameApplication {


//    public static final String FLAG_SIGN = "⚑";
//    public static final String LAND_MINE_SIGN = "☼";
//    public static final String CLOSED_CELL_SIGN = "□";
//    public static final String OPENED_CELL_SIGN = "■";

    public static void main(String[] args) {
        GameLevel gameLevel = new Beginner();
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        Minesweeper minesweeper = new Minesweeper(gameLevel, inputHandler, outputHandler);
        minesweeper.initialize();
        minesweeper.run();
    }

    /**
     * DIP (Dependency Inversion Principle)
     *
     * DI (Dependency Injection) - "3"
     *
     * IoC (Inversion of Control)
     *
     */

}
