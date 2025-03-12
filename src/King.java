public class King extends ChessPiece {

    public King(String color) {
        super(color);
    }

    public String getColor() {
        return color;
    }

    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line == toLine && column == toColumn) { return false; }
        if (!(chessBoard.checkPos(toLine) && chessBoard.checkPos(toColumn))) { return false; }

        if (Math.abs(line - toLine) <= 1 && Math.abs(column - toColumn) <= 1 && isSafePlace(chessBoard, toLine, toColumn)) {
            ChessPiece piece = chessBoard.board[toLine][toColumn];
            return piece == null || !piece.getColor().equals(color);
        } else {
            return false;
        }
    }

    public boolean isSafePlace(ChessBoard board, int line, int column) {
        for (int checkLine = 0; checkLine < board.board.length; checkLine++) {
            for (int checkColumn = 0; checkColumn < board.board[checkLine].length; checkColumn++) {
                ChessPiece piece = board.board[checkLine][checkColumn];
                if (piece == null) { continue; }
                board.board[checkLine][checkColumn] = this;
                if (!piece.getColor().equals(color) && piece.canMoveToPosition(board, checkLine, checkColumn, line, column)) {
                    board.board[checkLine][checkColumn] = piece;
                    return false;
                }
                board.board[checkLine][checkColumn] = piece;
            }
        }

        return true;
    }

    public String getSymbol() {
        return "K";
    }
}
