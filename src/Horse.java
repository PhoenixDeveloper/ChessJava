public class Horse extends ChessPiece {

    public Horse(String color) {
        super(color);
    }

    public String getColor() {
        return color;
    }

    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line == toLine && column == toColumn) { return false; }
        if (!(chessBoard.checkPos(toLine) && chessBoard.checkPos(toColumn))) { return false; }

        int lineDelta = Math.abs(line - toLine);
        int columnDelta = Math.abs(column - toColumn);

        if ((lineDelta == 1 && columnDelta == 2) || (lineDelta == 2 && columnDelta == 1)) {
            ChessPiece piece = chessBoard.board[toLine][toColumn];
            return piece == null || !piece.getColor().equals(color);
        } else {
            return false;
        }
    }

    public String getSymbol() {
        return "H";
    }
}
