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

public class Opening4 extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle nextBounds;
	Rectangle skipBounds;
	Vector2 touchPoint;
	Texture helpImage;
	TextureRegion helpRegion;
	
	public Opening4(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, 320, 480);
		nextBounds = new Rectangle(320 - 66, 0, 66, 50);
		skipBounds = new Rectangle(0, 0, 87, 50);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 2);
	}
	
	@Override
	public void resume() {
		helpImage = new Texture(glGame, "opening04.png" );
		helpRegion = new TextureRegion(helpImage, 0, 0, 320, 480);
	}
	@Override
	public void pause() {
		helpImage.dispose();
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
				game.setScreen(new GameScreen(game));
				return;
			}
			if(OverlapTester.pointInRectangle(skipBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new GameScreen(game));
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

		batcher.beginBatch(helpImage);
		batcher.drawSprite(160, 240, 320, 480, helpRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items01);
		batcher.drawSprite(320 - 33, 25, 66, 50, Assets.next);
		batcher.drawSprite(44, 25, 87, 50, Assets.skip);
		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void dispose() {
	}	
}
