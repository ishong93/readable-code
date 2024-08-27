package cleancode.minesweeper.tobe.cell;

public abstract class Cell_Save2 {

    protected static final String FLAG_SIGN = "⚑";
    protected static final String UNCHECKED_SIGN = "□";

    protected boolean isFlagged;
    protected boolean isOpened;

//    public abstract void turnOnLandMine();
//
//    public abstract void updateNearByLandMineCount(int count);

    public abstract boolean isLandMine();

    public abstract boolean hasLandMineCount();

    public abstract String getSign();


}
