<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/include/pageCss.jsp" %>
<%@ include file="/WEB-INF/jsp/include/pageJs.jsp" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<title>Homework #2</title>
	</head>
	
	<script>
		$(document).ready(function() {
	      
			//mask 설정
			setMask();
			
			//버튼 이벤트 설정
			bindEvent();
	    });
		
		function setMask(){
			$('input[type=text]').css('text-align', 'right');
            $('input[type=text]').mask('#,##0', {reverse: true});
		}
		
		//버튼 이벤트 설정
		function bindEvent(){
			
			//+버튼
			$('#btnPlus').click(function(){
				var sHtml = "";

				sHtml += '	<tr>                                                                                        ';
				sHtml += '		<td>                                                                                    ';
				sHtml += '			<input type="text" id="vwCoinType" name="vwCoinType" value=""  style="width:100px;"/>';
				sHtml += ' 			<input type="hidden" id="coinType" name="coinType" value=""/>						';
				sHtml += '			<input type="text" id="vwCoinCnt" name="vwCoinCnt" value=""  style="width:100px;" />';
				sHtml += ' 			<input type="hidden" id="coinCnt" name="coinCnt" value=""/>							';
				sHtml += '		</td>                                                                                   ';
				sHtml += '	</tr>                                                                                       ';
				
				$('table[id=coinRow] tbody').append(sHtml);
				
				$('table[id=coinRow] tr:last input[type=text]').css('text-align', 'right');
				$('table[id=coinRow] tr:last input[type=text]').mask('#,##0', {reverse: true});
			});
			
			//-버튼
			$('#btnMinus').click(function(){
				var rowIdx = $('table[id=coinRow] tr').length;
				
				if(rowIdx <= 1){
					alert('삭제할 행이 없습니다.');
				}else{
					$('table[id=coinRow] tr:last').remove();	
				}
			});
			
			//계산버튼
			$('#btnCalc').click(function(){
				if(!validator()){
					return false;
				}
				
				var param = JSON.stringify($('form[id="calcForm"]').serializeObject());
				
				$.ajax({
				    type       : 'POST',
				    async      : false,
				    contentType: 'application/json',
				    data        : param,
				    dataType   : 'json',
				    url        : '/coin/calculator.do',
				    error      : function(json){
				        alert('저장중 오류가 발생하였습니다');
				    },
				    success    : function(data){
				    	var status = data['status'];
			            var resultMessage = data['errorMessage'];
			            var errorCode = data['errorCode'];
			            var result = data['data'];
			            
				        if(status == "S" ) {
				            //alert('성공 = '+result.data);
				            $('#txtResult').text(result.data);
				            
				        } else {
				        	$('#txtResult').text(resultMessage);
				            
				            return;
				        }
				    }
				});

			});
		}
		
		// 전송 전 입력값 체크
		function validator(){
			var goFlag = true;
			if($('#vwMoneyAmt').val() == "" ){
				alert('지폐금액을 입력하세요.');
				$('#vwMoneyAmt').focus();
				goFlag = false;
			}
			
			$('#moneyAmt').val($('#vwMoneyAmt').val().replace(/,/gi, ""));
			
			$('input[id=coinType]').each(function(i){
				if($('input[id=vwCoinType]:eq('+i+')').val() == "" ){
					alert('['+(i+1)+']'+'번째 동전타입이 빈값입니다.');
					$('input[id=vwCoinCnt]:eq('+i+')').focus();
					goFlag = false;
				}
				$(this).val($('input[id=vwCoinType]:eq('+i+')').val().replace(/,/gi, ""));	
			});
			$('input[id=coinCnt]').each(function(i){
				if($('input[id=vwCoinCnt]:eq('+i+')').val() == "" ){
					alert('['+(i+1)+']'+'번째 동전갯수가 빈값입니다.');
					$('input[id=vwCoinCnt]:eq('+i+')').focus();
					goFlag = false;
				}
				$(this).val($('input[id=vwCoinCnt]:eq('+i+')').val().replace(/,/gi, ""));	
			});
			$('#txtResult').text('');
			return goFlag;
		}
	</script>
	<body>
		<form id="calcForm" name="calcForm" >
			<table class="table">
				<colgroup>
				    <col style="width:150px;" />
				    <col style="width:auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" class="accent">지폐금액</th>
						<td>
							<input type="text" id="vwMoneyAmt" name="vwMoneyAmt" value=""  style="width:100px;">
							<input type="hidden" id="moneyAmt" name="moneyAmt" value=""  style="width:100px;">
                        </td>
					</tr>
					<tr>
						<th scope="row" class="accent">동전 가지 수</th>
						<td>
							<input type="button" id="btnPlus" name="btnPlus" value="+" />
							<input type="button" id="btnMinus" name="btnMinus" value="-" />
                        </td>
					</tr>
					<tr>
						<td scope="row" colspan="2">
							<table id="coinRow" name="coinRow">
								<tr>
									<td>
										<input type="text" id="vwCoinType" name="vwCoinType" value=""  style="width:100px;"/>
										<input type="hidden" id="coinType" name="coinType" value=""/>
										<input type="text" id="vwCoinCnt" name="vwCoinCnt" value=""  style="width:100px;" />
										<input type="hidden" id="coinCnt" name="coinCnt" value=""/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td scope="row" colspan="2">
							<input type="button" id="btnCalc" name="btnCalc" value="계산" />
						</td>
					</tr>
					<tr>
						<td scope="row" colspan="2">
							<textarea rows="6" cols="20" id="txtResult" name="txtResult"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		
	</body>
</html>
