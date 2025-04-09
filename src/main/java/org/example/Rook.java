package org.example;

public class Rook extends ChessPiece {

    public Rook(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line == toLine && column == toColumn) {

            return false;
        }
        if (line < 0 || line > 7 || column < 0 || column > 7 ||
                toLine < 0 || toLine > 7 || toColumn < 0 || toColumn > 7) {

            return false;
        }
        if (line != toLine && column != toColumn) {
            return false;
        }
        // Проверка пути по горизонтали
        if (line == toLine) {
            int step = (toColumn > column) ? 1 : -1;
            for (int c = column + step; c != toColumn; c += step) {
                if (chessBoard.board[line][c] != null) {
                    return false;
                }
            }
        }

        // Проверка пути по вертикали
        if (column == toColumn) {
            int step = (toLine > line) ? 1 : -1;
            for (int r = line + step; r != toLine; r += step) {
                if (chessBoard.board[r][column] != null) {
                    return false;
                }
            }
        }

        ChessPiece target = chessBoard.board[toLine][toColumn];

        return target == null || !target.getColor().equals(this.color);
    }


    @Override
    public String getSymbol() {
        return "R";
    }

}
