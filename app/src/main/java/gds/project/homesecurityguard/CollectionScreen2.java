package gds.project.homesecurityguard;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import gds.project.framework.Game;
import gds.project.framework.Input.TouchEvent;
import gds.project.framework.gl.Camera2D;
import gds.project.framework.gl.SpriteBatcher;
import gds.project.framework.impl.GLScreen;
import gds.project.framework.math.OverlapTester;
import gds.project.framework.math.Rectangle;
import gds.project.framework.math.Vector2;

public class CollectionScreen2 extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle nextBounds;
	Vector2 touchPoint;
	
	public CollectionScreen2(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, 320, 480);
		nextBounds = new Rectangle(320 - 66, 0, 66, 50);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 1);
	}
	
	@Override
	public void resume() {
	}
	@Override
	public void pause() {
	}
	
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			
			if(OverlapTester.pointInRectangle(nextBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new CollectionScreen(game));
				return;
			}
		}
	}
	
	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);

		if(CollectionScreen.isEnd1touched) {
			presentEnd1();
		} else if(CollectionScreen.isEnd2touched) {
			presentEnd2();
		} else if(CollectionScreen.isEnd3touched) {
			presentEnd3();
		} else if(CollectionScreen.isEnd4touched) {
			presentEnd4();
		} else if(CollectionScreen.isEnd5touched) {
			presentEnd5();
		} else if(CollectionScreen.isEnd6touched) {
			presentEnd6();
		} else {
			batcher.beginBatch(Assets.background);
			batcher.drawSprite(160, 240, 320, 480, Assets.backgroundRegion);
		}
		
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items);
		batcher.drawSprite(320 - 33, 25, 66, 50, Assets.next2);
		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void presentEnd1() {
		batcher.beginBatch(Assets.end1);
		batcher.drawSprite(160, 240, 320, 480, Assets.end1Region);
	}
	
	private void presentEnd2() {
		batcher.beginBatch(Assets.end2);
		batcher.drawSprite(160, 240, 320, 480, Assets.end2Region);
	}
	
	private void presentEnd3() {
		batcher.beginBatch(Assets.end3);
		batcher.drawSprite(160, 240, 320, 480, Assets.end3Region);
	}
	
	private void presentEnd4() {
		batcher.beginBatch(Assets.end4);
		batcher.drawSprite(160, 240, 320, 480, Assets.end4Region);
	}
	
	private void presentEnd5() {
		batcher.beginBatch(Assets.end5);
		batcher.drawSprite(160, 240, 320, 480, Assets.end5Region);
	}
	
	private void presentEnd6() {
		batcher.beginBatch(Assets.end6);
		batcher.drawSprite(160, 240, 320, 480, Assets.end6Region);
	}

	@Override
	public void dispose() {
	}	
}
