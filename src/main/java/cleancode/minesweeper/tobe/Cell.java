package cleancode.minesweeper.tobe;

public class Cell {

    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGN = "□";
    private static final String EMPTY_SIGN = "■";

    private final String sign;
    private int nearbyLandMineCount;
    private boolean isLandMine;
    private boolean isFlagged;
    private boolean isOpened;

    // Cell이 가진 속성 : 근처 지뢰 숫자, 지뢰 여부
    // Cell의 상태 : 깃발 유무, 열렸다 / 닫혔다, 사용자가 확인함
    public Cell(String sign, int nearbyLandMineCount, boolean isLandMine, boolean isFlagged, boolean isOpened) {
        this.sign = sign;
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }

    public static Cell of(String sign, int nearbyLandMineCount, boolean isLandMine, boolean isFlagged, boolean isOpened) {
        return new Cell(sign, nearbyLandMineCount, isLandMine, isFlagged, isOpened);
    }

//    public static Cell ofFlag() {
//        return new Cell(FLAG_SIGN, 0, false);
//    }
//
//    public static Cell ofLandMine() {
//        return new Cell(LAND_MINE_SIGN, 0, false);
//    }
//
//    public static Cell ofClosedCell() {
//        return new Cell(UNCHECKED_SIGN, 0, false);
//    }
//
//    public static Cell ofOpenedCell() {
//        return new Cell(EMPTY_SIGN, 0, false, false, false);
//    }
//
//    public static Cell ofNearbyLandMineCount(int count) {
//        return new Cell(String.valueOf(count), count, false, false, false);
//    }

    public static Cell create() {
        return new Cell("", 0, false, false, false);
    }

    public boolean equalsSign(String sign) {
        return this.sign.equals(sign);
    }

    public boolean doesNotEqualSign(String sign) {
        return !equalsSign(sign);
    }

    public boolean isClosed() {
        return UNCHECKED_SIGN.equals(sign);
    }

    public boolean isNotClosed() {
        return !isClosed();
    }

    public void turnOnLandMine() {
        this.isLandMine = true;
    }

    public void plantFlag() {
        this.isFlagged = true;
    }

    public void open() {
        this.isOpened = true;
    }

    public void updateNearByLandMineCount(int count) {
        this.nearbyLandMineCount = count;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public boolean isLandMine() {
        return this.isLandMine;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean hasLandMineCount() {
        return this.nearbyLandMineCount != 0;
    }

    public String getSign() {
        if (isOpened) {
            if (isLandMine) {
                return LAND_MINE_SIGN;
            }

            if (hasLandMineCount()) {
                return String.valueOf(nearbyLandMineCount);
            }

            return EMPTY_SIGN;
        }

        if (isFlagged) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;
    }
}
