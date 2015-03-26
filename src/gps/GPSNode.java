package gps;

import simple_square.SimpleSquaresProblem;
import gps.api.GPSState;

public class GPSNode implements Comparable<GPSNode>{

	private GPSState state;

	private GPSNode parent;

	private Integer cost;

	public GPSNode(GPSState state, Integer cost) {
		super();
		this.state = state;
		this.cost = cost;
	}

	public GPSNode getParent() {
		return parent;
	}

	public void setParent(GPSNode parent) {
		this.parent = parent;
	}

	public GPSState getState() {
		return state;
	}

	public Integer getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return state.toString();
	}

	public String getSolution() {
		if (this.parent == null) {
			return this.state.toString();
		}
		return this.parent.getSolution() + "\n" + this.state;
	}

	@Override
	public int compareTo(GPSNode o) {
		if(SimpleSquaresProblem.getStrategy().equals(SearchStrategy.AStar)){
			return (SimpleSquaresProblem.getHValue(this.getState()) + cost ) - (SimpleSquaresProblem.getHValue(o.getState()) + o.cost);
		}else{
			return SimpleSquaresProblem.getHValue(this.getState()) - SimpleSquaresProblem.getHValue(o.getState());
		}
	}
}
