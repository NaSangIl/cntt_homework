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
@RequestMapping({"/coin"})
public class CoinExchangeController
{
  @RequestMapping({"/view.do"})
  public String view()
  {
    return "/WEB-INF/jsp/homework/homework2.jsp";
  }
  
  @RequestMapping({"/calculator.do"})
  @ResponseBody
  public Result calculator(@RequestBody Map<String, Object> param)
  {
    Result result = new Result();
    
    int intMoney = 0;
    if (!ObjectUtils.isEmpty(param.get("moneyAmt"))) {
      try
      {
        intMoney = (int)Double.parseDouble(param.get("moneyAmt").toString());
      }
      catch (NumberFormatException ex1)
      {
        intMoney = 0;
        result.setFail("금액의 입력값 형식이 잘못되었습니다.");
        return result;
      }
    }
    ArrayList<String> listCoinType = new ArrayList<String>();
    if (!ObjectUtils.isEmpty(param.get("coinType"))) {
      if ((param.get("coinType") instanceof ArrayList)) {
        listCoinType = (ArrayList<String>)param.get("coinType");
      } else {
        listCoinType.add(param.get("coinType").toString());
      }
    }
    ArrayList<String> listCoinCnt = new ArrayList<String>();
    if (!ObjectUtils.isEmpty(param.get("coinCnt"))) {
      if ((param.get("coinCnt") instanceof ArrayList)) {
        listCoinCnt = (ArrayList<String>)param.get("coinCnt");
      } else {
        listCoinCnt.add(param.get("coinCnt").toString());
      }
    }
    int[] coinTp = convertIntegers(listCoinType);
    int[] coinCnt = convertIntegers(listCoinCnt);
    
    result.addObject("data", exchange(intMoney, coinTp, coinCnt));
    return result;
  }
  
  public int[] convertIntegers(ArrayList<String> arr)
  {
    int[] ret = new int[arr.size()];
    for (int i = 0; i < arr.size(); i++)
    {
      int temp = Integer.parseInt((String)arr.get(i));
      ret[i] = temp;
    }
    return ret;
  }
  
  private String exchange(int money, int[] coinType, int[] coinCount)
  {
    String strRs = "";
    
    int coinTpCnt = coinType.length;
    int[][] caseArray = new int[coinTpCnt + 1][money + 1];
    int[] coinTp = new int[coinType.length + 1];
    int[] coinCnt = new int[coinCount.length + 1];
    
    caseArray[0][0] = 1;
    for (int i = 1; i <= coinTpCnt; i++)
    {
      caseArray[i][0] = 1;
      
      coinTp[i] = coinType[(i - 1)];
      coinCnt[i] = coinCount[(i - 1)];
    }
    for (int i = 1; i <= coinTpCnt; i++) {
      for (int j = 1; j <= money; j++) {
        for (int k = 0; k <= coinCnt[i]; k++)
        {
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
}
