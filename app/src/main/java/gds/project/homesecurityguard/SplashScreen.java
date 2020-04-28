package gds.project.homesecurityguard;

import javax.microedition.khronos.opengles.GL10;

import gds.project.framework.Game;
import gds.project.framework.gl.Camera2D;
import gds.project.framework.gl.SpriteBatcher;
import gds.project.framework.gl.Texture;
import gds.project.framework.gl.TextureRegion;
import gds.project.framework.impl.GLScreen;

public class SplashScreen extends GLScreen {
	float stateTime = 0;
	Camera2D guiCam;
	SpriteBatcher batcher;
	Texture splashImage;
	TextureRegion splashRegion;
	
	public SplashScreen(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, 320, 480);
		batcher = new SpriteBatcher(glGraphics, 1);
	}
	
	@Override
	public void resume() {
		splashImage = new Texture(glGame, "splash.png" );
		splashRegion = new TextureRegion(splashImage, 0, 0, 320, 480);
	}
	@Override
	public void pause() {
		splashImage.dispose();
	}
	
	@Override
	public void update(float deltaTime) {
		Assets.music.pause();
		stateTime += deltaTime;
		 if(stateTime > 5f) {
			 game.setScreen(new MainMenuScreen(game));
			 if(Settings.soundEnabled){
				 Assets.music.play();
			 }
		 }
	}
	
	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(splashImage);
		batcher.drawSprite(160, 240, 320, 480, splashRegion);
		batcher.endBatch();
	}

	@Override
	public void dispose() {
	}	
}

