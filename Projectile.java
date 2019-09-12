
public class Projectile 
{
	public int x;
	public int y;
	public int spriteSizeX;
	public int spriteSizeY;
	public AnimationData animation;
	public boolean destroyed = false;
	
	public Projectile(int x, int y, int spriteXSize, int spriteYSize, AnimationData animation)
	{
		this.x = x;
		this.y = y;
		spriteSizeX = spriteXSize;
		spriteSizeY = spriteYSize;
		this.animation = animation;
	}
	
	public void update(int deltaTimeMS, Projectile current, int boardSizeX) 
	{
		if (current.x < boardSizeX - current.spriteSizeX)
		{
			current.x = current.x + ((1 * deltaTimeMS) / 2);
		}
		else
		{
			current.x = boardSizeX - current.spriteSizeX;
			destroyed = true;
		}
	}
	
	public boolean collisionCheck(int xCord, int yCord, int enemySizeX, int enemySizeY, boolean alive)
	{
		if (x > (xCord + enemySizeX)) //Sprite is Right of Camera
		{
			return false;
		}
		if ((x + spriteSizeX) < xCord) //Sprite is Left of Camera
		{
			return false;
		}
		if (y > (yCord + enemySizeY)) //Sprite is Down of Camera
		{
			return false;
		}
		if ((y + spriteSizeY) < yCord) //Sprite is Up of Camera
		{
			return false;
		}
		if (alive == false)
		{
			return false;
		}
		destroyed = true;
		return true;
	}
}
