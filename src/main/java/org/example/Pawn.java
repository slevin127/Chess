package org.example;

public class Pawn extends ChessPiece {

    public Pawn(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line < 0 || line > 7 || column < 0 || column > 7 ||
                toLine < 0 || toLine > 7 || toColumn < 0 || toColumn > 7) {
            return false;
        }

        if (line == toLine && column == toColumn) {
            return false;
        }

        ChessPiece target = chessBoard.board[toLine][toColumn];

        // Белая пешка
        if (color.equals("White")) {
            // Вперёд на 1 клетку
            if (column == toColumn && toLine == line + 1 && target == null) return true;

            // Первый ход на 2 клетки
            if (column == toColumn && line == 1 && toLine == 3 &&
                    chessBoard.board[5][column] == null && target == null) return true;

            // Взятие по диагонали
            if (Math.abs(toColumn - column) == 1 && toLine == line + 1 &&
                    target != null && !target.getColor().equals(this.color)) return true;
        }

        // Чёрная пешка
        if (color.equals("Black")) {
            // Вперёд на 1 клетку
            if (column == toColumn && toLine == line - 1 && target == null) return true;

            // Первый ход на 2 клетки
            if (column == toColumn && line == 6 && toLine == 4 &&
                    chessBoard.board[2][column] == null && target == null) return true;

            // Взятие по диагонали
            if (Math.abs(toColumn - column) == 1 && toLine == line - 1 &&
                    target != null && !target.getColor().equals(this.color)) return true;
        }

        return false;
    }


    @Override
    public String getSymbol() {
        return "P";
    }
}
