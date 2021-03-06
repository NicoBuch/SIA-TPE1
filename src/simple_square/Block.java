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
//		return Math.sqrt(Math.pow(position.x - targetPosition.x, 2) + Math.pow(position.y - targetPosition.y, 2));
		return Math.abs(position.x - targetPosition.x) + Math.abs(position.y - targetPosition.y);
	}
	
	public int getManhattanDistanceToObjective() {
		if (targetPosition == null)
			return 0;
		return Math.abs(targetPosition.x - position.x) + Math.abs(targetPosition.y - position.y);
	}

	public boolean analyizeTotalArea(Block b) {
		Position from = b.getPosition();
		Position target = b.getTargetPosition();
		int difx = target.x - from.x;
		int dify = target.y - from.y;
		Direction direcICanMoveB = this.getDirection();
		
		//difx < 0 => target esta arriba de b
		//difx > 0 => target abajo de b
		// difx == 0 -> misma linea hor
		//dify < 0 => target esta a la izq de b
		//dify > 0 => target a la derecha de b
		// dify == 0 => target misma linea vert
	
		if(difx<0){
			//target arriba izquierda
			if(dify < 0){
				//
				if((this.getPosition().isAtDownFrom(from) && direcICanMoveB.equals(Direction.UP)) || (this.getPosition().isAtRightFrom(from) && direcICanMoveB.equals(Direction.LEFT)))
					return true;
			}
			//target esta arriba y en la misma linea vertical
			else if(dify == 0){
				if(this.getPosition().isAtDownFrom(from) && direcICanMoveB.equals(Direction.UP))
					return true;
			}
			//target arriba a la derecha
			else if (dify > 0){
				if((this.getPosition().isAtDownFrom(from) && direcICanMoveB.equals(Direction.UP)) || (this.getPosition().isAtLeftFrom(from) && direcICanMoveB.equals(Direction.RIGHT)))
					return true;
			}
		}
		else if (difx == 0){
			//misma linea horizontal y target a la derecha
			if(dify > 0){
				if(this.getPosition().isAtLeftFrom(from) && direcICanMoveB.equals(Direction.RIGHT))
					return true;
			}
			//misma linea horizontal y target a la izquierda
			else if (dify < 0){
				if(this.getPosition().isAtRightFrom(from) && direcICanMoveB.equals(Direction.LEFT))
					return true;
			}
			else if (dify == 0)
				return false;
		}
		else if (difx > 0){
			//target abajo izquierda
			if(dify < 0){
				if((this.getPosition().isAtRightFrom(from) && direcICanMoveB.equals(Direction.LEFT)) || (this.getPosition().isAtUpFrom(from) && direcICanMoveB.equals(Direction.DOWN)))
					return true;
			}
			//target abajo y en la misma linea vert
			else if (dify == 0){
				if(this.getPosition().isAtUpFrom(from) && direcICanMoveB.equals(Direction.DOWN))
					return true;
			}
			//target abajo derecha
			else if (dify > 0){
				if((this.getPosition().isAtUpFrom(from) && direcICanMoveB.equals(Direction.DOWN)) || (this.getPosition().isAtLeftFrom(from) && direcICanMoveB.equals(Direction.RIGHT)))
					return true;
			}
		}
		return false;
	}

}
