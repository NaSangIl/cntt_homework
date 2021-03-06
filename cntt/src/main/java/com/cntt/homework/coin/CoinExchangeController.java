package com.cntt.homework.coin;

import com.cntt.homework.util.Result;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/coin" })
public class CoinExchangeController {
	
	/**
	 * 화면 조회
	 * @return
	 */
	@RequestMapping({ "/view.do" })
	public String view() {
		return "/WEB-INF/jsp/homework/homework2.jsp";
	}

	/**
	 * 동전계산 요청
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/calculator.do" })
	@ResponseBody
	public Result calculator(@RequestBody Map<String, Object> param) {
		Result result = new Result();

		int intMoney = 0;
		if (!ObjectUtils.isEmpty(param.get("moneyAmt"))) {
			try {
				intMoney = (int) Double.parseDouble(param.get("moneyAmt").toString());
			} catch (NumberFormatException ex1) {
				intMoney = 0;
				result.setFail("금액의 입력값 형식이 잘못되었습니다.");
				return result;
			}
		}
		ArrayList<String> listCoinType = new ArrayList<String>();
		if (!ObjectUtils.isEmpty(param.get("coinType"))) {
			if ((param.get("coinType") instanceof ArrayList)) {
				listCoinType = (ArrayList<String>) param.get("coinType");
			} else {
				listCoinType.add(param.get("coinType").toString());
			}
		}
		ArrayList<String> listCoinCnt = new ArrayList<String>();
		if (!ObjectUtils.isEmpty(param.get("coinCnt"))) {
			if ((param.get("coinCnt") instanceof ArrayList)) {
				listCoinCnt = (ArrayList<String>) param.get("coinCnt");
			} else {
				listCoinCnt.add(param.get("coinCnt").toString());
			}
		}
		int[] coinTp = convertIntegers(listCoinType);
		int[] coinCnt = convertIntegers(listCoinCnt);

		result.addObject("data", exchange(intMoney, coinTp, coinCnt));
		return result;
	}

	/**
	 * ArrayList<String> To int[]
	 * @param arr
	 * @return int[] ret
	 */
	public int[] convertIntegers(ArrayList<String> arr) {
		int[] ret = new int[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			int temp = Integer.parseInt((String) arr.get(i));
			ret[i] = temp;
		}
		return ret;
	}

	/**
	 * 동전교환 경우의 수 구하기
	 * @param money
	 * @param coinType
	 * @param coinCount
	 * @return 경우의 수
	 */
	private String exchange(int money, int[] coinType, int[] coinCount) {
		String strRs = "";

		int coinTpCnt = coinType.length;
		int[][] caseArray = new int[coinTpCnt + 1][money + 1];
		int[] coinTp = new int[coinType.length + 1];
		int[] coinCnt = new int[coinCount.length + 1];

		caseArray[0][0] = 1;
		for (int i = 1; i <= coinTpCnt; i++) {
			caseArray[i][0] = 1;

			coinTp[i] = coinType[(i - 1)];
			coinCnt[i] = coinCount[(i - 1)];
		}
		for (int i = 1; i <= coinTpCnt; i++) {
			for (int j = 1; j <= money; j++) {
				for (int k = 0; k <= coinCnt[i]; k++) {
					if (coinTp[i] * k > j) {
						break;
					}
					caseArray[i][j] += caseArray[(i - 1)][(j - coinTp[i] * k)];
				}
			}
		}
		strRs = "총 " + caseArray[coinTpCnt][money] + "가지";

		return strRs;
	}
	
	/**
	 * 동전 계산 출력용 테스트
	 */
//	public static void main(String[] args) {
//		
//		ArrayList<String> result = new ArrayList<String>();
//		int[] coinTp = new int[] {3, 2, 1};
//		int[] coinCnt = new int[] {2, 2, 2};
//		int money = 10;
////		int[] coinTp = new int[] {10, 5, 1};
////		int[] coinCnt = new int[] {2, 3, 5};
////		int money = 20;
//		
//		int tmpSum = 0;
//		String tmpStr = "";
//		boolean startFlag = true;
//		
//		int coinTpCnt = coinTp.length;
//
//		
//		System.out.println(countCombinations(money, coinTp , 0, 0 , new Integer[coinTp[0]+1][money+coinTp[0]]));
//		
//		//결과 출력
//		for(String rst:result) {
//			
//			//rstStrArr
//			System.out.println(rst);
//		}
//	}
//	
//	private static int countCombinations(Integer targetAmount, int[] coinTp, int currentAmount, int coin, Integer[][] memory){
//
//		//Comment below if block if you want to see the perf difference
//		
//	    if(memory[coin][currentAmount] != null){
//	        return memory[coin][currentAmount];
//	        
//	    }
//
//	    if(currentAmount > targetAmount){
//	        memory[coin][currentAmount] = 0;
//	        return 0;
//	    }
//	    
//	    if(currentAmount == targetAmount){
//	        return 1;
//	    }      
//	    
//	    int count = 0;
//	    for(int i=0; i< coinTp.length ; i++) {
//	    	int selectedCoin = coinTp[i];
//	    		
//    		if(selectedCoin >= coin){
//    			count += countCombinations(targetAmount, coinTp, currentAmount+selectedCoin, selectedCoin, memory);
//    			if(count > 0 ) {
//    			}
//    		}
//	    }        
//	    memory[coin][currentAmount] = count;        
//	    return count;
//	}
}
