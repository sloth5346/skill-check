package q007_2;

import java.util.ArrayList;

public class MazeData {
	
	// 座標用の配列のインデックス指定用
	private static final int X_INDEX = 0;
	private static final int Y_INDEX = 1;
	
	// 迷路データ(ArrayList)のインデックス
	private static final int COORDINATE_NUM_INDEX = 0;
	private static final int PASS_CHK_INDEX = 1;
	private static final int PRE_X_INDEX = 2;
	private static final int PRE_Y_INDEX = 3;
	
	// 迷路データ
	private static final int DEFAULT_PRE_POINT = -1;
	private static final int PASSED_POINT = 1;
	private static final int NO_PASSED_POINT = -1;
	
	// 迷路
	private final Maze oriMaze;
	
	private int[] sPoint = new int[2];
	
	private int[] ePoint = new int[2];
	
	public int[] getSPoint() {
		return sPoint;
	}
	
	public int[] getEPoint() {
		return ePoint;
	}
	
	// 探索時に使用するデータ
	private ArrayList<ArrayList<ArrayList<Integer>>> mazeData;
	
	public ArrayList<ArrayList<ArrayList<Integer>>> getArray(){
		return this.mazeData;
	}
	
	/**
	 *  コンストラクタ
	 * @param mazeInputStream
	 */
	public MazeData(MazeInputStream mazeInputStream) {
		this.oriMaze = new Maze(mazeInputStream);
		this.mazeData = makeMazeData(this.oriMaze.getMazeData());
		this.sPoint = this.oriMaze.getSPoint();
		this.ePoint = this.oriMaze.getEPoint();
	}
	
	/**
	 * 移動後の座標に移動前の座標を記録
	 * @param prePoint
	 * @param curPoint
	 */
	public void setRouteData(final int[] curPoint, final int[] nexPoint) {
		this.mazeData.get(nexPoint[Y_INDEX]).get(nexPoint[X_INDEX]).set(PRE_X_INDEX, curPoint[X_INDEX]);
		this.mazeData.get(nexPoint[Y_INDEX]).get(nexPoint[X_INDEX]).set(PRE_Y_INDEX, curPoint[Y_INDEX]);
	}
	
	/**
	 * 通過済みに設定
	 * @param curPoint
	 * @param nexPoint
	 */
	public void setPassed(int xCurPoint, int yCurPoint) {
		this.mazeData.get(yCurPoint).get(xCurPoint).set(PASS_CHK_INDEX, PASSED_POINT);
	}
	
	public int getCoordinateNum(int xPoint, int yPoint) {
		return this.mazeData.get(yPoint).get(xPoint).get(COORDINATE_NUM_INDEX);
	}
	
	public int getCoordinatePassed(int xPoint, int yPoint) {
		return this.mazeData.get(yPoint).get(xPoint).get(PASS_CHK_INDEX);
	}
	
	public int getPreXPoint(int xPoint, int yPoint) {
		return this.mazeData.get(yPoint).get(xPoint).get(PRE_X_INDEX);
	}
	
	public int getPreYPoint(int xPoint, int yPoint) {
		return this.mazeData.get(yPoint).get(xPoint).get(PRE_Y_INDEX);
	}
	
	/**
	 * 元の迷路データに「通過済みチェック用定数」、「前の場所のx座標」、「前の場所のy座標」を追加したListを作成
	 * @param originMazeData
	 * @return
	 */
	private ArrayList<ArrayList<ArrayList<Integer>>> makeMazeData(ArrayList<ArrayList<Integer>> originMazeData) {
		ArrayList<ArrayList<ArrayList<Integer>>> mazeData = new ArrayList<ArrayList<ArrayList<Integer>>>();

		for(ArrayList<Integer> oriMazeDataLine : originMazeData) {
			ArrayList<ArrayList<Integer>> mazeDataLine = new ArrayList<ArrayList<Integer>>();
			for(Integer oriData : oriMazeDataLine) {
				ArrayList<Integer> array = new ArrayList<Integer>();
				array.add(oriData);
				array.add(NO_PASSED_POINT);
				array.add(DEFAULT_PRE_POINT);
				array.add(DEFAULT_PRE_POINT);
				
				mazeDataLine.add(array);
			}
			mazeData.add(mazeDataLine);
		}
		return mazeData;
	}
	
}
