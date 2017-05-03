package com.seewo.trade.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seewo.modules.ItemQueryService;
import com.seewo.modules.Modules;
import com.seewo.modules.OrderHandler;
import com.seewo.trade.bean.ResultEntity;

@Controller
@RequestMapping("/trade")
public class TradeController {
	
	OrderHandler orderHandler;
	ItemQueryService itemQueryService;
	
	public TradeController(){
		orderHandler=new OrderHandler();
		Modules modules=new Modules();
		itemQueryService=new ItemQueryService();
		orderHandler.setItemQueryService(itemQueryService);
		orderHandler.setModules(modules);
	}
	
	@RequestMapping(value = "/list",method=RequestMethod.GET)
	public void getItemList(HttpServletResponse resp){
		ResultEntity res = new ResultEntity();
		res.setData(itemQueryService.getItems());
		try {
			resp.getWriter().write(new ObjectMapper().writeValueAsString(res));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value = "/{id}",method=RequestMethod.GET)
	public String getItemDetail(@PathVariable Long id,ModelMap model){
		model.addAttribute("resp",itemQueryService.queryItemById(id));
		return "2";
	}
	
	@RequestMapping(value = "/order/item/{id}",method=RequestMethod.POST)
	public void addOrder(@PathVariable Long id,@RequestParam int num, ModelMap model,HttpServletResponse resp){
		try {
			resp.getWriter().write(new ObjectMapper().writeValueAsString(orderHandler.createOrder(id, num)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/order/{id}",method=RequestMethod.GET)
	public String getOrderDetail(@PathVariable Long id , ModelMap model){
		model.addAttribute("resp",orderHandler.queryById(id));
		return "3";
	}
}
