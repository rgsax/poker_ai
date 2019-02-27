package it.unical.poker.game.states;

public class WaitingForInputState extends State {
	State state;
	
	boolean gotInput = false;
	
	public WaitingForInputState(State state) {
		super(null);
		this.state = state;
	}
	
	public State resume() {
		return state;
	}
	
	@Override
	public void process() {
		gotInput = true;
		super.process();
	}
	
	@Override
	public State next() {
		super.next();
		
		if(!gotInput)
			return this;
		
		return resume();
	}
}
