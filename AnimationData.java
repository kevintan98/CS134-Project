import com.jogamp.opengl.GL2;

public class AnimationData {
	public AnimationDef def;
	public int curFrame;
	public float secsUntilNextFrame;
	public String animationDirection;
	
	public AnimationData(AnimationDef def, int curFrame, float secsUntilNextFrame, String animationDirection)
	{
		this.def = def;
		this.curFrame = curFrame;
		this.secsUntilNextFrame = secsUntilNextFrame;
		this.animationDirection = animationDirection;
	}
	
	public void update(float deltaTime)
	{
		secsUntilNextFrame = secsUntilNextFrame - deltaTime;
		if (secsUntilNextFrame < 0)
		{
			curFrame++;
			if (curFrame == def.frames.length)
			{
				curFrame = 0;
			}
			secsUntilNextFrame = secsUntilNextFrame + def.frames[curFrame].frameTimeSecs;
		}
	}
	
	public void draw(GL2 gl, int x, int y, int camX, int camY, int spriteSizeX, int spriteSizeY)
	{
		JavaTemplate.glDrawSprite(gl, def.frames[curFrame].image, x - camX, y - camY, spriteSizeX, spriteSizeY);
	}
}
