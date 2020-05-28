package code;

import java.awt.Dimension;


import java.awt.Graphics;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	//Instances Variables
	private final int NUM_IMAGES = 13;
	private final int CELL_SIZE = 20;
	private ImageIcon thing;
	private int cell = 0;
	private final int COVER_FOR_CELL = 10;
	private final int MARK_FOR_CELL = 10;
	private final int EMPTY_CELL = 0;
	private final int MINE_CELL = 9;
	private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
	private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

	private final int DRAW_MINE = 9;
	private final int DRAW_COVER = 10;
	private final int DRAW_MARK = 11;
	private final int DRAW_WRONG_MARK = 12;

	private final int N_MINES = 40;
	private final int N_ROWS = 16;
	private final int N_COLS = 16;

	private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
	private final int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;

	private int[] field;
	private boolean inGame;
	private int minesLeft;

	
	
	private int allCells;
	
	//ImageIcon Variables
	private ImageIcon e = new ImageIcon(getClass().getResource("exit.png"));
	private ImageIcon zero = new ImageIcon(getClass().getResource("0.png"));
	private ImageIcon one = new ImageIcon(getClass().getResource("1.png"));
	private ImageIcon two = new ImageIcon(getClass().getResource("2.png"));
	private ImageIcon three = new ImageIcon(getClass().getResource("3.png"));
	private ImageIcon four = new ImageIcon(getClass().getResource("4.png"));
	private ImageIcon five = new ImageIcon(getClass().getResource("5.png"));
	private ImageIcon six = new ImageIcon(getClass().getResource("6.png"));
	private ImageIcon seven = new ImageIcon(getClass().getResource("7.png"));
	private ImageIcon eight = new ImageIcon(getClass().getResource("8.png"));
	private ImageIcon nine = new ImageIcon(getClass().getResource("9.png"));
	private ImageIcon ten = new ImageIcon(getClass().getResource("10.png"));
	private ImageIcon eleven = new ImageIcon(getClass().getResource("11.png"));
	private ImageIcon twelve = new ImageIcon(getClass().getResource("12.png"));
	
	//JFrame Variables
	private JLabel statusbar;

	private JPanel status;
	
	private JButton exit = new JButton(e);

	//Get method
		public JPanel getStatus() {
			return getStatus();
		}
		
	//This method creates the JFrame itself
	public Board(JPanel statusbar) {

		this.status = statusbar;

		initBoard();
	}

	
	//This method adds to the status JFrame
	private void setStatus() {
		statusbar = new JLabel("");

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		status.add(statusbar);

		status.add(exit);

	}

	//This method sets the properties for the JFrame to be constructed and begins the game
	private void initBoard() {

		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		addMouseListener(new MinesAdapter());
		setStatus();
		newGame();

	}

	//This method creates the game state and creates the mines once the player has made his fist click
	//It checks and sets a random orientation with a set number the mines in a cloaked field
	private void newGame() {

		int cell;

		Random random = new Random();
		inGame = true;
		minesLeft = N_MINES;

		allCells = N_ROWS * N_COLS;
		field = new int[allCells];

		for (int i = 0; i < allCells; i++) {

			field[i] = COVER_FOR_CELL;
		}
		statusbar.setText("There are " + minesLeft + " flags left to place!");

		int i = 0;

		while (i < N_MINES) {

			int position = (int) (allCells * random.nextDouble());

			if ((position < allCells) && (field[position] != COVERED_MINE_CELL)) {

				int current_col = position % N_COLS;
				field[position] = COVERED_MINE_CELL;
				i++;

				if (current_col > 0) {
					cell = position - 1 - N_COLS;
					if (cell >= 0) {
						if (field[cell] != COVERED_MINE_CELL) {
							field[cell] += 1;
						}
					}
					cell = position - 1;
					if (cell >= 0) {
						if (field[cell] != COVERED_MINE_CELL) {
							field[cell] += 1;
						}
					}

					cell = position + N_COLS - 1;
					if (cell < allCells) {
						if (field[cell] != COVERED_MINE_CELL) {
							field[cell] += 1;
						}
					}
				}

				cell = position - N_COLS;
				if (cell >= 0) {
					if (field[cell] != COVERED_MINE_CELL) {
						field[cell] += 1;
					}
				}

				cell = position + N_COLS;
				if (cell < allCells) {
					if (field[cell] != COVERED_MINE_CELL) {
						field[cell] += 1;
					}
				}

				if (current_col < (N_COLS - 1)) {
					cell = position - N_COLS + 1;
					if (cell >= 0) {
						if (field[cell] != COVERED_MINE_CELL) {
							field[cell] += 1;
						}
					}
					cell = position + N_COLS + 1;
					if (cell < allCells) {
						if (field[cell] != COVERED_MINE_CELL) {
							field[cell] += 1;
						}
					}
					cell = position + 1;
					if (cell < allCells) {
						if (field[cell] != COVERED_MINE_CELL) {
							field[cell] += 1;
						}
					}
				}
			}
		}

	}
	
	//This method using recursion to check for the positions that need to be revealed when a player clicks
	private void find_empty_cells(int j) {

		int current_col = j % N_COLS;
		int cell;

		if (current_col > 0) {
			cell = j - N_COLS - 1;
			if (cell >= 0) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL) {
						find_empty_cells(cell);
					}
				}
			}

			cell = j - 1;
			if (cell >= 0) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL) {
						find_empty_cells(cell);
					}
				}
			}

			cell = j + N_COLS - 1;
			if (cell < allCells) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL) {
						find_empty_cells(cell);
					}
				}
			}
		}

		cell = j - N_COLS;
		if (cell >= 0) {
			if (field[cell] > MINE_CELL) {
				field[cell] -= COVER_FOR_CELL;
				if (field[cell] == EMPTY_CELL) {
					find_empty_cells(cell);
				}
			}
		}

		cell = j + N_COLS;
		if (cell < allCells) {
			if (field[cell] > MINE_CELL) {
				field[cell] -= COVER_FOR_CELL;
				if (field[cell] == EMPTY_CELL) {
					find_empty_cells(cell);
				}
			}
		}

		if (current_col < (N_COLS - 1)) {
			cell = j - N_COLS + 1;
			if (cell >= 0) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL) {
						find_empty_cells(cell);
					}
				}
			}

			cell = j + N_COLS + 1;
			if (cell < allCells) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL) {
						find_empty_cells(cell);
					}
				}
			}

			cell = j + 1;
			if (cell < allCells) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL) {
						find_empty_cells(cell);
					}
				}
			}
		}

	}

	//This method actually makes the components of the game visible and displayed to the player
	@Override
	public void paintComponent(Graphics g) {

		int uncover = 0;

		for (int i = 0; i < N_ROWS; i++) {

			for (int j = 0; j < N_COLS; j++) {

				cell = field[(i * N_COLS) + j];

				if (inGame && cell == MINE_CELL) {

					inGame = false;
				}

				if (!inGame) {

					if (cell == COVERED_MINE_CELL) {
						cell = DRAW_MINE;
					} else if (cell == MARKED_MINE_CELL) {
						cell = DRAW_MARK;
					} else if (cell > COVERED_MINE_CELL) {
						cell = DRAW_WRONG_MARK;
					} else if (cell > MINE_CELL) {
						cell = DRAW_COVER;
					}

				} else {

					if (cell > COVERED_MINE_CELL) {
						cell = DRAW_MARK;
					} else if (cell > MINE_CELL) {
						cell = DRAW_COVER;
						uncover++;
					}
				}
				if(cell == 0) {
					thing = zero;					
				}
				if(cell == 1) {
					thing = one;					
				}
				if(cell == 2) {
					thing = two;					
				}
				if(cell == 3) {
					thing = three;					
				}
				if(cell == 4) {
					thing = four;					
				}
				if(cell == 5) {
					thing = five;					
				}
				if(cell == 6) {
					thing = six;					
				}
				if(cell == 7) {
					thing = seven;					
				}
				if(cell == 8) {
					thing = eight;					
				}
				if(cell == 9) {
					thing = nine;					
				}
				if(cell == 10) {
					thing = ten;					
				}
				if(cell == 11) {
					thing = eleven;					
				}
				if(cell == 12) {
					thing = twelve;					
				}
							
				g.drawImage(thing.getImage(), (j * CELL_SIZE), (i * CELL_SIZE),this);
			}
		}

		if (uncover == 0 && inGame) {

			inGame = false;
			statusbar.setText("YOU WON!");

		} else if (!inGame) {

			statusbar.setText("You lost :<(");
		}
	}

	
	//This class is created to intake the mouse clicks and the appropriate actions tied to the mouse  
	private class MinesAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			int x = e.getX();
			int y = e.getY();

			int cCol = x / CELL_SIZE;
			int cRow = y / CELL_SIZE;

			boolean doRepaint = false;

			if (!inGame) {

				newGame();
				repaint();
			}

			if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {

				if (e.getButton() == MouseEvent.BUTTON3) {

					if (field[(cRow * N_COLS) + cCol] > MINE_CELL) {

						doRepaint = true;

						if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL) {

							if (minesLeft > 0) {
								field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
								minesLeft--;
								String msg = "There are " + minesLeft + " flags left to place!";
								statusbar.setText(msg);
							} else {
								statusbar.setText("You have no marks left");
							}
						} else {

							field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
							minesLeft++;
							String msg = "There are " + minesLeft + " flags left to place!";
							statusbar.setText(msg);
						}
					}

				} else {

					if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL) {

						return;
					}

					if ((field[(cRow * N_COLS) + cCol] > MINE_CELL)
							&& (field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)) {

						field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
						doRepaint = true;

						if (field[(cRow * N_COLS) + cCol] == MINE_CELL) {
							inGame = false;
						}

						if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL) {
							find_empty_cells((cRow * N_COLS) + cCol);
						}
					}
				}

				if (doRepaint) {
					repaint();
				}
			}
		}
	}
}