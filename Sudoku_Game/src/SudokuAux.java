public class SudokuAux {
	
	
	// 1. Validar se uma matriz de inteiros 9x9 representa um Sudoku válido, sendo
		// que o
		// valor 0 representa uma posição não jogada.

	static boolean noDuplicates(int[] values) {
	    for (int i = 0; i < values.length - 1; i++) {
	        if (values[i] != 0) {
	            for (int j = i + 1; j < values.length; j++) {
	                if (values[j] != 0 && values[i] == values[j]) {
	                    return false;
	                }
	            }
	        }
	    }
	    return true;
	}


	static boolean isValidSudoku(int[][] board) {
		// Verificar linhas
		for (int i = 0; i < 9; i++) {
			if (!noDuplicates(board[i])) {
				return false;
			}
		}

		// Verificar colunas
		for (int j = 0; j < 9; j++) {
			int[] column = new int[9];
			for (int i = 0; i < 9; i++) {
				column[i] = board[i][j];
			}
			if (!noDuplicates(column)) {
				return false;
			}
		}

		// Verificar subgrades 3x3
		for (int i = 0; i < 9; i += 3) {
			for (int j = 0; j < 9; j += 3) {
				int[] subgrid = new int[9];
				int index = 0;
				for (int x = i; x < i + 3; x++) {
					for (int y = j; y < j + 3; y++) {
						subgrid[index++] = board[x][y];
					}
				}
				if (!noDuplicates(subgrid)) {
					return false;
				}
			}
		}

		return true;
	}
	
//	2. Gerar uma matriz de jogo dada uma solução completa válida (sem zeros),
//	alterando-a de forma a que parte dos números da mesma sejam substituídos por
//	zero. Deverá ser fornecida uma percentagem que determina qual a proporção de
//	posições a zerar.

	static int[][] sudokuWithZeros(int[][]sudoku, double percentage){  
		int[][]matrix = new int[9][9];
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				int rand = (int)(Math.random()*100);
				if(percentage < rand){
					matrix[i][j] = sudoku[i][j];
				}
			}
		}
		return matrix;
	}
	
//	3. Produzir uma String com o conteúdo de uma matriz de inteiros.
//	{{1,2,3},{4,5,6}} → “1 2 3\n4 5 6”
	
	 static String matrixToString(int[][] m) {
        String s1 = "";

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                s1 = s1 +  m[i][j] + " ";
            }
           s1 = s1.substring(0,s1.length()-1);
           s1 +="\n"; 
        }

        return s1;
    }
	
//	 4. Produzir uma imagem a cores com o desenho do tabuleiro Sudoku a partir de uma
//	 matrix de inteiros 9x9. Poderá ser útil desenvolver procedimentos auxiliares para
//	 abordar partes do objetivo (p.e. desenho de grelha, desenho de números).

	
	// Desenho da grelha 
	 
	 static void drawBoard(ColorImage img){
		    for(int xh = 0; xh < img.getWidth() ; xh++ ){
		        for(int yh= 0; yh < img.getHeight() ; yh+=49){
		            img.drawCenteredText(xh, yh, "-", 10, new Color(210,210,210));
		            if(yh == 0 || yh == 147 || yh == 294 || yh == img.getHeight() - 1){
		                img.drawCenteredText(xh, yh, "-", 5, new Color(0,0,0));
		                img.drawCenteredText(xh, 440, "-", 5, new Color(0,0,0));
		            }
		        }
		    }
		    for(int xv = 0; xv < img.getWidth(); xv+=49){
		        for(int yv = 0; yv < img.getHeight();yv++){
		            img.drawCenteredText(xv, yv, "|", 10, new Color(210,210,210));
		            if(xv == 0 || xv == 147 || xv == 294 || xv == img.getWidth() - 1){
		                img.drawCenteredText(xv, yv, "|", 10, new Color(0,0,0));
		                img.drawCenteredText(440, yv, "|", 10, new Color(0,0,0));
		            }
		        }
		    }
		}


	// Desenho dos números nos quadrados
	
		static void drawNumbers(ColorImage img, int[][] sudoku) {
		    int squareSize = 49;

		    for (int i = 0; i < sudoku.length; i++) {
		        for (int j = 0; j < sudoku[i].length; j++) {
		            if (sudoku[i][j] != 0) {
		                String number = String.valueOf(sudoku[i][j]);
		                int x = (j + 1) * squareSize - squareSize / 2;
		                int y = (i + 1) * squareSize - squareSize / 2;
		                img.drawCenteredText(x, y, number, 15, new Color(0, 0, 0));
		            }
		        }
		    }
		}
	
		//5. Dada uma imagem resultante de (4), alterar uma posição da imagem do tabuleiro,
		//fornecendo uma coordenada e o número a colocar.

	
		static void swapNumber(ColorImage img, int x, int y, String num) {
		    int squareSize = 49;
		    int spacing = 2;  // Espaço entre o quadrado e a borda da grelha
		    if(x >= 0 && x <=8 || y >= 0 && y <= 8){ 
		        int centerX = x * squareSize + squareSize / 2;
		        int centerY = y * squareSize + squareSize / 2;
		        drawSquare(img, centerX - squareSize / 2 + spacing, centerY - squareSize / 2 + spacing, squareSize - 2 * spacing, new Color(255,255,255));
		        img.drawCenteredText(centerX, centerY, num, 15, new Color(0, 0, 255));
		    } else {
		        throw new IllegalArgumentException("that's not on the grid xD");
		    }
		}

		static void drawSquare(ColorImage img, int x, int y, int size, Color c){
		    for(int i = x; i < x + size && i < img.getWidth(); i++){
		        for(int j = y; j < y + size && j < img.getHeight(); j++){
		            img.setColor(i, j, c);
		        }
		    }
		}



		 
		 
