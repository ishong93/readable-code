package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

import java.util.Arrays;

public class Minesweeper {
    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
//    private static final Cell[][] board = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];
//    private static final Integer[][] NEARBY_LAND_MINE_COUNTS = new Integer[BOARD_ROW_SIZE][BOARD_COL_SIZE];
//    private static final boolean[][] LAND_MINES = new boolean[BOARD_ROW_SIZE][BOARD_COL_SIZE];

    private final GameBoard gameBoard = new GameBoard(BOARD_ROW_SIZE, BOARD_COL_SIZE);
    private final static ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final static ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public void run() {
        consoleOutputHandler.showGameStartComments();
        gameBoard.initializeGame();

        while (true) {
            try {
                consoleOutputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameWinningComment();
                    break;
                }

                if (doesUserLooseTheGame()) {
                    consoleOutputHandler.printGameLosingComment();
                    break;
                }

                // Scanner scanner = new Scanner(System.in);
                String cellInput = getCellInputFromUser();
                String actionInput = getActionInputFromUser();
                actOnCell(cellInput, actionInput);
            } catch (AppException e) {
                consoleOutputHandler.printExceptionMessage(e);
            } catch (Exception e) {
                consoleOutputHandler.printOutputMessage("프로그램에 문제가 생겼습니다.");
                e.printStackTrace();
            }
        }
    }

    private void actOnCell(String cellInput, String actionInput) {
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooseToPlantFlag(actionInput)) {
            gameBoard.plantFlag(selectedRowIndex, selectedColIndex);
//            BOARD[selectedRowIndex][selectedColIndex].plantFlag();
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(actionInput)) {
            if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
//                BOARD[selectedRowIndex][selectedColIndex] = Cell.ofLandMine();
//                BOARD[selectedRowIndex][selectedColIndex].open();
                gameBoard.openWithSurroundingCells(selectedRowIndex, selectedColIndex);
                changeGameStatusToLose();
                return;
            }

//            open(selectedRowIndex, selectedColIndex);
            gameBoard.openWithSurroundingCells(selectedRowIndex, selectedColIndex);
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

    private int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    private String getActionInputFromUser() {
        consoleOutputHandler.printCommentForChoosingAction();
//        return SCANNER.nextLine();
        return consoleInputHandler.getLineInput();
    }

    private static String getCellInputFromUser() {
        consoleOutputHandler.printCommentForSelectingCell();
//        return SCANNER.nextLine();
        return consoleInputHandler.getLineInput();
    }

    private boolean doesUserLooseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex > BOARD_ROW_SIZE) {
            throw new AppException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char cellInputCol) {
        switch (cellInputCol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new AppException ("잘못된 입력입니다.");
        }
    }


}
