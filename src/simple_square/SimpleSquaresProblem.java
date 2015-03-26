package simple_square;

import gps.GPSNode;
import gps.Heuristic;
import gps.SearchStrategy;
import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleSquaresProblem implements GPSProblem {
	public static final int MAX = 10;
	public static final Position INITIAL_POSITION = new Position(2, 2);
	public static final Integer HEURISTIC_MAX = Integer.MAX_VALUE;
	public static Position max_position;
	private static SearchStrategy strategy;
	private static String filepath;
	private static Heuristic heuristic;

	public SimpleSquaresProblem(SearchStrategy s, String boardName, Heuristic h) {
		super();
		this.strategy = s;
		this.heuristic = h;
		this.filepath = "src/simple_square/board/" + boardName + ".txt";

	}

	@Override
	public GPSState getInitState() {
		List<Block> blocks = new ArrayList<Block>();
		List<Arrow> arrows = new ArrayList<Arrow>();
		System.out.println(strategy);
		BufferedReader br;
		String curline;
		try {
			br = new BufferedReader(new FileReader(filepath));
			while ((curline = br.readLine()) != null) {
				String[] sline = curline.split(",");

				if (sline[0].toLowerCase().equals("block")) {
					Direction dir;
					if (sline[3].toLowerCase().equals("right")) {
						dir = Direction.RIGHT;
					} else if (sline[3].toLowerCase().equals("left")) {
						dir = Direction.LEFT;
					} else if (sline[3].toLowerCase().equals("up")) {
						dir = Direction.UP;
					} else {
						dir = Direction.DOWN;
					}
					System.out.println("bloque");
					Position target = null;
					if (!sline[4].equals("null")) {
						target = new Position(Integer.valueOf(sline[4]),
								Integer.valueOf(sline[5]));
					}
					blocks.add(new Block(new Position(
							Integer.valueOf(sline[1]), Integer
									.valueOf(sline[2])), dir, target));

				} else if (sline[0].toLowerCase().equals("arrow")) {
					Direction dir;
					if (sline[3].toLowerCase().equals("right")) {
						dir = Direction.RIGHT;
					} else if (sline[3].toLowerCase().equals("left")) {
						dir = Direction.LEFT;
					} else if (sline[3].toLowerCase().equals("up")) {
						dir = Direction.UP;
					} else {
						dir = Direction.DOWN;
					}
					System.out.println("flecha");
					arrows.add(new Arrow(new Position(
							Integer.valueOf(sline[1]), Integer
									.valueOf(sline[2])), dir));
				} else if (sline[0].toLowerCase().equals("max")) {
					System.out.println("max");
					max_position = new Position(Integer.valueOf(sline[1]),
							Integer.valueOf(sline[2]));
				}
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new SimpleSquaresState(blocks, arrows);
	}

	@Override
	public GPSState getGoalState() {
		List<Block> blocks = new ArrayList<Block>();
		List<Arrow> arrows = new ArrayList<Arrow>();
		return new SimpleSquaresState(blocks, arrows);
	}

	@Override
	public boolean isGoalState(GPSState state) {
		for (Block block : state.getBlocks()) {
			if (!block.isInGoal()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<GPSRule> getRules() {
		List<GPSRule> rules = new ArrayList<GPSRule>();

		for (int i = 0; i < MAX; i++)
			for (int j = 0; j < MAX; j++) {
				rules.add(new SimpleSquaresRule(i, j));
			}
		return rules;
	}

	public static Integer getHValue(GPSState state) {
		Integer value = 0;
		List<Double> manDistList = new ArrayList<Double>();
		for (Block b : state.getBlocks()) {
			if (b.getPosition().isAtLeftFrom(INITIAL_POSITION)
					&& !b.getDirection().equals(Direction.RIGHT)) {
				return HEURISTIC_MAX;
			}
			if (b.getPosition().isAtUpFrom(INITIAL_POSITION)
					&& !b.getDirection().equals(Direction.DOWN)) {
				return HEURISTIC_MAX;
			}
			if (b.getPosition().isAtRightFrom(max_position)
					&& !b.getDirection().equals(Direction.LEFT)) {
				return HEURISTIC_MAX;
			}
			if (b.getPosition().isAtDownFrom(max_position)
					&& !b.getDirection().equals(Direction.UP)) {
				return HEURISTIC_MAX;
			}
			if (heuristic.equals(Heuristic.MinDistance)) {
				value += b.getManhattanDistanceToObjective();
			} else if(heuristic.equals(Heuristic.InPath)) {
				SimpleSquaresState aux = (SimpleSquaresState) state;
				value += aux.isInPath(b);
			} else if(heuristic.equals(Heuristic.OneDistance)){
				manDistList.add(b.getDistanceToObjective());
			}
			else if(heuristic.equals(Heuristic.AdmisibleMinDistance)){
				int currentValue = b.getManhattanDistanceToObjective();
				if(currentValue != 0){
					int blocksInArea = 1;
					for(Block block : state.getBlocks()){
						if(!block.equals(b) && block.getPosition().isInArea(b.getPosition(), b.getTargetPosition())){
							blocksInArea ++;
						}
					}
					value += (currentValue / blocksInArea);
				}
			}

		}
		if(heuristic.equals(Heuristic.OneDistance)){
			value = Collections.min(manDistList).intValue();
		}
		return value;
	}

	public static SearchStrategy getStrategy() {
		return strategy;
	}

}
