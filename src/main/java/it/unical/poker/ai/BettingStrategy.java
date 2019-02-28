package it.unical.poker.ai;

import java.io.File;
import java.util.List;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import it.unical.poker.game.Player;
import it.unical.poker.game.Table;

public class BettingStrategy {
	private Handler handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
	private InputProgram program; 
	
	public BettingStrategy(File f) {		
		program = new ASPInputProgram(); 
		program.addFilesPath(f.getAbsolutePath());
		handler.addProgram(program); 
	}
	
	public String evaluate(Player p, Table t) {
		if (p.canCall.get()) {
			program.addProgram("can_call");
		}
		
		if (p.canCheck.get()) {
			program.addProgram("can_check");
		}
		
		if (p.canRaise.get()) {
			program.addProgram("can_raise");
		}
		
		if (p.canFold.get()) {
			program.addProgram("can_fold");
		}
		
		if (p.canAllIn.get()) {
			program.addProgram("can_alllin");
		}
		
		// TODO: probabilità vera
		program.addProgram(String.format("probability(%f)", 0.5)); 
		program.addProgram(String.format("pot(%d)", t.getPot().get()));
		program.addProgram(String.format("chips(%d)", p.getChips().get()));
		program.addProgram(String.format("myBet(%d)", p.getBet().get()));
		program.addProgram(String.format("minimumBet(%d)", t.getBet().get()));
		program.addProgram(String.format("ante(%d)", t.getAnte().get())); 
		
		AnswerSets as = (AnswerSets) handler.startSync();
		AnswerSet a = as.getAnswersets().get(0); 
		
		System.out.println("BETTING PROGRAM: " + program.getPrograms());
					
		try {
			List<String> atoms = a.getAnswerSet(); 
			if (atoms.contains("fold")) {
				return "fold"; 
			} else if (atoms.contains("raise")) {
				return "raise"; 
			} else if (atoms.contains("check")) {
				return "check"; 
			} else if (atoms.contains("allin")) {
				return "allin"; 
			} else if (atoms.contains("call")) {
				return "call";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "fold"; 
	}
}
