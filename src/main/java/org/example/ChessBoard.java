package org.example;

public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // creating a field for game
    String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        // Проверка границ
        if (!checkPos(startLine) || !checkPos(startColumn) || !checkPos(endLine) || !checkPos(endColumn)) {
            return false;
        }

        ChessPiece movingPiece = board[startLine][startColumn];
        if (movingPiece == null) return false;

        // Проверка цвета игрока
        if (!nowPlayer.equals(movingPiece.getColor())) return false;

        // Может ли фигура ходить
        if (movingPiece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
            // временно делаем ход
            ChessPiece capturedPiece = board[endLine][endColumn];
            board[endLine][endColumn] = movingPiece;
            board[startLine][startColumn] = null;

            if (isKingUnderAttack(nowPlayer)) {
                board[startLine][startColumn] = movingPiece;
                board[endLine][endColumn] = capturedPiece;
                System.out.println("❗ Ход невозможен — ваш король окажется под шахом!");
                return false;
            }

            // если всё нормально
            this.nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
            if (isCheckmate(nowPlayer)) {
                System.out.println("♟ ШАХ И МАТ! Победил игрок " + (nowPlayer.equals("White") ? "Black" : "White") + "!");
            }
            return true;
        } else {
            System.out.println("⛔ Фигура не может так ходить.");
            return false;
        }
    }
    public boolean isCheckmate(String color) {
        if (!isKingUnderAttack(color)) return false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor().equals(color)) {
                    for (int toLine = 0; toLine < 8; toLine++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            if (piece.canMoveToPosition(this, i, j, toLine, toCol)) {

                                // ВРЕМЕННЫЙ ХОД
                                ChessPiece captured = board[toLine][toCol];
                                board[toLine][toCol] = piece;
                                board[i][j] = null;

                                boolean kingUnderAttack = isKingUnderAttack(color);

                                // ОТКАТ
                                board[i][j] = piece;
                                board[toLine][toCol] = captured;

                                if (!kingUnderAttack) {
                                    return false; // хоть один допустимый ход найден
                                }
                            }
                        }
                    }
                }
            }
        }

        return true; // нет ни одного хода, спасающего от шаха
    }

    public boolean isKingUnderAttack(String color) {
        int[] kingPos = findKing(color);
        if (kingPos == null) return true;

        int kingLine = kingPos[0];
        int kingCol = kingPos[1];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && !piece.getColor().equals(color)) {
                    if (piece.canMoveToPosition(this, i, j, kingLine, kingCol)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int[] findKing(String color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getSymbol().equals("K") && piece.getColor().equals(color)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public void printBoard() {  //print board in console
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public boolean castling0() {
        if (nowPlayer.equals("White")) {
            if (board[0][0] == null || board[0][4] == null) return false;
            if (board[0][0].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") && // check that King and Rook
                    board[0][1] == null && board[0][2] == null && board[0][3] == null) {              // never moved
                if (board[0][0].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        board[0][0].check && board[0][4].check &&
                        !new King("White").isUnderAttack(this, 0, 2)) { // check that position not in under attack
                    board[0][4] = null;
                    board[0][2] = new King("White");   // move King
                    board[0][2].check = false;
                    board[0][0] = null;
                    board[0][3] = new Rook("White");   // move Rook
                    board[0][3].check = false;
                    nowPlayer = "Black";  // next turn
                    return true;
                } else return false;
            } else return false;
        } else {
            if (board[7][0] == null || board[7][4] == null) return false;
            if (board[7][0].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") && // check that King and Rook
                    board[7][1] == null && board[7][2] == null && board[7][3] == null) {              // never moved
                if (board[7][0].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][0].check && board[7][4].check &&
                        !new King("Black").isUnderAttack(this, 7, 2)) { // check that position not in under attack
                    board[7][4] = null;
                    board[7][2] = new King("Black");   // move King
                    board[7][2].check = false;
                    board[7][0] = null;
                    board[7][3] = new Rook("Black");   // move Rook
                    board[7][3].check = false;
                    nowPlayer = "White";  // next turn
                    return true;
                } else return false;
            } else return false;
        }
    }
    public boolean castling7() {
        if (nowPlayer.equals("White")) {
            if (board[0][7] == null || board[0][4] == null) return false;
            if (board[0][7].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") && // check that King and Rook
                    board[0][6] == null && board[0][5] == null) {     // never moved
                if (board[0][7].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        board[0][7].check && board[0][4].check &&
                        !new King("White").isUnderAttack(this, 0, 6)) { // check that position not in under attack
                    board[0][4] = null;
                    board[0][6] = new King("White");   // move King
                    board[0][6].check = false;
                    board[0][7] = null;
                    board[0][5] = new Rook("White");   // move Rook
                    board[0][5].check = false;
                    nowPlayer = "Black";  // next turn
                    return true;
                } else return false;
            } else return false;
        } else {
            if (board[7][7] == null || board[7][4] == null) return false;
            if (board[7][7].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") && // check that King and Rook
                    board[7][6] == null && board[7][5] == null) {// never moved
                if (board[7][7].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][7].check && board[7][4].check &&
                        !new King("Black").isUnderAttack(this, 7, 6)) { // check that position not in under attack
                    board[7][4] = null;
                    board[7][6] = new King("Black");   // move King
                    board[7][6].check = false;
                    board[7][7] = null;
                    board[7][5] = new Rook("Black");   // move Rook
                    board[7][5].check = false;
                    nowPlayer = "White";  // next turn
                    return true;
                } else return false;
            } else return false;
        }
    }
}