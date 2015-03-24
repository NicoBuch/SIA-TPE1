package gps.api;

import java.util.List;

import simple_square.Arrow;
import simple_square.Block;
import simple_square.Position;

/**
 * GPSState interface.
 */
public interface GPSState {
	
	/**
	 * Compares self to another state to determine
	 * whether they are the same or not.
	 * @param state The state to compare to.
	 * @return true if self is the same as the state given,
	 * false if they are different.
	 */
	boolean compare(GPSState state);
	
	List<Block> getBlocks();
	
	List<Arrow> getArrows();

	Block getBlockAt(Position at);
	Arrow getArrowAt(Position at);
	
	GPSState clone();
	
	Block getMove(GPSState state);
	
}
