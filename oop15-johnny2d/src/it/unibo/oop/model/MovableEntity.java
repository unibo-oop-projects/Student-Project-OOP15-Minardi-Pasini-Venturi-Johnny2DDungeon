package it.unibo.oop.model;
import it.unibo.oop.utilities.Direction;
import it.unibo.oop.utilities.Position;
import it.unibo.oop.utilities.Vector2;
import it.unibo.oop.utilities.Velocity;

public abstract class MovableEntity extends AbstractEntity implements Movable {

	protected Vector2 movementVector;
	protected Velocity speedValues;
	
	public MovableEntity(int startingX, int startingY, Vector2 movementVector) {
		super(startingX,startingY);
		this.movementVector = movementVector;
	}
	
	public MovableEntity(int startingX, int startingY, Vector2 movementVector, Velocity speedValue) {
		this(startingX,startingY, movementVector);
		this.speedValues = speedValue;		
	}		
	
	public void move(){
		this.entityPosition = this.getPosition().sumVector(movementVector); 
	}
	
	public void setInput(Vector2 newMovement){
		this.movementVector = newMovement;
	}
	
	public abstract boolean checkCollision(Position newPosition);
	
	public void setDirection(Vector2 newMovement){
		this.movementVector = newMovement;
	}
	
	public Vector2 getDirection(){
		return this.movementVector;
	}
	
	public void setVelocity(Velocity newVelocity){
		this.speedValues = newVelocity;
	}
	
	public Velocity getVelocity(){
		return this.speedValues;
	}
	
	public Direction getFaceDirection(){
		
		if (Math.abs(this.movementVector.getX()) > Math.abs(this.movementVector.getY())){
			if (this.movementVector.getX() > 0){
				return Direction.RIGHT; 
			} else {
				return Direction.LEFT;
			}
		} else{
			if (this.movementVector.getY() > 0){
				return Direction.DOWN; 
			} else {
				return Direction.UP;
			}
		}
	}
}
