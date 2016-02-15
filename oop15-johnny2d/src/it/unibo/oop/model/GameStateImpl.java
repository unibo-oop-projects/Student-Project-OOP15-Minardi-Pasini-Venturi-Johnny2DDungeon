package it.unibo.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.oop.utilities.Direction;

public class GameStateImpl implements GameState {

    private static final GameState SINGLETON = new GameStateImpl();
    private List<MovableEntity> movableList;
    private List<AbstractEntity> stableList;
    private Optional<MainCharacter> johnnyCharacter;
    
    private GameStateImpl() {
        this.movableList = new ArrayList<>();
        this.stableList = new ArrayList<>();
        johnnyCharacter = Optional.of(new MainCharacter());
     
    }
    
    public static GameState getInstance() {
        return SINGLETON;
    }
    
    @Override
    public void initialize(int levelNumber) {
    }

    @Override
    public void removeEntity(Entity entity) {
    }

    @Override
    public void updatePositions() {
    }

    public void updateHeroPos(final Direction newDirection, final boolean isShooting ) {
        johnnyCharacter.ifPresent(c -> c.update(newDirection, isShooting));
    }

	public void addShoot(Bullet newBullet) {
		this.movableList.add(newBullet);
	}

	public List<AbstractEntity> getStableList() {
		return new ArrayList<>(this.stableList);
	}
	
	public List<AbstractEntity> getMovableList() {
		return new ArrayList<>(this.movableList);
	}
	
	public Optional<MainCharacter> getMainChar() {
		return this.johnnyCharacter;
	}
    
}
