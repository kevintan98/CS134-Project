import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.opengl.*;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class JavaTemplate {
    // Set this to true to make the game loop exit.
    private static boolean shouldExit;

    // The previous frame's keyboard state.
    private static boolean kbPrevState[] = new boolean[256];

    // The current frame's keyboard state.
    private static boolean kbState[] = new boolean[256];

    // Position of the sprite.
    //private static int[] spritePos = new int[] { 20, 20 };
    //private static int[] spritePos2 = new int[] { 700 , 500 };
    //private static int[] spritePos3 = new int[] { 100 , 500 };

    private static int pointerLocation;
    private static int battleScreen;
    
    // Texture for the sprite.
    
    private static int grassTex;
    private static int tallGrassTex;
    private static int treeTex;
    private static int waterTex;
    private static int topLeftWater;
    private static int topMiddleWater;
    private static int topRightWater;
    private static int middleLeftWater;
    private static int middleRightWater;
    private static int bottomLeftWater;
    private static int bottomMiddleWater;
    private static int bottomRightWater;
    private static int oneWayLeft;
    private static int oneWayMid;
    private static int oneWayRight;
    
    private static int karp1;
    private static int karp2;
    private static int karp3;
    private static int arceus;
    
    private static int playerStandingDown;
    private static int playerStandingUp;
    private static int playerStandingRight;
    private static int playerStandingLeft;
    private static int playerWalkingDown1;
    private static int playerWalkingDown2;
    private static int playerWalkingUp1;
    private static int playerWalkingUp2;
    private static int playerWalkingRight1;
    private static int playerWalkingRight2;
    private static int playerWalkingLeft1;
    private static int playerWalkingLeft2;
    private static int font0;
    private static int font1;
    private static int font2;
    private static int font3;
    private static int font4;
    private static int font5;
    private static int font6;
    private static int font7;
    private static int font8;
    private static int font9;
    private static int battleGrassTex;
    private static int battleFBPRBoxTex;
    private static int battleBackTextTex;
    private static int battleHolderTex;
    private static int pointerTex;
    private static int hpBarPlayerTex;
    private static int hpBarEnemyTex;
    private static int battleGrass2Tex;
    
    // Size of the sprite.
    private static int[] battleGrass2Size = new int[2];
    private static int[] hpBarPlayerSize = new int[2];
    private static int[] hpBarEnemySize = new int[2];
    private static int[] pointerSize = new int[2];
    private static int[] karpSize = new int[2];
    private static int[] arceusSize = new int[2];
    private static int[] playerSize = new int[2];
    private static int[] grassTexSize = new int[2];
    private static int[] tallGrassTexSize = new int[2];
    private static int[] treeTexSize = new int[2];
    private static int[] font0Size = new int[2];
    private static int[] font1Size = new int[2];
    private static int[] font2Size = new int[2];
    private static int[] font3Size = new int[2];
    private static int[] font4Size = new int[2];
    private static int[] font5Size = new int[2];
    private static int[] font6Size = new int[2];
    private static int[] font7Size = new int[2];
    private static int[] font8Size = new int[2];
    private static int[] font9Size = new int[2];
    private static int[] battleGrassSize = new int[2];
    private static int[] battleFBPRBoxSize = new int[2];
    private static int[] battleBackTextSize = new int[2];
    private static int[] battleHolderSize = new int[2];
    
    
    private static AnimationData karpAnimation;
    private static AnimationData walkingDownAnimation;
    private static AnimationData walkingUpAnimation;
    private static AnimationData walkingRightAnimation;
    private static AnimationData walkingLeftAnimation;
    private static AnimationData standingDownAnimation;
    private static AnimationData standingUpAnimation;
    private static AnimationData standingRightAnimation;
    private static AnimationData standingLeftAnimation;
    private static Player player;
    private static Enemy enemy1;
    private static Enemy enemy2;
    private static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    
    public static boolean inBattle;

    public static void main(String[] args) {
        GLProfile gl2Profile;

        try {
            // Make sure we have a recent version of OpenGL
            gl2Profile = GLProfile.get(GLProfile.GL2);
        }
        catch (GLException ex) {
            System.out.println("OpenGL max supported version is too low.");
            System.exit(1);
            return;
        }

        // Create the window and OpenGL context.
        GLWindow window = GLWindow.create(new GLCapabilities(gl2Profile));
        window.setSize(800, 600);
        window.setTitle("Karp Kingdom");
        window.setVisible(true);
        window.setDefaultCloseOperation(
                WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.isAutoRepeat()) {
                    return;
                }
                kbState[keyEvent.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.isAutoRepeat()) {
                    return;
                }
                kbState[keyEvent.getKeyCode()] = false;
            }
        });

        // Setup OpenGL state.
        window.getContext().makeCurrent();
        GL2 gl = window.getGL().getGL2();
        gl.glViewport(0, 0, 800, 600);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glOrtho(0, 800, 600, 0, 0, 100);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

		// Game initialization goes here.
        grassTex = glTexImageTGAFile(gl, "grass.tga", grassTexSize);
        tallGrassTex = glTexImageTGAFile(gl, "tallGrass.tga", tallGrassTexSize);
        treeTex = glTexImageTGAFile(gl, "tree.tga", treeTexSize);
        waterTex = glTexImageTGAFile(gl, "water.tga", treeTexSize);
        topLeftWater = glTexImageTGAFile(gl, "topLeftWater.tga", treeTexSize);
        topMiddleWater = glTexImageTGAFile(gl, "topMiddleWater.tga", treeTexSize);
        topRightWater = glTexImageTGAFile(gl, "topRightWater.tga", treeTexSize);
        middleLeftWater = glTexImageTGAFile(gl, "middleLeftWater.tga", treeTexSize);
        middleRightWater = glTexImageTGAFile(gl, "middleRightWater.tga", treeTexSize);
        bottomLeftWater = glTexImageTGAFile(gl, "bottomLeftWater.tga", treeTexSize);
        bottomMiddleWater = glTexImageTGAFile(gl, "bottomMiddleWater.tga", treeTexSize);
        bottomRightWater = glTexImageTGAFile(gl, "bottomRightWater.tga", treeTexSize);
        oneWayLeft = glTexImageTGAFile(gl, "One-Way Left.tga", treeTexSize);
        oneWayMid = glTexImageTGAFile(gl, "One-Way Middle.tga", treeTexSize);
        oneWayRight = glTexImageTGAFile(gl, "One-Way Right.tga", treeTexSize);
        
        karp1 = glTexImageTGAFile(gl, "karp1.tga", karpSize);
        karp2 = glTexImageTGAFile(gl, "karp2.tga", karpSize);
        karp3 = glTexImageTGAFile(gl, "karp3.tga", karpSize);
        arceus = glTexImageTGAFile(gl, "arceus.tga", arceusSize);
        
        
        playerStandingDown = glTexImageTGAFile(gl, "standingDown.tga", playerSize);
        playerStandingUp = glTexImageTGAFile(gl, "standingUp.tga", playerSize);
        playerStandingRight = glTexImageTGAFile(gl, "standingRight.tga", playerSize);
        playerStandingLeft = glTexImageTGAFile(gl, "standingLeft.tga", playerSize);
        playerWalkingDown1 = glTexImageTGAFile(gl, "walkingDown1.tga", playerSize);
        playerWalkingDown2 = glTexImageTGAFile(gl, "walkingDown2.tga", playerSize);
        playerWalkingUp1 = glTexImageTGAFile(gl, "walkingUp1.tga", playerSize);
        playerWalkingUp2 = glTexImageTGAFile(gl, "walkingUp2.tga", playerSize);
        playerWalkingRight1 = glTexImageTGAFile(gl, "walkingRight1.tga", playerSize);
        playerWalkingRight2 = glTexImageTGAFile(gl, "walkingRight2.tga", playerSize);
        playerWalkingLeft1 = glTexImageTGAFile(gl, "walkingLeft1.tga", playerSize);
        playerWalkingLeft2 = glTexImageTGAFile(gl, "walkingLeft2.tga", playerSize);
        
        font0 = glTexImageTGAFile(gl, "0Black.tga", font0Size);
        font1 = glTexImageTGAFile(gl, "1Black.tga", font1Size);
        font2 = glTexImageTGAFile(gl, "2Black.tga", font2Size);
        font3 = glTexImageTGAFile(gl, "3Black.tga", font3Size);
        font4 = glTexImageTGAFile(gl, "4Black.tga", font4Size);
        font5 = glTexImageTGAFile(gl, "5Black.tga", font5Size);
        font6 = glTexImageTGAFile(gl, "6Black.tga", font6Size);
        font7 = glTexImageTGAFile(gl, "7Black.tga", font7Size);
        font8 = glTexImageTGAFile(gl, "8Black.tga", font8Size);
        font9 = glTexImageTGAFile(gl, "9Black.tga", font9Size);
        
        battleGrassTex = glTexImageTGAFile(gl, "BattleGrass.tga", battleGrassSize);
        battleGrass2Tex = glTexImageTGAFile(gl, "battle2.tga", battleGrass2Size);
        battleFBPRBoxTex = glTexImageTGAFile(gl, "BattleFBPRBox.tga", battleFBPRBoxSize);
        battleBackTextTex = glTexImageTGAFile(gl, "BattleBackTextBox.tga", battleBackTextSize);
        battleHolderTex = glTexImageTGAFile(gl, "battle.tga", battleHolderSize);
        pointerTex = glTexImageTGAFile(gl, "Pointer.tga", pointerSize);
        hpBarPlayerTex = glTexImageTGAFile(gl, "hpBarPlayer.tga", hpBarPlayerSize);
        hpBarEnemyTex = glTexImageTGAFile(gl, "hpBarEnemy.tga", hpBarEnemySize);
        
        
        pointerLocation = 1;
        battleScreen = 1;
        
        int boardHeight = 2432;
        int boardWidth = 3200;
        int screenHeight = 600;
        int screenWidth = 800;
        Camera cam = new Camera(0,0);
        int[][] backgroundTiles = BackgroundDef.backgroundTiles;
        int[][] battleTiles = BackgroundDef.battleTiles;
        int physicsDeltaMs = 10;
        int lastPhysicsFrameMs = 0;
        int projectileSpacer = 0;
        float secsLeft = 1;
        int frameCount = 0;
        int frameDisplay = frameCount;
        
        inBattle = false;
        
        FrameDef framesKarp[] = new FrameDef[]{new FrameDef(karp1, 150),new FrameDef(karp3, 150), new FrameDef(karp2, 150), new FrameDef(karp3, 150)};
        FrameDef playerWalkingDown[] = new FrameDef[]{new FrameDef(playerWalkingDown1, 250), new FrameDef(playerWalkingDown2, 250)};
        FrameDef playerWalkingUp[] = new FrameDef[]{new FrameDef(playerWalkingUp1, 250), new FrameDef(playerWalkingUp2, 250)};
        FrameDef playerWalkingRight[] = new FrameDef[]{new FrameDef(playerWalkingRight1, 250), new FrameDef(playerWalkingRight2, 250)};
        FrameDef playerWalkingLeft[] = new FrameDef[]{new FrameDef(playerWalkingLeft1, 250), new FrameDef(playerWalkingLeft2, 250)};
        FrameDef standingDown[] = new FrameDef[]{new FrameDef(playerStandingDown, 250)};
        FrameDef standingUp[] = new FrameDef[]{new FrameDef(playerStandingUp, 250)};
        FrameDef standingRight[] = new FrameDef[]{new FrameDef(playerStandingRight, 250)};
        FrameDef standingLeft[] = new FrameDef[]{new FrameDef(playerStandingLeft, 250)};
        karpAnimation = new AnimationData(new AnimationDef(framesKarp), 0, 100, "Null");
        standingDownAnimation = new AnimationData(new AnimationDef(standingDown), 0, 100, "StandingDown");
        standingUpAnimation = new AnimationData(new AnimationDef(standingUp), 0, 100, "StandingUp");
        standingRightAnimation = new AnimationData(new AnimationDef(standingRight), 0, 100, "StandingRight");
        standingLeftAnimation = new AnimationData(new AnimationDef(standingLeft), 0, 100, "StandingLeft");
        walkingDownAnimation = new AnimationData(new AnimationDef(playerWalkingDown), 0, 100, "Down");
        walkingUpAnimation = new AnimationData(new AnimationDef(playerWalkingUp), 0, 100, "Up");
        walkingRightAnimation = new AnimationData(new AnimationDef(playerWalkingRight), 0, 100, "Right");
        walkingLeftAnimation = new AnimationData(new AnimationDef(playerWalkingLeft), 0, 100, "Left");
        
        AnimationData[] playerAnimations = new AnimationData[]{walkingDownAnimation, walkingUpAnimation, walkingRightAnimation, walkingLeftAnimation, standingDownAnimation, 
        		standingUpAnimation, standingRightAnimation, standingLeftAnimation};
        //player = new Player(karpAnimation, 100, 100, karpSize[0], karpSize[1]);
        player = new Player(playerAnimations, 100, 100, 34, 46);
        player.pokemon[0] = new Pokemon("magikarp", karp1, karpSize);
        player.pokemon[1] = new Pokemon("arceus", arceus, arceusSize);
        player.pokemon[1].setStats(100, 350, 220, 220, 220, 220, 220);
        player.pokemon[1].moves[0] = new PokemonMoves("1", "normal", 50, "physical", 20, 100, "normal");
        
        
        
        enemy1 = new Enemy(karpAnimation, 400, 300, karpSize[0], karpSize[1]);
        enemy1.pokemon = player.pokemon;
        //enemy2 = new Enemy(karpAnimation, 1150, 1600, karpSize[0], karpSize[1]);
        
        GlyphDef glyphs[] = new GlyphDef[]
        		{new GlyphDef("0", font0Size[0], font0), new GlyphDef("1", font1Size[0], font1), new GlyphDef("2", font2Size[0], font2), new GlyphDef("3", font3Size[0], font3),
        		new GlyphDef("4", font4Size[0], font4), new GlyphDef("5", font5Size[0], font5), new GlyphDef("6", font6Size[0], font6), new GlyphDef("7", font7Size[0], font7),
        		new GlyphDef("8", font8Size[0], font8), new GlyphDef("9", font9Size[0], font9)};
        FontDef font = new FontDef(38, glyphs);
        
        // The game loop
        long lastFrameNS;
        long curFrameNS = System.nanoTime();
        while (!shouldExit) {
            System.arraycopy(kbState, 0, kbPrevState, 0, kbState.length);
            lastFrameNS = curFrameNS;
            curFrameNS = System.nanoTime();
            int deltaTimeMS = (int)(curFrameNS - lastFrameNS) / 1000000;
            projectileSpacer++;

            // Actually, this runs the entire OS message pump.
            window.display();
            
            if (!window.isVisible()) {
                shouldExit = true;
                break;
            }

            // Game logic goes here.
            
            //Frame counting Code
            if (secsLeft == 1) {
            	frameDisplay = frameCount;
            	frameCount = 0;
            } else {
            	frameCount++;
            }
            secsLeft = secsLeft - ((float)deltaTimeMS / 1000);
            if (secsLeft <= 0) {
            	secsLeft = 1;
            }
            
            //Physics Loop
            do {
            	//Projectile Code
            	/**
            	for (Projectile current : projectiles) //Collision Detection: Checks for collisions and updates enemy health
            	{
            		enemy1.updateHealth(current);
            		//enemy2.updateHealth(current);
            	}
            	
            	player.collisionCheck(enemy1.xCord, enemy1.yCord, enemy1.spriteSizeX, enemy1.spriteSizeY, enemy1.alive);
            	player.collisionResolve(enemy1.xCord, enemy1.yCord, enemy1.spriteSizeX, enemy1.spriteSizeY, enemy1.alive);
            	player.collisionCheck(enemy2.xCord, enemy2.yCord, enemy2.spriteSizeX, enemy2.spriteSizeY, enemy2.alive);
            	
            	for (int i = 0; i < projectiles.size(); i++) //Collision Resolution: Deletes the destroyed projectiles from ArrayList
                {
                	if (projectiles.get(i).destroyed == true)
                	{
                		projectiles.remove(i);
                	}
                }
                **/
            	lastPhysicsFrameMs += physicsDeltaMs;
            } while (lastPhysicsFrameMs + physicsDeltaMs < deltaTimeMS);
            if(!inBattle){
           	//Up Movement for Character
            	if(kbState[KeyEvent.VK_W]) {
            		player.y = characterSet(player.y, player.spriteSizeY, boardHeight, deltaTimeMS, -1);
            		player.collisionResolveBack(backgroundTiles, "Up");
            		player.setDirection("Up");
            		player.collisionResolve(enemy1.xCord, enemy1.yCord, enemy1.spriteSizeX, enemy1.spriteSizeY, enemy1.alive, "Up");
            		//player.collisionResolve(enemy2.xCord, enemy2.yCord, enemy2.spriteSizeX, enemy2.spriteSizeY, enemy2.alive, "Up");
            	} else if(kbState[KeyEvent.VK_S]) {
            		player.y = characterSet(player.y, player.spriteSizeY, boardHeight, deltaTimeMS, 1);
            		player.collisionResolveBack(backgroundTiles, "Down");
            		player.setDirection("Down");
            		player.collisionResolve(enemy1.xCord, enemy1.yCord, enemy1.spriteSizeX, enemy1.spriteSizeY, enemy1.alive, "Down");
            		//player.collisionResolve(enemy2.xCord, enemy2.yCord, enemy2.spriteSizeX, enemy2.spriteSizeY, enemy2.alive, "Down");
            	} else if(kbState[KeyEvent.VK_A]) {
            		player.x = characterSet(player.x, player.spriteSizeX, boardWidth, deltaTimeMS, -1);
            		player.collisionResolveBack(backgroundTiles, "Left");
            		player.setDirection("Left");
            		player.collisionResolve(enemy1.xCord, enemy1.yCord, enemy1.spriteSizeX, enemy1.spriteSizeY, enemy1.alive, "Left");
            		//player.collisionResolve(enemy2.xCord, enemy2.yCord, enemy2.spriteSizeX, enemy2.spriteSizeY, enemy2.alive, "Left");
            	} else if(kbState[KeyEvent.VK_D]) {
            		player.x = characterSet(player.x, player.spriteSizeX, boardWidth, deltaTimeMS, 1);
            		player.collisionResolveBack(backgroundTiles, "Right");
            		player.setDirection("Right");
            		player.collisionResolve(enemy1.xCord, enemy1.yCord, enemy1.spriteSizeX, enemy1.spriteSizeY, enemy1.alive, "Right");
            		//player.collisionResolve(enemy2.xCord, enemy2.yCord, enemy2.spriteSizeX, enemy2.spriteSizeY, enemy2.alive, "Right");
            	} else {
            		if (!player.direction.contains("Standing"))
            		{
            			player.setDirection("Standing" + player.direction);
            		}
            	}
            	if(kbState[KeyEvent.VK_F]){
            		if (enemy1.xCord + enemy1.spriteSizeX + 1 == player.x){
            			inBattle = true;
            		}
            		if (enemy1.xCord == player.x + player.spriteSizeX + 1){
            			inBattle = true;
            		}
            		if (enemy1.yCord + enemy1.spriteSizeY + 1 == player.y){
            			inBattle = true;
            		}
            		if (enemy1.yCord == player.y + player.spriteSizeY + 1){
            			inBattle = true;
            		}	
            		Arrays.fill(kbState, false); //Without this it automatically clicks Fight
            	}
            }
            else{
            	
            	if(kbState[KeyEvent.VK_F] && battleScreen == 1){
            		
            		if(battleScreen == 1 && pointerLocation == 1){
            			battleScreen = 2;
            		}
            		if(battleScreen == 1 && pointerLocation == 4){
            			inBattle = false;
            			
            		}   	
            		Arrays.fill(kbState,false);
            	}
            	else if(kbState[KeyEvent.VK_F] && battleScreen == 2){
            		System.out.println("Before hp: " + enemy1.pokemon[1].currentHp);
            		if(pointerLocation == 1){
            			enemy1.pokemon[1].currentHp -= 50;
            			if(enemy1.pokemon[1].currentHp <= 0){
            				enemy1.pokemonAlive--;
            			}
            			System.out.println("After hp: " + enemy1.pokemon[1].currentHp);
            			if(enemy1.pokemonAlive == 0){
            				
            				System.out.println("Victory");
            				inBattle = false;
            				
            			}
            			battleScreen = 1;
            			Arrays.fill(kbState,false);
            		}
            		
            		else if(pointerLocation == 2){
            			enemy1.pokemon[1].currentHp -= 60;
            			if(enemy1.pokemon[1].currentHp <= 0){
            				enemy1.pokemonAlive--;
            			}
            			System.out.println("After hp: " + enemy1.pokemon[1].currentHp);
            			if(enemy1.pokemonAlive == 0){
            				
            				System.out.println("Victory");
            				inBattle = false;
            				
            			}
            			battleScreen = 1;
            			Arrays.fill(kbState,false);
            			pointerLocation = 1;
            		}
            		else if(pointerLocation == 3){
            			enemy1.pokemon[1].currentHp -= 70;
            			if(enemy1.pokemon[1].currentHp <= 0){
            				enemy1.pokemonAlive--;
            			}
            			System.out.println("After hp: " + enemy1.pokemon[1].currentHp);
            			if(enemy1.pokemonAlive == 0){
            				
            				System.out.println("Victory");
            				inBattle = false;
            				
            			}
            			battleScreen = 1;
            			Arrays.fill(kbState,false);
            			pointerLocation = 1;
            		}
            		else if(pointerLocation == 4){
            			enemy1.pokemon[1].currentHp -= 80;
            			if(enemy1.pokemon[1].currentHp <= 0){
            				enemy1.pokemonAlive--;
            			}
            			System.out.println("After hp: " + enemy1.pokemon[1].currentHp);
            			if(enemy1.pokemonAlive == 0){
            				
            				System.out.println("Victory");
            				inBattle = false;
            				
            			}
            			battleScreen = 1;
            			Arrays.fill(kbState,false);
            			pointerLocation = 1;
            		}
            	}
            	if(kbState[KeyEvent.VK_B]){
            		if(battleScreen != 1){
            			battleScreen = 1;
            		}
            	}
            	if(kbState[KeyEvent.VK_W]){
            		if(pointerLocation == 3){
            			pointerLocation = 1;
            		}
            		else if (pointerLocation == 4){
            			pointerLocation = 2;
            		}
            	}
            	if(kbState[KeyEvent.VK_A]){
            		if(pointerLocation == 2){
            			pointerLocation = 1;
            		}
            		else if (pointerLocation ==4){
            			pointerLocation = 3;
            		}
            	}
            	if(kbState[KeyEvent.VK_S]){
            		if(pointerLocation == 1){
            			pointerLocation = 3;
            		}
            		else if (pointerLocation == 2){
            			pointerLocation = 4;
            		}
            	}
            	if(kbState[KeyEvent.VK_D]){
            		if(pointerLocation == 1){
            			pointerLocation = 2;
            		}
            		else if (pointerLocation == 3){
            			pointerLocation = 4;
            		}
            	}        	
            }
           	//Projectile Code
           	/**
            //Fire Projectile
            if(kbState[KeyEvent.VK_SPACE] && projectileSpacer % 15 == 0) {
            	projectiles.add(new Projectile(player.x + (player.spriteSizeX / 4), player.y + (player.spriteSizeY / 4), 40, 40, karpAnimation)); //Makes new projectiles while Space is held
           	}
           	**/
            
           	//Camera Movement Code
            /**
            //Up Movement Camera
           	if(kbState[KeyEvent.VK_UP]) {
           		if(cam.y > 0) {
           			cam.y = cam.y - (1 * deltaTimeMS);
            	} else {
            		cam.y = 0;
            	}
           	}
            
           	//Down Movement Camera
           	if(kbState[KeyEvent.VK_DOWN]) {
            	if(cam.y < boardHeight - screenHeight) {
            		cam.y = cam.y + (1 * deltaTimeMS);
            	} else {
            		cam.y = boardHeight - screenHeight;
            	}
            }
            
            //Left Movement Camera
            if(kbState[KeyEvent.VK_LEFT]) {
            	if(cam.x > 0) {
            		cam.x = cam.x - (1 * deltaTimeMS);
            	} else {
            		cam.x = 0;
            	}
            }
            
            //Right Movement Camera
            if(kbState[KeyEvent.VK_RIGHT]) {
            	if(cam.x < boardWidth - screenWidth) {
            		cam.x = cam.x + (1 * deltaTimeMS);
            	} else {
            		cam.x = boardWidth - screenWidth;
            	}
            }
            **/
            
            cam.y = Camera.cameraSet(player.y, player.spriteSizeY, screenHeight, boardHeight);
       		cam.x = Camera.cameraSet(player.x, player.spriteSizeX, screenWidth, boardWidth);
            
       		player.getCurrentAnimation().update(deltaTimeMS);
            
            //Updates Enemy's behavior and location
            //enemy1.update(deltaTimeMS, player);
            //enemy2.update(deltaTimeMS, player);
       		
       		//Projectile Code
            /**
            //Updates the projectiles positions with respect to deltaTimeMS
            for (Projectile current : projectiles)
            {
            	current.update(deltaTimeMS, current, boardWidth);
            }
            **/
            
            if (kbState[KeyEvent.VK_ESCAPE]) {
                shouldExit = true;
            }
            
            gl.glClearColor(0, 0, 0, 1);
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
            
            // Draw the background
            if(!inBattle){
            	for (int i = cam.y / 32; i < BackgroundDef.backgroundEndY(cam.y); i++)
            	{
            		for (int j = cam.x / 32; j < BackgroundDef.backgroundEndX(cam.x); j++)
            		{
            			if (backgroundTiles[i][j] == 0) {
            				glDrawSprite(gl, grassTex, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 1) {
            				glDrawSprite(gl, tallGrassTex, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 2) {
            				glDrawSprite(gl, waterTex, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 3) {
            				glDrawSprite(gl, topLeftWater, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 4) {
            				glDrawSprite(gl, topMiddleWater, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 5) {
            				glDrawSprite(gl, topRightWater, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 6){
            				glDrawSprite(gl, middleRightWater, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 7) {
            				glDrawSprite(gl, bottomRightWater, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 8) {
            				glDrawSprite(gl, bottomMiddleWater, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 9) {
            				glDrawSprite(gl, bottomLeftWater, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 10) {
            				glDrawSprite(gl, middleLeftWater, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 11) {
            				glDrawSprite(gl, oneWayLeft, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 12) {
            				glDrawSprite(gl, oneWayMid, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            			if (backgroundTiles[i][j] == 13) {
            				glDrawSprite(gl, oneWayRight, j*32 - cam.x, i*32 - cam.y, 32,32);
            			}
            		}
            	}

            // Draw the sprites
            	if (cam.onScreenCheck(player.x, player.y, player.spriteSizeX, player.spriteSizeY))
            	{
            	//glDrawSprite(gl, character.def.frames[character.curFrame].image, (spritePos[0] - cam.x), (spritePos[1] - cam.y), karpSize[0], karpSize[1]);
            	//player.animation.draw(gl, player.x, player.y, cam.x, cam.y, player.spriteSizeX, player.spriteSizeY);
            		player.getCurrentAnimation().draw(gl, player.x, player.y, cam.x, cam.y, player.spriteSizeX, player.spriteSizeY);
            	}
            	if (cam.onScreenCheck(enemy1.xCord, enemy1.yCord, enemy1.spriteSizeX, enemy1.spriteSizeY) && enemy1.alive) //Checks if Enemy is on camera and alive
                {
                	//glDrawSprite(gl, enemy1.getAnimation().def.frames[enemy1.getAnimation().curFrame].image, (enemy1.xCord - cam.x), (enemy1.yCord - cam.y), enemy1.spriteSizeX, enemy1.spriteSizeY);
                	enemy1.animation.draw(gl, enemy1.xCord, enemy1.yCord, cam.x, cam.y, enemy1.spriteSizeX, enemy1.spriteSizeY);
                }
            }
            else{
            	if(battleScreen == 1){
            		glDrawSprite(gl, battleHolderTex,0,0, battleHolderSize[0], battleHolderSize[1]);
            		if(pointerLocation ==1)
            			glDrawSprite(gl, pointerTex,515,493, pointerSize[0], pointerSize[1]);
            		else if(pointerLocation == 2)
            			glDrawSprite(gl, pointerTex,650,493, pointerSize[0], pointerSize[1]);
            		else if(pointerLocation == 3)
            			glDrawSprite(gl, pointerTex,515,540, pointerSize[0], pointerSize[1]);
            		else if(pointerLocation == 4)
            			glDrawSprite(gl, pointerTex,650,540, pointerSize[0], pointerSize[1]);	
            	}
            	else if(battleScreen == 2){
            		glDrawSprite(gl, battleGrass2Tex,0,0, battleGrass2Size[0], battleGrass2Size[1]);
            		
            		//To do make it draw word based on name
            		glDrawSprite(gl, font1, 20+pointerSize[0] + 3 , 485, font1Size[0], font1Size[1]);
            		if(pointerLocation ==1)
                		glDrawSprite(gl, pointerTex,20,485, pointerSize[0], pointerSize[1]);
            		else if(pointerLocation == 2)
                		glDrawSprite(gl, pointerTex,270,485, pointerSize[0], pointerSize[1]);
            		else if(pointerLocation == 3)
                		glDrawSprite(gl, pointerTex,20,540, pointerSize[0], pointerSize[1]);
            		else if(pointerLocation == 4)
                		glDrawSprite(gl, pointerTex,270,540, pointerSize[0], pointerSize[1]);              	
            	}
            		
            	//player pokemon position
            	glDrawSprite(gl, player.pokemon[player.currentPokemon].gl, 130,285, player.pokemon[player.currentPokemon].size[0]*2,player.pokemon[player.currentPokemon].size[1]*2);
            	//enemy pokemon position
            	glDrawSprite(gl, player.pokemon[player.currentPokemon].gl, 490,80, player.pokemon[player.currentPokemon].size[0]*2,player.pokemon[player.currentPokemon].size[1]*2);
            	
            	glDrawSprite(gl,hpBarPlayerTex, 410,315, hpBarPlayerSize[0], hpBarPlayerSize[1]);
            	glDrawSprite(gl, hpBarEnemyTex, 0, 30, hpBarEnemySize[0], hpBarEnemySize[1]);
            //	glDrawSprite(gl,battleGrassTex,0,0, battleGrassSize[0], battleGrassSize[1]);
            //	glDrawSprite(gl,battleBackTextTex, 0, battleGrassSize[1], battleBackTextSize[0], battleBackTextSize[1]);
            //	glDrawSprite(gl, battleFBPRBoxTex,battleBackTextSize[0] - battleFBPRBoxSize[0],battleGrassSize[1], battleFBPRBoxSize[0], battleFBPRBoxSize[1]);
            	/**
            	for(int i = 0; i < battleTiles.length; i++){
            		for(int j = 0; j < battleTiles[0].length; j++){
            			if (battleTiles[i][j] == 0) {
            				glDrawSprite(gl, grassTex, j*32, i*32, 32,32);
            			}
            			if (battleTiles[i][j] == 2) {
            				glDrawSprite(gl, waterTex, j*32, i*32, 32,32);
            			}
            			if (battleTiles[i][j] == 3) {
            				glDrawSprite(gl, topLeftWater, j*32, i*32, 32,32);
            			}
            			if (battleTiles[i][j] == 4) {
            				glDrawSprite(gl, topMiddleWater, j*32, i*32, 32,32);
            			}
            			if (battleTiles[i][j] == 5) {
            				glDrawSprite(gl, topRightWater, j*32, i*32, 32,32);
            			}
            			if (battleTiles[i][j] == 6){
            				glDrawSprite(gl, middleRightWater, j*32, i*32, 32,32);
            			}
            			if (battleTiles[i][j] == 7) {
            				glDrawSprite(gl, bottomRightWater, j*32, i*32, 32,32);
            			}
            			if (battleTiles[i][j] == 8) {
            				glDrawSprite(gl, bottomMiddleWater, j*32, i*32, 32,32);
            			}
            			if (battleTiles[i][j] == 9) {
            				glDrawSprite(gl, bottomLeftWater, j*32, i*32, 32,32);
            			}
            			if (battleTiles[i][j] == 10) {
            				glDrawSprite(gl, middleLeftWater, j*32, i*32, 32,32);
            			}
            			
            		}
            		
            	}
            	*/
            	//glDrawSprite(gl,playerStandingRight,3*32, 9*32,32,32);
            	//glDrawSprite(gl,playerStandingLeft,21*32, 9*32,32,32);
            	//glDrawSprite(gl,karp1,7*32, 9*32,32,32);
            	//glDrawSprite(gl,karp1,17*32, 9*32,32,32);
            }
            
            //Enemy Code
            /**
            
            if (cam.onScreenCheck(enemy2.xCord, enemy2.yCord, enemy2.spriteSizeX, enemy2.spriteSizeY) && enemy2.alive) //Checks if Enemy is on camera and alive
            {
            	//glDrawSprite(gl, enemy2.getAnimation().def.frames[enemy2.getAnimation().curFrame].image, (enemy2.xCord - cam.x), (enemy2.yCord - cam.y), enemy2.spriteSizeX, enemy2.spriteSizeY);
            	enemy2.animation.draw(gl, enemy2.xCord, enemy2.yCord, cam.x, cam.y, enemy2.spriteSizeX, enemy2.spriteSizeY);
            }
            **/
            
            //Projectile Code
            /**
            //Draws all projectiles that are on screen
            for (Projectile current : projectiles)
            {
            	if (cam.onScreenCheck(current.x, current.y, current.spriteSizeX, current.spriteSizeY))
            	{
            		current.animation.draw(gl, current.x, current.y, cam.x, cam.y, current.spriteSizeX, current.spriteSizeY);
            	}
            }
            **/
            
            //Draws Enemy HUD Sprite
          //  glDrawSprite(gl, karp2, 0, 0, karpSize[0], karpSize[1]);
            
           // int karpsAlive = 1;
            
            //Draws Number of Karps
          //  glDrawText(gl, font, Integer.toString(karpsAlive), karpSize[0], 8);
            
            //Draws FPS
           // glDrawText(gl, font, Integer.toString(frameDisplay), 730, 8);
        }
    }
    
    public static void glDrawText(GL2 gl, FontDef font, String str, int x, int y)
    {
    	int currentX = x;
    	for (int i = 0; i < str.length(); i++)
    	{
    		//System.out.println(font.glyphs[0].image);
    		//System.out.println(font0);
    		String currentSub = str.substring(i, i + 1);
    		if (currentSub.equals("0")) {
    			glDrawSprite(gl, font0, currentX, y, font.glyphs[0].width, font.lineHeight);
    			currentX = currentX + font.glyphs[0].width;
    		} else if (currentSub.equals("1")) {
    			glDrawSprite(gl, font1, currentX, y, font.glyphs[1].width, font.lineHeight);
    			currentX = currentX + font.glyphs[1].width;
    		} else if (currentSub.equals("2")) {
    			glDrawSprite(gl, font2, currentX, y, font.glyphs[2].width, font.lineHeight);
    			currentX = currentX + font.glyphs[2].width;
    		} else if (currentSub.equals("3")) {
    			glDrawSprite(gl, font3, currentX, y, font.glyphs[3].width, font.lineHeight);
    			currentX = currentX + font.glyphs[3].width;
    		} else if (currentSub.equals("4")) {
    			glDrawSprite(gl, font4, currentX, y, font.glyphs[4].width, font.lineHeight);
    			currentX = currentX + font.glyphs[4].width;
    		} else if (currentSub.equals("5")) {
    			glDrawSprite(gl, font5, currentX, y, font.glyphs[5].width, font.lineHeight);
    			currentX = currentX + font.glyphs[5].width;
    		} else if (currentSub.equals("6")) {
    			glDrawSprite(gl, font6, currentX, y, font.glyphs[6].width, font.lineHeight);
    			currentX = currentX + font.glyphs[6].width;
    		} else if (currentSub.equals("7")) {
    			glDrawSprite(gl, font7, currentX, y, font.glyphs[7].width, font.lineHeight);
    			currentX = currentX + font.glyphs[7].width;
    		} else if (currentSub.equals("8")) {
    			glDrawSprite(gl, font8, currentX, y, font.glyphs[8].width, font.lineHeight);
    			currentX = currentX + font.glyphs[8].width;
    		} else if (currentSub.equals("9")) {
    			glDrawSprite(gl, font9, currentX, y, font.glyphs[9].width, font.lineHeight);
    			currentX = currentX + font.glyphs[9].width;
    		}
    	}
    }

    // Load a file into an OpenGL texture and return that texture.
    public static int glTexImageTGAFile(GL2 gl, String filename, int[] out_size) {
        final int BPP = 4;

        DataInputStream file = null;
        try {
            // Open the file.
            file = new DataInputStream(new FileInputStream(filename));
        } catch (FileNotFoundException ex) {
            System.err.format("File: %s -- Could not open for reading.", filename);
            return 0;
        }

        try {
            // Skip first two bytes of data we don't need.
            file.skipBytes(2);

            // Read in the image type.  For our purposes the image type
            // should be either a 2 or a 3.
            int imageTypeCode = file.readByte();
            if (imageTypeCode != 2 && imageTypeCode != 3) {
                file.close();
                System.err.format("File: %s -- Unsupported TGA type: %d", filename, imageTypeCode);
                return 0;
            }

            // Skip 9 bytes of data we don't need.
            file.skipBytes(9);

            int imageWidth = Short.reverseBytes(file.readShort());
            int imageHeight = Short.reverseBytes(file.readShort());
            int bitCount = file.readByte();
            file.skipBytes(1);

            // Allocate space for the image data and read it in.
            byte[] bytes = new byte[imageWidth * imageHeight * BPP];

            // Read in data.
            if (bitCount == 32) {
                for (int it = 0; it < imageWidth * imageHeight; ++it) {
                    bytes[it * BPP + 0] = file.readByte();
                    bytes[it * BPP + 1] = file.readByte();
                    bytes[it * BPP + 2] = file.readByte();
                    bytes[it * BPP + 3] = file.readByte();
                }
            } else {
                for (int it = 0; it < imageWidth * imageHeight; ++it) {
                    bytes[it * BPP + 0] = file.readByte();
                    bytes[it * BPP + 1] = file.readByte();
                    bytes[it * BPP + 2] = file.readByte();
                    bytes[it * BPP + 3] = -1;
                }
            }

            file.close();

            // Load into OpenGL
            int[] texArray = new int[1];
            gl.glGenTextures(1, texArray, 0);
            int tex = texArray[0];
            gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
            gl.glTexImage2D(
                    GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, imageWidth, imageHeight, 0,
                    GL2.GL_BGRA, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(bytes));
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);

            out_size[0] = imageWidth;
            out_size[1] = imageHeight;
            return tex;
        }
        catch (IOException ex) {
            System.err.format("File: %s -- Unexpected end of file.", filename);
            return 0;
        }
    }

    public static void glDrawSprite(GL2 gl, int tex, int x, int y, int w, int h) {
        gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
        gl.glBegin(GL2.GL_QUADS);
        {
            gl.glColor3ub((byte)-1, (byte)-1, (byte)-1);
            gl.glTexCoord2f(0, 1);
            gl.glVertex2i(x, y);
            gl.glTexCoord2f(1, 1);
            gl.glVertex2i(x + w, y);
            gl.glTexCoord2f(1, 0);
            gl.glVertex2i(x + w, y + h);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2i(x, y + h);
        }
        gl.glEnd();
    }
    
    //Sets character position based on input and current position
    public static int characterSet(int currentPos, int spriteSize, int boardSize, int deltaTimeMS, int modifier)
    {
    	int characterCord;
    	if (currentPos > 0 && modifier == -1) 
    	{
    		characterCord = currentPos - (int)(.5 * deltaTimeMS);
    	}
    	else if (currentPos < boardSize - spriteSize && modifier != -1)
    	{
    		characterCord = currentPos + (int)(.5 * deltaTimeMS);
    	}
    	else if (modifier == -1)
    	{
    		characterCord = 0;
    	}
    	else
    	{
    		characterCord = boardSize - spriteSize;
    	}
    	return characterCord;
    }
}
