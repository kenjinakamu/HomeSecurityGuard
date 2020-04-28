package gds.project.homesecurityguard;

import gds.project.framework.Music;
import gds.project.framework.Sound;
import gds.project.framework.gl.Animation;
import gds.project.framework.gl.Font;
import gds.project.framework.gl.Texture;
import gds.project.framework.gl.TextureRegion;
import gds.project.framework.impl.GLGame;

public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;

	public static Music music;
	public static Sound hitSound;
	public static Sound throwSound;
	public static Sound clickSound;
	public static Sound canSound;
	public static Sound openSound;
	
	public static Texture items01;
	public static TextureRegion logo;
	public static TextureRegion helpcollection;
	public static TextureRegion start;
	public static Animation throwAnim;
	public static Animation hitAnim;
	public static Animation enemy1;
	public static Animation enemy2;
	public static Animation enemy3;
	public static TextureRegion food1;
	public static TextureRegion food2;
	public static TextureRegion food3;
	public static Animation pound;
	public static TextureRegion hitOn;
	public static TextureRegion throwOn;
	public static TextureRegion hitOff;
	public static TextureRegion throwOff;
	public static TextureRegion canOpen;
	public static TextureRegion canOn;
	public static TextureRegion canOff;
	public static TextureRegion bar;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion pauseOn;
	public static TextureRegion pauseOff;
	public static TextureRegion next;
	public static TextureRegion skip;
	public static TextureRegion pauseMenu;
	public static TextureRegion gameOver;
	public static Animation startAnim;
	public static TextureRegion ready;
	public static TextureRegion go;
	
	public static Texture items;
	public static TextureRegion soundOn2;
	public static TextureRegion soundOff2;
	public static TextureRegion pauseOn2;
	public static TextureRegion pauseOff2;
	public static TextureRegion next2;
	public static TextureRegion skip2;
	public static Font font2;
	
	public static Font font;
	
	public static Texture mainMenuScreen;
	public static TextureRegion menuRegion;
	
	public static Texture darkScreen;
	public static TextureRegion darkRegion;
	
	public static Texture end0;
	public static Texture end1;
	public static Texture end2;
	public static Texture end3;
	public static Texture end4;
	public static Texture end5;
	public static Texture end6;
	public static TextureRegion end0Region;
	public static TextureRegion end1Region;
	public static TextureRegion end2Region;
	public static TextureRegion end3Region;
	public static TextureRegion end4Region;
	public static TextureRegion end5Region;
	public static TextureRegion end6Region;
	
	public static void load(GLGame game) {
		background = new Texture(game, "light.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);
		
		mainMenuScreen = new Texture(game, "menu.png");
		menuRegion = new TextureRegion(mainMenuScreen, 0, 0, 320, 480);
		
		darkScreen = new Texture(game, "dark.png");
		darkRegion = new TextureRegion(darkScreen, 0, 0, 320, 480);

		items01 = new Texture(game, "items01.png");
		throwAnim = new Animation(0.1f,
				new TextureRegion(items01, 0, 180, 120, 180),
				new TextureRegion(items01, 120, 180, 120, 180),
				new TextureRegion(items01, 240, 180, 120, 180),
				new TextureRegion(items01, 360, 180, 120, 180));
		hitAnim = new Animation(0.1f,
				new TextureRegion(items01, 120, 0, 120, 180),
				new TextureRegion(items01, 240, 0, 120, 180),
				new TextureRegion(items01, 360, 0, 120, 180),
				new TextureRegion(items01, 480, 0, 120, 180),
				new TextureRegion(items01, 600, 0, 120, 180));
		enemy1 = new Animation(0.2f,
				new TextureRegion(items01, 200, 360, 70, 70),
				new TextureRegion(items01, 270, 360, 70, 70));
		enemy2 = new Animation(0.2f,
				new TextureRegion(items01, 340, 360, 70, 70),
				new TextureRegion(items01, 410, 360, 70, 70));
		enemy3 = new Animation(0.2f,
				new TextureRegion(items01, 480, 360, 70, 70),
				new TextureRegion(items01, 550, 360, 70, 70));
		food1 = new TextureRegion(items01, 620, 360, 70, 70);
		food2 = new TextureRegion(items01, 690, 360, 70, 70);
		food3 = new TextureRegion(items01, 760, 360, 70, 70);
		pound = new Animation(0.3f,
				new TextureRegion(items01, 200, 430, 199, 203),
				new TextureRegion(items01, 399, 430, 199, 203));
		hitOn = new TextureRegion(items01, 598, 430, 100, 100);
		throwOn = new TextureRegion(items01, 698, 430, 100, 100);
		hitOff = new TextureRegion(items01, 598, 530, 100, 100);
		throwOff = new TextureRegion(items01, 698, 530, 100, 100);
		canOpen = new TextureRegion(items01, 530, 763, 80, 120);
		canOn = new TextureRegion(items01, 450, 763, 80, 80);
		canOff = new TextureRegion(items01, 450, 843, 80, 80);
		bar = new TextureRegion(items01, 598, 630, 320, 3);
		soundOn = new TextureRegion(items01, 733, 633, 60, 50);
		soundOff = new TextureRegion(items01, 793, 633, 70, 50);
		pauseOn = new TextureRegion(items01, 603, 633, 73, 50);
		pauseOff = new TextureRegion(items01, 676, 633, 57, 50);
		next = new TextureRegion(items01, 451, 633, 65, 50);
		skip = new TextureRegion(items01, 516, 633, 87, 50);
		pauseMenu = new TextureRegion(items01, 480, 180, 240, 180);
		gameOver = new TextureRegion(items01, 200, 633, 250, 320);
		startAnim = new Animation(0.5f,
				new TextureRegion(items01, 0, 360, 200, 200),
				new TextureRegion(items01, 0, 560, 200, 200),
				new TextureRegion(items01, 0, 760, 200, 200));
		ready = new TextureRegion(items01, 720, 0, 304, 180);
		go = new TextureRegion(items01, 720, 180, 304, 180);
		
		items = new Texture(game, "items.png");
		soundOn2 = new TextureRegion(items, 733, 633, 60, 50);
		soundOff2 = new TextureRegion(items, 793, 633, 70, 50);
		pauseOn2 = new TextureRegion(items, 603, 633, 73, 50);
		pauseOff2 = new TextureRegion(items, 676, 633, 57, 50);
		next2 = new TextureRegion(items, 451, 633, 65, 50);
		skip2 = new TextureRegion(items, 516, 633, 87, 50);
		font2 = new Font(items, 768, 904, 16, 16, 20);
		
		font = new Font(items01, 768, 904, 16, 16, 20);	
		
		music = game.getAudio().newMusic("mapleleafrag.ogg");
		music.setLooping(true);
		music.setVolume(0.5f);
		if(Settings.soundEnabled)
			music.play();
		hitSound = game.getAudio().newSound("hit1.ogg");
		throwSound = game.getAudio().newSound("throw1.ogg");
		clickSound = game.getAudio().newSound("click5.ogg");
		canSound = game.getAudio().newSound("hit2.ogg");
		openSound = game.getAudio().newSound("hit4.ogg");
		
		end0 = new Texture(game, "end00.png");
		end1 = new Texture(game, "end01.png");
		end2 = new Texture(game, "end02.png");
		end3 = new Texture(game, "end03.png");
		end4 = new Texture(game, "end04.png");
		end5 = new Texture(game, "end05.png");
		end6 = new Texture(game, "end06.png");
		end0Region = new TextureRegion(end0, 0, 0, 320, 480);
		end1Region = new TextureRegion(end1, 0, 0, 320, 480);
		end2Region = new TextureRegion(end2, 0, 0, 320, 480);
		end3Region = new TextureRegion(end3, 0, 0, 320, 480);
		end4Region = new TextureRegion(end4, 0, 0, 320, 480);
		end5Region = new TextureRegion(end5, 0, 0, 320, 480);
		end6Region = new TextureRegion(end6, 0, 0, 320, 480);
	}

	public static void reload() {
		background.reload();
		items01.reload();
		items.reload();
		mainMenuScreen.reload();
		darkScreen.reload();
		end0.reload();
		end1.reload();
		end2.reload();
		end3.reload();
		end4.reload();
		end5.reload();
		end6.reload();
		if(Settings.soundEnabled)
			music.play();
	}

	public static void playSound(Sound sound) {
		if(Settings.soundEnabled)
			sound.play(1);
	}
}
