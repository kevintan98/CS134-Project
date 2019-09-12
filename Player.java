
public class Player 
{
	public AnimationData[] animations;
	public int x;
	public int y;
	public int spriteSizeX;
	public int spriteSizeY;
	public int prevX;
	public int prevY;
	public String direction = "Right";
	public Pokemon[] pokemon = new Pokemon[6];
	public int currentPokemon = 1;
	public int pokemonAlive;
	
	public Player(AnimationData[] animations, int x, int y, int spriteSizeX, int spriteSizeY)
	{
		this.animations = animations;
		this.x = x;
		this.y = y;
		this.spriteSizeX = spriteSizeX;
		this.spriteSizeY = spriteSizeY;
		
		pokemonAlive = 1; // to be changed
	}
	
	//Checks if the entered enemy is colliding with the player
	public boolean collisionCheck(int xCord, int yCord, int enemyWidth, int enemyHeight, boolean enemyAlive)
	{
		if (x + spriteSizeX < xCord) //Sprite is left of enemy
		{
			return false;
		}
		if (x > xCord + enemyWidth) //Sprite is right of enemy
		{
			return false;
		}
		if (y + spriteSizeY < yCord) //Sprite is above enemy
		{
			return false;
		}
		if (y > yCord + enemyHeight) //Sprite is below enemy
		{
			return false;
		}
		if (enemyAlive == false)
		{
			return false;
		}
		return true; 
	}
	
	//Checks for collision. -1 means no collision. 1 means normal collision. 2 means one-way collision. 3 means tall grass.
	public int collisionCheckBack(int[][] background)
	{
		int collision = -1;
		int limitX = ((x + spriteSizeX) / 32) + 1;
		int limitY = ((y + spriteSizeY) / 32) + 1;
		if (limitX > 100)
		{
			limitX = 100;
		}
		if (limitY > 76)
		{
			limitY = 76;
		}
		
		for (int i = (y) / 32; i < limitY; i++)
		{
			for (int j = (x) / 32; j < limitX; j++)
			{
				if (background[i][j] == 11 || background[i][j] == 12 || background[i][j] == 13)
				{
					collision = 2;
				}
				if (background[i][j] == 2 || background[i][j] == 3 || background[i][j] == 4 || background[i][j] == 4 || background[i][j] == 5 || background[i][j] == 6 
						|| background[i][j] == 7 ||background[i][j] == 8 ||background[i][j] == 9 || background[i][j] == 10)
				{
					collision = 1;
				}
				if (background[i][j] == 1)
				{
					collision = 3;
				}
			}
		}
		return collision;
	}
	
	public void collisionResolveBack(int[][] background, String direction)
	{
		if (collisionCheckBack(background) == 1 && direction.equals("Right"))
		{
			x = x - ((x + spriteSizeX)  % 32) - 1;
		}
		if (collisionCheckBack(background) == 1 && direction.equals("Left"))
		{
			x = x + (32 - (x % 32));
		}
		if (collisionCheckBack(background) == 1 && direction.equals("Down"))
		{
			y = y - ((y + spriteSizeY) % 32) - 1;
		}
		if (collisionCheckBack(background) == 1 && direction.equals("Up"))
		{
			y = y + (32 - (y % 32));
		}
		if (collisionCheckBack(background) == 2 && direction.equals("Up"))
		{
			y = y + (32 - (y % 32));
		}
		if (collisionCheckBack(background) == 2 && direction.equals("Left"))
		{
			x = x + (32 - (x % 32));
		}
		if (collisionCheckBack(background) == 2 && direction.equals("Right"))
		{
			x = x - (x % 32);
		}
	}
	
	//If the enemy is colliding then it moves the player to the edge of the enemy based off the direction
	public void collisionResolve(int xCord, int yCord, int enemyWidth, int enemyHeight, boolean enemyAlive, String direction)
	{
		if (x + spriteSizeX < xCord == false && collisionCheck(xCord, yCord, enemyWidth, enemyHeight, enemyAlive) && direction.equals("Right"))
		{
			x = x - (x + spriteSizeX - xCord) - 1;
		}
		if (x > xCord + enemyWidth == false && collisionCheck(xCord, yCord, enemyWidth, enemyHeight, enemyAlive) && direction.equals("Left"))
		{
			x = x + (xCord + enemyWidth - x) + 1;
		}
		if (y + spriteSizeY < yCord == false && collisionCheck(xCord, yCord, enemyWidth, enemyHeight, enemyAlive) && direction.equals("Down"))
		{
			y = y - (y + spriteSizeY - yCord) - 1;
		}
		if (y > yCord + enemyHeight == false && collisionCheck(xCord, yCord, enemyWidth, enemyHeight, enemyAlive) && direction.equals("Up"))
		{
			y = y + (yCord + enemyHeight - y) + 1;
		}
	}
	
	public void setDirection(String currentDirection)
	{
		direction = currentDirection;
	}
	
	public AnimationData getCurrentAnimation()
	{
		for (AnimationData current : animations)
		{
			if (direction.equals(current.animationDirection))
			{
				return current;
			}
		}
		
		return animations[0];
	}
	
}
