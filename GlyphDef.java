
public class GlyphDef {
	public String texName;
	public int width;
	public int image;
	
	public GlyphDef(String texName, int width, int image)
	{
		this.texName = texName;
		this.width = width;
	}
	
	public int getImage()
	{
		return image;
	}
}
