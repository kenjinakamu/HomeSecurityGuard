package gds.project.homesecurityguard;

import gds.project.framework.GameObject;

public class Enemy extends GameObject {
	public static final float ENEMY_WIDTH = 2.2f;
	public static final float ENEMY_HEIGHT = 2.2f;
	public static final int ENEMY_1 = 0;
	public static final int ENEMY_2 = 1;
	public static final int ENEMY_3 = 2;
	
	float stateTime;
	int type;
	
	public Enemy(int type,float x, float y) {
		super(x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
		stateTime = 0;
		this.type = type;
	}
	
	public void update(float deltaTime) {
		stateTime += deltaTime;
	}
}
