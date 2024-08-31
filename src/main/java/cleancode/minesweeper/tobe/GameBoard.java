package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.*;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.Arrays;
import java.util.List;

public class GameBoard {
    public static final int LAND_MINE_COUNT = 10;

    private final Cell[][] board;
    private final int rowSize, colSize;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        rowSize = gameLevel.getRowSize();
        colSize = gameLevel.getColSize();
        landMineCount = gameLevel.getLandMineCount();

        board = new Cell[rowSize][colSize];
    }

//    public GameBoard(int rowSize, int colSize) {
//        board = new Cell[rowSize][colSize];
//        this.rowSize = rowSize;
//        this.colSize = colSize;
//    }

    public Cell getCellAt(int row, int col) {
        return board[row][col];
    }


    public void initializeGame() {

        CellPositions cellPositions = CellPositions.from(board);

        initilizeEmptyCells(cellPositions);

//        for (int row = 0; row < rowSize; row++) {
//            for (int col = 0; col < colSize; col++) {
////                board[row][col] = Cell.create();
//                board[row][col] = new EmptyCell();
//            }
//        }

        List<CellPosition> landMinePositions = cellPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMinePositions);

//        for (int i = 0; i < landMineCount; i++) {
//            int col = new Random().nextInt(colSize);
//            int row = new Random().nextInt(rowSize);
//
//            turnOnLandMine(row, col);
//        }

        List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMinePositions);
        initializeNumberCells(numberPositionCandidates);
//        for (int row = 0; row < rowSize; row++) {
//            for (int col = 0; col < colSize; col++) {
//                CellPosition cellPosition = new CellPosition(row, col);
//                if (hasLandMineAt(cellPosition)) {
//                    continue;
//                }
//
//                int count = countSurroundingLandMines(cellPosition);
//
//                if (count == 0) continue;
//
//                updateLandMineCount(row, col, count);
//            }
//        }
    }

    private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
        for (CellPosition candidatePosition : numberPositionCandidates) {
            int count = countSurroundingLandMines(candidatePosition);
            if (count != 0) {
                updateCellAt(candidatePosition, new NumberCell(count));
            }
        }
    }

    private void initilizeEmptyCells(CellPositions cellPositions) {
        List<CellPosition> allPositions = cellPositions.getPositions();
        for (CellPosition position : allPositions) {
            updateCellAt(position, new EmptyCell());
        }
    }

    private void initializeLandMineCells(List<CellPosition> landMinePositions) {
        for (CellPosition position : landMinePositions) {
            updateCellAt(position, new LandMineCell());
        }
    }

    private void updateCellAt(CellPosition position, Cell cell) {
        board[position.getRowIndex()][position.getColIndex()] = cell;
    }

    //    public void plantFlag(int row, int col) {
//        board[row][col].plantFlag();
//    }
    public void plantFlagAt(CellPosition cellPosition) {
        Cell cell = getCellAt(cellPosition);
        cell.plantFlag();
    }

    private Cell getCellAt(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public boolean hasLandMineAt(CellPosition cellPosition) {
        Cell cell = getCellAt(cellPosition);
        return cell.isLandMine();
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

    public boolean isAllCellChecked2() {
        Cells cells = Cells.from(board);
        return cells.isAllChecked();
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        return !isValidCellPosition(cellPosition);
    }

    public boolean isValidCellPosition(CellPosition cellPosition) {
        return cellPosition.isRowWithin(rowSize)
            && cellPosition.isColWithin(colSize);
    }

    public void openWithSurroundingCells(CellPosition cellPosition) {
//        if (isInvalidCellPosition(cellPosition)) {
//            return;
//        }
        if (isOpenedAt(cellPosition)) {
            return;
        }

        openCellAt(cellPosition);

        if (hasLandMineAt(cellPosition)) {
            return;
        }

        if (hasLandMineCountAt(cellPosition))
            return;

        calculateSurroundingPositions(cellPosition)
                .forEach(this::openWithSurroundingCells);

//        for (RelativePosition relativePosition : RelativePosition.SURROUNDING_POSITIONS) {
//            if (canMoveToPostion(cellPosition, relativePosition)) {
//                CellPosition nextCellPosition = cellPosition.calculatePositionBy(relativePosition);
//                openWithSurroundingCells(nextCellPosition);
//            }
//        }
//        openWithSurroundingCells(row - 1, col - 1);
//        openWithSurroundingCells(row - 1, col);
//        openWithSurroundingCells(row - 1, col + 1);
//        openWithSurroundingCells(row, col - 1);
//        openWithSurroundingCells(row, col + 1);
//        openWithSurroundingCells(row + 1, col - 1);
//        openWithSurroundingCells(row + 1, col);
//        openWithSurroundingCells(row + 1, col + 1);
    }

    private static boolean canMoveToPostion(CellPosition cellPosition, RelativePosition relativePosition) {
        return cellPosition.canCalculatePositionBy(relativePosition);
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public String getSign(CellPosition cellPosition) {
        Cell cell = getCellAt(cellPosition);
        return cell.getSign();
    }

    private void turnOnLandMine(int row, int col) {
        Cell landMineCell = new LandMineCell();

//        landMineCell.turnOnLandMine();
        board[row][col] = landMineCell;
    }

    private void updateLandMineCount(int row, int col, int count) {
        //        numberCell.updateNearByLandMineCount(count);
        board[row][col] = new NumberCell(count);
    }

    private int countSurroundingLandMines(CellPosition cellPosition) {

        long count = calculateSurroundingPositions(cellPosition).stream()
                .filter(this::hasLandMineAt)
                .count();


//
//        int count = 0;
//        if (row - 1 >= 0 && col - 1 >= 0 && hasLandMineAt(row - 1, col - 1)) {
//            count++;
//        }
//        if (row - 1 >= 0 && hasLandMineAt(row - 1, col)) {
//            count++;
//        }
//        if (row - 1 >= 0 && col + 1 < colSize && hasLandMineAt(row - 1, col + 1)) {
//            count++;
//        }
//        if (col - 1 >= 0 && hasLandMineAt(row, col - 1)) {
//            count++;
//        }
//        if (col + 1 < colSize && hasLandMineAt(row, col + 1)) {
//            count++;
//        }
//        if (row + 1 < rowSize && col - 1 >= 0 && hasLandMineAt(row + 1, col - 1)) {
//            count++;
//        }
//        if (row + 1 < rowSize && hasLandMineAt(row + 1, col)) {
//            count++;
//        }
//        if (row + 1 < rowSize && col + 1 < colSize && hasLandMineAt(row + 1, col + 1)) {
//            count++;
//        }

        return (int)count;
    }

    private List<CellPosition> calculateSurroundingPositions(CellPosition cellPosition) {
        return RelativePosition.SURROUNDING_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(this::isValidCellPosition)
                .toList();
    }

    private Cell getCell(int row, int col) {
        return board[row][col];
    }

    private void openCellAt(CellPosition cellPosition) {
        Cell cell = getCellAt(cellPosition);
        cell.open();
    }

    private boolean isOpenedAt(CellPosition cellPosition) {
        Cell cell = getCellAt(cellPosition);
        return cell.isOpened();
    }

    private boolean hasLandMineCountAt(CellPosition cellPosition) {
        Cell cell = getCellAt(cellPosition);
        return cell.hasLandMineCount();
    }
}
