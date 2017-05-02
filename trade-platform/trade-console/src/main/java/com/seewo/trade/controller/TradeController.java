package com.seewo.trade.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seewo.trade.bean.ResultEntity;
import com.seewo.trade.service.TradeService;

@Controller
@RequestMapping("/trade")
public class TradeController {
	@Autowired
	private TradeService tradeService;
	@RequestMapping(value = "/list",method=RequestMethod.GET)
	public void getItemList(HttpServletResponse resp){
		ResultEntity res = new ResultEntity();
		res.setData(tradeService.getItems());
		try {
			resp.getWriter().write(new ObjectMapper().writeValueAsString(res));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value = "/{id}",method=RequestMethod.GET)
	public String getItemDetail(@PathVariable Long id,ModelMap model){
		model.addAttribute("resp",tradeService.getItemDetail(id));
		return "2";
	}
	
	@RequestMapping(value = "/order/item/{id}",method=RequestMethod.POST)
	public String addOrder(@PathVariable Long id,@RequestParam int num, ModelMap model){
		model.addAttribute("resp",tradeService.addOrder(id, num));
		return "3";
	}
}
