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

public class HelpScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle hitBounds;
	Vector2 touchPoint;
	Texture helpImage;
	TextureRegion helpRegion;
	
	public HelpScreen(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, 320, 480);
		hitBounds = new Rectangle(320 - 100, 0, 100, 100);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 1);
	}
	
	@Override
	public void resume() {
		helpImage = new Texture(glGame, "help01.png" );
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
			
			if(OverlapTester.pointInRectangle(hitBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HelpScreen2(game));
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
	}

	@Override
	public void dispose() {
	}	
}
