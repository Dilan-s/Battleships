import javax.swing.ScrollPaneLayout;

public class Grid {

  private static final int WIDTH = 10;
  private static final int HEIGHT = 10;

  private final Piece[][] grid = new Piece[HEIGHT][WIDTH];

  public Grid() {
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        grid[i][j] = Piece.WATER;
      }
    }
  }

  public boolean canPlace(Coordinate c, int size, boolean isDown) {
    int y = c.getRow();
    int x = c.getColumn();
    for (int i = 0; i < size; i++) {
      if (isDown){
        if (grid[y+i][x] != Piece.WATER){
          return false;
        }
      }
      if (!isDown){
        if (grid[y][x+i] != Piece.WATER){
          return false;
        }
      }
    }
    return true;
  }

  public void placeShip(Coordinate c, int size, boolean isDown) {
    if (!canPlace(c, size, isDown)){
      throw new IllegalArgumentException();
    }
    int y = c.getRow();
    int x = c.getColumn();
    for (int i = 0; i < size; i++) {
      if (isDown){
        grid[y+i][x] = Piece.SHIP;
      }
      if (!isDown)
        grid[y][x+i] = Piece.SHIP;
    }
  }

  public boolean wouldAttackSucceed(Coordinate c) {
    int x = c.getColumn();
    int y = c.getRow();

    return grid[y][x] == Piece.SHIP;
  }

  public void attackCell(Coordinate c) {
    int x = c.getColumn();
    int y = c.getRow();

    if (wouldAttackSucceed(c)){
      grid[y][x] = Piece.DAMAGED_SHIP;
    } else {
      grid[y][x] = Piece.MISS;
    }
  }

  public boolean areAllSunk() {
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        if (grid[i][j] == Piece.SHIP){
          return false;
        }
      }
    }
    return true;
  }

  public String toPlayerString() {
    Piece[][] playerGrid = new Piece[HEIGHT][WIDTH];
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        playerGrid[i][j] = grid[i][j];
      }
    }
    Util.hideShips(playerGrid);
    return renderGrid(playerGrid);
  }

  @Override
  public String toString() {
    return renderGrid(grid);
  }

  private static String renderGrid(Piece[][] grid) {
    StringBuilder sb = new StringBuilder();
    sb.append(" 0123456789\n");
    for (int i = 0; i < grid.length; i++) {
      sb.append((char) ('A' + i));
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == null) {
          return "!";
        }
        switch (grid[i][j]) {
          case SHIP:
            sb.append('#');
            break;
          case DAMAGED_SHIP:
            sb.append('*');
            break;
          case MISS:
            sb.append('o');
            break;
          case WATER:
            sb.append('.');
            break;
        }

      }
      sb.append('\n');
    }

    return sb.toString();
  }
}
