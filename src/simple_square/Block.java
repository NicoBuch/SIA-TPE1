package simple_square;

public class Block implements Cloneable{
	private Position position;
	private Position targetPosition;
	private Direction direction;
	
	public Block(Position position, Direction direction, Position targetPosition){
		this.position = position;
		this.direction = direction;
		this.targetPosition = targetPosition;
	}
	
	public Position getPosition() {
		return position;
	}

	public Position getTargetPosition() {
		return targetPosition;
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void move(){
		this.position = position.move(direction);
	}
	
	public Position getNextPosition(Direction dir){
		return position.move(dir);	
	}
	
	public void move(Direction d){
		this.position = position.move(d);
	}
	
	public void rotate(Direction dir){
		this.direction = dir;
	}
	
	public boolean isInGoal(){
		if(targetPosition == null){
			return true;
		}
		return position.equals(targetPosition);
	}
	
	@Override
	public Block clone(){
		return new Block(this.position, this.direction, this.targetPosition);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result
				+ ((targetPosition == null) ? 0 : targetPosition.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (direction != other.direction)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (targetPosition == null) {
			if (other.targetPosition != null)
				return false;
		} else if (!targetPosition.equals(other.targetPosition))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "direction: " + direction.toString() + ", position: " + position.toString() +  ", Objective: " + targetPosition.toString();
	}

	public double getDistanceToObjective() {
		if (targetPosition == null)
			return 0;
		return Math.sqrt(Math.pow(position.x - targetPosition.x, 2) + Math.pow(position.y - targetPosition.y, 2));
	}
	
	public Block simulateMove(){
		return new Block(this.position.move(this.direction), this.direction,this.targetPosition);
	}

}
