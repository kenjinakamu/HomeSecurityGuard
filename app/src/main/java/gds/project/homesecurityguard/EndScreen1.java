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

public class EndScreen1 extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle nextBounds;
	Vector2 touchPoint;
	
	public EndScreen1(Game game) {
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
				game.setScreen(new MainMenuScreen(game));
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

		batcher.beginBatch(Assets.end1);
		batcher.drawSprite(160, 240, 320, 480, Assets.end1Region);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items01);
		batcher.drawSprite(320 - 33, 25, 66, 50, Assets.next);
		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void dispose() {
	}	
}
