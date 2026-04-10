class SudokuBoard {
	// Parte 2

	
	// Atributos
	
	private int[][] play_sudoku; 
	private int[][] originalSudoku = new int[9][9];
	

    private int[][] estado;
    private int num_jogadas;
    private int count_zeros;

	
	
	// Métodos construtores
	// 2.1 é valido?
    
    public SudokuBoard(int[][]originalSudoku){
        if(SudokuAux.isValidSudoku(originalSudoku)){
            this.originalSudoku = originalSudoku;
            this.play_sudoku = copyBoard(originalSudoku);
        }else{
            throw new IllegalArgumentException("Sudoku não é válido!");
        }
     // TODO alterar
        int count_zeros = contaZeros();
        this.estado = new int[count_zeros][3];
        this.num_jogadas = -1;
    }
   public SudokuBoard(int[][]originalSudoku,int[][]actual_Sudoku){
	   
	   if(SudokuAux.isValidSudoku(originalSudoku)){
           this.originalSudoku = originalSudoku;
           this.play_sudoku = copyBoard(originalSudoku);
       }else{
           throw new IllegalArgumentException("Sudoku inicial não é válido!");
       }
	   if(SudokuAux.isValidSudoku(actual_Sudoku)){
		   this.play_sudoku = actual_Sudoku;
	   }else{
		   throw new IllegalArgumentException("Sudoku actual não é válido!");
	   }
	   // TODO alterar
	   int count_zeros = contaZeros();
       this.estado = new int[count_zeros][3];
       this.num_jogadas = -1;
   }
  
	// Métodos de instância
	public int[][] getInitialMatrix(){
		
		return this.originalSudoku;
	}
	public int[][] getPlayMatrix(){
		
		return this.play_sudoku;
	}
	
	// 2.2 número na coordenada
	
	 public int numberAtCoordinate(int x, int y) {
	    if (x >= 0 && x < play_sudoku.length && y >= 0 && y < play_sudoku[x].length) {
	        return play_sudoku[x][y];
	    } else {
	        return 0;
	    }
	}
    public void jogar(int x, int y, int valor) {
        if (numberAtCoordinate(x, y) == 0) {
            play_sudoku[x][y] = valor;
            updateEstado(x, y, valor);
        } else {
            throw new IllegalArgumentException("Jogada inválida");
        }
    }

    public void updateEstado(int x, int y, int valor) {
        if (num_jogadas < estado.length - 1) {
            num_jogadas++;
            estado[num_jogadas][0] = valor;
            estado[num_jogadas][1] = x;
            estado[num_jogadas][2] = y;
        } else {
            // Array cheio!
        }
    }

	// 2.4 jogada num 0 aleatório
    
	public int[] findRandomEmptyPosition() {
		int[][]matrix = new int[81][2];// 81 possíveis posições livres
		int count = 0;
	    for (int i = 0; i < 9; i++) {
	        for (int j = 0; j < 9; j++) {
	            if (play_sudoku[i][j] == 0) {
	            	matrix[count][1] = j;
	            	matrix[count][0] = i;
	            	count++;
	            }
	        }
	    }
	    
	    if(count == 0){
	    	throw new IllegalArgumentException("Não há posições vazias");
	    }
	    int rand = (int)(Math.random()*(count));
	    return matrix[rand]; // Não há posições vazias
	}

	public int[] makeRandomMove() {
	  // gerar posicao aleatorio vazia
	  int[] position = findRandomEmptyPosition();
	  int i = position[0];
	  int j = position[1];
	 
	  // gerar numero aleatorio, valido para esta box
	  int boxIndex = boxIndex(i,j) ;	  
	  int[] unusedValues = findUnusedValuesInBox(boxIndex);
	  int rand = (int)(Math.random()*(unusedValues.length));
	  int randValue = unusedValues[rand];
	 
	        // Efetuar a jogada
	        play_sudoku[i][j] = randValue;

	        // Atualizar o estado
	        updateEstado(i, j, randValue);
	    
	   int[]play = new int[3];
	   play[0] = i;
	   play[1] = j;
	   play[2] = randValue;

		
	    return play;
	}

	
	// 2.5 reinicia o tabuleiro
    public int[][] copyBoard(int[][]sudoku){
			int[][]copy = new int[9][9];
		    for(int i = 0; i < 9; i++){
		        for(int j = 0; j < 9; j++){
		            copy[i][j] = sudoku[i][j];
		        }
		    }
		    return copy;
		}
    
	public void resetBoard(){
		this.play_sudoku = copyBoard(this.originalSudoku);
		this.num_jogadas = 0;
	}
	
	
	// 2.6 obter a caixa com duplicates
	
	public int[] findUnusedValuesInBox(int index) {
		boolean[]foundNum = new boolean[10];
		
		int[]v2 = boxTopLeftPosition(index);
		for(int i = v2[0]; i < v2[0]+3; i++){
			for(int j = v2[1]; j < v2[1] + 3; j++){
				int num = this.play_sudoku[i][j];
				foundNum[num] = true;
				
			}
			 for (int k = 0; k < foundNum.length;k++) 
			        foundNum[k] = false;
			    
		}
		int[]unusedValues = new int[9];
		int count = 0;
		for(int i=1; i <= 9; i++){
			if(foundNum[i] == false){
				unusedValues[count] = i;
				count++;
			}
		}
		int[]result = new int[count];
		for(int i = 0; i < count; i++){
			result[i] = unusedValues[i];
		}
		return result;
	}
	
	public int[]boxTopLeftPosition(int index){
		int boxrow = (index/3)*3;
		int boxcol = (index%3)*3;
		int[]v = new int[2];
		
		v[0] = boxrow;
		v[1] = boxcol;
		
		return v;
		
	}
	
	public int boxIndex(int i, int j) {
	   return (i/3)*3 + (j%3);
	}


	// 2.7 obter as linhas/colunas com duplicates
	public int getInvalidLines(){
		int index = -1;
		for(int i = 0; i < play_sudoku.length ; i++){
			for(int j = 0; j < play_sudoku[i].length ; j++){
				if(!isValidRow(i)){
					index = i;
				}
			}
		}
		return index;
	}


	public int getInvalidCols(){
		int index = -1;
		for(int i = 0; i < play_sudoku.length ; i++){
			for(int j = 0; j < play_sudoku[i].length ; j++){
				if(!isValidColumn(j)){
					index = j;
				}
		
			}
		}
		return index;
	}
	public boolean isValidRow(int row) {
        boolean[] seen = new boolean[10]; // Index 0 is not used
        for (int col = 0; col < 9; col++) {
            int num = play_sudoku[row][col];
            if (num != 0) {
                if (seen[num]) {
                    return false; // Duplicado encontrado na linha
                }
                seen[num] = true;
            }
        }
        return true; // Linha válida
    }
	
	  public boolean isValidColumn(int col) {
        boolean[] seen = new boolean[10]; // Index 0 is not used
        for (int row = 0; row < 9; row++) {
            int num = play_sudoku[row][col];
            if (num != 0) {
                if (seen[num]) {
                    return false; // Duplicado encontrado na coluna
                }
                seen[num] = true;
            }
        }
        return true; // Coluna válida
    }
	
	
	// 2.8 isFinished?
	
	public boolean isFinished(){
		
		if(!sweepMatrix(play_sudoku)){
			throw new IllegalArgumentException(" Sudoku não acabado ");
		}
		return true;
	}
	
	public boolean sweepMatrix(int[][]sudoku){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				
				if(sudoku[i][j] == 0){ // se encontrar alguma casa vazia
					return false; 
				}
				
			}
		}
		return true;
	}
	

	
	// 2.9 undo
	 public int[] undo() {
	        if (num_jogadas >= 0) {
	            int x = estado[num_jogadas][1];
	            int y = estado[num_jogadas][2];
	            play_sudoku[x][y] = 0;
	            estado[num_jogadas][0] = 0;
	            num_jogadas--;
	            int[]v = new int[2];
	            v[0] = x;
	            v[1] = y;
	            return v;
	        } else {
	            throw new IllegalArgumentException("Não há jogadas a desfazer");
	        }
	        
	    }
	

	 public int contaZeros() {
		 int count = 0;
		    for (int i = 0; i < 9; i++) {
		        for (int j = 0; j < 9; j++) {
		            if (play_sudoku[i][j] == 0) {
		                count++;
		            }
		        }
		    }
		    return count;
		}
	static void main(){
		int[][]originalSudoku =
			{{7, 0, 0, 0, 0, 4, 3, 8, 6},
					{0, 4, 3, 0, 2, 7, 1, 5, 9},
					{0, 5, 0, 3, 0, 0, 7, 2, 0},
					{2, 6, 5, 9, 0, 3, 8, 4, 1},
					{4, 0, 9, 5, 6, 1, 0, 0, 3},
					{3, 1, 0, 4, 0, 0, 0, 0, 5},
					{1, 3, 6, 7, 4, 8, 5, 9, 2},
					{0, 0, 4, 2, 1, 5, 6, 3, 8},
					{0,2, 0, 6, 3, 0, 4, 1, 0}};



		SudokuBoard s = new SudokuBoard(originalSudoku);
		//s.numberAtCoordinate(3,2);
//		s.copyBoard(sudoku);
//		s.resetBoard();
		//s.play(2,5,6);
		//s.isFinished();
//		s.getInvalidLines();
//		s.getInvalidCols();
		return;
	}
	
	
}