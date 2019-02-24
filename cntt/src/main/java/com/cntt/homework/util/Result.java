package com.cntt.homework.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class Result
  implements Serializable
{
  private static final long serialVersionUID = 1761628266194274540L;
  private static final String STATUS_SUCCESS_CODE = "S";
  private static final String STATUS_FAIL_CODE = "F";
  private String status = "S";
  private String errorCode = "0";
  private String errorMessage = "";
  private Map<String, Object> data = new HashMap();
  
  public Map<String, Object> getData()
  {
    return this.data;
  }
  
  public void setData(Map<String, Object> data)
  {
    this.data = data;
  }
  
  public Result() {}
  
  public Result(Map data)
  {
    this.data = data;
  }
  
  public Result(String errorMessage)
  {
    this("-1", errorMessage);
  }
  
  public Result(String errorCode, String errorMessage)
  {
    this.status = "F";
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
  
  public String getErrorCode()
  {
    return this.errorCode;
  }
  
  public void setErrorCode(int errorCode)
  {
    this.errorCode = String.valueOf(errorCode);
  }
  
  public void setErrorCode(String errorCode)
  {
    this.errorCode = errorCode;
  }
  
  public String getErrorMessage()
  {
    return this.errorMessage;
  }
  
  public void setErrorMessage(String message)
  {
    this.errorMessage = message;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public void setFail(String message)
  {
    this.status = "F";
    this.errorMessage = message;
  }
  
  public void setFail(int errorCode, String message)
  {
    setFail(String.valueOf(errorCode), message);
  }
  
  public void setFail(String errorCode, String message)
  {
    this.status = "F";
    this.errorCode = errorCode;
    this.errorMessage = message;
  }
  
  public void setSuccess(String message)
  {
    this.status = "S";
    this.errorMessage = message;
  }
  
  public void setSuccess(int errorCode, String message)
  {
    setSuccess(String.valueOf(errorCode), message);
  }
  
  public void setSuccess(String errorCode, String message)
  {
    this.status = "S";
    this.errorCode = errorCode;
    this.errorMessage = message;
  }
  
  public void addObject(String key, Object obj)
  {
    this.data.put(key, obj);
  }
  
  public Object getObject(String key)
  {
    return this.data.get(key);
  }
  
  public void addDataList(String id, List<Map<String, Object>> dataList)
  {
    if (StringUtils.isEmpty(id)) {
      throw new RuntimeException("DataList id is not defined.");
    }
    this.data.put(id, dataList);
  }
}
