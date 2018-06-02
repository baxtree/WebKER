package MyEditor.test;

import jackteng.pattree.ArrayPrinter;
import jackteng.pattree.RDFGenerator;
import jackteng.pattree.Evaluation;
import jackteng.pattree.IBExtractor;
import jackteng.pattree.InfoSplit;
import jackteng.pattree.MyMapper;
import jackteng.pattree.PatArray;
import jackteng.pattree.RDFOperater;
import jackteng.pattree.StringOperator;
import jackteng.pattree.TagMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import demo.XMLHelperException;

public class MainFrame extends JFrame {

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JLabel jLabel2 = null;

	private JTextField jTextField2 = null;

	private JButton jButton4 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable2 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private File originalfile = null; // 

	private File similarfile = null;

	private File ontologyfile = null;

	private String textdatapath = null;

	private String beforemappedxml = null;

	private String aftermappedxml = null;

	private JFileChooser jfc = null;

	// private String[][] lefttablecontent =
	// {{"property","value"},{"property","value"},{"property","value"},{"property","value"},{"property","value"},{"property","value"},{"property","value"},{"property","value"},{"property","value"},{"property","value"}};
	 private String[] lefttablehead = {"PROPERTY","VALUE"};
	// private String[][] righttablecontent =
	// {{"object","predicate","subject"},{"object","predicate","subject"},{"object","predicate","subject"},{"object","predicate","subject"},{"object","predicate","subject"},{"object","predicate","subject"},{"object","predicate","subject"},{"object","predicate","subject"},{"object","predicate","subject"},{"object","predicate","subject"}};
	 private String[] righttablehead = {"OBJECT","PREDICATE","SUBJECT"};
	private LeftTableModel ltm = new LeftTableModel();

	private RightTableModel rtm = new RightTableModel();

	private JButton jButton6 = null;

	private JButton jButton7 = null;

	private JButton jButton5 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel3 = null;

