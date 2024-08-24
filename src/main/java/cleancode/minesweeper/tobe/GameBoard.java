package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {
    public static final int LAND_MINE_COUNT = 10;

    private final Cell[][] board;
    private final int rowSize, colSize;

    public GameBoard(int rowSize, int colSize) {
        board = new Cell[rowSize][colSize];
        this.rowSize = rowSize;
        this.colSize = colSize;
    }

    public void initializeGame() {
//        int rowSize = board.length;
//        int colSize = board[0].length;

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                Cell cell = getCell(row, col);
                cell = Cell.create();
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(colSize);
            int row = new Random().nextInt(rowSize);
            turnOnLandMine(row, col);
//            board[row][col].turnOnLandMine();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {

                if (isLandMineCell(row, col)) {
                    // NEARBY_LAND_MINE_COUNTS[row][col] = 0;
                    continue;
                }

                int count = countSurroundingLandMines(row, col);
//                NEARBY_LAND_MINE_COUNTS[row][col] = count;
                updateLandMineCount(row, col, count);
//                board[row][col].updateNearByLandMineCount(count);
            }
        }
    }

    public void plantFlag(int row, int col) {
        Cell cell = getCell(row, col);
        cell.plantFlag();
    }

    public boolean isLandMineCell(int row, int col) {
//        return LAND_MINES[selectedRowIndex][selectedColIndex];
        Cell cell = getCell(row, col);
        return cell.isLandMine();
    }

    public void openWithSurroundingCells(int row, int col) {
        if (row < 0 || row >= rowSize || col < 0 || col >= colSize) {
            return;
        }
//        if (BOARD[row][col].isNotClosed()) {
        if (isOpenedCell(row, col)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }

        openCell(row, col);
//        if (NEARBY_LAND_MINE_COUNTS[row][col] != 0) {
        if (doesCellHaveLandMineCount(row, col))
//        {
//            BOARD[row][col].open();
//            BOARD[row][col] = Cell.ofNearbyLandMineCount(NEARBY_LAND_MINE_COUNTS[row][col]);
            return;
//        } else {
//            BOARD[row][col] = Cell.ofOpenedCell();
//        }

        openWithSurroundingCells(row - 1, col - 1);
        openWithSurroundingCells(row - 1, col);
        openWithSurroundingCells(row - 1, col + 1);
        openWithSurroundingCells(row, col - 1);
        openWithSurroundingCells(row, col + 1);
        openWithSurroundingCells(row + 1, col - 1);
        openWithSurroundingCells(row + 1, col);
        openWithSurroundingCells(row + 1, col + 1);
    }

    public boolean isAllCellChecked() {
//        Stream<String[]> stringArrayStream = Arrays.stream(BOARD);
//        Stream<String> stringStream = stringArrayStream
//                .flatMap(stringArr -> {
//                    Stream<String> stringStream2 = Arrays.stream(stringArr);
//                    return stringStream2;
//                });
//
//        return stringStream
//                .noneMatch(cell -> cell.equals(CLOSED_CELL_SIGN));
        return Arrays.stream(board) // Stream<String[]>
                .flatMap(Arrays::stream) // Stream<String> (flatmap), Steram<Stream<String>> (map)
//                .noneMatch(cell -> cell.isClosed());
//                .noneMatch(Cell::isUnchecked);
                .allMatch(Cell::isChecked);
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public String getSign(int row, int col) {
        Cell cell = getCell(row, col);
        return cell.getSign();
    }

    private void turnOnLandMine(int row, int col) {
        Cell cell = getCell(row, col);
        cell.turnOnLandMine();
    }

    private int countSurroundingLandMines(int row, int col) {
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < colSize && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    private void updateLandMineCount(int row, int col, int count) {
        Cell cell = getCell(row, col);
        cell.updateNearByLandMineCount(count);
    }

    private Cell getCell(int row, int col) {
        return board[row][col];
    }

    private void openCell(int row, int col) {
        Cell cell = getCell(row, col);
        cell.open();
    }

    private boolean isOpenedCell(int row, int col) {
        Cell cell = board[row][col];
        return cell.isOpened();
    }

    private boolean doesCellHaveLandMineCount(int row, int col) {
        Cell cell = getCell(row, col);
        return cell.hasLandMineCount();
    }

}
