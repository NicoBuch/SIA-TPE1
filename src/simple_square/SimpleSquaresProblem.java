package simple_square;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;

import java.util.ArrayList;
import java.util.List;

public class SimpleSquaresProblem implements GPSProblem {
	public static final int MAX = 10;
	
	@Override
	public GPSState getInitState() {
		List<Block> blocks = new ArrayList<Block>();
		List<Arrow> arrows = new ArrayList<Arrow>(); 
		
		//tablero 21
//		blocks.add(new Block (new Position (2,2), Direction.DOWN, new Position (3,2)));
//		blocks.add(new Block (new Position (4,2), Direction.RIGHT, new Position (2,3)));
//		blocks.add(new Block (new Position (4,4), Direction.UP, new Position (3,4)));
//		blocks.add(new Block (new Position (2,4), Direction.LEFT, new Position (4,3)));
//		arrows.add(new Arrow (new Position (2,2), Direction.DOWN));
//		arrows.add(new Arrow (new Position (4,2), Direction.RIGHT));
//		arrows.add(new Arrow (new Position (4,4), Direction.UP));
//		arrows.add(new Arrow (new Position (2,4), Direction.LEFT));
//		arrows.add(new Arrow (new Position (6,2), Direction.UP));
		//tablero 16
		blocks.add(new Block (new Position (2,2), Direction.DOWN, new Position (3,3)));
		blocks.add(new Block (new Position (4,4), Direction.UP, new Position (4,3)));
		arrows.add(new Arrow (new Position (2,5), Direction.LEFT));
		arrows.add(new Arrow (new Position (3,2), Direction.RIGHT));
		arrows.add(new Arrow (new Position (2,2), Direction.DOWN));
		// tablero 9
//		blocks.add(new Block (new Position (4,2), Direction.DOWN, new Position (4,4)));
//		blocks.add(new Block (new Position (6,2), Direction.RIGHT, new Position (4,5)));
//		arrows.add(new Arrow (new Position (6,4), Direction.UP));
//		arrows.add(new Arrow (new Position (6,2), Direction.RIGHT));
		
		//tablero 1
//		blocks.add(new Block (new Position (4,2), Direction.RIGHT, new Position (4,4)));

		//tablero 2
//		blocks.add(new Block (new Position (4,3), Direction.DOWN, new Position (5,3)));
//		blocks.add(new Block (new Position (5,4), Direction.UP, new Position (4,4)));
		//tablero 3
//		blocks.add(new Block (new Position (4,2), Direction.RIGHT, new Position (4,4)));
//		blocks.add(new Block (new Position (6,3), Direction.UP, new Position (4,3)));
//		blocks.add(new Block (new Position (5,5), Direction.LEFT, new Position (5,2)));
		//tablero 4
//		blocks.add(new Block (new Position (5,6), Direction.LEFT, new Position (5,2)));
//		blocks.add(new Block (new Position (2,5), Direction.DOWN, new Position (7,4)));
		//tablero 5
//		blocks.add(new Block (new Position (2,3), Direction.DOWN, new Position (3,3)));
//		blocks.add(new Block (new Position (3,2), Direction.RIGHT, new Position (5,5)));
//		blocks.add(new Block (new Position (3,4), Direction.DOWN, new Position (4,4)));
		
		
		
		
		return new SimpleSquaresState(blocks,arrows);
	}

	@Override
	public GPSState getGoalState() {
		List<Block> blocks = new ArrayList<Block>();
		List<Arrow> arrows = new ArrayList<Arrow>();
		return new SimpleSquaresState(blocks,arrows);
	}

	@Override
	public boolean isGoalState(GPSState state){
		for(Block block : state.getBlocks()){
			if(!block.isInGoal()){
				return false;
			}
		}
		return true;
	}
	@Override
	public List<GPSRule> getRules() {
		List<GPSRule> rules = new ArrayList<GPSRule>();
		
		for (int i = 0; i < MAX ; i++)
			for (int j = 0 ; j < MAX; j++){
				rules.add(new SimpleSquaresRule(i,j));
			}
		return rules;
	}

	@Override
	public Integer getHValue(GPSState state) {
		// TODO Auto-generated method stub
		return null;
	}

}
