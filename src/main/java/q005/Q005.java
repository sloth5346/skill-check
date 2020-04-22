package q005;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Q005 データクラスと様々な集計
 *
 * 以下のファイルを読み込んで、WorkDataクラスのインスタンスを作成してください。
 * resources/q005/data.txt
 * (先頭行はタイトルなので読み取りをスキップする)
 *
 * 読み込んだデータを以下で集計して出力してください。
 * (1) 役職別の合計作業時間
 * (2) Pコード別の合計作業時間
 * (3) 社員番号別の合計作業時間
 * 上記項目内での出力順は問いません。
 *
 * 作業時間は "xx時間xx分" の形式にしてください。
 * また、WorkDataクラスは自由に修正してください。
 *
[出力イメージ]
部長: xx時間xx分
課長: xx時間xx分
一般: xx時間xx分
Z-7-31100: xx時間xx分
I-7-31100: xx時間xx分
T-7-30002: xx時間xx分
（省略）
194033: xx時間xx分
195052: xx時間xx分
195066: xx時間xx分
（省略）
 */
public class Q005 {
	
	public static void main(String[] args) {
		// data.txtから英文を文字列で取得
		List<WorkData> list = new ArrayList<WorkData>();
		list = getDataList(openDataFile());
		
		// カテゴリ別の作業合計時間を取得
		Map<String, Long> sumTimePerPos = getSumTimeMapPerPos(list);
		Map<String, Long> sumTimePerPCode = getSumTimeMapPerPCode(list);
		Map<String, Long> sumTimePerNum = getSumTimeMapPerNum(list);
		
		// 集計結果表示
		disTimePerPos(sumTimePerPos);
		disTimePerPCode(sumTimePerPCode);
		disTimePerNum(sumTimePerNum);
		
	}
	
	/**
	 * データファイルを開く resources/q005/data.txt
	 */
	private static InputStream openDataFile() {
		return Q005.class.getResourceAsStream("data.txt");
	}
	
	/**
	 * data.txtを一列の文字列として取得
	 */
	private static List<WorkData> getDataList(InputStream is) {
		
		List<WorkData> list = new ArrayList<WorkData>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String str;
			while ((str = reader.readLine()) != null) {
				if(!str.contains("社員番号")) {

					String[] array = new String[5];
					array = str.split(",");
					
					// workDataインスタンスにデータを設定
					WorkData workData = new WorkData();
					workData.setNumber(array[0]);
					workData.setDepartment(array[1]);
					workData.setPosition(array[2]);
					workData.setPCode(array[3]);
					workData.setWorkTime(Integer.parseInt(array[4]));
					
					// ListにworkDataインスタンスを追加
					list.add(workData);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			return list;
	}
	
	/**
	 * 役職別の作業時間の合計をマップにして取得
	 */
	private static Map<String, Long> getSumTimeMapPerPos(List<WorkData> list){
		return list.stream().collect(
                Collectors.groupingBy(WorkData::getPosition, 
                        Collectors.summingLong(WorkData::getWorkTime)));
	}
	
	/**
	 * P-CODE別の作業時間の合計をマップにして取得
	 */
	private static Map<String, Long> getSumTimeMapPerPCode(List<WorkData> list){
		return list.stream().collect(
                Collectors.groupingBy(WorkData::getPCode, 
                        Collectors.summingLong(WorkData::getWorkTime)));
	}
	
	/**
	 * 社員番号別の作業時間の合計をマップにして取得
	 */
	private static Map<String, Long> getSumTimeMapPerNum(List<WorkData> list){
		return list.stream().collect(
                Collectors.groupingBy(WorkData::getNumber, 
                        Collectors.summingLong(WorkData::getWorkTime)));
	}
	
	/**
	 * 役職別の作業時間合計を表示
	 */
	private static void disTimePerPos(Map<String, Long> sumTimePerPos) {
		System.out.println("-----役職別-----");
		for(Map.Entry<String, Long> entry : sumTimePerPos.entrySet()) {
			System.out.println(entry.getKey() +  ": " + conTime(entry.getValue()));
		}
		System.out.println("");
	}
	
	/**
	 * P-CODE別の作業時間合計を表示
	 */
	private static void disTimePerPCode(Map<String, Long> sumTimePerPCode) {
		System.out.println("-----P-CODE別-----");
		for(Map.Entry<String, Long> entry : sumTimePerPCode.entrySet()) {
			System.out.println(entry.getKey() +  ": " + conTime(entry.getValue()));
		}
		System.out.println("");
	}
	
	/**
	 * 社員番号別の作業時間合計を表示
	 */
	private static void disTimePerNum(Map<String, Long> sumTimePerNum) {
		System.out.println("-----社員番号別-----");
		for(Map.Entry<String, Long> entry : sumTimePerNum.entrySet()) {
			System.out.println(entry.getKey() +  ": " + conTime(entry.getValue()));
		}
		System.out.println("");
	}
	
	private static String conTime(Long time) {
		int hour = (int)(time / 60);
		int minutes = (int)(time % 60);
		
		return String.valueOf(hour) + "時間" + String.valueOf(minutes) + "分";
	}
	
}
// 完成までの時間: 2時間 15分