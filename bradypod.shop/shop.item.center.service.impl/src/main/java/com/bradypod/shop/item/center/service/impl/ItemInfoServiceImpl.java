/*
 * Powered By [generator-framework]
 * Web Site: http://blog.bradypod.com
 * Github: https://github.com/JumperYu
 * Since 2015 - 2015
 */

package com.bradypod.shop.item.center.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bradypod.common.po.PageData;
import com.bradypod.common.service.BaseMybatisServiceImpl;
import com.bradypod.shop.item.center.mapper.ItemInfoMapper;
import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.ItemInfoService;

@Service("itemInfoService")
@Transactional
public class ItemInfoServiceImpl extends
		BaseMybatisServiceImpl<ItemInfoMapper, ItemInfo> implements
		ItemInfoService {

	@Override
	public PageData<List<ItemInfo>> findPageData(Long id, Integer pageNO,
			Integer pageSize) {
		pageNO = pageNO - 1; // 使用mysql limit实现需要减去1

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageSize", pageSize);
		params.put("pageNO", pageNO);
		params.put("id", id);

		// 1.找到mapper的list位置 和 count位置
		long count = getMapper().countData(params);
		List<ItemInfo> result = getMapper().listData(params);

		// 2.指定Page对象
		PageData<List<ItemInfo>> pageResult = new PageData<List<ItemInfo>>();
		pageResult.setCurrentPage(pageNO);
		pageResult.setPageSize(pageSize);
		pageResult.setCount(count);
		pageResult.setTotalPage((count % pageSize > 0) ? (count / pageSize + 1)
				: (count / pageSize));
		pageResult.setData(result);

		// 3.返回List<T>
		return pageResult;
	}

	@Override
	public Long count() {
		return getMapper().countData(null);
	}

}