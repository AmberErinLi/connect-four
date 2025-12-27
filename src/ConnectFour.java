import java.util.Scanner;

// A strategy game where two players take turns placing their tokens. The first player to
// get four in a row of their token, either horizontally, vertically, or diagonally, wins.
public class ConnectFour extends AbstractStrategyGame {
    private static final char PLAYER_1_TOKEN = 'X';
    private static final char PLAYER_2_TOKEN = 'O';
    private static final int PLAYER_1 = 1;
    private static final int PLAYER_2 = 2;
    private static final int TIE = 0;
    private static final int EMPTY = 0;
    private static final int GAME_IS_OVER = -1;
    private static final int GAME_NOT_OVER = -1;
    private static final int COLUMN_FULL = -1;
    
    private char[][] board;
    private boolean isXTurn;

    // Behavior:
    //   - Creates a new ConnectFour game with an empty 6 x 7 game board
    public ConnectFour() {
        board = new char[][]{{' ', ' ', ' ', ' ', ' ', ' ', ' '},
                             {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                             {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                             {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                             {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                             {' ', ' ', ' ', ' ', ' ', ' ', ' '}};
        isXTurn = true;
    }

    // Returns:
    //   - a String containing instructions to play the game.
    public String instructions() {
        String result = "";
        result += "Player 1 is X and goes first. Choose where to play by entering the number\n";
        result += "of the column. Blank spaces are empty. The game ends when one player\n";
        result += "connects four spaces with their token (X or O) in a row, in which that\n";
        result += "player wins, or when the board is full, in which case the game ends in a tie.";
        return result;
    }

    // Returns:
    //   - a String representation of the current state of the board.
    //     For example, the empty board looks like:
    //     |   |   |   |   |   |   |   |
    //     |   |   |   |   |   |   |   |
    //     |   |   |   |   |   |   |   |
    //     |   |   |   |   |   |   |   |
    //     |   |   |   |   |   |   |   |
    //     |   |   |   |   |   |   |   |
    //     When a player makes a turn, the chosen empty space is replaced with their token.
    public String toString() {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            result += "|";
            for (int j = 0; j < board[0].length; j++) {
                result += " " + board[i][j] + " |";
            }
            result += "\n";
        }
        return result;
    }

    // Returns:
    //   - An int indicating which player's turn it is. 1 if player 1 (X), 2 if player 2 (O),
    //     -1 if the game is over
    public int getNextPlayer() {
        if (isGameOver()) {
            return GAME_IS_OVER;
        }
        
        return isXTurn ? PLAYER_1 : PLAYER_2;
    }

    // Behavior: 
    //   - Given the input, places an X or an O at the lowest empty space in the column that
    //     the player specifies.
    // Exceptions: 
    //   - Throws an IllegalArgumentException if the provided scanner is null.
    //   - Throws an IllegalArgumentException if the position is invalid, whether that be
    //     out of bounds or an already full column. Board bounds are [1, 7] for columns.
    // Parameters:
    //   - Scanner input - Takes input from the user about which column to add their token to
    public void makeMove(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("Scanner cannot be null.");
        }
        char currToken = isXTurn ? PLAYER_1_TOKEN : PLAYER_2_TOKEN;

        System.out.print("Column? (1-7) ");
        int column = input.nextInt();

        makeMove(column, currToken);
        isXTurn = !isXTurn;
    }

    // Behavior:
    //   - Places the given token at the lowest empty row in the given column.
    // Exceptions: 
    //   - Throws an IllegalArgumentException if the position is invalid, whether that be
    //     out of bounds or an already full column. Board bounds are [1, 7] for columns.
    // Parameters:
    //   - int column - The column to place the given token in. It will be placed at the
    //                  lowest unoccupied row in the column.
    //   - char token - The token to place in the given column. X for player 1 or O for player 2
    private void makeMove(int column, char token) {
        if (column < 1 || column > 7) {
            throw new IllegalArgumentException("Column must be between 1 and 7.");
        } else if (getLowestRow(column - 1) == COLUMN_FULL) {
            throw new IllegalArgumentException("Column is full.");
        }
        board[getLowestRow(column - 1)][column - 1] = token;
    }

    // Returns:  
    //   - The index of the lowest unoccupied row in the given column or -1 if the column is
    //     full
    // Parameters:
    //   - int col - The index of the column to find the lowest empty spot in.
    private int getLowestRow(int col) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][col] != PLAYER_1_TOKEN && board[row][col] != PLAYER_2_TOKEN) {
                return row;
            }
        }
        return COLUMN_FULL;
    }

    // Returns:
    //   - The index of the winner of the game. 1 if player 1 (X), 2 if player 2 (O),
    //     0 if a tie occurred, and -1 if the game is not over.
    public int getWinner() {
        for (int i = 0; i < board.length; i++) {
            int rowWinner = getRowWinner(i);
            if (rowWinner != GAME_NOT_OVER) {
                return rowWinner;
            }

            int colWinner = getColWinner(i);
            if (colWinner != GAME_NOT_OVER) {
                return colWinner;
            }
        }

        int diagWinner = getDiagWinner();
        if (diagWinner != GAME_NOT_OVER) {
            return diagWinner;
        }
        return checkTie();
    }

    // Returns:
    //   - The winner of the row. 1 if player 1 won, 2 if player 2 won, -1 otherwise.
    // Parameters:
    //   - int row - The row of the game board to be checked for a winner
    private int getRowWinner(int row) {
        int consecutive = 0;
        int player = EMPTY;
        for (int col = 0; col < board[row].length; col++) {
            if (board[row][col] == PLAYER_1_TOKEN) {
                if (player == PLAYER_1) {
                    consecutive++;
                } else {
                    player = PLAYER_1;
                    consecutive = 1;
                }
            } else if (board[row][col] == PLAYER_2_TOKEN) {
                if (player == PLAYER_2) {
                    consecutive++;
                } else {
                    player = PLAYER_2;
                    consecutive = 1;
                }
            } else {
                player = EMPTY;
                consecutive = 0;
            }
            if (consecutive == 4) {
                return player;
            }
        }
        return GAME_NOT_OVER;
    }

    // Returns:
    //   - The winner of the column. 1 if player 1 won, 2 if player 2 won, -1 otherwise.
    // Parameters:
    //   - int col - The column of the game board to be checked for a winner
    private int getColWinner(int col) {
        int consecutive = 0;
        int player = EMPTY;
        for (int row = 0; row < board.length; row++) {
            if (board[row][col] == PLAYER_1_TOKEN) {
                if (player == PLAYER_1) {
                    consecutive++;
                } else {
                    player = PLAYER_1;
                    consecutive = 1;
                }
            } else if (board[row][col] == PLAYER_2_TOKEN) {
                if (player == PLAYER_2) {
                    consecutive++;
                } else {
                    player = PLAYER_2;
                    consecutive = 1;
                }
            } else {
                player = EMPTY;
                consecutive = 0;
            }
            if (consecutive == 4) {
                return player;
            }
        }
        return GAME_NOT_OVER;
    }

    // Returns:
    //   - The winner of any diagonal. 1 if player 1 won, 2 if player 2 won, -1 otherwise.
    private int getDiagWinner() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i + 3 < board.length && j + 3 < board[i].length) {
                    if (board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2]
                        && board[i][j] == board[i + 3][j + 3]) {
                            if (board[i][j] == PLAYER_1_TOKEN) {
                                return PLAYER_1;
                            } else if (board[i][j] == PLAYER_2_TOKEN) {
                                return PLAYER_2;
                            }
                    }
                } else if (i - 3 >= 0 && j + 3 < board[i].length) {
                    if (board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2]
                        && board[i][j] == board[i - 3][j + 3]) {
                            if (board[i][j] == PLAYER_1_TOKEN) {
                                return PLAYER_1;
                            } else if (board[i][j] == PLAYER_2_TOKEN) {
                                return PLAYER_2;
                            }
                    }
                }
            }
        }
        return GAME_NOT_OVER;
    }

    // Returns:
    //   - 0 if there's a tie (which occurs when the entire game board is full and no player
    //     has won) and -1 otherwise.
    private int checkTie() {
        for (int i = 0; i < board[0].length; i++) {
            if (getLowestRow(i) != -1) {
                return GAME_NOT_OVER;
            }
        }
        return TIE;
    }

}
