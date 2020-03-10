import java.util.Arrays;

public class Util {

  public static final int LETTER_A = 65;

  private static int letterToIndex(char letter) {
    return ((int) letter - LETTER_A);
  }

  private static int numberToIndex(char number) {
    return Integer.parseInt(Character.toString(number));
  }

  public static Coordinate parseCoordinate(String s) {
    assert s.length() == 2;
    return new Coordinate(letterToIndex(s.charAt(0)), numberToIndex(s.charAt(1)));
  }

  public static Piece hideShip(Piece piece) {
    if (piece == Piece.SHIP) {
      return Piece.WATER;
    }
    return piece;
  }

  public static void hideShips(Piece[][] grid) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        grid[i][j] = hideShip(grid[i][j]);
      }
    }
  }

  public static Piece[][] deepClone(Piece[][] input) {
    Piece[][] output = new Piece[input.length][];
    for (int i = 0; i < input.length; i++) {
      output[i] = Arrays.copyOf(input[i], input[i].length);
    }
    return output;
  }
}
