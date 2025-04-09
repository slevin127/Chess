package org.example;

public class Queen extends ChessPiece {

    public Queen(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверка выхода за границы
        if (line < 0 || line > 7 || column < 0 || column > 7 ||
                toLine < 0 || toLine > 7 || toColumn < 0 || toColumn > 7) {
            return false;
        }

        // Нельзя остаться на месте
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Диагональное движение
        if (Math.abs(toLine - line) == Math.abs(toColumn - column)) {
            int stepLine = (toLine > line) ? 1 : -1;
            int stepCol = (toColumn > column) ? 1 : -1;

            int r = line + stepLine;
            int c = column + stepCol;

            while (r != toLine && c != toColumn) {
                if (chessBoard.board[r][c] != null) return false;
                r += stepLine;
                c += stepCol;
            }
        }
        // Вертикальное движение
        else if (column == toColumn) {
            int step = (toLine > line) ? 1 : -1;
            for (int r = line + step; r != toLine; r += step) {
                if (chessBoard.board[r][column] != null) return false;
            }
        }
        // Горизонтальное движение
        else if (line == toLine) {
            int step = (toColumn > column) ? 1 : -1;
            for (int c = column + step; c != toColumn; c += step) {
                if (chessBoard.board[line][c] != null) return false;
            }
        }
        // Недопустимое направление
        else {
            return false;
        }

        // Проверка конечной клетки
        ChessPiece target = chessBoard.board[toLine][toColumn];
        return target == null || !target.getColor().equals(this.color);
    }


    @Override
    public String getSymbol() {
        return "Q";
    }
}
