import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Sudoku {
	
	
	// Atributos
	private SudokuBoard sudokuBoard;
	public ColorImage boardImage; // para ver no PandionJ
	
	// Métodos Construtores
	public Sudoku(String file, double difficulty) {
		String[] s1 = file.split("\\.");
		
		if(s1[1].equals("sud")){
			readSudokuSud(file, difficulty);
		}
		else if(s1[1].equals("sudgame")){
			readSudokuSudgame(file);
	
		}else{
			throw new IllegalArgumentException("Extensão desconhecida " + file);
		}
	}
	
	public void writeSudokuSudgame(String file){
	
		try{
			PrintWriter out = new PrintWriter(file);
			int[][]initial_matrix = this.sudokuBoard.getInitialMatrix();
			int[][]current_matrix = this.sudokuBoard.getPlayMatrix();
			String s1 = SudokuAux.matrixToString(initial_matrix);
			String s2 = SudokuAux.matrixToString(current_matrix);
			
			String text = s1 + "\n" + s2;
			out.print(text);
			out.close();
			
		}catch(FileNotFoundException e){
			System.out.println(" Não consigo criar o ficheiro " + file);
		}
		
	}
	
	
	public void printMatrix(int[][]matrix, String filename){
		
		int[][]matrix2 = new int[matrix.length][matrix[0].length];
		try{
			PrintWriter out = new PrintWriter(filename);
			out.println(matrix[0].length);
			out.println(matrix.length);
			for(int i = 0; i < matrix.length; i++){
				for(int j = 0; j < matrix[i].length; j++){
					matrix2[i][j] = matrix[i][j];
					out.println(matrix2[i][j]);
				}
			}
			out.close();
		}catch(FileNotFoundException e){
			System.out.println(" Não consigo criar o ficheiro " + filename);
		}
		
	}
	
	
	public void readSudokuSud(String file, double difficulty){
		
		int[][]matrix = new int[9][9];
		
		
		try{ 
			File f = new File(file);
			Scanner s = new Scanner(f);
			for( int num_line = 0; num_line < 9; num_line++){
				String line = s.nextLine();
				String[] line_split = line.split(" ");
				for(int i = 0; i < 9; i++){
					int value = Integer.valueOf(line_split[i]);
					matrix[num_line][i] = value;
				}
			}
			s.close();
			
			
		}catch(FileNotFoundException e){
			throw new IllegalArgumentException("Não existe o ficheiro " + file);
		}catch(Exception e){
			throw new IllegalArgumentException("Erro na leitura do ficheiro " + file + ": " + e.getMessage());
		
		}
		int[][]matrix2 = SudokuAux.sudokuWithZeros(matrix,difficulty*100);
		
		
		this.sudokuBoard = new SudokuBoard(matrix2);
		this.boardImage = SudokuAux.drawSudoku(matrix2);
		
	}
	
	public void readSudokuSudgame(String file){
		int[][]matrix = new int[9][9];
		int[][]board_atual = new int[9][9];
		
		try{
			File f = new File(file);
			Scanner s = new Scanner(f);
			for(int num_line = 0; num_line < 9; num_line++){
				String line = s.nextLine();
				String[] line_split = line.split(" ");
				for(int i = 0; i < 9; i++){
					int value = Integer.valueOf(line_split[i]);
					matrix[num_line][i] = value;
				}
			}
			s.nextLine();
			for(int num_line = 0; num_line < 9; num_line++){
				String line = s.nextLine();
				String[] line_split = line.split(" ");
				for(int i = 0; i < 9; i++){
					int value = Integer.valueOf(line_split[i]);
					board_atual[num_line][i] = value;
				}
			}
			s.close();
		}catch(FileNotFoundException e){
			throw new IllegalArgumentException("Não existe o ficheiro " + file);
		}catch(Exception e){
			throw new IllegalArgumentException("Erro na leitura do ficheiro " + file + ": " + e.getMessage());
		
		}
		
		this.sudokuBoard =  new SudokuBoard(matrix,board_atual);
		this.sudokuBoard = new SudokuBoard(board_atual);
		this.boardImage = SudokuAux.drawSudoku(board_atual);
		
	}

	// Funções que permitem fazer jogadas
	public void play(int x, int y, int valor){
		
		try{
		this.sudokuBoard.jogar(x,y,valor);
		SudokuAux.swapNumber(this.boardImage,x,y,String.valueOf(valor));
		
		}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void undo(){
		try{
			int[]v = this.sudokuBoard.undo();
			SudokuAux.swapNumber(this.boardImage,v[0],v[1],"0");
			
			}catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
			
		
	}
	public void makeRandomPlay(){
		try{
			int[]play = this.sudokuBoard.makeRandomMove();
			SudokuAux.swapNumber(this.boardImage,play[0],play[1],String.valueOf(play[2]));
			
			}catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
		
	}
	
	public void resetBoard(){
		
		this.sudokuBoard.resetBoard();
		this.sudokuBoard.getPlayMatrix();
		int[][]matrix = this.sudokuBoard.getPlayMatrix();;
		this.boardImage = SudokuAux.drawSudoku(matrix);
	}
	
	public void isFinished(){
		this.sudokuBoard.isFinished();
		
		
	}
	
	
	
	
	
	
	
}