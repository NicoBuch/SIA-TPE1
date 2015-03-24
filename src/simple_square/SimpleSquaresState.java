package simple_square;

import gps.api.GPSState;

import java.util.ArrayList;
import java.util.List;

public class SimpleSquaresState implements GPSState {
	
	List<Block> blocks = new ArrayList<Block>();
	List<Arrow> arrows = new ArrayList<Arrow>(); 
	
	public SimpleSquaresState(List<Block> blocks, List<Arrow> arrows){
		this.blocks = blocks;
		this.arrows = arrows;
	}
	
	@Override
	public boolean compare(GPSState state) {
		for( int i=0; i<blocks.size(); i++)
			if(!blocks.get(i).equals(state.getBlocks().get(i)))
				return false;
		
		return true;
	}

	@Override
	public List<Block> getBlocks() {
		return blocks;
	}

	@Override
	public List<Arrow> getArrows() {
		return arrows;
	}

	@Override
	public Block getBlockAt(Position at) {
		for(Block b : blocks){
			if(b.getPosition().equals(at))
				return b;
		}
		return null;
	}
	
	public Arrow getArrowAt(Position at){
		for(Arrow b : arrows){
			if(b.getPos().equals(at))
				return b;
		}
		return null;
	}
	
	@Override
	public GPSState clone(){
		List<Block> cloned_blocks = new ArrayList<Block>();
		ArrayList<Arrow> cloned_arrows = new ArrayList<Arrow>();
		for(Block b : blocks){
			cloned_blocks.add(b.clone());
		}
		for(Arrow a : arrows){
			cloned_arrows.add(a.clone());
		}
		return new SimpleSquaresState(cloned_blocks, cloned_arrows);
	}
	
	@Override
	public String toString() {
		String s = "" + '\n';
		s += "En este estado los bloques son: "+ '\n';
		for(Block b : blocks){
			s += "posicion: " + b.getPosition().x + ", " + b.getPosition().y + " direccion: " + b.getDirection() + '\n';
		}
		return s;
	}
	
}
