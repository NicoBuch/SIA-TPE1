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
import java.util.List;

public class SimpleSquaresProblem implements GPSProblem {
	public static final int MAX = 10;
	public static final Position INITIAL_POSITION = new Position(2, 2);
	public static final Integer HEURISTIC_MAX = Integer.MAX_VALUE;
	public static Position max_position;
	public static SearchStrategy strategy;
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

		// tablero 21
		// max_position = new Position(6, 4);
		// blocks.add(new Block (new Position (2,2), Direction.DOWN, new
		// Position (3,2)));
		// blocks.add(new Block (new Position (4,2), Direction.RIGHT, new
		// Position (2,3)));
		// blocks.add(new Block (new Position (4,4), Direction.UP, new Position
		// (3,4)));
		// blocks.add(new Block (new Position (2,4), Direction.LEFT, new
		// Position (4,3)));
		// arrows.add(new Arrow (new Position (2,2), Direction.DOWN));
		// arrows.add(new Arrow (new Position (4,2), Direction.RIGHT));
		// arrows.add(new Arrow (new Position (4,4), Direction.UP));
		// arrows.add(new Arrow (new Position (2,4), Direction.LEFT));
		// arrows.add(new Arrow (new Position (6,2), Direction.UP));
		// tablero 16
		// max_position = new Position(4, 5);
		// blocks.add(new Block(new Position(2, 2), Direction.DOWN, new
		// Position(
		// 3, 3)));
		// blocks.add(new Block(new Position(4, 4), Direction.UP, new
		// Position(4,
		// 3)));
		// arrows.add(new Arrow(new Position(2, 5), Direction.LEFT));
		// arrows.add(new Arrow(new Position(3, 2), Direction.RIGHT));
		// arrows.add(new Arrow(new Position(2, 2), Direction.DOWN));

		// tablero 9
		//
		// max_position = new Position(6,5);
		// blocks.add(new Block (new Position (4,2), Direction.DOWN, new
		// Position (4,4)));
		// blocks.add(new Block (new Position (6,2), Direction.RIGHT, new
		// Position (4,5)));
		// arrows.add(new Arrow (new Position (6,4), Direction.UP));
		// arrows.add(new Arrow (new Position (6,2), Direction.RIGHT));

		// tablero 1
		// blocks.add(new Block (new Position (4,2), Direction.RIGHT, new
		// Position (4,4)));

		// tablero 2
		// blocks.add(new Block (new Position (4,3), Direction.DOWN, new
		// Position (5,3)));
		// blocks.add(new Block (new Position (5,4), Direction.UP, new Position
		// (4,4)));
		// tablero 3
		// blocks.add(new Block (new Position (4,2), Direction.RIGHT, new
		// Position (4,4)));
		// blocks.add(new Block (new Position (6,3), Direction.UP, new Position
		// (4,3)));
		// blocks.add(new Block (new Position (5,5), Direction.LEFT, new
		// Position (5,2)));
		// tablero 4
		// blocks.add(new Block (new Position (5,6), Direction.LEFT, new
		// Position (5,2)));
		// blocks.add(new Block (new Position (2,5), Direction.DOWN, new
		// Position (7,4)));
		// tablero 5
		// blocks.add(new Block (new Position (2,3), Direction.DOWN, new
		// Position (3,3)));
		// blocks.add(new Block (new Position (3,2), Direction.RIGHT, new
		// Position (5,5)));
		// blocks.add(new Block (new Position (3,4), Direction.DOWN, new
		// Position (4,4)));

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

	public static Integer getHValue(GPSNode node) {
		GPSState state = node.getState();
		Integer totalDistance = 0;
		for (Block b : state.getBlocks()) {
			if (heuristic.equals(Heuristic.MinDistance)) {
				totalDistance += (int) b.getDistanceToObjective();
			} else if(heuristic.equals(Heuristic.DEFAULT)) {

				if (b.getPosition().isAtLeftFrom(INITIAL_POSITION)
						&& b.getDirection().equals(Direction.LEFT)) {
					return HEURISTIC_MAX;
				}
				if (b.getPosition().isAtUpFrom(INITIAL_POSITION)
						&& b.getDirection().equals(Direction.UP)) {
					return HEURISTIC_MAX;
				}
				if (b.getPosition().isAtRightFrom(max_position)
						&& b.getDirection().equals(Direction.RIGHT)) {
					return HEURISTIC_MAX;
				}
				if (b.getPosition().isAtDownFrom(max_position)
						&& b.getDirection().equals(Direction.DOWN)) {
					return HEURISTIC_MAX;
				}
			}

		}
		return totalDistance;
	}

	public static SearchStrategy getStrategy() {
		return strategy;
	}

}
