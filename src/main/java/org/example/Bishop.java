package org.example;

public class Bishop extends ChessPiece {
    public Bishop(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
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
        if (Math.abs(toLine - line) != Math.abs(toColumn - column)) {
            return false;
        }

        int stepLine = (toLine > line) ? 1 : -1;
        int stepCol = (toColumn > column) ? 1 : -1;

        int currentLine = line + stepLine;
        int currentCol = column + stepCol;


        while (currentLine != toLine && currentCol != toColumn) {
            if (chessBoard.board[currentLine][currentCol] != null) {
                return false;
            }
            currentLine += stepLine;
            currentCol += stepCol;
        }


        ChessPiece target = chessBoard.board[toLine][toColumn];

        return target == null || !target.getColor().equals(this.color);
    }


    @Override
    public String getSymbol() {
        return "B";
    }
}
