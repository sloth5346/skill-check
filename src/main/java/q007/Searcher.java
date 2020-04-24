package q007;

import java.util.Stack;

/**
 * 探検者のクラス
 */
public class Searcher {

	// 現在位置(x)
	private int xPos;

	// 現在位置(y)
	private int yPos;

	// 移動回数
	private int moveCount;

	// 移動情報
	private Stack<int[]> moveData = new Stack<int[]>();
	
	// スタートからどの座標に移動したか
	private int sXNextPoint;
	private int sYNextPoint;
	
	// 定数
	private static final int X_INDEX = 0;
	private static final int Y_INDEX = 1;
	private static final int COUNT_INDEX = 2;
	
	public static final int UP_DIRECTION = 0;
	public static final int RIGHT_DIRECTION = 1;
	public static final int DOWN_DIRECTION = 2;
	public static final int LEFT_DIRECTION = 3;
	public static final int CANNOT_MOVE = -1;

	/**
	 * コンストラクタ
	 * @param xPos スタート位置(x)
	 * @param yPos スタート位置(y)
	 */
	public Searcher(int sXPoint, int sYPoint) {
		this.xPos = sXPoint;
		this.yPos = sYPoint;
	}
	
	public int getXpos() {
		return xPos;
	}

	public void setXpos(int xPos) {
		this.xPos = xPos;
	}

	public int getYpos() {
		return yPos;
	}

	public void setYpos(int yPos) {
		this.yPos = yPos;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	public int getsXNextPoint() {
		return sXNextPoint;
	}

	public int getsYNextPoint() {
		return sYNextPoint;
	}

	public void moveUp() {
		this.yPos--;
		this.moveCount++;
	}
	
	public void moveRight() {
		this.xPos++;
		this.moveCount++;
	}
	
	public void moveDown() {
		this.yPos++;
		this.moveCount++;
	}
	
	public void moveLeft() {
		this.xPos--;
		this.moveCount++;
	}
	
	/**
	 * 行き止まりになった際に元いた場所に戻る
	 */
	public void returnHisPot() {
		int[] data = this.moveData.pop();
		this.moveCount = data[COUNT_INDEX];
		this.yPos = data[Y_INDEX];
		this.xPos = data[X_INDEX];
	}
	
	/**
	 * 現在の情報を保存する
	 */
	public void saveCurData() {
		int[] data = new int[3];
		data[X_INDEX] = this.xPos;
		data[Y_INDEX] = this.yPos;
		data[COUNT_INDEX] = this.moveCount;
		this.moveData.push(data);
	}
	
	/**
	 * 壁がないルートを探す
	 * @param mazeData
	 * @return 移動可能な方向
	 */
	public int searchRoot(final MazeData mazeData) {
		if(mazeData.getMazePointNum(this.xPos, this.yPos - 1) == 32 ||
				mazeData.getMazePointNum(this.xPos, this.yPos - 1) == 69) {
			return UP_DIRECTION;
		} else if (mazeData.getMazePointNum(this.xPos + 1, this.yPos) == 32 ||
				mazeData.getMazePointNum(this.xPos + 1, this.yPos) == 69) {
			return RIGHT_DIRECTION;
		} else if (mazeData.getMazePointNum(this.xPos, this.yPos + 1) == 32 ||
				mazeData.getMazePointNum(this.xPos, this.yPos + 1) == 69) {
			return DOWN_DIRECTION;
		} else if (mazeData.getMazePointNum(this.xPos - 1, this.yPos) == 32 ||
				mazeData.getMazePointNum(this.xPos - 1, this.yPos) == 69) {
			return LEFT_DIRECTION;
		} else {
			return CANNOT_MOVE;
		}
	}
	
	public void resetMoveCount() {
		setMoveCount(-1);
	}
	
	public void setStartNextPoint() {
		this.sXNextPoint = this.xPos;
		this.sYNextPoint = this.yPos;
	}

}