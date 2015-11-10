import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SudokuApp extends JFrame implements KeyListener, ActionListener{

	private static final long serialVersionUID = 1L;
	
	private SudokuValidator sv = new SudokuValidator();
	
	private JTextField[] cells = new JTextField[81];
	private JButton solve = new JButton("Solve");
	private JButton clear = new JButton("clear");
	
	public SudokuApp(){
		
		/*
		 *Create and configure components
		 */
		for(int i = 0; i < 81; i++){
			JTextField tf = new JTextField();
			tf.setFont(new Font("Courier", Font.BOLD, 24));
			tf.setHorizontalAlignment(JTextField.CENTER);
			this.cells[i] = tf;
		}
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setPreferredSize(new Dimension(500, 500));
		
		JPanel gridPanel = new JPanel();
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		JPanel panel8 = new JPanel();
		JPanel panel9 = new JPanel();
		
		panel1.setSize(50, 50);
		panel2.setSize(50, 50);
		panel3.setSize(50, 50);
		panel4.setSize(50, 50);
		panel5.setSize(50, 50);
		panel6.setSize(50, 50);
		panel7.setSize(50, 50);
		panel8.setSize(50, 50);
		panel9.setSize(50, 50);
		
		GridLayout gridLayout = new GridLayout(3, 3);
		gridLayout.setHgap(3);
		gridLayout.setVgap(3);
		
		GridLayout box1 = new GridLayout(3, 3);
		GridLayout box2 = new GridLayout(3, 3);
		GridLayout box3 = new GridLayout(3, 3);
		GridLayout box4 = new GridLayout(3, 3);
		GridLayout box5 = new GridLayout(3, 3);
		GridLayout box6 = new GridLayout(3, 3);
		GridLayout box7 = new GridLayout(3, 3);
		GridLayout box8 = new GridLayout(3, 3);
		GridLayout box9 = new GridLayout(3, 3);
		
		gridPanel.setLayout(gridLayout);
		
		panel1.setLayout(box1);
		panel2.setLayout(box2);
		panel3.setLayout(box3);
		panel4.setLayout(box4);
		panel5.setLayout(box5);
		panel6.setLayout(box6);
		panel7.setLayout(box7);
		panel8.setLayout(box8);
		panel9.setLayout(box9);
		
		/*
		 * add listeners
		 */
		for(int i = 0; i < 81; i++)
			cells[i].addKeyListener(this);
		
		this.solve.addActionListener(this);
		this.clear.addActionListener(this);
		
		/*
		 * arrange components
		 */
		gridPanel.add(panel1);
		gridPanel.add(panel2);
		gridPanel.add(panel3);
		gridPanel.add(panel4);
		gridPanel.add(panel5);
		gridPanel.add(panel6);
		gridPanel.add(panel7);
		gridPanel.add(panel8);
		gridPanel.add(panel9);
		
		//adding text fields to panels
		for(int i = 0; i < 9; i++)
			panel1.add(this.cells[i]);
		
		for(int i = 9; i < 18; i++)
			panel2.add(this.cells[i]);
		
		for(int i = 18; i < 27; i++)
			panel3.add(this.cells[i]);
		
		for(int i = 27; i < 36; i++)
			panel4.add(this.cells[i]);
		
		for(int i = 36; i < 45; i++)
			panel5.add(this.cells[i]);
		
		for(int i = 45; i < 54; i++)
			panel6.add(this.cells[i]);
		
		for(int i = 54; i < 63; i++)
			panel7.add(this.cells[i]);
		
		for(int i = 63; i < 72; i++)
			panel8.add(this.cells[i]);
		
		for(int i = 72; i < 81; i++)
			panel9.add(this.cells[i]);
		
		gridPanel.setBounds(50, 25, 400, 400);
		this.solve.setBounds(140, 450, 100, 25);
		this.clear.setBounds(260, 450, 100, 25);
		
		mainPanel.add(gridPanel);
		mainPanel.add(this.solve);
		mainPanel.add(this.clear);
		
		this.setContentPane(mainPanel);
	}
	
	/*
	 * implements key listeners
	 */
	public void keyPressed(KeyEvent ke){}
	
	public void keyReleased(KeyEvent ke){}
	
	public void keyTyped(KeyEvent ke){
		char c = ke.getKeyChar();
		JTextField temp = (JTextField) ke.getSource();
		
		if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE))
			ke.consume();
		else if(c == 48) //digit 0 is not allowed
			ke.consume();
		else if(!(temp.getText().equals("")))
			ke.consume();
	}
	
	/*
	 * implements action listeners
	 */
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == this.solve){
			int[][] sudoku = buildSudoku();
			int numOfClues = sv.check(sudoku);
			if(numOfClues != -1){
				sudoku = sv.getSudoku(sudoku, numOfClues);
				if(sudoku != null)
					setSudoku(sudoku);
				else{
					Frame f = JOptionPane.getRootFrame();
					JOptionPane.showMessageDialog(f, "Sudoku has no solution", "Invalid Sudoku",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				Frame f = JOptionPane.getRootFrame();
				JOptionPane.showMessageDialog(f, "Sudoku should have at least 17 numbers...",
						"Invalid Sudoku", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(e.getSource() == this.clear)
			for(int i = 0; i < 81; i++)
				this.cells[i].setText("");
	}
	
	private int[][] buildSudoku(){
		int[][] sudoku = new int[9][9];
		int[] clues = new int[81];
		
		for(int i = 0; i < 81; i++){
			if(this.cells[i].getText().equals(""))
				clues[i] = 0;
			else
				clues[i] = Integer.parseInt(this.cells[i].getText());
		}
		
		int v = 0;
		for(int i = 0; i < 9; i++){
			if(i == 3 || i == 6)
				v += 18;
			for(int j = 0; j < 9; j++){
				if(j == 3 || j == 6)
					v += 6;
				sudoku[i][j] = clues[v];
				v++;
			}
			v -= 18;
		}
		
		return sudoku;
	}
	
	private void setSudoku(int[][] sudoku){
		int v = 0;
		for(int i = 0; i < 9; i++){
			if(i == 3 || i == 6)
				v += 18;
			for(int j = 0; j < 9; j++){
				if(j == 3 || j == 6)
					v += 6;
				this.cells[v].setText("" + sudoku[i][j]);
				v++;
			}
			v -= 18;
		}
	}
	
	public static void main(String[] args){
		try{
	         UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	      }
		catch (Exception e) {}

		SudokuApp frame = new SudokuApp();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setTitle("Sudoku Solver");
	    frame.setSize(new Dimension(500, 500));
	    frame.setLocationRelativeTo(null);
	    frame.setResizable(false);
	    frame.setVisible(true);
	}
}
