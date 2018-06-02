package MyEditor.test;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Rectangle;
import javax.swing.JToolBar;
import javax.swing.JPanel;

public class MyXMLEditor extends JFrame{

	
	private JPanel jPanel = null;
	private JMenuBar jJMenuBar = null;
	/**
	 * This method initializes 
	 * 
	 */
	public MyXMLEditor() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setBounds(new Rectangle(0, 0, 1000, 800));
        this.setJMenuBar(getJJMenuBar());
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
		}
		return jPanel;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	
	public static void main(String[] args){
		MyXMLEditor mxe = new MyXMLEditor();
		mxe.setVisible(true);
	}
}  //  @jve:decl-index=0:visual-constraint="21,-1"
