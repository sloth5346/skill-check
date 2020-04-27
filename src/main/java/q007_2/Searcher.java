package q007_2;

import java.util.ArrayDeque;
import java.util.Queue;

public class Searcher {
	
	// 座標用の配列のインデックス指定用
	private static final int X_INDEX = 0;
	private static final int Y_INDEX = 1;
	
	// 通過済みのとき1,未通過のとき-1
	private static final int PASSED_POINT = 1;
	private static final int NO_PASSED_POINT = -1;
	
	// queueに投入する配列のインデックス
	private static final int X_CUR_POINT = 0;
	private static final int Y_CUR_POINT = 1;
	private static final int X_NEX_POINT = 2;
	private static final int Y_NEX_POINT = 3;
	
	// 迷路データ(ArrayList)のインデックス
	private static final int COORDINATE_NUM_INDEX = 0;
	private static final int PASS_CHK_INDEX = 1;
	private static final int PRE_X_INDEX = 2;
	private static final int PRE_Y_INDEX = 3;
	
	private MazeData mazeData;
	
	private int[] curPos = new int[2];
	
	private Queue<int[]> queue = new ArrayDeque<>();
	
	private int moveCount;
	
	// 一つ前のポイント
	private int xBackPoint;
	private int yBackPoint;
	
	public Searcher(MazeInputStream mazeInputStream) {
		this.mazeData = new MazeData(mazeInputStream);
		int[] point = this.mazeData.getSPoint();
		this.curPos[X_INDEX] = point[X_INDEX];
		this.curPos[Y_INDEX] = point[Y_INDEX];
		
	}
	
	public void setPassed() {
		this.mazeData.setPassed(this.curPos[X_INDEX], this.curPos[Y_INDEX]);
	}
	
	/**
	 * 移動可能なポイント(ゴール、通過済みのぞく)があればキューに詰める
	 */
	public void searchNextPoint() {
		
		int[] nexPoint = new int[2];
		
		if(//!chkUpGoal(this.curPos[X_INDEX], this.curPos[Y_INDEX]) &&
				!chkUpPassed(this.curPos[X_INDEX], this.curPos[Y_INDEX]) &&
				(nexPoint = chkUpRoot(this.curPos[X_INDEX], this.curPos[Y_INDEX])) != null) {
			this.queue.add(getQueueData(this.curPos[X_INDEX], this.curPos[Y_INDEX], nexPoint[X_INDEX], nexPoint[Y_INDEX]));
		}
		if(//!chkRightGoal(this.curPos[X_INDEX], this.curPos[Y_INDEX]) &&
				!chkRightPassed(this.curPos[X_INDEX], this.curPos[Y_INDEX]) &&
				(nexPoint = chkRightRoot(this.curPos[X_INDEX], this.curPos[Y_INDEX])) != null) {
			this.queue.add(getQueueData(this.curPos[X_INDEX], this.curPos[Y_INDEX], nexPoint[X_INDEX], nexPoint[Y_INDEX]));
		}
		if(//!chkDownGoal(this.curPos[X_INDEX], this.curPos[Y_INDEX]) &&
				!chkDownPassed(this.curPos[X_INDEX], this.curPos[Y_INDEX]) &&
				(nexPoint = chkDownRoot(this.curPos[X_INDEX], this.curPos[Y_INDEX])) != null) {
			this.queue.add(getQueueData(this.curPos[X_INDEX], this.curPos[Y_INDEX], nexPoint[X_INDEX], nexPoint[Y_INDEX]));
		}
		if(//!chkLeftGoal(this.curPos[X_INDEX], this.curPos[Y_INDEX]) &&
				!chkLeftPassed(this.curPos[X_INDEX], this.curPos[Y_INDEX]) &&
				(nexPoint = chkLeftRoot(this.curPos[X_INDEX], this.curPos[Y_INDEX])) != null) {
			this.queue.add(getQueueData(this.curPos[X_INDEX], this.curPos[Y_INDEX], nexPoint[X_INDEX], nexPoint[Y_INDEX]));
		}
	}
	
	private int[] getQueueData(int xCurPoint, int yCurPoint, int xNexPoint, int yNexPoint) {
		int[] queueData = new int[4];
		queueData[X_CUR_POINT] = xCurPoint;
		queueData[Y_CUR_POINT] = yCurPoint;
		queueData[X_NEX_POINT] = xNexPoint;
		queueData[Y_NEX_POINT] = yNexPoint;
		return queueData;
	}
	
