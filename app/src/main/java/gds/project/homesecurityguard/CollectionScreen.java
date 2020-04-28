package gds.project.homesecurityguard;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import gds.project.framework.Game;
import gds.project.framework.Input.TouchEvent;
import gds.project.framework.gl.Camera2D;
import gds.project.framework.gl.SpriteBatcher;
import gds.project.framework.gl.Texture;
import gds.project.framework.gl.TextureRegion;
import gds.project.framework.impl.GLScreen;
import gds.project.framework.math.OverlapTester;
import gds.project.framework.math.Rectangle;
import gds.project.framework.math.Vector2;

public class CollectionScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	Texture helpImage;
	TextureRegion helpRegion;
	String collection;
	
	Rectangle nextBounds;
	Rectangle end1Bounds;
	Rectangle end2Bounds;
	Rectangle end3Bounds;
	Rectangle end4Bounds;
	Rectangle end5Bounds;
	Rectangle end6Bounds;
	
	static boolean isEnd1touched;
	static boolean isEnd2touched;
	static boolean isEnd3touched;
	static boolean isEnd4touched;
	static boolean isEnd5touched;
	static boolean isEnd6touched;
	
	GL10 gl;
	
	public CollectionScreen(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, 320, 480);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 100);
		collection = "CollectIon";
		nextBounds = new Rectangle(320 - 66, 0, 66, 50);
		end1Bounds = new Rectangle(57.5f - 45, 300 - 60, 90, 120);
		end2Bounds = new Rectangle(160 - 45, 300 - 60, 90, 120);
		end3Bounds = new Rectangle(262.5f - 45, 300 - 60, 90, 120);
		end4Bounds = new Rectangle(57.5f - 45, 145 - 60, 90, 120);
		end5Bounds = new Rectangle(160 - 45, 145 - 60, 90, 120);
		end6Bounds = new Rectangle(262.5f - 45, 145- 60, 90, 120);
		
		
	}
	
	@Override
	public void resume() {
	}
	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}
	
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			if(event.type != TouchEvent.TOUCH_UP) 
				return;
			
			if(OverlapTester.pointInRectangle(nextBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
			if(Settings.ends[0]) {
				if(OverlapTester.pointInRectangle(end1Bounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new CollectionScreen2(game));
					isEnd1touched = true;
					return;
				}
			}
			if(Settings.ends[1]) {
				if(OverlapTester.pointInRectangle(end2Bounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new CollectionScreen2(game));
					isEnd2touched = true;
					return;
				}
			}
			if(Settings.ends[2]) {
				if(OverlapTester.pointInRectangle(end3Bounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new CollectionScreen2(game));
					isEnd3touched = true;
					return;
				}
			}
			if(Settings.ends[3]) {
				if(OverlapTester.pointInRectangle(end4Bounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new CollectionScreen2(game));
					isEnd4touched = true;
					return;
				}
			}
			if(Settings.ends[4]) {
				if(OverlapTester.pointInRectangle(end5Bounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new CollectionScreen2(game));
					isEnd5touched = true;
					return;
				}
			}
			if(Settings.ends[5]) {
				if(OverlapTester.pointInRectangle(end6Bounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new CollectionScreen2(game));
					isEnd6touched = true;
					return;
				}
			}
		}
		
		isEnd1touched = false;
		isEnd2touched = false;
		isEnd3touched = false;
		isEnd4touched = false;
		isEnd5touched = false;
		isEnd6touched = false;
	}
	
	@Override
	public void present(float deltaTime) {
		gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.background);
		batcher.drawSprite(160, 240, 320, 480, Assets.backgroundRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(320 - 33, 25, 66, 50, Assets.pauseOff2);
		float collectionWidth = Assets.font2.glyphWidth * collection.length();
		Assets.font2.drawText(batcher, collection, 160 - collectionWidth / 2 + 10,
							430 - 30, 20, 30);
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
		
		if(Settings.ends[0]) {
			presentEnd1();
		}
		if(Settings.ends[1]) {
			presentEnd2();
		}
		if(Settings.ends[2]) {
			presentEnd3();
		}
		if(Settings.ends[3]) {
			presentEnd4();
		}
		if(Settings.ends[4]) {
			presentEnd5();
		}
		if(Settings.ends[5]) {
			presentEnd6();
		}
	}
	
	private void presentEnd1() {
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.end1);
		batcher.drawSprite(57.5f, 300, 90, 120, Assets.end1Region);
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void presentEnd2(){
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.end2);
		batcher.drawSprite(160, 300, 90, 120, Assets.end2Region);
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void presentEnd3() {
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.end3);
		batcher.drawSprite(262.5f, 300, 90, 120, Assets.end3Region);
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void presentEnd4() {
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.end4);
		batcher.drawSprite(57.5f, 145, 90, 120, Assets.end4Region);
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void presentEnd5() {
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.end5);
		batcher.drawSprite(160, 145, 90, 120, Assets.end5Region);
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void presentEnd6() {
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.end6);
		batcher.drawSprite(262.5f, 145, 90, 120, Assets.end6Region);
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void dispose() {
	}	
}
