package org.example;

public class Horse extends ChessPiece {


    public Horse(String color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line,
                                     int column, int toLine, int toColumn) {
        if (line < 0 || line > 7 || column < 0 || column > 7 ||
                toLine < 0 || toLine > 7 || toColumn < 0 || toColumn > 7) {
            return false;
        }
        if (line == toLine && column == toColumn) {
            return false;
        }

        int dLine = Math.abs(toLine - line);
        int dColumn = Math.abs(toColumn - column);
        if ((dLine == 2 && dColumn == 1) || (dLine == 1 && dColumn == 2)) {
            ChessPiece target = chessBoard.board[toLine][toColumn];
            // Можно пойти, если клетка пуста или на ней фигура другого цвета
            return target == null || !target.getColor().equals(this.color);
        }

        return false;
    }
        @Override
        public String getSymbol () {
            return "H";
        }

        @Override
        public String getColor () {
            return color;
        }

}
