public class Pawn extends ChessPiece {

    public Pawn(String color) {
        super(color);
    }

    public String getColor() {
        return color;
    }

    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line == toLine && column == toColumn) { return false; }
        if (!(chessBoard.checkPos(toLine) && chessBoard.checkPos(toColumn))) { return false; }
        if ((color.equals("White") && toLine < line) || (color.equals("Black") && toLine > line)) { return false; }

        boolean canMove;
        if (color.equals("White") && line == 1) {
            canMove = toLine - line <= 2 && column == toColumn;
        } else if (color.equals("Black") && line == 6) {
            canMove = line - toLine <= 2 && column == toColumn;
        } else {
            canMove = Math.abs(toLine - line) == 1 && column == toColumn;
        }

        ChessPiece piece = chessBoard.board[toLine][toColumn];

        boolean canEat = piece != null && !piece.getColor().equals(color) && Math.abs(toLine - line) == 1 && Math.abs(toColumn - column) == 1;

        if (canMove || canEat) {
            return piece == null || !piece.getColor().equals(color);
        } else {
            return false;
        }
    }

    public String getSymbol() {
        return "P";
    }
}
