/*
 * Powered By [generator-framework]
 * Web Site: http://blog.bradypod.com
 * Github: https://github.com/JumperYu
 * Since 2015 - 2015
 */

package com.bradypod.shop.item.center.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bradypod.common.service.BaseMybatisServiceImpl;
import com.bradypod.shop.item.center.mapper.CtgItemMapper;
import com.bradypod.shop.item.center.po.CtgItem;
import com.bradypod.shop.item.center.service.CtgItemService;

@Transactional
@Service
public class CtgItemServiceImpl extends
		BaseMybatisServiceImpl<CtgItemMapper, CtgItem> implements
		CtgItemService {

}