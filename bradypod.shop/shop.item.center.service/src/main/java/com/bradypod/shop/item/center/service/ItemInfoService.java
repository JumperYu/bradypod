package com.bradypod.shop.item.center.service;

import java.util.List;

import com.bradypod.common.po.PageData;
import com.bradypod.common.service.BaseMybatiService;
import com.bradypod.shop.item.center.po.ItemInfo;

/**
 * 商品查询
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015-09-20
 */
public interface ItemInfoService extends BaseMybatiService<ItemInfo> {

	/**
	 * 获取分页数据
	 * 
	 * @param page
	 *            - 页大小&页码
	 * @param params
	 *            - Map<String, Object> 参数
	 * @return
	 */
	public PageData<List<ItemInfo>> findPageData(Long id, Integer pageNO,
			Integer pageSize);

	public Long count();
}