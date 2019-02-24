package com.cntt.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/hello"})
public class HelloController
{
  @RequestMapping({"/view.do"})
  public String view()
  {
    return "/WEB-INF/jsp/homework/homework.jsp";
  }
}