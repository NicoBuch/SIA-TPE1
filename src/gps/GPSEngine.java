package gps;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import simple_square.SimpleSquaresProblem;

public abstract class GPSEngine {

	// protected List<GPSNode> open = new LinkedList<GPSNode>();

	protected List<GPSNode> closed = new ArrayList<GPSNode>();

	protected AbstractCollection<GPSNode> open;
	// protected PriorityQueue<GPSNode> openH = new PriorityQueue<GPSNode>();

	protected GPSProblem problem;

	protected int deepIterationValue = 0;

	static StringWriter nodes = new StringWriter();
	static PrintWriter nodeWriter = new PrintWriter(nodes, false);
	
	static StringWriter color = new StringWriter();
	static PrintWriter colorWriter = new PrintWriter(color, false);

	StringWriter arcs = new StringWriter();
	PrintWriter arcWriter = new PrintWriter(arcs, false);

	// Use this variable in the addNode implementation
	protected SearchStrategy strategy;

	public void engine(GPSProblem myProblem, SearchStrategy myStrategy)
			throws IOException {

		problem = myProblem;
		strategy = myStrategy;
		GPSNode rootNode = new GPSNode(problem.getInitState(), 0);
		boolean finished = false;
		boolean failed = false;
		long explosionCounter = 0;
		if (strategy.equals(SearchStrategy.AStar)) {
			open = new PriorityQueue<GPSNode>();
		} else {
			open = new LinkedList<GPSNode>();
		}

		open.add(rootNode);
		while (!failed && !finished) {
			if (open.size() <= 0) {
				if (strategy.equals(SearchStrategy.DeepIteration)) {
					deepIterationValue++;
					closed.clear();
					System.out.println("new deepIterationValue: "
							+ deepIterationValue);
					open.add(rootNode);
				} else {
					failed = true;
				}
			} else {
				GPSNode currentNode = null;
				if (strategy.equals(SearchStrategy.AStar)) {
					PriorityQueue<GPSNode> aux = (PriorityQueue<GPSNode>) open;
					currentNode = aux.poll();
				} else {
					LinkedList<GPSNode> aux = (LinkedList<GPSNode>) open;
					currentNode = aux.get(0);
					aux.remove(0);
				}
				closed.add(currentNode);
				if (isGoal(currentNode)) {
					paintPath(currentNode);
					finished = true;
					System.out.println(currentNode.getSolution());
					System.out.println("Movimientos: " + currentNode.getCost());
					System.out.println("Expanded nodes: " + explosionCounter);
//					
					createDotFile();
				} else {
					explosionCounter++;
					explode(currentNode);
				}
			}
		}

		if (finished) {
			System.out.println("OK! solution found!");
			System.out.println("Generated States: " + (closed.size() + open.size()));
			System.out.println("Opened Nodes: " + open.size());
		} else if (failed) {
			System.err.println("FAILED! solution not found!");
		}
	}

	private boolean isGoal(GPSNode currentNode) {
		return currentNode.getState() != null
				&& problem.isGoalState(currentNode.getState());
	}

	private boolean explode(GPSNode node) {
		if (problem.getRules() == null) {
			System.err.println("No rules!");
			return false;
		}
		PriorityQueue<GPSNode> greedyQueue = new PriorityQueue<GPSNode>();
		for (GPSRule rule : problem.getRules()) {
			GPSState newState = null;
			try {
				if (!strategy.equals(SearchStrategy.DeepIteration)
						|| (strategy.equals(SearchStrategy.DeepIteration) && node
								.getCost() + rule.getCost() <= deepIterationValue))
					newState = rule.evalRule(node.getState());
			} catch (NotAppliableException e) {
				// Do nothing
			}
			if (newState != null
					&& !checkBranch(node, newState)
					&& !checkOpenAndClosed(node.getCost() + rule.getCost(),
							newState)) {
				GPSNode newNode = new GPSNode(newState, node.getCost()
						+ rule.getCost());
				newNode.setParent(node);
				addNodeToDiGraph(newNode);
				addArc(node, newNode);
				if (strategy.equals(SearchStrategy.Greedy)) {
					greedyQueue.add(newNode);
				} else {
					addNode(newNode);
				}
			}
		}
		if (strategy.equals(SearchStrategy.Greedy)) {

			for (int i = 0; !greedyQueue.isEmpty(); i++) {
				((LinkedList<GPSNode>) open).add(i, greedyQueue.remove());
			}
		}
		return true;
	}

	private boolean checkOpenAndClosed(Integer cost, GPSState state) {
		GPSNode repetedNode = null;
		for (GPSNode openNode : open) {
			if (openNode.getState().compare(state)) {
				if (openNode.getCost() <= cost) {
					return true;
				} else {
					repetedNode = openNode;
				}
			}
		}
		if (repetedNode != null) {
			open.remove(repetedNode);
			repetedNode = null;
		}
		for (GPSNode closedNode : closed) {
			if (closedNode.getState().compare(state)) {
				if (closedNode.getCost() <= cost) {
					return true;
				} else {
					repetedNode = closedNode;
				}
			}
		}
		if (repetedNode != null) {
			closed.remove(repetedNode);
		}
		return false;
	}

	private boolean checkBranch(GPSNode parent, GPSState state) {
		if (parent == null) {
			return false;
		}
		return state.compare(parent.getState())
				|| checkBranch(parent.getParent(), state);
	}

	public abstract void addNode(GPSNode node);

	public boolean isHeuristicStrategy() {
		return strategy.equals(SearchStrategy.Greedy)
				|| strategy.equals(SearchStrategy.AStar);
	}

	public void createDotFile() throws FileNotFoundException {
		File dir = new File ("Solutions");
		if(!dir.exists())
			dir.mkdirs();
		
		String sstrategy = this.strategy.toString();
		String sheuristic = "";
		String sboard = "_board_"  + ((SimpleSquaresProblem)problem).getBoardName();
		if(strategy.equals(SearchStrategy.AStar) || strategy.equals(SearchStrategy.Greedy))
			sheuristic += "_heuristic_" + ((SimpleSquaresProblem)problem).getHeuristic();
		
		File file = new File(dir, "strategy_" + sstrategy + sheuristic + sboard + ".dot");
		
		PrintWriter pw = new PrintWriter(new FileOutputStream(file, false));
		pw.println("digraph GPS {");
		pw.println(nodes.toString());
		pw.println(color.toString());
		pw.println(arcs.toString());
		pw.println("}");
		nodeWriter.close();
		colorWriter.close();
		arcWriter.close();
		pw.close();
	}

	private void addNodeToDiGraph(GPSNode node) {
		nodeWriter.println(node.getName() + " [label = \"" + node.getPrintableName() + "\"];");
	}

	private void addArc(GPSNode from, GPSNode to) {
		arcWriter.println(from.getName() + " -> " + to.getName() + ";");
	}

	private void paintPath(GPSNode node) {
		GPSNode currentNode = node;
		while (currentNode != null) {
			paintNode(currentNode, "darkturquoise");
			currentNode = currentNode.getParent();
		}
	}
	
	public static void paintNode(GPSNode node, String color){
		colorWriter.println(node.getName() + " [fillcolor = " + color + ", style = filled];");
	}
}
