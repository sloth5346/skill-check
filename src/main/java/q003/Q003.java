package q003;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Q003 集計と並べ替え
 *
 * 以下のデータファイルを読み込んで、出現する単語ごとに数をカウントし、アルファベット辞書順に並び変えて出力してください。
 * resources/q003/data.txt
 * 単語の条件は以下となります
 * - "I"以外は全て小文字で扱う（"My"と"my"は同じく"my"として扱う）
 * - 単数形と複数形のように少しでも文字列が異れば別単語として扱う（"dream"と"dreams"は別単語）
 * - アポストロフィーやハイフン付の単語は1単語として扱う（"isn't"や"dead-end"）
 *
 * 出力形式:単語=数
 *
[出力イメージ]
（省略）
highest=1
I=3
if=2
ignorance=1
（省略）
 * 参考
 * http://eikaiwa.dmm.com/blog/4690/
 */
public class Q003 {
	/**
	 * データファイルを開く resources/q003/data.txt
	 */
	private static InputStream openDataFile() {
		return Q003.class.getResourceAsStream("data.txt");
	}
	
	public static void main(String[] args) {
		// data.txtから英文を文字列で取得
		String str = getStrLine(openDataFile());
		// "."と","を削除し、単語ごとに配列に格納
		List<String> list = toArrayList(str);
		// "I"以外を小文字に変換
		list = toLowCase(list);
		// 大文字、小文字関係なくアルファベット順にソート
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		// 単語=数の形式のlist取得
		list = getCountList(list);

		// 表示		
		for(String tmp : list) {
			System.out.println(tmp);
		}
	}

	/**
	 * data.txtを一列の文字列として取得
	 */
	private static String getStrLine(InputStream is) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuffer buf = new StringBuffer();
			String str;
			while ((str = reader.readLine()) != null ) {
				buf.append(str).append(" ");
			}
			// "."と","を削除 小文字に変換
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * "I"以外の単語内の大文字を小文字に変換
	 */
	private static List<String> toLowCase(List<String> list) {
		return list.stream()
			.filter(str -> !str.equals("I"))
			.map(str -> str.toLowerCase())
			.collect(Collectors.toList());
	}
	
	/**
	 * 取得した英文内の単語を配列に格納
	 */
	private static List<String> toArrayList(String str) {
		// 英単語の後の".",","や文中の"-"を削除し。単語ごとに配列に格納
		// 例(前半)：「slowly,」 → ("slowl""y")(",")に分け、$1で単語部分("slowly")だけ抽出
		// 例(後半)：「-」(記号のみ)を削除
		return Arrays.asList(str.replaceAll("([^\\s]*\\w)([^\\s\\w]*)", "$1").replaceAll("[^\\s\\w]+( )", "").split(" "));
	}
	
	/**
	 * 単語数追加したListを取得
	 */
	private static List<String> getCountList(List<String> list){
	
		for(int i = 0; i < list.size(); i++) {
			int wordCount = 1;
			while(i + 1 < list.size() && 
					list.get(i).equals(list.get(i + 1))) {
				list.remove(i + 1);
				wordCount++;
			}
			list.set(i, list.get(i) + "=" + wordCount);
			if(i == list.size()) {
				break;
			}
		}
		return list;
	}
	
}

// 完成までの時間: 2時間 00分