package MyEditor.UseSwing;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
public class HTMLExample {
	public static void main(String[] args){
		JEditorPane pane = null;
//		pane.setContentType("text/html");
		try{
			pane = new JEditorPane("file:///e:/600030.html");
		}
		catch(IOException ex){
			ex.printStackTrace(System.err);
			System.exit(1);
		}
		pane.setEditable(true);
		final JEditorPane finalPane = pane;
		pane.addHyperlinkListener(new HyperlinkListener(){
			public void hyperlinkUpdate(HyperlinkEvent ev){
				try{
					if (ev.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
						finalPane.setPage(ev.getURL());
					} catch (IOException ex){ex.printStackTrace(System.err);}
			}
		});
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new JScrollPane(pane));
		frame.setSize(350,400);
		frame.setVisible(true);
	}
}