	/**
	 * キューから移動可能なポイントを取り出し移動する
	 */
	public void move() {
		if(!this.queue.isEmpty()) {
			int[] queueData = this.queue.poll();
			if(this.mazeData.getArray().get(queueData[Y_NEX_POINT]).get(queueData[X_NEX_POINT]).get(PASS_CHK_INDEX) == NO_PASSED_POINT) {
				int[] curPosLocal = {queueData[X_CUR_POINT], queueData[Y_CUR_POINT]};
				//this.curPos[X_INDEX] = xIndex;
				//this.curPos[Y_INDEX] = yIndex;
				int[] nexPos = {queueData[X_NEX_POINT], queueData[Y_NEX_POINT]};
				//nexPos[X_INDEX] = queueData[X_NEX_POINT];
				//nexPos[Y_INDEX] = queueData[Y_NEX_POINT];
				// 移動前にルート情報を保存
				this.mazeData.setRouteData(curPosLocal, nexPos);
				this.curPos[X_INDEX] = queueData[X_NEX_POINT];
				this.curPos[Y_INDEX] = queueData[Y_NEX_POINT];
				display();
			}
		}
	}
	
	/**
	 * ゴールに到達したかどうかをチェックする
	 * @return
	 */
	public boolean chkReachGoal() {
		int[] ePoint = this.mazeData.getEPoint();
		return this.curPos[X_INDEX] == ePoint[X_INDEX] && this.curPos[Y_INDEX] == ePoint[Y_INDEX];
	}
	
	/**
	 * ゴールからスタートまでバックし、移動回数を数える(ゴールの隣を通っていない場合は到達不可だった時として-1をリターン)
	 * @return
	 */
	public int countMove() {
		int[] preGoalPoint = searchPreGoalPoint();
		if(preGoalPoint[X_INDEX] == 0 && preGoalPoint[Y_INDEX] == 0) {
			return -1;
		} else {
			this.moveCount = 1;
			this.xBackPoint = preGoalPoint[X_INDEX];
			this.yBackPoint = preGoalPoint[Y_INDEX];

			int[] sPoint = this.mazeData.getSPoint();
			while(this.xBackPoint != sPoint[X_INDEX] || this.yBackPoint != sPoint[Y_INDEX]) {
				int xBackPoint = this.mazeData.getPreXPoint(this.xBackPoint, this.yBackPoint);
				int yBackPoint = this.mazeData.getPreYPoint(this.xBackPoint, this.yBackPoint);
				this.xBackPoint = xBackPoint;
				this.yBackPoint = yBackPoint;
				
				this.moveCount++;
			}
		}
		return this.moveCount;
	}
	
	/**
	 * ゴール手前の座標を求める
	 * @return
	 */
	public int[] searchPreGoalPoint() {
		int[] preGoalPoint = new int[2];
		int[] ePoint = this.mazeData.getEPoint();
		
		// ゴールの一つ上のポイント？
		if(this.mazeData.getCoordinateNum(ePoint[X_INDEX], ePoint[Y_INDEX] - 1) == 32 &&
				this.mazeData.getCoordinatePassed(ePoint[X_INDEX], ePoint[Y_INDEX] - 1) == PASSED_POINT) {
			preGoalPoint[X_INDEX] = ePoint[X_INDEX];
			preGoalPoint[Y_INDEX] = ePoint[Y_INDEX] - 1;
		} else if (this.mazeData.getCoordinateNum(ePoint[X_INDEX] + 1, ePoint[Y_INDEX]) == 32 &&
				this.mazeData.getCoordinatePassed(ePoint[X_INDEX] + 1, ePoint[Y_INDEX]) == PASSED_POINT) {
			preGoalPoint[X_INDEX] = ePoint[X_INDEX] + 1;
			preGoalPoint[Y_INDEX] = ePoint[Y_INDEX];	
		} else if (this.mazeData.getCoordinateNum(ePoint[X_INDEX], ePoint[Y_INDEX] + 1) == 32 &&
				this.mazeData.getCoordinatePassed(ePoint[X_INDEX], ePoint[Y_INDEX] + 1) == PASSED_POINT) {
			preGoalPoint[X_INDEX] = ePoint[X_INDEX];
			preGoalPoint[Y_INDEX] = ePoint[Y_INDEX] + 1;
		} else if (this.mazeData.getCoordinateNum(ePoint[X_INDEX] - 1, ePoint[Y_INDEX]) == 32 &&
				this.mazeData.getCoordinatePassed(ePoint[X_INDEX] - 1, ePoint[Y_INDEX]) == PASSED_POINT) {
			preGoalPoint[X_INDEX] = ePoint[X_INDEX] - 1;
			preGoalPoint[Y_INDEX] = ePoint[Y_INDEX];
		}
		
		return preGoalPoint;
	}
	
