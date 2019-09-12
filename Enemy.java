import java.util.Random;

public class Enemy 
{
	public AnimationData animation;
	public int health;
	public int xCord;
	public int yCord;
	public int spriteSizeX;
	public int spriteSizeY;
	public boolean alive = true;
	public float secsUntilNextBehavior;
	public int currentBehavior;
	public int randomPointX;
	public int randomPointY;
	public Pokemon[] pokemon = new Pokemon[6];
	public int currentPokemon = 1;
	public int pokemonAlive;
	
	public Enemy(AnimationData currentAnimation, int x, int y, int spriteXSize, int spriteYSize)
	{
		animation = currentAnimation;
		health = 20;
		xCord = x;
		yCord = y;
		spriteSizeX = spriteXSize;
		spriteSizeY = spriteYSize;
		secsUntilNextBehavior = 5;
		currentBehavior = new Random().nextInt(3);
		randomPointX = new Random().nextInt(3200 - spriteSizeX);
		randomPointY = new Random().nextInt(2432 - spriteSizeY);
		
		pokemonAlive = 1; //to be changed
	}
	
	public AnimationData getAnimation()
	{
		return animation;
	}
	
	public boolean checkAlive()
	{
		if (health == 0)
		{
			return false;
		}
		return true;
	}
	
	public void updateHealth(Projectile current)
	{
		if (current.collisionCheck(xCord, yCord, spriteSizeX, spriteSizeY, alive))
		{
			health = health - 1;
		}
		if (health == 0)
		{
			alive = false;
		}
	}
	
	public void update(float dt, Player player)
	{
		secsUntilNextBehavior = secsUntilNextBehavior - (dt / 1000);
		if (secsUntilNextBehavior < 0)
		{
			currentBehavior = new Random().nextInt(3);
			if (currentBehavior == 2)
			{
				randomPointX = new Random().nextInt(3200 - spriteSizeX);
				randomPointY = new Random().nextInt(2432 - spriteSizeY);
			}
			secsUntilNextBehavior = 5;
		}
		
		if (currentBehavior == 0) // This is the code for the enemy moving towards the player
		{
			if (Math.abs(xCord - player.x) < .5 * dt)
			{
				xCord = player.x;
			}
			else
			{
				if (xCord < player.x)
				{
					xCord += .5 * dt;
				}
				else
				{
					xCord += -.5 * dt;
				}
			}
			if (Math.abs(yCord - player.y) < .5 * dt)
			{
				yCord = player.y;
			}
			else
			{
				if (yCord < player.y)
				{
					yCord += .5 * dt;
				}
				else
				{
					yCord += -.5 * dt;
				}
			}
		}
		else if (currentBehavior == 1) // This is the code for the enemy moving away from the player
		{
			if (xCord < player.x && xCord < (.5 * dt)) // If enemy would move past 0 in the x direction, then it sets the xCord to 0
			{
				xCord = 0;
			}
			else if (xCord < player.x && xCord + (-.5 * dt) >= 0)
			{
				xCord += -.5 * dt;
			}
			else if (xCord + spriteSizeX + (.5 * dt) <= 3200)
			{
				xCord += +.5 * dt;
			}
			
			if (yCord < player.y && yCord < (.5 * dt)) // If enemy would move past 0 in the y direction, then it sets the yCord to 0
			{
				yCord = 0;
			}
			else if (yCord < player.y && yCord + (-.5 * dt) >= 0)
			{
				yCord += -.5 * dt;
			}
			else if (yCord + spriteSizeY + (.5 * dt) <= 2432)
			{
				yCord += .5 * dt;
			}
		}
		else if (currentBehavior == 2) // This is the code for the enemy moving towards a random point
		{
			if (Math.abs(xCord - randomPointX) < .5 * dt)
			{
				xCord = randomPointX;
			}
			else
			{
				if (xCord < randomPointX)
				{
					xCord += .5 * dt;
				}
				else
				{
					xCord += -.5 * dt;
				}
			}
			if (Math.abs(yCord - randomPointY) < .5 * dt)
			{
				yCord = randomPointY;
			}
			else
			{
				if (yCord < randomPointY)
				{
					yCord += .5 * dt;
				}
				else
				{
					yCord += -.5 * dt;
				}
			}
		}
	}
	
	//Checks if the enemy is colliding with the player
	public boolean collisionCheck(Player player)
	{
		if (xCord + spriteSizeX < player.x) //Sprite is left of enemy
		{
			return false;
		}
		if (xCord > player.x + player.spriteSizeX) //Sprite is right of enemy
		{
			return false;
		}
		if (yCord + spriteSizeY < player.y) //Sprite is above enemy
		{
			return false;
		}
		if (yCord > player.y + player.spriteSizeY) //Sprite is below enemy
		{
			return false;
		}
		if (alive == false)
		{
			return false;
		}
		return true;
	}
	
	public void collisionResolve(Player player, String direction)
	{
		if (xCord + spriteSizeX < player.x == false && collisionCheck(player) && direction.equals("Right"))
		{
			xCord = xCord - (xCord + spriteSizeX - player.x) - 1;
		}
		if (xCord > player.x + player.spriteSizeX == false && collisionCheck(player) && direction.equals("Left"))
		{
			xCord = xCord + (player.x + player.spriteSizeX - xCord) + 1;
		}
		if (yCord + spriteSizeY < player.y == false && collisionCheck(player) && direction.equals("Down"))
		{
			yCord = yCord - (yCord + spriteSizeY - player.y) - 1;
		}
		if (yCord > player.y + player.spriteSizeY == false && collisionCheck(player) && direction.equals("Up"))
		{
			yCord = yCord + (player.y + player.spriteSizeY - yCord) + 1;
		}
	}
}
