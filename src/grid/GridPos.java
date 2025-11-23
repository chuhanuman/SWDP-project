package grid;

public record GridPos(int row, int col) {
    public boolean inBounds (int minR, int minC, int maxR, int maxC) {
        return row >= minR && row <= maxR && col >= minC && col <= maxC;
    }
}