	/**
	 * This method initializes
	 * 
	 */
	public MainFrame() {
		super();
		initialize();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(676, 642));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/MyEditor/test/frameicon.gif")));
		this.setResizable(false);
		this.setTitle("Knowledge Exctractor");
		this.setContentPane(getJPanel());
		
		

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(8, 182, 645, 420));
			jLabel3.setText("");
			TitledBorder title3 = new TitledBorder("Results");
			jLabel3.setBorder(title3);
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(8, 102, 645, 72));
			jLabel5.setText("");
			TitledBorder title5 = new TitledBorder("Excution");
			jLabel5.setBorder(title5);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(8, 1, 645, 97));
			jLabel4.setText("");
			TitledBorder title4 = new TitledBorder("Document Appointment");
			jLabel4.setBorder(title4);
			
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(431, 24, 119, 23));
			jLabel2.setText("Domain Ontologies:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(29, 57, 114, 23));
			jLabel1.setText("Similar Document:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(24, 26, 114, 26));
			jLabel.setText("Original Document:");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(getJButton4(), null);
			jPanel.add(getJScrollPane(), null);
			jPanel.add(getJScrollPane1(), null);
			jPanel.add(getJButton6(), null);
			jPanel.add(getJButton7(), null);
			jPanel.add(getJButton5(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel3, null);
		}
		return jPanel;
	}

	// private String getOriginalFile(){
	// JFileChooser chooser = new JFileChooser();
	// ExampleFileFilter filter = new ExampleFileFilter ();
	//		
	// }
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(148, 23, 168, 24));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(148, 60, 168, 23));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(329, 24, 84, 22));
			jButton.setText("Browse");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser("e:\\");
					// File f = new File("f:\\");
					// jfc = new JFileChooser(f);
					jfc.setDialogType(JFileChooser.OPEN_DIALOG);
					jfc.setDialogTitle("Choose a document for extraction");
					int option = jfc.showOpenDialog(MainFrame.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						originalfile = jfc.getSelectedFile();
						jTextField.setText(originalfile.getPath());
					}

				}
			});

		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(330, 60, 84, 22));
			jButton1.setText("Browse");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser("e:\\");
					jfc.setDialogType(JFileChooser.OPEN_DIALOG);
					jfc.setDialogTitle("Choose a similar document");
					int option = jfc.showOpenDialog(MainFrame.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						similarfile = jfc.getSelectedFile();
						jTextField1.setText(similarfile.getPath());
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(23, 134, 92, 24));
			jButton2.setFont(new Font("Arial", Font.BOLD, 15));
			jButton2.setBackground(new Color(162, 167, 207));
			jButton2.setText("Extract");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jButton.setEnabled(false);
					jButton1.setEnabled(false);
					jButton4.setEnabled(false);
					jTextField.setEditable(false);
					jTextField1.setEditable(false);
					jTextField2.setEditable(false);
					try {
						System.out.println(originalfile.getPath() + " "
								+ similarfile.getPath());
						IBExtractor ibe = new IBExtractor(
								StringOperator
										.getNormalizedFilePath(originalfile
												.getPath()),// "F:\\test_data\\yahoo\\600031.htm",
								StringOperator
										.getNormalizedFilePath(similarfile
												.getPath()),
								"e:\\test\\InfoBlock.txt");// "F:\\test_data\\yahoo\\600033.htm",
						// "f:\\InfoBlock.txt");
						String[] s = TagMap
								.getOriginalTagsFromFile("e:\\test\\InfoBlock.txt");
						ArrayPrinter.printArray(s);
						String[] mappedstring = TagMap.mapTagToCodedAlph(s);
						PatArray pa = new PatArray(mappedstring);
						ArrayPrinter.printArray(pa.getPatArray());
						System.out.println();
						// System.out.println(pa.a.length+"
						// "+pa.getPatArray().length);
						pa.getAssitantArray();
						pa.getPrefixStartPosition();
						String[] p = pa.getPatterns(30); // 设置所要pattern的最小长度
						String fs = StringOperator
								.getSringFromArray(mappedstring);
						// System.out.println(fs);
						ArrayList al = new ArrayList(3);
						for (int i = 0; i < p.length; i++) {
							if (p[i] == null)
								continue;
							p[i] = p[i].trim().replace("|", "");
							Evaluation eva = new Evaluation(p[i], fs);
							if (eva.getdivisor() == 0)
								continue;
							if (eva.satisfyRLVC() && eva.validStartsWith()) {// 同时满足阀值限制和有效开头标签两个条件
								al.add(p[i]);
								System.out.println(p[i]);
							}
						}
						String[] bestpatterns = new String[al.size()];
						// 从筛选出的patterns中选择最短的作为最优pattern
						int bestidx = 0;
						int len = ((String) al.get(0)).length();
						for (int i = 0; i < bestpatterns.length; i++) {
							bestpatterns[i] = (String) al.get(i);
							if (len > bestpatterns[i].length()
									&& bestpatterns[i].length() >= 5) {// 限制最优模式的长度不能小于5，考虑最简单的情况<a>text<b>text</a>
								len = bestpatterns[i].length();
								bestidx = i;
							}
							ArrayPrinter.printArray(TagMap
									.mapCodedAlphToTag(bestpatterns[i]
											.toCharArray()));
							System.out.println();
						}
						// String a = TagMap.mapCodedAlphToTag(bestpatterns)[0];
						// System.out.print(a);
						// pa.printPattern(p);
						System.out.println();
						// pa.getPatterns(3);
						ArrayPrinter.printArray(TagMap
								.mapCodedAlphToTag(bestpatterns[bestidx]
										.toCharArray()));
						String wrapper = StringOperator
								.getSringFromArray(TagMap
										.mapCodedAlphToTag(bestpatterns[bestidx]
												.toCharArray()));
						wrapper = StringOperator.removeSelfRepeated(wrapper);
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println(wrapper);
						System.out.println();
						System.out.println();
						System.out.println();
						// 假设最佳的pattern已经找到(需要找到一个评估函数来判定哪个pattern好)

						wrapper = wrapper.replace("<TXT/>", "[^<^>]*?");

						pa.extractByRegex(wrapper, "e:\\test\\InfoBlock.txt",
								"e:\\test\\matched.txt");// "f:\\InfoBlock.txt");
						// ArrayPrinter.printArray(pa.getPatArray());
						// System.out.println(Integer.parseInt("0001"));
						// System.out.println(pa.getAssitantArray().toString());
											
						ltm.data = pa.extractByRegex(wrapper, "e:\\test\\InfoBlock.txt",
						"e:\\test\\matched.txt");
						System.out.println(ltm.data[0][0]+" "+ltm.data[0][1]);
						ltm.column = lefttablehead;
						ltm.fireTableDataChanged();
					} catch (Exception exc) {
						exc.printStackTrace();
					}

				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(135, 134, 114, 28));
			jButton3.setText("Generate XML");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser("e:\\test");
					jfc.setDialogType(JFileChooser.OPEN_DIALOG);
					jfc.setDialogTitle("Choose an XML file for save");
					int option = jfc.showOpenDialog(MainFrame.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						File xml = jfc.getSelectedFile();
						beforemappedxml = xml.getPath();
						try {
							InfoSplit.generateXMLfile(ltm.data,
									beforemappedxml);
						} catch (Exception exp) {
							exp.printStackTrace();
						}
					}

				}

			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(new Rectangle(432, 60, 215, 21));
		}
		return jTextField2;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(560, 23, 84, 22));
			jButton4.setText("Browse");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser("e:\\test\\finance");
					jfc.setDialogType(JFileChooser.OPEN_DIALOG);
					jfc.setDialogTitle("Choose an ontology file");
					int option = jfc.showOpenDialog(MainFrame.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						ontologyfile = jfc.getSelectedFile();
						jTextField2.setText(ontologyfile.getPath());
					}
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(22, 208, 272, 385));
			jScrollPane.setViewportView(getJTable2());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable(ltm);
			jTable2.setEnabled(false);
			jTable2.setColumnSelectionAllowed(false);
			jTable2.setCellSelectionEnabled(true);
		}
		return jTable2;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(348, 208, 297, 386));
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable(rtm);
			jTable1.setEnabled(false);
			jTable1.setColumnSelectionAllowed(false);
			jTable1.setCellSelectionEnabled(true);
		}
		return jTable1;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setBounds(new Rectangle(269, 134, 111, 28));
			jButton6.setText("Align Names");
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser("e:\\test");
					jfc.setDialogType(JFileChooser.OPEN_DIALOG);
					jfc
							.setDialogTitle("Choose an XML file for name alingement");
					int option = jfc.showOpenDialog(MainFrame.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						File xml = jfc.getSelectedFile();
						aftermappedxml = xml.getPath();
						try {
							MyMapper mm = new MyMapper(beforemappedxml,
									aftermappedxml);
							mm.map();
						} catch (XMLHelperException xhe) {
							xhe.printStackTrace();
						} catch (FileNotFoundException fnfe) {
							fnfe.printStackTrace();
						}
					}

				}
			});
		}
		return jButton6;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setBounds(new Rectangle(400, 134, 111, 28));
			jButton7.setText("Generate RDF");
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser("e:\\test");
					jfc.setDialogType(JFileChooser.OPEN_DIALOG);
					jfc.setDialogTitle("Save RDF to file ...");
					int option = jfc.showOpenDialog(MainFrame.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						File rdf = jfc.getSelectedFile();
						try {
							RDFGenerator crdfr = new RDFGenerator(
									ontologyfile.getPath(), aftermappedxml, rdf
											.getPath());
							crdfr.createRDFFile();
							RDFOperater rdfo = new RDFOperater(rdf.getPath());
							rtm.data = rdfo.get3TuplesTable();
//							rtm.column = lefttablehead;
							System.out.println(rtm.data[0][0] + " "
									+ rtm.data[0][1] + " " + rtm.data[0][2]);
							rtm.fireTableDataChanged();
						} catch (XMLHelperException xhe) {
							xhe.printStackTrace();
						} catch (FileNotFoundException fnfe) {
							fnfe.printStackTrace();
						}
					}
				}
			});
		}
		return jButton7;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(new Rectangle(531, 134, 111, 28));
			jButton5.setText("Reset");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jButton.isEnabled() == false) {
						jButton.setEnabled(true);
						jButton1.setEnabled(true);
						jButton4.setEnabled(true);
						jTextField.setEditable(true);
						jTextField1.setEditable(true);
						jTextField2.setEditable(true);
						jTextField.setText("");
						jTextField1.setText("");
						jTextField2.setText("");
						originalfile = null; // 
						similarfile = null;
						ontologyfile = null;
						textdatapath = null;
						beforemappedxml = null;
						aftermappedxml = null;
						ltm.remove();
						rtm.remove();
						ltm.fireTableDataChanged();
						rtm.fireTableDataChanged();
					}
				}
			});
		}
		return jButton5;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		MainFrame mf = new MainFrame();
		mf.setVisible(true);

	}

}  //  @jve:decl-index=0:visual-constraint="127,-14"
