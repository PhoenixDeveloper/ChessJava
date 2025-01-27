import java.util.Arrays;

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
        if (checkPos(startLine) && checkPos(startColumn)) {

            if (!nowPlayer.equals(board[startLine][startColumn].getColor())) return false;

            if (isCheck() && !board[startLine][startColumn].getSymbol().equals("K") && !canSafeKingIfAttack(endLine, endColumn)) {
                System.out.println("Вам необходимо устранить угрозу королю");
                return false;
            }

            if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                ChessPiece piece = board[startLine][startColumn];
                board[endLine][endColumn] = piece; // if piece can move, we moved a piece
                board[startLine][startColumn] = null; // set null to previous cell
                if (isCheck()) { // move doesn't lead to check
                    System.out.println("Этот ход приведет к шаху. Так нельзя.");
                    board[startLine][startColumn] = piece;
                    board[endLine][endColumn] = null;
                    return false;
                }
                if ("K, R".contains(piece.getSymbol())) {
                    board[endLine][endColumn].check = false;
                }
                if (piece.getSymbol().equals("P")) {
                    if (piece.getColor().equals("White") && endLine == 7) {
                        board[endLine][endColumn] = new Queen(piece.color);
                    } else if (piece.getColor().equals("Black") && endLine == 0) {
                        board[endLine][endColumn] = new Queen(piece.color);
                    }
                }
                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";

                return true;
            } else return false;
        } else return false;
    }

    public boolean isCheck() {
        for (int line = 0; line < board.length; line++) {
            for (int column = 0; column < board[line].length; column++) {
                ChessPiece piece = board[line][column];

                if (piece != null && piece.getColor().equals(nowPlayer) && piece.getSymbol().equals("K") && !((King) piece).isSafePlace(this, line, column)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isMate() {
        for (int line = 0; line < board.length; line++) {
            for (int column = 0; column < board[line].length; column++) {
                ChessPiece piece = board[line][column];

                if (piece != null && piece.getColor().equals(nowPlayer) && piece.getSymbol().equals("K") && !((King) piece).isSafePlace(this, line, column)) {
                    for (int possibleLine = line - 1; possibleLine <= line + 1; possibleLine++) {
                        for (int possibleColumn = column - 1; possibleColumn <= column + 1; possibleColumn++) {
                            if (!checkPos(possibleLine) || !checkPos(possibleColumn)) { continue; }
                            ChessPiece possiblePiece = board[possibleLine][possibleColumn];
                            if (possiblePiece != null && possiblePiece.getColor().equals(nowPlayer)) { continue; }

                            board[possibleLine][possibleColumn] = piece;
                            board[line][column] = null;
                            if (((King) board[possibleLine][possibleColumn]).isSafePlace(this, possibleLine, possibleColumn)) {
                                board[possibleLine][possibleColumn] = possiblePiece;
                                board[line][column] = piece;
                                return false;
                            }
                            board[possibleLine][possibleColumn] = possiblePiece;
                            board[line][column] = piece;
                        }
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public boolean canSafeKing() {
        for (int enemyLine = 0; enemyLine < board.length; enemyLine++) {
            for (int enemyColumn = 0; enemyColumn < board[enemyLine].length; enemyColumn++) {
                if (canSafeKingIfAttack(enemyLine, enemyColumn)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canSafeKingIfAttack(int line, int column) {
        ChessPiece piece = board[line][column];

        if (piece != null && piece.getColor().equals(nowPlayer)) { return false; }

        if (!canSomeOneMoveTo(line, column, nowPlayer)) { return false; }

        boolean canSafe = false;
        board[line][column] = new Pawn(nowPlayer);
        canSafe = !isCheck();
        board[line][column] = piece;
        return canSafe;
    }

    private boolean canSomeOneMoveTo(int line, int column, String color) {
        for (int checkLine = 0; checkLine < board.length; checkLine++) {
            for (int checkColumn = 0; checkColumn < board[checkLine].length; checkColumn++) {
                ChessPiece piece = board[checkLine][checkColumn];
                if (piece != null && piece.getColor().equals(color) && piece.canMoveToPosition(this, checkLine, checkColumn, line, column)) {
                    if (piece.getSymbol().equals("K") && !((King) piece).isSafePlace(this, line, column)) { continue; }
                    return true;
                }
            }
        }

        return false;
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
                        new King("White").isSafePlace(this, 0, 2)) { // check that position not in under attack
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
                        new King("Black").isSafePlace(this, 7, 2)) { // check that position not in under attack
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
                    board[0][5] == null && board[0][6] == null) {              // never moved
                if (board[0][7].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        board[0][7].check && board[0][4].check &&
                        new King("White").isSafePlace(this, 0, 6)) { // check that position not in under attack
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
                    board[7][5] == null && board[7][6] == null) {              // never moved
                if (board[7][7].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][7].check && board[7][4].check &&
                        new King("Black").isSafePlace(this, 7, 6)) { // check that position not in under attack
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
