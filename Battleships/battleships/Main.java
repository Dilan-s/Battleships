import java.util.Random;
import java.util.Scanner;

public class Main {

  private static int NUMBER_OF_SHIPS = 7;

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Grid grid;
    if (args.length == 0) {
      grid = makeInitialGrid();
    } else {
      grid = makeRandomGrid();
    }
    System.out.println(grid);
    int numberOfAttacks = 0;
    while (!grid.areAllSunk()) {
      System.out.println(grid.toPlayerString());
      System.out.println("What is your move?");
      String token = input.next();
      Coordinate c = Util.parseCoordinate(token);
      if (grid.wouldAttackSucceed(c)) {
        System.out.println("Direct Hit!");
      } else {
        System.out.println("Miss!");
      }
      grid.attackCell(c);
      numberOfAttacks++;
    }
    System.out.println("Number of attacks is: " + numberOfAttacks);
  }

  private static Grid makeInitialGrid() {
    Grid grid = new Grid();

    String[] coords = {"A7", "B1", "B4", "D3", "F7", "H1", "H4"};
    int[] sizes = {2, 4, 1, 3, 1, 2, 5};
    boolean[] isDowns = {false, true, true, false, false, true, false};

    for (int i = 0; i < coords.length; i++) {
      Coordinate c = Util.parseCoordinate(coords[i]);
      grid.placeShip(c, sizes[i], isDowns[i]);
    }

    return grid;
  }

  private static Grid makeRandomGrid() {
    Random rng = new Random();
    Grid grid = new Grid();
    for (int i = 0; i < NUMBER_OF_SHIPS; i++) {
      char row = (char) (rng.nextInt(9) + 65);
      int column = rng.nextInt(9);
      String cordString = String.valueOf(row) + String.valueOf(column);
      int length = rng.nextInt(9);
      boolean isDowns;
      if (rng.nextInt(2) == 1) {
        isDowns = true;
      } else {
        isDowns = false;
      }
      if (grid.canPlace(Util.parseCoordinate(cordString), length, isDowns)) {
        grid.placeShip(Util.parseCoordinate(cordString), length, isDowns);
      } else {
        i--;
      }
    }
    return grid;
  }
}
