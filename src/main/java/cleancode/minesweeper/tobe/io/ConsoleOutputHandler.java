package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameException;
import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.position.CellPosition;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements OutputHandler{

    @Override
    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!") ;
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void showBoard(GameBoard board) {
        List<String> alphabets = generateColAlphabets(board);
        String joiningAlphabets = String.join(" ", alphabets);

        System.out.println("    " + joiningAlphabets);

//        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < board.getRowSize(); row++) {  // BOARD_ROW_SIZE
            System.out.printf("%2d  ", row + 1);
            for (int col = 0; col < board.getColSize(); col++) {  // BOARD_COL_SIZE
                CellPosition cellPosition = CellPosition.of(row, col);
                System.out.print(board.getSign(cellPosition) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void showGameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    @Override
    public void showGameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    @Override
    public void showCommentForSelectingCell() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    @Override
    public void showCommentForChoosingAction() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void showExceptionMessage(GameException e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void showOutputMessage(String message) {
        System.out.println(message);
    }

    private static List<String> generateColAlphabets(GameBoard board) {
        return IntStream.range(0, board.getColSize())
                .mapToObj(index -> (char) ('a' + index))
                .map(c -> c.toString())
                .toList();
    }

}
