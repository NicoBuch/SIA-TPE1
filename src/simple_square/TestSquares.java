package simple_square;

import gps.GPSEngine;
import gps.Heuristic;
import gps.SearchStrategy;

public class TestSquares {
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		GPSEngine engine = new SimpleSquaresEngine();

		String strat = args[1];
		Heuristic heuristic = Heuristic.DEFAULT;
		SearchStrategy strategy = SearchStrategy.BFS;
		String heur = "default";
		
		if (args.length > 2) {
			heur = args[2];
			if (heur.toString().equals("mindistance")) {
				heuristic = Heuristic.MinDistance;
				System.out.println("Min Distance");
			}
			else if(heur.toString().equals("inpath")){
				heuristic = Heuristic.InPath;
				System.out.println("In Path");
			}else if(heur.toString().equals("manDist")){
				heuristic = Heuristic.OneDistance;
				System.out.println("One Man Distance");
			}
			else if(heur.toString().equals("admisiblemindistance")){
				heuristic = Heuristic.AdmisibleMinDistance;
				System.out.println("Admisible Min Distance");
			}
			
		} else {
			heuristic = Heuristic.DEFAULT;
			System.out.println("Sin heurística");
		}

		if (strat.toLowerCase().equals("bfs")) {
			strategy = SearchStrategy.BFS;
		} else if (strat.toLowerCase().equals("dfs")) {
			strategy = SearchStrategy.DFS;
		} else if (strat.toLowerCase().equals("astar")) {
			strategy = SearchStrategy.AStar;
		} else if (strat.toLowerCase().equals("deepiteration")) {
			strategy = SearchStrategy.DeepIteration;
		} else if (strat.toLowerCase().equals("greedy")) {
			strategy = SearchStrategy.Greedy;
		} else if (strat.toLowerCase().equals("quit")) {
			return;
		} else {
			System.out.println("No se encontró estrategia con ese nombre");
		}

		engine.engine(new SimpleSquaresProblem(strategy, args[0], heuristic),
				strategy);
		System.out.println("Total time in milis: "
				+ ((System.currentTimeMillis() - time)));
	}
}
