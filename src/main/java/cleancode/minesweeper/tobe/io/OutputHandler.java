package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;


public interface OutputHandler {

    void showGameStartComments();

    void showBoard (GameBoard board);

    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelectingCell();

    void showCommentForChoosingAction();

    void showExceptionMessage(GameException e);

    void showOutputMessage(String message);

}
