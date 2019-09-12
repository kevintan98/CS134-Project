
public class FrameDef {
	public int image;
	public float frameTimeSecs;
	
	public FrameDef(int sprite, float secsPerSprite)
	{
		image = sprite;
		frameTimeSecs = secsPerSprite;
	}
}
