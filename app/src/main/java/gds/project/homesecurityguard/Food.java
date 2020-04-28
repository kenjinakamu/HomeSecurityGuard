package gds.project.homesecurityguard;

import gds.project.framework.DynamicGameObject;

public class Food extends DynamicGameObject {
	public static final float FOOD_WIDTH = 2.2f;
	public static final float FOOD_HEIGHT = 2.2f;
	public static final float FOOD_VELOCITY = 18f;
	public static final int FOOD_1 = 0;
	public static final int FOOD_2 = 1;
	public static final int FOOD_3 = 2;
	public static final int FOOD_STATIC = 0;
	public static final int FOOD_MOVE = 1;
	
	float stateTime;
	int type;
	int state;
	
	public Food(int type, float x, float y) {
		super(x, y, FOOD_WIDTH, FOOD_HEIGHT);
		velocity.set(0, 0);
		stateTime = 0;
		this.type = type;
		this.state = FOOD_STATIC;
	}
	
	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(FOOD_WIDTH / 2, FOOD_HEIGHT / 2);
		stateTime += deltaTime;
	}
	
	public void move() {
		if(state == FOOD_STATIC) {
			state = FOOD_MOVE;
		}
		stateTime = 0;
	}
}
