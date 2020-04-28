package gds.project.homesecurityguard;

import javax.microedition.khronos.opengles.GL10;

import gds.project.framework.gl.Animation;
import gds.project.framework.gl.Camera2D;
import gds.project.framework.gl.SpriteBatcher;
import gds.project.framework.gl.TextureRegion;
import gds.project.framework.impl.GLGraphics;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	GLGraphics glGraphics;
	World world;
	Camera2D cam;
	SpriteBatcher batcher;

	public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
		this.glGraphics = glGraphics;
		this.world = world;
		this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.batcher = batcher;
	}	

	public void render() {
		cam.setViewportAndMatrices();
		renderBackground();
		renderObjects();
	}
	public void lightRender() {
		cam.setViewportAndMatrices();
		renderBackground();
	}
	
	public void darkRender() {
		cam.setViewportAndMatrices();
		batcher.beginBatch(Assets.darkScreen);
		batcher.drawSprite(cam.position.x, cam.position.y,
				FRUSTUM_WIDTH, FRUSTUM_HEIGHT,
				Assets.darkRegion);
		batcher.endBatch();
	}
	
	public void renderBackground() {
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(cam.position.x, cam.position.y,
				FRUSTUM_WIDTH, FRUSTUM_HEIGHT,
				Assets.backgroundRegion);
		batcher.endBatch();
	}	
	
	public void renderObjects() {
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items01);
		renderEnemies();
		renderFoods();
		renderTentacles();
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void renderTentacles() {
		int len = world.tentacles.size();
		TextureRegion keyFrame;
		for(int i = 0; i < len; i++) {
			Tentacle tentacle = world.tentacles.get(i);
			switch(tentacle.type) {
			case Tentacle.TENTACLE_HIT:
				keyFrame = Assets.hitAnim.getKeyFrame(tentacle.stateTime, Animation.ANIMATION_NONLOOPING);
				batcher.drawSprite(tentacle.position.x, tentacle.position.y, 3.7f, 5.6f, keyFrame);
				break;
				
			case Tentacle.TENTACLE_THROW:
				keyFrame = Assets.throwAnim.getKeyFrame(tentacle.stateTime, Animation.ANIMATION_NONLOOPING);
				batcher.drawSprite(tentacle.position.x, tentacle.position.y, 3.7f, 5.6f, keyFrame);
			}
		}
	}
	
	private void renderFoods() {
		int len = world.foods.size();
		TextureRegion keyFrame;
		for(int i = 0; i < len; i++) {
			Food food = world.foods.get(i);
			
			switch(food.type) {
			case Food.FOOD_1:
				keyFrame = Assets.food1;
				batcher.drawSprite(food.position.x, food.position.y, 2.5f, 2.5f, keyFrame);
				break;
				
			case Food.FOOD_2:
				keyFrame = Assets.food2;
				batcher.drawSprite(food.position.x, food.position.y, 2.5f, 2.5f, keyFrame);
				break;
				
			case Food.FOOD_3:
				keyFrame = Assets.food3;
				batcher.drawSprite(food.position.x, food.position.y, -2.5f, 2.5f, keyFrame);
			}
		}
	}	
	
	private void renderEnemies() {
		int len = world.enemies.size();
		TextureRegion keyFrame;
		for(int i = 0; i < len; i++) {
			Enemy enemy = world.enemies.get(i);
			
			switch(enemy.type) {
			case Enemy.ENEMY_1:
				keyFrame = Assets.enemy1.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING);
				batcher.drawSprite(enemy.position.x, enemy.position.y, 2.5f, 2.5f, keyFrame);
				break;
				
			case Enemy.ENEMY_2:
				keyFrame = Assets.enemy2.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING);
				batcher.drawSprite(enemy.position.x, enemy.position.y, -2.5f, 2.5f, keyFrame);
				break;
				
			case Enemy.ENEMY_3:
				keyFrame = Assets.enemy3.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING);
				batcher.drawSprite(enemy.position.x, enemy.position.y, 2.5f, 2.5f, keyFrame);
			}
		}
	}
}