	/**
	 * キューが空になったときに終了
	 * @return
	 */
	public boolean chkSearchFin() {
		return this.queue.isEmpty();
	}
	
	private boolean chkUpPassed(int xPoint, int yPoint) {
		return this.mazeData.getCoordinatePassed(xPoint, yPoint - 1) == PASSED_POINT;
	}
	
	private boolean chkRightPassed(int xPoint, int yPoint) {
		return this.mazeData.getCoordinatePassed(xPoint + 1, yPoint) == PASSED_POINT;
	}
	
	private boolean chkDownPassed(int xPoint, int yPoint) {
		return this.mazeData.getCoordinatePassed(xPoint, yPoint + 1) == PASSED_POINT;
	}
	
	private boolean chkLeftPassed(int xPoint, int yPoint) {
		return this.mazeData.getCoordinatePassed(xPoint - 1, yPoint) == PASSED_POINT;
	}
	
	private boolean chkUpGoal(int xPoint, int yPoint) {
		return this.mazeData.getCoordinateNum(xPoint, yPoint - 1) == 69;
	}
	
	private boolean chkRightGoal(int xPoint, int yPoint) {
		return this.mazeData.getCoordinateNum(xPoint + 1, yPoint) == 69;
	}
	
	private boolean chkDownGoal(int xPoint, int yPoint) {
		return this.mazeData.getCoordinateNum(xPoint, yPoint + 1) == 69;
	}
	
	private boolean chkLeftGoal(int xPoint, int yPoint) {
		return this.mazeData.getCoordinateNum(xPoint - 1, yPoint) == 69;
	}
	
	private int[] chkUpRoot(int xPoint, int yPoint) {
		if (this.mazeData.getCoordinateNum(xPoint, yPoint - 1) == 32 ||
				this.mazeData.getCoordinateNum(xPoint, yPoint - 1) == 69) {
			int[] nexPoint = new int[2];
			nexPoint[X_INDEX] = xPoint;
			nexPoint[Y_INDEX] = yPoint - 1;
			return nexPoint;
		} else {
			return null;
		}
	}
	
	private int[] chkRightRoot(int xPoint, int yPoint) {
		if (this.mazeData.getCoordinateNum(xPoint + 1, yPoint) == 32 ||
				this.mazeData.getCoordinateNum(xPoint + 1, yPoint) == 69) {
			int[] nexPoint = new int[2];
			nexPoint[X_INDEX] = xPoint + 1;
			nexPoint[Y_INDEX] = yPoint;
		return nexPoint;
		} else {
			return null;
		}
	}
	
	private int[] chkDownRoot(int xPoint, int yPoint) {
		if (this.mazeData.getCoordinateNum(xPoint, yPoint + 1) == 32 ||
				this.mazeData.getCoordinateNum(xPoint, yPoint + 1) == 69) {
			int[] nexPoint = new int[2];
			nexPoint[X_INDEX] = xPoint;
			nexPoint[Y_INDEX] = yPoint + 1;
			return nexPoint;
		} else {
			return null;
		}
	}
	
	private int[] chkLeftRoot(int xPoint, int yPoint) {
		if (this.mazeData.getCoordinateNum(xPoint - 1, yPoint) == 32 ||
				this.mazeData.getCoordinateNum(xPoint - 1, yPoint) == 69) {
			int[] nexPoint = new int[2];
			nexPoint[X_INDEX] = xPoint - 1;
			nexPoint[Y_INDEX] = yPoint;
			return nexPoint;
		} else {
			return null;
		}
	}
	
	/**
	 * 探索の様子を表示する
	 */
	public void display() {
		for(int i = 0; i < this.mazeData.getArray().size(); i++) {
			for(int j = 0; j < this.mazeData.getArray().get(i).size(); j++) {
				char ch = ' ';
				if(this.mazeData.getArray().get(i).get(j).get(COORDINATE_NUM_INDEX) == 32 &&
						this.mazeData.getArray().get(i).get(j).get(PASS_CHK_INDEX) == PASSED_POINT) {
					ch = '○';
				} else if(this.mazeData.getArray().get(i).get(j).get(COORDINATE_NUM_INDEX) == 32) {
					ch = ' ';
				} else if(this.mazeData.getArray().get(i).get(j).get(COORDINATE_NUM_INDEX) == 69) {
					ch = 'E';
				} else if(this.mazeData.getArray().get(i).get(j).get(COORDINATE_NUM_INDEX) == 83) {
					ch = 'S';
				}
				else if(this.mazeData.getArray().get(i).get(j).get(COORDINATE_NUM_INDEX) == 88) {
					ch = 'X';
				}
				System.out.print(ch);
			}
			System.out.println();
		}
	
	}

}
