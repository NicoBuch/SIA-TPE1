package simple_square;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class SimpleSquaresRule implements GPSRule {
	Position from;

	public SimpleSquaresRule(int xfrom, int yfrom) {
		from = new Position(xfrom, yfrom);
	}

	@Override
	public Integer getCost() {
		return 1;
	}

	@Override
	public String getName() {
		return "Move block in position: " + from.toString();
	}

	@Override
	public GPSState evalRule(GPSState state) throws NotAppliableException {
		GPSState newState = state.clone();
		Block current = newState.getBlockAt(from);
		if( current == null)
			throw new NotAppliableException();
		Direction dir = current.getDirection();
		while(current != null){
			Position pos = current.getNextPosition(dir);
			if(isOutOfBounds(pos)){
				throw new NotAppliableException();
			}
			Block next = newState.getBlockAt(pos);
			current.move(dir);
			if(newState.getArrowAt(current.getPosition()) != null){
				current.rotate(newState.getArrowAt(current.getPosition()).getDir());
			}
			current = next;
		}
//		System.out.println((from.getX()) + ", " + (from.getY()));
		return newState;		
	}
	
	private boolean isOutOfBounds(Position pos){
		if(pos.getX() < 0 || pos.getX() >= SimpleSquaresProblem.MAX){
			return true;
		}
		if(pos.getY() < 0 || pos.getY() >= SimpleSquaresProblem.MAX){
			return true;
		}
		return false;
	}

}
