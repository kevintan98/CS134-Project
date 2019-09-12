
public class Pokemon {
	public String name;
	public int gl;
	public int[] size; //texture dimensions
	public PokemonMoves[] moves = new PokemonMoves[4];	
	public String status;
	public int level;
	public int hp;
	public int attack;
	public int defense;
	public int spAtk;
	public int spDef;
	public int speed;
	public int currentHp;
	public Pokemon(String name, int gl, int[] size){
		this.name = name;
		this.gl = gl;
		this.size = size;
		status = "normal";
	}
	public void setStats(int level, int hp, int attack, int defense, int spAtk, int spDef, int speed){
		this.level = level;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.spAtk = spAtk;
		this.spDef = spDef;
		this.speed = speed;
		currentHp = hp;
	}
}
