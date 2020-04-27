package q007_2;

import java.util.ArrayList;

public class Maze {
	
	// 座標用の配列のインデックス指定用
	private static final int X_INDEX = 0;
	private static final int Y_INDEX = 1;
	
	private int[] sPoint = new int[2];
	
	private int[] ePoint = new int[2];

	// 迷路
	private ArrayList<ArrayList<Integer>> mazeData;

	/**
	 * コンストラクタ
	 * ・迷路作成
	 * ・スタートとゴールの座標を探す
	 * @param mazeInputStream
	 */
	public Maze(MazeInputStream mazeInputStream) {
		this.mazeData = makeMazeData(mazeInputStream);
		setSPoint();
		setEPoint();	
	}
	
	public int[] getSPoint() {
		return sPoint;
	}

	private void setSPoint() {
		Loop:
		for(int yPoint = 0; yPoint < mazeData.size() - 1; yPoint++) {
			for(int xPoint = 0; xPoint < mazeData.get(yPoint).size(); xPoint++) {
				if(mazeData.get(yPoint).get(xPoint) == 83) {
					this.sPoint[X_INDEX] = xPoint;
					this.sPoint[Y_INDEX] = yPoint;
					break Loop;
				}
			}
		}
	}

	public int[] getEPoint() {
		return ePoint;
	}

	public void setEPoint() {
		Loop:
		for(int yPoint = 0; yPoint < mazeData.size() - 1; yPoint++) {
			for(int xPoint = 0; xPoint < mazeData.get(yPoint).size(); xPoint++) {
				if(mazeData.get(yPoint).get(xPoint) == 69) {
					this.ePoint[X_INDEX] = xPoint;
					this.ePoint[Y_INDEX] = yPoint;
					break Loop;
				}
			}
		}
	}	
	
	/**
	 * コンストラクタ内で迷路をListで作成
	 * @param mazeInputStream
	 * @return
	 */
	private ArrayList<ArrayList<Integer>> makeMazeData(MazeInputStream mazeInputStream) {

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
	
	public ArrayList<ArrayList<Integer>> getMazeData(){
		return this.mazeData;
	}

}
