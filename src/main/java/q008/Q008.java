package q008;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Q008 埋め込み文字列の抽出
 *
 * 一般に、定数定義の場合を除いて、プログラム中に埋め込み文字列を記述するのは良くないとされています。
 * そのような埋め込み文字列を見つけるために、埋め込み文字列や埋め込み文字（"test"や'a'など）が
 * 記述された行を出力するプログラムを作成してください。
 *
 * - 実行ディレクトリ配下（再帰的にチェック）に存在するすべてのjavaファイルをチェックする
 * - ファイル名はディレクトリ付きでも無しでも良い
 * - 行の内容を出力する際は、先頭のインデントは削除しても残しても良い

[出力イメージ]
Q001.java(12): return "test";  // テストデータ
Q002.java(10): private static final DATA = "test"
Q002.java(11): + "aaa";
Q002.java(20): if (test == 'x') {
Q004.java(13): Pattern pattern = Pattern.compile("(\".*\")|(\'.*\')");

 */
public class Q008 {

	// ファイルの終点
	private static final int EOF = -1;

	private static List<Character> codeLine = new ArrayList<>();

	private static boolean comPointFlg = false;

	public static void main(String[] args) {
		ArrayList<File> fileList = listUpFiles();
		for (File file : fileList) {
			try {
				FileReader filereader = new FileReader(file);

				String fileName = file.getName();
				int lineCnt = 1;
				int ch;

				// 1ファイルずつ埋め込み文字を検索する
				Loop: while (true) {
					// 各行1文字ずつListに詰める
					while (true) {
						ch = filereader.read();
						if (chkFileEnd(ch)) {
							break Loop;
						}
						// 改行箇所までリストに1文字ずつ詰める
						if (!chkLineStart(ch)) {
							makeCodeLine(ch);
						} else {
							break;
						}
					}
					// 埋め込み文字列があった場合はそのラインを表示
					if (chkComma()) {
						disEmbCharLine(fileName, lineCnt);
					}
					// 次のライン読み込みにうつる
					lineCnt++;
					clearCodeLine();
				}
				filereader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * JavaファイルのStreamを作成する
	 *
	 * @return
	 * @throws IOException
	 */
	private static Stream<File> listJavaFiles() throws IOException {
		return Files.walk(Paths.get(".")).map(Path::toFile).filter(f -> f.getName().endsWith(".java"));
	}

	private static ArrayList<File> listUpFiles() {
		final ArrayList<File> fileList = new ArrayList<>();
		try {
			listJavaFiles().forEach(fileList::add);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return fileList;
	}

	/**
	 * 読み取った文字をリストに詰める
	 * 
	 * @param ch
	 */
	private static void makeCodeLine(int ch) {
		codeLine.add((char) ch);
	}

	/**
	 * リスト内の全要素を削除する
	 */
	private static void clearCodeLine() {
		codeLine.clear();
	}

	/**
	 * 各行の開始をチェック
	 * 
	 * @param ch
	 * @return
	 */
	private static boolean chkLineStart(int ch) {
		return (char) ch == '\n';
	}

	/**
	 * EOFチェック
	 * 
	 * @param ch
	 * @return
	 */
	private static boolean chkFileEnd(int ch) {
		return ch == EOF;
	}

	/**
	 * 取得したコードライン内に「"」あるいは「'」が一つでも含まれていればtrue
	 * 
	 * @return
	 */
	private static boolean chkComma() {
		boolean result = false;
		for (int i = 0; i < codeLine.size() - 1; i++) {
			chkComStart(codeLine.get(i), codeLine.get(i + 1));
			chkComEnd(codeLine.get(i), codeLine.get(i + 1));
			// コメント箇所でないときチェック
			if (!comPointFlg) {
				if (codeLine.get(i) == '"' || codeLine.get(i) == '\'') {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * コメントアウト開始箇所をチェックし、コメントアウト開始とする
	 * 
	 * @param curCh
	 * @param nexCh
	 */
	private static void chkComStart(char curCh, char nexCh) {
		if (comPointFlg == false) {
			if ((curCh == '/' && nexCh == '/') || (curCh == '/' && nexCh == '*')) {
				comPointFlg = true;
			}
		}
	}

	/**
	 * コメントアウト終了箇所をチェックし、コメントアウト終了とする
	 * 
	 * @param curCh
	 * @param nexCh
	 */
	private static void chkComEnd(char curCh, char nexCh) {
		if (comPointFlg == true) {
			if ((curCh == '*' && nexCh == '/')) {
				comPointFlg = false;
			}
		}
	}

	/**
	 * 埋め込み文字があったラインを表示する
	 * @param fileName
	 * @param codeLineCnt
	 */
	private static void disEmbCharLine(String fileName, int codeLineCnt) {
		StringBuilder sb = new StringBuilder();
		sb.append(fileName).append("(").append(codeLineCnt).append("): ");
		StringBuilder sb_tmp = new StringBuilder();
		for (char ch : codeLine) {
			sb_tmp.append(ch);
		}
		sb.append(sb_tmp.toString().trim());
		System.out.println(sb);
	}
}
// 完成までの時間: 5時間 00分
