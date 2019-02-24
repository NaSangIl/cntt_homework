<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String url = request.getRequestURL().toString();
    String uri = request.getRequestURI();
    
    String location = "";
    
    if(uri.indexOf("/") == -1){
        location = url + "/hello/view.do";
    }else if("/index.jsp".equals(uri)){
        location = url.replaceAll(uri, "/hello/view.do");
    }else{
        location = "/hello/view.do";
    }

    response.sendRedirect(location);

%>
