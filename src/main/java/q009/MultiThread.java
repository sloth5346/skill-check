package q009;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiThread extends Thread {

	// 入力数
	private final BigInteger inputNum;
	
	public MultiThread(BigInteger inputNum){
		this.inputNum = inputNum;
		// map初期化
		primeFactorManegeMap.put(this.inputNum, null);
	}
	
	// 計算結果を管理するMap("入力数", "素数のリスト")
	static Map<BigInteger,List<BigInteger>> primeFactorManegeMap = new HashMap<>();
	
	
	/**
	 * 素因数分解を実行し、結果をmapに詰める
	 */
	public void run() {
		
		// 平方根を求める
		BigInteger sqrtNum = sqrt(this.inputNum);
		// 素因数分解の結果をリストで受け取り、該当の入力文字の要素に詰める
		primeFactorManegeMap.put(this.inputNum, getPrimeFactorList(sqrtNum));
	}
	
	/**
	 * 平方根を求める
	 * @return
	 */
	private static BigInteger sqrt(BigInteger x) {
	    BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
	    BigInteger div2 = div;
	    for(;;) {
	        BigInteger y = div.add(x.divide(div)).shiftRight(1);
	        if (y.equals(div) || y.equals(div2)) {
	            return y;
	        }
	        div2 = div;
	        div = y;
	    }
	}
	
	/**
	 * 素因数分解を実行し、結果をリストに詰める
	 * @param sqrtNum
	 */
	private List<BigInteger> getPrimeFactorList(BigInteger sqrtNum) {
		List<BigInteger> list = new ArrayList<BigInteger>();
		BigInteger exponent = BigInteger.ZERO;
		BigInteger num = this.inputNum;
		
		// 2から平方根までの素因数を求める
		for (BigInteger i = new BigInteger("2"); i.compareTo(sqrtNum) <= 0; i = i.add(BigInteger.ONE)) {
			if ((num.remainder(i)).compareTo(BigInteger.ZERO) == 0) {
				// 指数カウンタクリア
				exponent = BigInteger.ZERO;
				do {
					// 指数カウンタプラス
					exponent = exponent.add(BigInteger.ONE);
					num = num.divide(i);
				} while ((num.remainder(i)).compareTo(BigInteger.ZERO) == 0);
	
				// 素因数iをリストに保存
				list.add(i);
			}
		}
		// 残った素数を追加
		if (num.compareTo(sqrtNum) > 0) {
			list.add(num);
		}
		return list;
	}
}
