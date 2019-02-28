package it.unical.poker.ai;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import phe.Card;
import phe.Hand;

public class DiscardStrategy {
	private Handler handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
	private InputProgram program; 
	
	public DiscardStrategy(File f) {
		try {
			ASPMapper.getInstance().registerClass(DiscardAdapter.class);
			System.out.println("DISCARD HELPER REGISTRATA");
		} catch (ObjectNotValidException | IllegalAnnotationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		program = new ASPInputProgram(); 
		program.addFilesPath(f.getAbsolutePath());
		handler.addProgram(program); 
	}
	
	public List<Integer> chooseWhatToDiscard(Card[] cs) {
		List<Integer> indexes = new ArrayList<>(); 
		
		for (Card c: cs) {
			try {
				program.addObjectInput(new CardAdapter(c));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		AnswerSets as = (AnswerSets) handler.startSync();
		AnswerSet a = as.getAnswersets().get(0); 
					
		try {
			for (Object o: a.getAtoms()) {
				if (o instanceof DiscardAdapter) {
					Card c = ((DiscardAdapter) o).getCard(); 					
					for (int i = 0; i < 5; ++i) {
						if (cs[i].equals(c)) {
							indexes.add(i); 
							break; 
						}
					}
					
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return indexes;  
	}
	
	public static void main(String[] args) {
		try {
			ASPMapper.getInstance().registerClass(DiscardAdapter.class);
			System.out.println("DISCARD HELPER REGISTRATA");
		} catch (ObjectNotValidException | IllegalAnnotationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		
		Card[] cs = Hand.fromString("Ad 3h 5s 7c 9d"); 		
		File f = new File(DiscardStrategy.class.getClassLoader().getResource("encodings/discard_pokerNew.lp").getFile()); 		
		
		DiscardStrategy ds = new DiscardStrategy(f); 
		List<Integer> is = ds.chooseWhatToDiscard(cs); 
		
		for (Integer i: is) {
			System.out.println("Discarding: " + cs[i]);
		}
		
	}
}
