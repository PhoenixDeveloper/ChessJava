public class Bishop extends ChessPiece {

    public Bishop(String color) {
        super(color);
    }

    public String getColor() {
        return color;
    }

    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line == toLine && column == toColumn) { return false; }
        if (!(chessBoard.checkPos(toLine) && chessBoard.checkPos(toColumn))) { return false; }

        ChessPiece piece = chessBoard.board[toLine][toColumn];
        if (piece != null && piece.getColor().equals(color)) { return false; }

        int lineDelta = Math.abs(line - toLine);
        int columnDelta = Math.abs(column - toColumn);
        if (lineDelta == columnDelta) {
            boolean lineIncrement = line < toLine;
            boolean columnIncrement = column < toColumn;
            for (int delta = 1; delta < lineDelta; delta++) {
                if (chessBoard.board[lineIncrement ? line+delta : line-delta][columnIncrement ? column+delta : column-delta] != null) { return false; }
            }

            return true;
        } else {
            return false;
        }
    }

    public String getSymbol() {
        return "B";
    }
}
