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
	 * ȭ�� ��ȸ
	 * @return
	 */
	@RequestMapping({ "/view.do" })
	public String view() {
		return "/WEB-INF/jsp/homework/homework2.jsp";
	}

	/**
	 * ������� ��û
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
				result.setFail("�ݾ��� �Է°� ������ �߸��Ǿ����ϴ�.");
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
	 * ������ȯ ����� �� ���ϱ�
	 * @param money
	 * @param coinType
	 * @param coinCount
	 * @return ����� ��
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
		strRs = "�� " + caseArray[coinTpCnt][money] + "����";

		return strRs;
	}
	
	/**
	 * ���� ��� ��¿� �׽�Ʈ
	 */
//	public static void main(String[] args) {
//		
//		ArrayList<String> result = new ArrayList<String>();
////		int[] coinTp = new int[] {1, 2, 3};
////		int[] coinCnt = new int[] {2, 2, 2};
////		int money = 10;
//		int[] coinTp = new int[] {10, 5, 1};
//		int[] coinCnt = new int[] {2, 3, 5};
//		int money = 20;
//		
//		int tmpSum = 0;
//		String tmpStr = "";
//		boolean startFlag = true;
//		//���� Ÿ�Ը�ŭ
//		for(int i=0; i<coinTp.length; i++) {
//			
//			tmpSum = 0;
//			tmpStr = "";
//			startFlag = true;
//			//���� Ÿ�� ������ üũ
//			for(int j=0; j<coinTp.length; j++) {
//
//				//���� ���� ��ŭ
//				for(int k=0; k<coinCnt[j]; k++) {
//
//					if(startFlag) {
//						tmpSum += coinTp[i] + coinTp[j];
//					}else {
//						tmpSum += coinTp[j];
//					}
//					
//					if(tmpSum < money) {
//						if(startFlag) {
//							if("".equals(tmpStr)) {
//								tmpStr += "" + coinTp[i] + "+" + coinTp[j];
//							}else {
//								tmpStr += "+" + coinTp[i] + "+" + coinTp[j];
//							}
//							
//						}else {
//							if("".equals(tmpStr)) {
//								tmpStr += "" + coinTp[j];
//							}else {
//								tmpStr += "+" + coinTp[j];
//							}
//						}
//						startFlag = false;
//					}else if(tmpSum == money){
//						if(startFlag) {
//							if("".equals(tmpStr)) {
//								tmpStr += "" + coinTp[i] + "+" + coinTp[j];
//							}else {
//								tmpStr += "+" + coinTp[i] + "+" + coinTp[j];
//							}
//						}else {
//							if("".equals(tmpStr)) {
//								tmpStr += "" + coinTp[j];
//							}else {
//								tmpStr += "+" + coinTp[j];
//							}
//						}
//						result.add(tmpStr);
//						startFlag = true;
//						k=0;
//						tmpSum = 0;
//						tmpStr = "";
////						k = coinCnt[j]-1;
//					}else {
//						startFlag = false;
//						tmpSum -= coinTp[j];
//						//k = coinCnt[j]-1;
//					}
//				}
//			}
//		}
//		
//		//��� ���
//		for(String rst:result) {
//			
//			//rstStrArr
//			System.out.println(rst);
//		}
//	}
}
