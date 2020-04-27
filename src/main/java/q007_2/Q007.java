package q007_2;

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
		Searcher searcher = new Searcher(new MazeInputStream());
		while(true) {
			searcher.setPassed();
			searcher.searchNextPoint();
			// 進める場所がなくなったときにループから抜ける
			if(searcher.chkSearchFin()) {
				break;
			}
			searcher.move();
			// ゴールに到達したらループをぬける
			if(searcher.chkReachGoal()) {
				break;
			}
		}
		System.out.println(searcher.countMove());
	}
}
// 完成までの時間: 9時間 00分