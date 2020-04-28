package gds.project.homesecurityguard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gds.project.framework.math.OverlapTester;
import gds.project.framework.math.Rectangle;

public class World {
	public interface WorldListener {
		public void hit();
		public void throwing();
		public void canHit();
		public void canOpen();
	}
	
	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_GAME_CLASH = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int WORLD_TIME = 30;
	public static final int WORLD_SCORE = 1;

	public final List<Tentacle> tentacles;
	public final List<Enemy> enemies;
	public final List<Food> foods;
	public final WorldListener listener;
	public final Random rand;

	public int score;
	public int state;
	float counterTime;
	
	static int counter;
	static boolean isGameClash;
	boolean isTouched;
	boolean isMaked;
	
	Rectangle targetBounds;
	
	public World(WorldListener listener) {
		this.tentacles = new ArrayList<Tentacle>();
		this.enemies = new ArrayList<Enemy>();
		this.foods = new ArrayList<Food>();
		this.listener = listener;
		rand = new Random();
		generateLevel();

		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
		World.counter = WORLD_TIME;
		isGameClash = false;
		isTouched = false;
		isMaked = false;
		this.targetBounds = new Rectangle(0, 3, 10, 3);
		
		counterTime = 0;
	}	
	
	private void generateLevel() {
		float y = WORLD_HEIGHT * 20 + WORLD_HEIGHT / 5/ 2;
		int enemyType, foodType;
		float x;
		float randType;
		
		while(y > WORLD_HEIGHT / 4) {
			randType = rand.nextFloat();
			if(randType < 0.3) {
				enemyType = Enemy.ENEMY_1;
				foodType = Food.FOOD_1;
			} else if(randType < 0.6) {
				enemyType = Enemy.ENEMY_2;
				foodType = Food.FOOD_2;
			} else {
				enemyType = Enemy.ENEMY_3;
				foodType = Food.FOOD_3;
			}
			x = rand.nextFloat() > 0.5f ?  
					WORLD_WIDTH / 4 : WORLD_WIDTH * 3 / 4;
			if(rand.nextFloat() > 0.5f) {
				Enemy enemy = new Enemy(enemyType, x, y);
				enemies.add(enemy);
			} else {
				Food food = new Food(foodType, x, y);
				foods.add(food);
			}
			y -= WORLD_HEIGHT / 5;
		}
	}

	public void update(float deltaTime) {
		updateTentacles(deltaTime);
		updateEnemies(deltaTime);
		updateFoods(deltaTime);
		checkCollisions(deltaTime);
		checkGameOver();
		updateTime(deltaTime);
		isTouched = false;
	}
	
	public void updateTime(float deltaTime) {
		counterTime += deltaTime;
		if(counterTime > 1f) {
			counter--;
			counterTime = 0;
		}
		if(counter < 0) {
			counter = 0;
		}
	}
	
	private void updateTentacles(float deltaTime) {
		int len = tentacles.size();
		for(int i = 0; i < len; i++) {
			Tentacle tentacle = tentacles.get(i);
			tentacle.update(deltaTime);
			if(tentacle.stateTime > 0.3f) {
				if(!isMaked) {
					isTouched = true;
				}
				if(tentacle.stateTime > 0.4f) {
					isMaked = false;
					tentacle.remove();
					tentacle.position.x = -10;
				}
			}
		}
	}
	
	private void updateEnemies(float deltaTime) {
		int len = enemies.size();
		for(int i = 0; i < len; i++) {
			Enemy enemy = enemies.get(i);
			enemy.update(deltaTime);
			if(isTouched) {
				enemy.position.y -= WORLD_HEIGHT / 5;
				isMaked = true;
			}
			if(GameScreen.isRecovered) {
				enemy.position.y -= WORLD_HEIGHT / 5;
			}
		}
	}
	
	private void updateFoods(float deltaTime) {
		int len = foods.size();
		for(int i = 0; i < len; i++) {
			Food food = foods.get(i);
			food.update(deltaTime);
			if(isTouched) {
				if(food.state == Food.FOOD_STATIC) {
					food.position.y -= WORLD_HEIGHT / 5;
					isMaked = true;
				}
			}
			if(GameScreen.isRecovered) {
				food.position.y -= WORLD_HEIGHT / 5;
			}
			if(food.position.x > WORLD_WIDTH + Food.FOOD_WIDTH / 2) {
				foods.remove(food);
				len = foods.size();
				score += WORLD_SCORE;
			}
		}
	}
	
	private void checkCollisions(float deltaTime) {
		checkClash();
		checkEnemyCollisions();
		checkFoodCollisions(deltaTime);
	}

	private void checkEnemyCollisions() {
		int len = enemies.size();
		for(int i = 0; i < len; i++) {
			Enemy enemy = enemies.get(i);
			if(OverlapTester.pointInRectangle(targetBounds, enemy.position)) {
				if(GameScreen.isThrowTouched) {
					enemies.remove(enemy);
					len = enemies.size();
					isGameClash = true;
					return;
				}
				if(GameScreen.isHitTouched) {
					Tentacle tentacle = new Tentacle(Tentacle.TENTACLE_HIT, 
							enemy.position.x, 3.5f);
					tentacles.add(tentacle);
					enemies.remove(enemy);
					len = enemies.size();
					listener.hit();
					score += WORLD_SCORE;
				}
			}
		}
	}

	private void checkFoodCollisions(float deltaTime) {
		int len = foods.size();
		for(int i = 0; i < len; i++) {
			Food food = foods.get(i);
			switch(food.state) {
			case Food.FOOD_STATIC:
				if(OverlapTester.pointInRectangle(targetBounds, food.position)) {
					if(GameScreen.isHitTouched) {
						foods.remove(food);
						len = foods.size();
						isGameClash = true;
						return;
					}
					if(GameScreen.isThrowTouched) {
						Tentacle tentacle = new Tentacle(Tentacle.TENTACLE_THROW, 
								food.position.x - 1, 3.5f);
						tentacles.add(tentacle);
						food.move();
						listener.throwing();
					}
				}
				break;
				
			case Food.FOOD_MOVE:
				if(food.stateTime > 0.3f) {
					food.velocity.x = Food.FOOD_VELOCITY;
				}
			}
		}
	}
	
	private void checkClash() {
		if(isGameClash) {
			state = WORLD_STATE_GAME_CLASH;
		}
	}
	
	public void checkGameOver() {
		if (World.counter <= 0) {
			state = WORLD_STATE_GAME_OVER;
		}
	}	
}
