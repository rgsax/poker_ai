package it.unical.poker.ai;

import java.io.File;
import java.util.List;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import it.unical.poker.game.Player;
import it.unical.poker.game.Table;
import phe.Hand;

public class BettingStrategy {
	private Handler handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
	private InputProgram program; 
	
	public BettingStrategy(File f) {		
		program = new ASPInputProgram(); 
		program.addFilesPath(f.getAbsolutePath());
		handler.addProgram(program); 
		handler.addOption(new OptionDescriptor("--printonlyoptimum "));
	}
	
	public String evaluate(Player player, Table table) {
		if (player.canCall.get()) {
			program.addProgram("can_call.");
		}
		
		if (player.canCheck.get()) {
			program.addProgram("can_check.");
		}
		
		if (player.canRaise.get()) {
			program.addProgram("can_raise.");
		}
		
		if (player.canFold.get()) {
			program.addProgram("can_fold.");
		}
		
		if (player.canAllIn.get()) {
			program.addProgram("can_allin.");
		}
		
		int virtualPot = table.getPot().get() + table.getTotalBets();
		
		//int probability = (int)(Probability.montecarloSimulateHands(player.getCards(), 1000000) * 100);
		int probability = (int) (Probability.approximateProbability(player.getCards())* 100) ; 
		
		program.addProgram(Hand.handRank(player.getCards()).toString().toLowerCase() + ".");
		program.addProgram(String.format("probability(%d).", probability)); 
		program.addProgram(String.format("pot(%d).", virtualPot));
		program.addProgram(String.format("chips(%d).", player.getChips().get()));
		program.addProgram(String.format("myBet(%d).", player.getBet().get()));
		program.addProgram(String.format("minimumBet(%d).", table.getBet().get()));
		program.addProgram(String.format("ante(%d).", table.getAnte().get())); 
		program.addProgram(String.format("toCall(%d).", player.getToCall().get()));		
		program.addProgram(String.format("points(%d).", Hand.evaluate(player.getCards())));
		program.addProgram(String.format("hasAlreadyBet(%b).", player.isHasAlreadyBet()));
		
		program.addProgram(String.format("minRaise(%d).", 1));
		program.addProgram(String.format("maxRaise(%d).", player.getChips().get() - 1 - player.getToCall().get()));
//		System.exit(21);

		
		AnswerSets as = (AnswerSets) handler.startSync();
		System.out.println("ERRORS:" + as.getErrors());
		System.out.println("OUTPUT: " + as.getOutput());


		AnswerSet a = as.getAnswersets().get(0); 
		
		
					
		try {
			List<String> atoms = a.getAnswerSet(); 
			if (atoms.contains("fold")) {
				return "fold"; 
			} else if (atoms.contains("raise")) {
				String amount = "";
				
				for(String atom : a.getAnswerSet())
					if(atom.contains("raiseAmount")) {
						amount = atom.substring(atom.lastIndexOf("(") + 1, atom.length() - 1);
					}
				
				player.raiseAmount.set(Integer.parseInt(amount));
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
