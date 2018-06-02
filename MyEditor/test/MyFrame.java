package MyEditor.test;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.HyperlinkEvent;

public class MyFrame extends JFrame {

	private JPanel jPanel = null;
	private JTextArea jTextArea = null;
	private JButton jButton = null;
	private JEditorPane jEditorPane = null;

	/**
	 * This method initializes 
	 * 
	 */
	public MyFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(666, 723));
        this.setContentPane(getJPanel());
			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJTextArea(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJEditorPane(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(11, 5, 139, 127));
			jTextArea.setText("This is the stroy\nof the hare who\nlost his spectacles.");
		}
		return jTextArea;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(172, 11, 71, 26));
			jButton.setText("Button");
			jButton.addActionListener(new MultiHighlight(jTextArea, "aeiouAEIOU"));
		}
		return jButton;
	}
	/**
	 * This method initializes jEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			try{
				jEditorPane = new JEditorPane("file:///e:/bbs.html");
			}catch(IOException ex){
				ex.printStackTrace(System.err);
				System.exit(1);
				
			}
			jEditorPane.setBounds(new Rectangle(12, 146, 629, 566));
			jEditorPane.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
				public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent e) {
					try{
						if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
							jEditorPane.setPage(e.getURL());
						} catch (IOException ex){ex.printStackTrace(System.err);}
				}
			});
		}
		return jEditorPane;
	}

	public static void main(String[] args){
		MyFrame mf = new MyFrame();
		mf.setVisible(true);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
