package cleancode.minesweeper.tobe.cell;

public class NumberCell implements Cell {

    private final int nearbyLandMineCount;

    private final CellState cellState = CellState.initialize();

    public NumberCell(int nearbyLandMineCount) {
        this.nearbyLandMineCount = nearbyLandMineCount;
    }

//    @Override
//    public void turnOnLandMine() {
//        throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
//    }

//
//    @Override
//    public void updateNearByLandMineCount(int count) {
//        nearbyLandMineCount = count;
//    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return true;
    }

    @Override
    public String getSign() {
        if (cellState.isOpened()) {
            return String.valueOf(nearbyLandMineCount);
        }

        if (cellState.isFlagged()) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;
    }

    @Override
    public void plantFlag() {
        cellState.plantFlag();
    }

    @Override
    public void open() {
        cellState.open();
    }

    @Override
    public boolean isChecked() {
        return cellState.isChecked();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }

    @Override
    public boolean isFlagged() {
        return cellState.isFlagged();
    }
}