//		 6. Dada uma imagem resultante de (4), marcar uma linha do tabuleiro com contorno
//		 vermelho (para sinalizar que está inválida), fornecendo o índice da linha.

		static void invalidLine(ColorImage img, int index){
		    index = index*49; // FUNCIONA PARA 1-9
		    for(int xh = 0; xh < img.getWidth() ; xh++ ){
		        for(int yh= 0; yh < img.getHeight() ; yh+=49){
		            if(yh == index || yh == index - 49){
		                img.drawText(xh, yh, "-", 1, new Color(255,0,0));
		            }
		        }
		        // Desenhar a última linha
		        if(index == 441){
		            img.drawText(xh, img.getHeight() - 1, "-", 1, new Color(255,0,0));
		        }
		    }
		   
		    // Desenhar linha vertical
		    for(int yv = index - 49; yv <= index; yv++){
		        img.drawText(0, yv, "|", 1, new Color(255,0,0));
		        img.drawText(img.getWidth()-1, yv, "|", 1, new Color(255,0,0));
		    }
		}


		
	// 7. Análogo a (6), mas para colunas.

	static void invalidColumn(ColorImage img, int index){
		    index = index*49; // FUNCIONA PARA 1-9
		    for(int xh = 0; xh < img.getWidth() ; xh+=49 ){
		        for(int yh= 0; yh < img.getHeight() ; yh++){
		            if(xh == index || xh == index - 49){
		                img.drawText(xh, yh, "|", 1, new Color(255,0,0));
		            }
		      
//		        // Desenhar a última linha vertical
		        if(index == 441){
		            img.drawText(img.getWidth()-1, yh, "|", 1, new Color(255,0,0));
		        }
		        }
		    }
//		   
		    // Desenhar linha horizontal
		    for(int xh = index - 49; xh <= index; xh++){
		        img.drawText(xh, 0, "-", 1, new Color(255,0,0));
		        img.drawText(xh, img.getHeight()-1, "|", 1, new Color(255,0,0));
		    }
		}
			 
			
		// 8. Dada uma imagem resultante de (4), marcar o segmento com um contorno vermelho
		//(para sinalizar que está inválido), fornecendo o índice do segmento. Um segmento
		//corresponde a um dos nove quadrados 3x3
		
	
	
	
	
	static void markInvalidBox(ColorImage img, int index) {
	    int sizeOfBox = img.getWidth() / 3; // -> tamanho da box
	    int x = (index % 3) * sizeOfBox;
	    int y = (index / 3) * sizeOfBox; // Canto sup esq

	    for (int i = x; i < x + sizeOfBox; i++) {
	        for (int j = y; j < y + sizeOfBox; j++) {
	            if (i == x || i == x + sizeOfBox - 1 || j == y || j == y + sizeOfBox - 1) {
	                img.setColor(i, j, new Color(255, 0, 0));
	            }
	        }
	    }
	}
	
	
		static ColorImage drawSudoku(int[][]matrix){
			ColorImage img = new ColorImage(441,441, new Color(255,255,255));
			drawBoard(img);
			drawNumbers(img, matrix);
			
			return img;
		}
		
		
		
		
		
	// Função Teste
	static void main(){
		ColorImage img = new ColorImage(441,441, new Color(255,255,255));
		int[][]sudoku = {{7, 9, 2, 1, 5, 4, 3, 8, 6},
				{6, 4, 3, 0, 2, 7, 1, 5, 9},
				{8, 5, 1, 3, 9, 0, 7, 2, 4},
				{2, 6, 5, 9, 7, 3, 8, 4, 1},
				{4, 0, 9, 5, 6, 1, 0, 7, 3},
				{3, 1, 7, 4, 8, 2, 9, 6, 5},
				{1, 3, 6, 7, 4, 8, 5, 9, 2},
				{9, 7, 4, 2, 1, 5, 6, 3, 8},
				{5,2, 8, 6, 3, 9, 4, 1, 7}};
//		drawBoard(img);
//		drawNumbers(img,sudoku);
//	swapNumber(img, 8, 8, "0");
//		invalidLine(img, 1); 
//		invalidColumn(img, 5);
//		img.setColor(10,10,new Color(255,255,255));
		img = drawSudoku(sudoku);
		markInvalidBox(img,4);
		return;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}