package it.unibo.oop.model;

import static it.unibo.oop.utilities.CharactersSettings.BULLET;
import static it.unibo.oop.utilities.CharactersSettings.MAIN_CHARACTER;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import it.unibo.oop.exceptions.CollisionHandlingException;
import it.unibo.oop.utilities.Position;
import it.unibo.oop.utilities.Vector2;

/**
 * Class representing the Bullet fired from an {@link Entity} that implements a
 * {@link Shooter}.
 */
public class Bullet extends MovableEntity implements Shot {

    private double remainingDistance = 600 + new Random().nextInt(400);

    public Bullet(final double startingX, final double startingY, final Vector2 movementVector) {
        super(startingX, startingY, movementVector, BULLET.getSpeed());
        this.setMovement(movementVector);
    }

    public Bullet(final MainCharacter heroPosition) {
        this(heroPosition.getX(), heroPosition.getY(), heroPosition.getMovement());
        // Takes the hero position
        this.setMovement(heroPosition.getLastDirection().getVector2());
        // The movement vector is in the same Hero direction but in another
        // speed values
        this.getMovement().setLength(this.getVelocity().getMinVelocity());
    }

    public void checkCollision(final Position newPosition) throws CollisionHandlingException {
        final Bullet tmpBullet = Factory.BulletFactory.createBullet(newPosition.getIntX(), newPosition.getIntY(), this.getMovement());
        // Counting how much walls it collides (Usually 1)
        if (!this.getEnvironment().getArena().isInside(tmpBullet)) {
        	this.killEntity(true);
        	throw new CollisionHandlingException("Next movement not inside the arena");
        }
        final long numWallCollisions = this.getEnvironment().getStableList().stream().filter(x -> x instanceof Wall)
                .filter(tmpBullet::intersecate).count();
        // Collects all the Enemies collided (usually 1)
        final List<AbstractEnemy> enemyCollisions = this.getEnvironment().getMovableList().stream()
                .filter(x -> x instanceof AbstractEnemy).filter(tmpBullet::intersecate).map(x -> (AbstractEnemy) x)
                .collect(Collectors.toList());
        // If collides a wall the bullet dies and gets removed
        if (numWallCollisions > 0) {
            this.killEntity(true); //this.removeFromEnvironment();
            throw new CollisionHandlingException("Next movement collides a wall");
        }
        // If the bullet collides with an enemy both die
        if (!enemyCollisions.isEmpty()) {
            // Calculates the score obtained killing the monsters
            final int tmpScore = enemyCollisions.stream().map(x -> x.getScoreValue()).reduce((x, y) -> x + y).get();
            this.getEnvironment().getMainChar().get().getScore().increaseScore(tmpScore);

            // Removes the monsters from the envirnoment
            enemyCollisions.stream().forEach(x -> x.killEntity(true));
            this.killEntity(true);
            // Throws the exception avoiding the next movement
            throw new CollisionHandlingException("This bullet collided an enemy");
        }
    }

    public void update() {
        try {
            // Calculates the new movement vector
            final Vector2 newMovement = this.getMovement()
                    .setLength(this.getVelocity().accelerate(this.getMovement().length()));
            // Check if there are collision in the new position
            this.checkCollision(this.getPosition().sumVector(newMovement));
            // moves if no exception
            this.setMovement(newMovement);
            this.move();
            this.remainingDistance -= this.getMovement().length();
            if (remainingDistance <= 0) {
            	this.killEntity(true);//this.removeFromEnvironment();
            }
        } catch (CollisionHandlingException e) {
            System.out.println(e.getMessage());
        }
    }

    protected int getEntityHeight() {
        return BULLET.getHeight();
    }

    @Override
    protected int getEntityWidth() {
        return BULLET.getWidth();
    }

    public double getRemainingDistance() {
        return this.remainingDistance;
    }
}
