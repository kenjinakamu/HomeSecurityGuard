package gds.project.homesecurityguard;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import gds.project.framework.Game;
import gds.project.framework.Input.TouchEvent;
import gds.project.framework.gl.Animation;
import gds.project.framework.gl.Camera2D;
import gds.project.framework.gl.FPSCounter;
import gds.project.framework.gl.SpriteBatcher;
import gds.project.framework.gl.TextureRegion;
import gds.project.framework.impl.GLScreen;
import gds.project.framework.math.OverlapTester;
import gds.project.framework.math.Rectangle;
import gds.project.framework.math.Vector2;
import gds.project.homesecurityguard.World.WorldListener;

public class GameScreen extends GLScreen {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_CLASH = 3;
	static final int GAME_OVER = 4;

	int state;
	Camera2D guiCam;
	static Vector2 touchPoint;
	SpriteBatcher batcher;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Rectangle hitBounds;
	Rectangle throwBounds;
	Rectangle centerBounds;
	Rectangle nextBounds;
	
	static int lastScore;
	static int missScore;
	int lastTime;
	int touchCounter;
	float stateTime;
	float counter;
	String scoreString;
	String timeString;
    FPSCounter fpsCounter;
    
    static boolean isHitTouched;
    static boolean isThrowTouched;
    static boolean isRecovered;
    static boolean isStarted;
    
	static boolean isEnd6maked = false;
	
    boolean isOpened;
    boolean isReady;
    boolean isGo;
	
	public GameScreen(Game game) {
		super(game);
		state = GAME_READY;
		guiCam = new Camera2D(glGraphics, 320, 480);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 500);
		worldListener = new WorldListener() {
			public void hit() {
				Assets.playSound(Assets.hitSound);
			}
			public void throwing() {
				Assets.playSound(Assets.throwSound);
			}
			public void canHit() {
				Assets.playSound(Assets.canSound);
			}
			public void canOpen() {
				Assets.playSound(Assets.openSound);
			}
		};
		world = new World(worldListener);
		renderer = new WorldRenderer(glGraphics, batcher, world);
		pauseBounds = new Rectangle(160- 33, 395 - 50, 66, 50);	
		resumeBounds = new Rectangle(160 - 120, 240, 240, 90);
		quitBounds = new Rectangle(160 - 120, 240 - 90, 240, 90);
		hitBounds = new Rectangle(320 - 100, 0, 100, 100);
		throwBounds = new Rectangle(0, 0, 100, 100);
		centerBounds = new Rectangle(160 - 40, 0, 80, 80);
		nextBounds = new Rectangle(160 - 33, 0, 66, 50);
		
		lastScore = 0;
		missScore = 0;
		stateTime = World.WORLD_TIME;
		counter = 0;
		scoreString = "SCORE: 0";
		timeString = "TIME:" + World.WORLD_TIME;
		fpsCounter = new FPSCounter();
		
