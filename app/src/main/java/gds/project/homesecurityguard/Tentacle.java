package gds.project.homesecurityguard;

import gds.project.framework.GameObject;

public class Tentacle extends GameObject {
	public static final float TENTACLE_WIDTH = 2.2f;
	public static final float TENTACLE_HEIGHT = 2.2f;
	public static final int TENTACLE_HIT = 0;
	public static final int TENTACLE_THROW = 1;
	public static final int TENTACLE_STATE_NORMAL = 0;
	public static final int TENTACLE_STATE_END = 1;
	
	float stateTime;
	int type;
	int state;
	
	public Tentacle(int type, float x, float y) {
		super(x, y, TENTACLE_WIDTH, TENTACLE_HEIGHT);
		stateTime = 0;
		this.type = type;
		this.state = TENTACLE_STATE_NORMAL;
	}
	
	public void update(float deltaTime) {
		if(state == TENTACLE_STATE_NORMAL)
			stateTime += deltaTime;
	}
	
	public void remove() {
		stateTime = 0;
		if(state == TENTACLE_STATE_NORMAL)
			state = TENTACLE_STATE_END;
	}
}
