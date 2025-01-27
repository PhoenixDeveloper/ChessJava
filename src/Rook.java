public class Rook extends ChessPiece {

    public Rook(String color) {
        super(color);
    }

    public String getColor() {
        return color;
    }

    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line == toLine && column == toColumn) { return false; }
        if (!(chessBoard.checkPos(toLine) && chessBoard.checkPos(toColumn))) { return false; }

        if (line == toLine || column == toColumn) {
            if (line != toLine) {
                int minLine = Math.min(line, toLine);
                int maxLine = Math.max(line, toLine);

                for (int checkLine = minLine + 1; checkLine < maxLine; checkLine++) {
                    if (chessBoard.board[checkLine][column] != null) { return false; }
                }
            } else {
                int minColumn = Math.min(column, toColumn);
                int maxColumn = Math.max(column, toColumn);

                for (int checkColumn = minColumn + 1; checkColumn < maxColumn; checkColumn++) {
                    if (chessBoard.board[line][checkColumn] != null) { return false; }
                }
            }

            ChessPiece piece = chessBoard.board[toLine][toColumn];
            return piece == null || !piece.getColor().equals(color);
        } else {
            return false;
        }
    }

    public String getSymbol() {
        return "R";
    }
}
