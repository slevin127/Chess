package org.example;

public class King extends ChessPiece {
    public King(String color) {
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


        int dLine = Math.abs(toLine - line);
        int dColumn = Math.abs(toColumn - column);
        if (dLine <= 1 && dColumn <= 1) {
            // Проверка: нельзя вставать на фигуру своего цвета
            ChessPiece target = chessBoard.board[toLine][toColumn];
            return target == null || !target.getColor().equals(this.color);

        }

        return false;
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = chessBoard.board[i][j];


                if (piece != null && !piece.getColor().equals(this.color)) {
                    if (piece.getSymbol().equals("K")) continue;
                    if (piece.canMoveToPosition(chessBoard, i, j, line, column)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
