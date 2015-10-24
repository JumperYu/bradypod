package com.bradypod.shop.item.center.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bradypod.shop.item.center.service.ItemInfoService;
import com.bradypod.shop.item.center.vo.ItemInfoVO;

/**
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015-09-20
 */
@Controller
@RequestMapping("/iteminfo")
public class ItemInfoController {

	// 默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	private final String LIST_ACTION = "redirect:/iteminfo";

	private ItemInfoService itemInfoService;

	@Resource
	public void setItemInfoService(ItemInfoService itemInfoService) {
		this.itemInfoService = itemInfoService;
	}

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/** 列表 */
	@RequestMapping(value = "/index")
	@ResponseBody
	public ItemInfoVO index(ModelMap model, ItemInfoVO itemInfo) {
		return itemInfo;
	}

	/** 显示 */
	@RequestMapping(value = "/{id}")
	public String show(ModelMap model, @PathVariable java.lang.Long id)
			throws Exception {
		itemInfoService.save(null);
		return "/iteminfo/show";
	}

	/** 进入新增 */
	@RequestMapping(value = "/new")
	public String _new(ModelMap model) throws Exception {
		return "/iteminfo/new";
	}

	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult */
	@RequestMapping(method = RequestMethod.POST)
	public String create(ModelMap model) throws Exception {
		return LIST_ACTION;
	}

	/** 编辑 */
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap model, @PathVariable java.lang.Long id)
			throws Exception {
		return "/iteminfo/edit";
	}

	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap model, @PathVariable java.lang.Long id)
			throws Exception {
		return LIST_ACTION;
	}

	/** 删除 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(ModelMap model, @PathVariable java.lang.Long id) {
		return LIST_ACTION;
	}

	/** 批量删除 */
	@RequestMapping(method = RequestMethod.DELETE)
	public String batchDelete(ModelMap model,
			@RequestParam("items") java.lang.Long[] items) {
		return LIST_ACTION;
	}

}
