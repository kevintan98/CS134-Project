
public class FontDef {
	public int lineHeight;
	public GlyphDef[] glyphs;
	
	public FontDef(int lineHeight, GlyphDef[] inputGlyphs)
	{
		this.lineHeight = lineHeight;
		glyphs = inputGlyphs;
	}
	
	public GlyphDef[] getGlyphs()
	{
		return glyphs;
	}
}
