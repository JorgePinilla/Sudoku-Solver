package app1;

public class SudokuValidator {

	private SudokuSolver ss;
	
	public SudokuValidator(){
	}
	
	//returns solution for given sudoku
	public int[][] getSudoku(int[][] sudoku, int numOfClues){
		this.ss = new SudokuSolver(sudoku, numOfClues);
		int[][] result = this.ss.solve();
		
		return result;
	}
	
	//checks if given sudoku has at least 17 numbers.
	public int check(int[][] sudoku){
		int numOfClues = 0;
		for(int i = 0; i < sudoku.length; i++)
			for(int j = 0; j < sudoku.length; j++)
				if(sudoku[i][j] != 0)
					numOfClues++;
		
		if(numOfClues >= 17)
			return numOfClues;
		
		return -1;
	}
}
