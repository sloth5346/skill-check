package q002;

/**
 * Q002 並べ替える
 *
 * dataListに "ID,名字" の形式で20個のデータがあります。
 * これをID順に並べて表示するプログラムを記述してください。
 *
 * dataListの定義を変更してはいけません。
 *
 *
[出力結果イメージ]
1,伊藤
2,井上
（省略）
9,清水
10,鈴木
11,高橋
（省略）
20,渡辺
 */
public class Q002 {
    /**
     * データ一覧
     */
    private static final String[] dataList = {
            "8,佐藤",
            "10,鈴木",
            "11,高橋",
            "12,田中",
            "20,渡辺",
            "1,伊藤",
            "18,山本",
            "13,中村",
            "5,小林",
            "3,加藤",
            "19,吉田",
            "17,山田",
            "7,佐々木",
            "16,山口",
            "6,斉藤",
            "15,松本",
            "2,井上",
            "4,木村",
            "14,林",
            "9,清水"
    };
    
    // main
    public static void main(String[] args) {
    	for(String str:sort(dataList)) {
    		System.out.println(str);
    	}
    }
    
    // 昇順にソートする関数(バブルソート使用)
    private static String[] sort(String[] defaultAry) {
    	
    	// 元の配列をコピー
    	String[] array = new String[defaultAry.length];
    	System.arraycopy(defaultAry, 0, array, 0, defaultAry.length);
    	
    	for(int i = 0; i < array.length - 1; i++) {
    		for(int j = 0; j < array.length - i - 1; j++) {
    			if(getId(array[j]) > getId(array[j + 1])) {
    				String str = array[j + 1];
    				array[j + 1] = array[j];
    				array[j] =str;
    			}
    		}
    	}
    	return array;
    }
    
    // IDを抜き出すメソッド
    private static int getId(String str) {
    	return Integer.parseInt(str.substring(0, str.indexOf(',')));
    }
}
// 完成までの時間: 0時間 40分