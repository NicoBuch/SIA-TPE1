package simple_square;

import gps.GPSEngine;
import gps.SearchStrategy;
import gps.api.GPSProblem;

import java.util.ArrayList;
import java.util.List;

public class TestSquares {
	public static void main(String[] args) {
		
		Chronometer c = new Chronometer();
		List <SearchStrategy> strategies = new ArrayList <SearchStrategy>(); 
		strategies.add(SearchStrategy.BFS);
		strategies.add(SearchStrategy.DFS);
		strategies.add(SearchStrategy.DeepIteration);
//		strategies.add(SearchStrategy.AStar);
		GPSProblem problem = new SimpleSquaresProblem();
		
		for(SearchStrategy s : strategies){
			c.start();
			GPSEngine engine = new SimpleSquaresEngine();
			engine.engine(problem, s);
			c.stop();
			System.out.println("Total iteration time of: " + s.toString() + " was: "+ c.getSeconds() + " seconds");
		}
	}
}
