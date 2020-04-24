package q007;

/**
 * q007 最短経路探索
 *
 * 壁を 'X' 通路を ' ' 開始を 'S' ゴールを 'E' で表現された迷路で、最短経路を通った場合に
 * 何歩でゴールまでたどり着くかを出力するプログラムを実装してください。
 * もし、ゴールまで辿り着くルートが無かった場合は -1 を出力してください。
 * なお、1歩は上下左右のいずれかにしか動くことはできません（斜めはNG）。
 *
 * 迷路データは MazeInputStream から取得してください。
 * 迷路の横幅と高さは毎回異なりますが、必ず長方形（あるいは正方形）となっており、外壁は全て'X'で埋まっています。
 * 空行が迷路データの終了です。
 *

[迷路の例]
XXXXXXXXX
XSX    EX
X XXX X X
X   X X X
X X XXX X
X X     X
XXXXXXXXX

[答え]
14
 */
public class Q007 {
	public static void main(String[] args) {
		MazeData mazeData = new MazeData(new MazeInputStream());
		
		Searcher searcher = new Searcher(mazeData.getSXPoint(), mazeData.getSYPoint());
		
		// スタート地点でtrue
		boolean initFlg = true;
		
		while(true) {
			// 壁がないルート探索
			final int direction = searcher.searchRoot(mazeData);
			if(searcher.getXpos() != mazeData.getSXPoint() || 
					searcher.getYpos() != mazeData.getSYPoint()) {
				mazeData.setPassedPoint(searcher.getXpos(), searcher.getYpos());
			}
			// 「上」「右」「下」「左」の順に壁がないルートに進む(ない場合は一つ前の位置に戻る)
			if(direction == Searcher.CANNOT_MOVE) {
				// 戻る
				searcher.returnHisPot();
				
				// 戻った位置を取得
				int sXPoint = mazeData.getSXPoint();
				int sYPoint = mazeData.getSYPoint();
				
				// スタート位置に戻ってしまった際にルートを変更
				if(searcher.getXpos() == sXPoint && 
						searcher.getYpos() == sYPoint) {
					// それまでのルートを閉じる(スタートの隣の位置をXにし、それ以外は元の迷路の状態に戻す)
					mazeData.closeRoot(searcher.getsXNextPoint(), searcher.getsYNextPoint());
					initFlg = true;
					
					// スタート位置からすべてのルートが埋まってしまった場合ゲーム終了
					if(mazeData.getMazePointNum(sXPoint, sYPoint - 1) == 88 &&
							mazeData.getMazePointNum(sXPoint + 1, sYPoint) == 88 &&
							mazeData.getMazePointNum(sXPoint, sYPoint + 1) == 88 &&
							mazeData.getMazePointNum(sXPoint - 1, sYPoint) == 88) {
						searcher.resetMoveCount();
						break;
					}
				}
			} else {
				// 経路情報、移動回数を移動する前に保存
				searcher.saveCurData();
				
				// 移動処理
				if(direction == Searcher.UP_DIRECTION) {
					searcher.moveUp();
				} else if (direction == Searcher.RIGHT_DIRECTION) {
					searcher.moveRight();
				} else if (direction == Searcher.DOWN_DIRECTION) {
					searcher.moveDown();
				} else if (direction == Searcher.LEFT_DIRECTION) {
					searcher.moveLeft();
				}
				
				// スタートから初回移動ポイントを記録しておく
				if(initFlg) {
					searcher.setStartNextPoint();
					initFlg = false;
				}
				
				// 移動した結果ゴール地点についた場合
				if(searcher.getXpos() == mazeData.getEXPoint() && 
						searcher.getYpos() == mazeData.getEYPoint()) {
					break;
				}
			}
			//mazeData.display();
			//try {
	        //    Thread.sleep(5000);
	        //} catch(InterruptedException e){
	        //    e.printStackTrace();
	        //} 
		}
		System.out.println(searcher.getMoveCount());
		
	}

}
// 完成までの時間: 6時間 00分