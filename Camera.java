
public class Camera 
{
	public int x;
	public int y;
	
	public Camera(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public static int cameraSet(int currentPos, int spriteSize, int screenSize, int boardSize)
    {
    	int cameraCord;
    	int halfScreenSize = screenSize/2;
    	if(currentPos < (halfScreenSize - spriteSize))
    	{
    		cameraCord = 0;
    	}
    	else if (currentPos > ((boardSize - halfScreenSize) - spriteSize))
    	{
    		cameraCord = boardSize - screenSize;
    	}
    	else
    	{
    		cameraCord = currentPos - (halfScreenSize - spriteSize);
    	}
    	return cameraCord;
    }
	
	//Checks if the entered sprite info is anywhere on screen. Checks all sides.
	public boolean onScreenCheck(int xCord, int yCord, int spriteSizeX, int spriteSizeY)
	{
		if (xCord > (x + 800)) //Sprite is Right of Camera
		{
			return false;
		}
		if ((xCord + spriteSizeX) < x) //Sprite is Left of Camera
		{
			return false;
		}
		if (yCord > (y + 600)) //Sprite is Down of Camera
		{
			return false;
		}
		if ((yCord + spriteSizeY) < y) //Sprite is Up of Camera
		{
			return false;
		}
		return true;
	}
}
