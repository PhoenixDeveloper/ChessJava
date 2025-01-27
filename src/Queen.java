public class Queen extends ChessPiece {

    public Queen(String color) {
        super(color);
    }

    public String getColor() {
        return color;
    }

    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        boolean canMoveLikeRock = new Rook(color).canMoveToPosition(chessBoard, line, column, toLine, toColumn);
        boolean canMoveLikeBishop = new Bishop(color).canMoveToPosition(chessBoard, line, column, toLine, toColumn);
        return canMoveLikeRock || canMoveLikeBishop;
    }

    public String getSymbol() {
        return "Q";
    }
}
