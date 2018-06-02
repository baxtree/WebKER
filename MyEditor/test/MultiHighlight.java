package MyEditor.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

public class MultiHighlight implements ActionListener {
	private JTextComponent comp;
	private String charsToHighlight;
	public MultiHighlight(JTextComponent c, String chars) {
		this.comp = c;
		this.charsToHighlight = chars;
	}
	public void actionPerformed(ActionEvent e){
		Highlighter h = comp.getHighlighter();
		h.removeAllHighlights();
		String text = comp.getText();
		for(int i = 0; i < text.length(); i++){
			char ch = text.charAt(i);
			if(charsToHighlight.indexOf(ch) >= 0)
				try{
					h.addHighlight(i, i+1, DefaultHighlighter.DefaultPainter);
				}catch(BadLocationException ble){}
			
		}
	}
	
}
