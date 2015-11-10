public class SudokuSolver{

	private int[][] sudoku;
	private int givenNumbers;
	
	public SudokuSolver(int[][] sudoku, int numbs){
		this.sudoku = sudoku;
		this.givenNumbers = numbs;
	}
	
	public int[][] solve(){
		int flag = 1;
		int row;
		
		for(row = 0; row < 9;){
			int column;
			int num = 1;
			int insert = 0;
			
			//Square stores the possible numbers that can be found in each cell for current row
			int[][] square = {{0, 0, 0, 0, 0, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0}};
			
			while(num < 10){
				int appears = 0;
				if(numRow(row, num))
					num++;
				else{
					for(column = 0; column < 9;){
						if(this.sudoku[row][column] != 0)
							column++;
						else if(numSquare(location(row), location(column), num))
							column = location(column) + 3;
						else if(numColumn(column, num))
							column++;
						else{
							appears++;
							square[0][column] = square[0][column] + 1;
							
							if(square[0][column] < 7)
								square[square[0][column]][column] = num;
							
							column++;
						}
					}
					
					//if a number does not appear in a row, then sudoku is invalid
					if(appears == 0)
						return null;
						
					num++;
				}
			}
			
			for(int c = 0; c < 9; c++){
				if(square[0][c] == 1){
					if(this.sudoku[row][c] != square[1][c])
						this.givenNumbers = this.givenNumbers + 1;
					this.sudoku[row][c] = square[1][c];
					insert = 1;
				}
			}
			
			if(insert == 1){
				row = 0;
				flag = 1;
			}
			else{
				row++;
				if(row == 9){
					flag++;
					row = 0;
				}
			}
			
			if(this.givenNumbers == 81)
				return this.sudoku;
			
			if(flag > 1 && flag < 7){
				int c;
				int flag2 = 0;
				for(c = 0; c < 9; c++){
					if(square[0][c] == flag){
						flag2 = 1;
						break;
					}
				}
				if(flag2 == 1){
					int[][] r = null;
					for(int i = 1; i <= flag && r == null; i++){
						int[][] temp = cloneSudoku();
						temp[((row + 9) - 1) % 9][c] = square[i][c];
						
						SudokuSolver s = new SudokuSolver(temp, this.givenNumbers + 1);
						r = s.solve();
					}
					return r;
				}
			}
		}
		return null;
	}
	
	//check if num is in row
	private boolean numRow(int row, int num){
		for(int c = 0; c < 9; c++){
			if(this.sudoku[row][c] == num)
				return true;
		}
		return false;
	}
	
	//check if number is in column
	private boolean numColumn(int column, int num){
		for(int r = 0; r < 9; r++){
			if(this.sudoku[r][column] == num)
				return true;
		}
		return false;
	}
	
	//check if number is in square(square meaning one of the 9 boxes that make up a sudoku)
	private boolean numSquare(int rowStart, int columnStart, int num){
		for(int i = rowStart; i < rowStart + 3; i++){
			for(int j = columnStart; j < columnStart + 3; j++){
				if(this.sudoku[i][j] == num)
					return true;
			}
		}
		return false;
	}
	
	private int location(int num){
		if(num < 3)
			return 0;
		else if(num < 6)
			return 3;
		else
			return 6;
	}
	
	private int[][] cloneSudoku(){
		int[][] temp = new int[9][9];
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++)
				temp[i][j] = this.sudoku[i][j];
		}
		return temp;
	}
}
