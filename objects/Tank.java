package objects;

import graphics.*;
import utilities.*;

public class Tank extends GameObject implements Movable
{
  private Commands.Direction curDirection;

  public static final char objectSymbol = '*';

  public Tank(int rowPos, int colPos)
  {
    super(rowPos, colPos, 5, 5, Color.ANSI_BLUE);
    curDirection = Commands.Direction.UP;
    drawingSymbol = objectSymbol;
  }

  public Tank(int rowPos, int colPos, Commands.Direction direction)
  {
    this(rowPos, colPos);
    curDirection = direction;
  }

  public Commands.Direction getCurDirection()
  {
    return curDirection;
  }

  public void move(Commands.Direction cmd, Renderer mainRenderer)
  {
    //Update the location or the current direction
    //based on the command received from the player
    switch (curDirection)
    {
      case UP:
        switch (cmd)
        {
          case UP:
            if(! isCollision(mainRenderer, getRowPos() - 1, getColPos()))
              setRowPos(getRowPos() - 1);
            break;
          case DOWN:
            if(! isCollision(mainRenderer, getRowPos() + 1, getColPos()))
              setRowPos(getRowPos() + 1);
            break;
          case LEFT:
            curDirection = Commands.Direction.LEFT;
            break;
          case RIGHT:
            curDirection = Commands.Direction.RIGHT;
            break;
          default:
            break;
        }
        break;
      case DOWN:
        switch (cmd)
        {
          case UP:
            if(! isCollision(mainRenderer, getRowPos() - 1, getColPos()))
              setRowPos(getRowPos() - 1);
            break;
          case DOWN:
            if(! isCollision(mainRenderer, getRowPos() + 1, getColPos()))
              setRowPos(getRowPos() + 1);
            break;
          case LEFT:
            curDirection = Commands.Direction.LEFT;
            break;
          case RIGHT:
            curDirection = Commands.Direction.RIGHT;
            break;
          default:
            break;
        }
        break;
      case LEFT:
        switch (cmd)
        {
          case UP:
            curDirection = Commands.Direction.UP;
            break;
          case DOWN:
            curDirection = Commands.Direction.DOWN;
            break;
          case LEFT:
            if(! isCollision(mainRenderer, getRowPos(), getColPos() - 1))
              setColPos(getColPos() - 1);
            break;
          case RIGHT:
            if(! isCollision(mainRenderer, getRowPos(), getColPos() + 1))
              setColPos(getColPos() + 1);
            break;
          default:
            break;
        }
        break;
      case RIGHT:
        switch (cmd)
        {
          case UP:
            curDirection = Commands.Direction.UP;
            break;
          case DOWN:
            curDirection = Commands.Direction.DOWN;
            break;
          case LEFT:
            if(! isCollision(mainRenderer, getRowPos(), getColPos() - 1))
              setColPos(getColPos() - 1);
            break;
          case RIGHT:
            if(! isCollision(mainRenderer, getRowPos(), getColPos() + 1))
              setColPos(getColPos() + 1);
            break;
          default:
            break;
        }
        break;
      default:
        break;
    }
  }

  public Bullet fire()
  {
    int row = 0, col = 0;

    switch (curDirection)
    {
      case UP:
        row = getRowPos() - 1;
        col = getColPos() + 2;
        break;
      case DOWN:
        row = getRowPos() + 5;
        col = getColPos() + 2;
        break;
      case LEFT:
        row = getRowPos() + 2;
        col = getColPos() - 1;
        break;
      case RIGHT:
        row = getRowPos() + 2;
        col = getColPos() + 5;
        break;
      default:
        break;
    }
    Bullet newBullet = new Bullet(row, col, this.curDirection);
    return newBullet;
  }

  // Overriding draw() in Object class to make tank shape
  public char[][] draw()
  {
    //allocate a bitmap for the tank
    char[][] bitmap = super.draw();

    //update the bitmap accordingly
    //based on the current direction
    switch (curDirection)
    {
      case UP:
        bitmap[0][0] = bitmap[0][4] = ' ';
        bitmap[1][0] = bitmap[1][1] = bitmap[1][3] = bitmap[1][4] = ' ';
        break;
      case DOWN:
        bitmap[3][0] = bitmap[3][1] = bitmap[3][3] = bitmap[3][4] = ' ';
        bitmap[4][0] = bitmap[4][4] = ' ';
        break;
      case LEFT:
        bitmap[0][0] = bitmap[0][1] = ' ';
        bitmap[1][1] = bitmap[3][1] = ' ';
        bitmap[4][0] = bitmap[4][1] = ' ';
        break;
      case RIGHT:
        bitmap[0][3] = bitmap[0][4] = ' ';
        bitmap[1][3] = bitmap[3][3] = ' ';
        bitmap[4][3] = bitmap[4][4] = ' ';
        break;
      default:
        break;
    }
    return bitmap;
  }

  private boolean isCollision(Renderer mainRenderer, int rowPos, int colPos) {
    // Collision check with the borders
    if((rowPos + this.getHeight() > mainRenderer.getHeight()) || (colPos + this.getWidth() > mainRenderer.getWidth()) || rowPos < 0 || colPos < 0)
      return true;
    // Collision check with obstacles in the map
    else {
      char[][] bitmap = mainRenderer.getBitMap();

      // Removes the player tank from the bitmap before checking for collision with enemy tanks
      for (int i = 0; i < this.getHeight(); i++)
        for (int j = 0; j < this.getWidth(); j++)
          bitmap[this.getRowPos() + i][this.getColPos() + j] = ' ';

      for (int i = 0; i < this.getHeight(); i++)
        for (int j = 0; j < this.getWidth(); j++)
         if(bitmap[rowPos + i][colPos + j] != ' ') return true;
    }
    return false;
  }

}