		isHitTouched = false;
		isThrowTouched = false;
		isRecovered = false;
		isOpened = false;
		isReady = false;
		isGo = false;
		isStarted = false;
	}
	
	@Override
	public void update(float deltaTime) {
		if(deltaTime > 0.1f)
			deltaTime = 0.1f;

		switch(state) {
		case GAME_READY:
			updateReady(deltaTime);
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_CLASH:
			updateGameClash(deltaTime);
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}	

	private void updateReady(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			isReady =true;
		}
		if(isReady) {
			counter += deltaTime;
			if(counter > 1.5f) {
				isGo = true;
				counter = 0;
			}
		} 
		if(isGo) {
			counter += deltaTime;
			if(counter > 1f) {
				state = GAME_RUNNING;
				counter = 0;
			}
		}
	}
	
	private void updateRunning(float deltaTime) {
		isStarted = true;
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;

			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if(OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}
			
			if(OverlapTester.pointInRectangle(hitBounds, touchPoint)) {
				isHitTouched = true;
			} 
			
			if(OverlapTester.pointInRectangle(throwBounds, touchPoint)) {
				isThrowTouched = true;
			}
		}

		world.update(deltaTime);
		if(world.score != lastScore) {
			lastScore = world.score;
			if(lastScore < 10) {
				scoreString = "SCORE: " + lastScore;
			} else {
				scoreString = "SCORE:" + lastScore;
			}
		}
		if(World.counter != lastTime) {
			lastTime = World.counter;
			if(lastTime < 10) {
				timeString = "TIME: " + lastTime;
			} else {
				timeString = "TIME:" + lastTime;
			}
		}
		if(world.state == World.WORLD_STATE_GAME_CLASH) {
			state = GAME_CLASH;
			missScore++;
		}
		if(world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			scoreString = "SCORE: " + lastScore;
			Settings.addScore(lastScore, missScore);
			Settings.save(game.getFileIO());
		}
		
		isHitTouched = false;
		isThrowTouched = false;
		isRecovered = false;
		isOpened = false;
		isReady = false;
		isGo = false;
	}
	
	private void updatePaused() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;

			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if(OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}
	
			if(OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}
	
	private void updateGameClash(float deltaTime) {
		world.updateTime(deltaTime);
		world.checkGameOver();
		if(World.counter != lastTime) {
			lastTime = World.counter;
			if(lastTime < 10) {
				timeString = "TIME: " + lastTime;
			} else {
				timeString = "TIME:" + lastTime;
			}
		}
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			if(OverlapTester.pointInRectangle(centerBounds, touchPoint)) {
				touchCounter++;
				if(touchCounter < 5) {
					worldListener.canHit();
				}
				if(touchCounter == 5) {
					worldListener.canOpen();
				}
				return;
			}
			if(OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}
		}
		if(touchCounter > 4) {
			isOpened = true;
		}
		if(touchCounter > 5) {
			touchCounter = 0;
			state = GAME_RUNNING;
			world.state = World.WORLD_STATE_RUNNING;
			World.isGameClash = false;
			isRecovered = true;
		}
		if(world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			scoreString = "SCORE: " + lastScore;
			Settings.addScore(lastScore, missScore);
			Settings.save(game.getFileIO());
		}
	}
	
	private void updateGameOver() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			if(OverlapTester.pointInRectangle(nextBounds, touchPoint)) {
				if(1 < GameScreen.missScore) {
					if(GameScreen.lastScore < 11) {
						game.setScreen(new EndScreen1(game));
					} else if(GameScreen.lastScore < 20) {
						game.setScreen(new EndScreen2(game));
					} else if(GameScreen.lastScore < 40) {
						game.setScreen(new EndScreen3(game));
					} else if(GameScreen.lastScore < 50) {
						game.setScreen(new EndScreen4(game));
					} else {
						game.setScreen(new EndScreen5(game));
					} 
				} else if(GameScreen.missScore == 1) {
					if(GameScreen.lastScore < 20) {
						game.setScreen(new EndScreen2(game));
					} else if(GameScreen.lastScore < 40) {
						game.setScreen(new EndScreen3(game));
					} else if(GameScreen.lastScore < 50) {
						game.setScreen(new EndScreen4(game));
					} else {
						game.setScreen(new EndScreen5(game));
					} 
				} else if(GameScreen.missScore < 1) {
					if(GameScreen.lastScore < 20) {
						game.setScreen(new EndScreen2(game));
					} else if(GameScreen.lastScore < 40) {
						game.setScreen(new EndScreen3(game));
					} else if(GameScreen.lastScore < 50) {
						game.setScreen(new EndScreen4(game));
					} else if(GameScreen.lastScore < 60) {
						game.setScreen(new EndScreen5(game));
					} else {
						game.setScreen(new EndScreen6(game));
					}
				}
				Assets.playSound(Assets.clickSound);
				return;
			}
		}
	}
	
	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		switch(state) {
		case GAME_RUNNING:
			renderer.render();
			break;
		case GAME_PAUSED:
		case GAME_CLASH:
			renderer.darkRender();
			break;
		case GAME_READY:
		case GAME_OVER:
			renderer.lightRender();
		}
		
		guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.items01);
		switch(state) {
		case GAME_READY:
			presentReady(deltaTime);
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			batcher.endBatch();
			gl.glDisable(GL10.GL_BLEND);
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			batcher.beginBatch(Assets.items);
			batcher.drawSprite(160, 395 - 25, 57, 50, Assets.pauseOff2);
			break;
		case GAME_CLASH:
			presentGameClash(deltaTime);
			break;
		case GAME_OVER:
			presentGameOver();
		}
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
		fpsCounter.logFrame();
	}
	
	private void presentReady(float deltaTime) {
		batcher.drawSprite(160, 190, 320, 3, Assets.bar);
		
		TextureRegion keyFrame;
		if(!isReady) {
			batcher.drawSprite(160, 240, 304, 180, Assets.ready);
		} else {
			if(!isGo) {
				keyFrame = Assets.startAnim.getKeyFrame(counter, Animation.ANIMATION_NONLOOPING);
				batcher.drawSprite(160, 240, 200, 200, keyFrame);
			} else {
				batcher.drawSprite(160, 240, 304, 180, Assets.go);
			}
		}
			
		batcher.drawSprite(320 - 50, 50, 100, 100, Assets.hitOn);
		batcher.drawSprite(160, 40, 80, 80, Assets.canOff);
		batcher.drawSprite(50, 50, 100, 100, Assets.throwOn);
		Assets.font.drawText(batcher, scoreString, 16, 480 - 70, 20, 30);
		Assets.font.drawText(batcher, timeString, 180, 480 - 70, 20, 30);
	}
	
	private void presentRunning() {
		batcher.drawSprite(160, 400 - 25, 66, 55, Assets.pauseOn);
		batcher.drawSprite(160, 190, 320, 3, Assets.bar);
		batcher.drawSprite(320 - 50, 50, 100, 100, Assets.hitOn);
		batcher.drawSprite(160, 40, 80, 80, Assets.canOff);
		batcher.drawSprite(50, 50, 100, 100, Assets.throwOn);
		Assets.font.drawText(batcher, scoreString, 16, 480 - 70, 20, 30);
		Assets.font.drawText(batcher, timeString, 180, 480 - 70, 20, 30);
	}	
	
	private void presentPaused() {
		batcher.drawSprite(160, 240, 240, 180, Assets.pauseMenu);
		batcher.drawSprite(320 - 50, 50, 100, 100, Assets.hitOff);
		batcher.drawSprite(160, 40, 80, 80, Assets.canOff);
		batcher.drawSprite(50, 50, 100, 100, Assets.throwOff);	
		Assets.font.drawText(batcher, scoreString, 16, 480 - 70, 20, 30);
		Assets.font.drawText(batcher, timeString, 180, 480 - 70, 20, 30);
	}

	private void presentGameClash(float deltaTime) {
		stateTime += deltaTime;
		TextureRegion keyFrame;
		if(!isOpened) {
			keyFrame = Assets.pound.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
		} else {
			keyFrame = Assets.pound.getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
		}
		
		batcher.drawSprite(160, 220, 199, 203, keyFrame);
		batcher.drawSprite(320 - 50, 50, 100, 100, Assets.hitOff);
		batcher.drawSprite(50, 50, 100, 100, Assets.throwOff);	
		if(!isOpened) {
			batcher.drawSprite(160, 40, 80, 80, Assets.canOn);
		} else {
			batcher.drawSprite(160, 60, 80, 120, Assets.canOpen);
		}
		Assets.font.drawText(batcher, scoreString, 16, 480 - 70, 20, 30);
		Assets.font.drawText(batcher, timeString, 180, 480 - 70, 20, 30);
	}

	private void presentGameOver() {
		batcher.drawSprite(160, 220, 250, 320, Assets.gameOver);
		batcher.drawSprite(160, 25, 66, 50, Assets.next);
		float scoreWidth = Assets.font.glyphWidth * scoreString.length();
		Assets.font.drawText(batcher, scoreString, 160 - scoreWidth / 2, 430 - 20, 24, 30);
	}
	
	@Override
	public void pause() {
		if(state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
