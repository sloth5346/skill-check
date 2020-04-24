package q007;

import java.util.ArrayList;

public class MazeData {
	
	// Sのx座標
	private int sXPoint;
	
	// Sのy座標
	private int sYPoint;
	
	// Eのx座標
	private int eXPoint;
	
	// Eのy座標
	private int eYPoint;
	
	// 迷路
	private ArrayList<ArrayList<Integer>> maze;
	
	// 迷路初期
	private ArrayList<ArrayList<Integer>> initMaze;
	
	/**
	 * コンストラクタ
	 * ・迷路作成
	 * ・スタートとゴールの座標を探す
	 * @param mazeInputStream
	 */
	public MazeData(MazeInputStream mazeInputStream) {
		this.maze = makeMaze(mazeInputStream);
		this.initMaze = copyMaze(maze);
		setSXPoint();
		setSYPoint();
		setEXPoint();
		setEYPoint();	
	}
	
	/**
	 * 指定の座標の値を取得
	 * @param xPoint
	 * @param yPoint
	 * @return
	 */
	public int getMazePointNum(int xPoint, int yPoint) {
		return maze.get(yPoint).get(xPoint);
	}
	
	/**
	 * 通過した座標は壁と同値の値を設定
	 * @param xPoint
	 * @param yPoint
	 */
	public void setPassedPoint(int xPoint, int yPoint) {
		maze.get(yPoint).set(xPoint, 88);
	}
	
	/**
	 * コンストラクタ内で迷路をListで作成
	 * @param mazeInputStream
	 * @return
	 */
	private ArrayList<ArrayList<Integer>> makeMaze(MazeInputStream mazeInputStream) {
		
		ArrayList<ArrayList<Integer>> arrays = new ArrayList<ArrayList<Integer>>();
		Loop:
		while(true) {
			ArrayList<Integer> array = new ArrayList<Integer>();
			while(true) {
				int num = mazeInputStream.read();
				
				// 迷路終了チェック
				if(checkFinish(num))
				{
					break Loop;
				}
				if(checkReadNum(num)) {
					// System.out.println(num);
					array.add(Integer.valueOf(num));
				}
				if(checkLineEnd(num)) {
					break;
				}
			}
			arrays.add(array);
        }
		return arrays;
	}
		
	/**
	 * 迷路の読み取り終了チェック
	 * @param num
	 * @return
	 */
	private boolean checkFinish(int num) {
		return num == -1;
	}
	
	/**
	 * 各行の読み取り終了チェック(LFを読み取る)
	 * @param num
	 * @return
	 */
	private boolean checkLineEnd(int num) {
		return num == 10;
	}
	
	/**
	 * 各行の読み取りチェック(CR,LF以外を読み取る)
	 * @param num
	 * @return
	 */
	private boolean checkReadNum(int num) {
		return num != 13 && num != 10;
	}

	public int getSXPoint() {
		return sXPoint;
	}

	private void setSXPoint() {
		for(int yPoint = 0; yPoint < maze.size() - 1; yPoint++) {
			for(int xPoint = 0; xPoint < maze.get(yPoint).size(); xPoint++) {
				if(maze.get(yPoint).get(xPoint) == 83) {
					this.sXPoint = xPoint;
				}
			}
		}
	}

	public int getSYPoint() {
		return sYPoint;
	}

	private void setSYPoint() {
		for(int yPoint = 0; yPoint < maze.size() - 1; yPoint++) {
			for(int xPoint = 0; xPoint < maze.get(yPoint).size(); xPoint++) {
				if(maze.get(yPoint).get(xPoint) == 83) {
					this.sYPoint = yPoint;
				}
			}
		}
	}

	public int getEXPoint() {
		return eXPoint;
	}

	public void setEXPoint() {
		for(int yPoint = 0; yPoint < maze.size() - 1; yPoint++) {
			for(int xPoint = 0; xPoint < maze.get(yPoint).size(); xPoint++) {
				if(maze.get(yPoint).get(xPoint) == 69) {
					this.eXPoint = xPoint;
				}
			}
		}
	}

	public int getEYPoint() {
		return eYPoint;
	}

	private void setEYPoint() {
		for(int yPoint = 0; yPoint < maze.size() - 1; yPoint++) {
			for(int xPoint = 0; xPoint < maze.get(yPoint).size(); xPoint++) {
				if(maze.get(yPoint).get(xPoint) == 69) {
					this.eYPoint = yPoint;
				}
			}
		}
	}
	
	public void display() {
		for(int i = 0; i < maze.size(); i++) {
			for(int j = 0; j < maze.get(i).size(); j++) {
				char ch = ' ';
				if(maze.get(i).get(j) == 88) {
					ch = 'X';
				} else if(maze.get(i).get(j) == 32) {
					ch = ' ';
				} else if(maze.get(i).get(j) == 69) {
					ch = 'E';
				} else if(maze.get(i).get(j) == 83) {
					ch = 'S';
				}
				System.out.print(ch);
			}
			System.out.println();
		}
	}
	
	public void closeRoot(int closeXPoint, int closeYPoint) {
		this.maze = copyMaze(initMaze);
		maze.get(closeYPoint).set(closeXPoint, 88);
	}
	
	private ArrayList<ArrayList<Integer>> copyMaze(ArrayList<ArrayList<Integer>> originMaze) {
		ArrayList<ArrayList<Integer>> arrays = new ArrayList<ArrayList<Integer>>();
		
		for(ArrayList<Integer> oriAry : originMaze) {
			ArrayList<Integer> array = new ArrayList<Integer>();
			for(Integer oriData : oriAry) {
				array.add(oriData);
			}
			arrays.add(array);
		}
		return arrays;
	}

}
