package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;

public class Minesweeper implements GameInitializable, GameRunnable {

    private final GameBoard gameBoard;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
//    private final static ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
//    private final static ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Minesweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        gameBoard = new GameBoard(gameLevel);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
    }

    @Override
    public void run() {
        outputHandler.showGameStartComments();
//        gameBoard.initializeGame();

        while (true) {
            try {
                outputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    outputHandler.showGameWinningComment();
                    break;
                }

                if (doesUserLooseTheGame()) {
                    outputHandler.showGameLosingComment();
                    break;
                }

                CellPosition cellPosition = getCellInputFromUser();
//                String cellInput = getCellInputFromUser();
                String actionInput = getActionInputFromUser();
//                actOnCell(cellInput, actionInput);
                actOnCell(cellPosition, actionInput);

            } catch (GameException e) {
                outputHandler.showExceptionMessage(e);
            } catch (Exception e) {
                outputHandler.showOutputMessage("프로그램에 문제가 생겼습니다.");
                e.printStackTrace();
            }
        }
    }
    private void actOnCell(CellPosition cellPosition, String actionInput) {
//    private void actOnCell(String cellInput, String actionInput) {
//        int selectedRowIndex = boardIndexConverter.getSelectedRowIndex(cellInput);
//        int selectedColIndex = boardIndexConverter.getSelectedColIndex(cellInput);

        if (doesUserChooseToPlantFlag(actionInput)) {
//            gameBoard.plantFlag(selectedRowIndex, selectedColIndex);
            gameBoard.plantFlagAt(cellPosition);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(actionInput)) {
            if (gameBoard.hasLandMineAt(cellPosition)) {
                gameBoard.openWithSurroundingCells(cellPosition);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openWithSurroundingCells(cellPosition);
            checkIfGameIsOver();
            return;
        }

        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

//    private boolean isAllCellOpened() {
//        boolean isAllOpened = true;
//        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
//            for (int col = 0; col < BOARD_COL_SIZE; col++) {
//                if (BOARD[row][col].isClosed()) {
//                    isAllOpened = false;
//                }
//            }
//        }
//        return isAllOpened;
//    }

    private boolean doesUserChooseToOpenCell(String actionInput) {
        return actionInput.equals("1");
    }

    private boolean doesUserChooseToPlantFlag(String actionInput) {
        return actionInput.equals("2");
    }


    private String getActionInputFromUser() {
        outputHandler.showCommentForChoosingAction();
        return inputHandler.getLineInput();
    }

    private CellPosition getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        CellPosition cellPosition = inputHandler.getCellPositionFromUser();

        if (gameBoard.isInvalidCellPosition(cellPosition)) {
            throw new GameException("잘못된 좌표를 선택하셨습니다.");
        }

        return cellPosition;
    }

//    private String getCellInputFromUser() {
//        outputHandler.showCommentForSelectingCell();
//        return inputHandler.getLineInput();
//    }

    private boolean doesUserLooseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

}
