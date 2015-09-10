package com.yu.sys.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yu.sys.service.TaskService;

/**
 * 调度系统基础模块
 *
 * @author zengxm
 * @date 2015年7月14日
 *
 */
public class ScheduleBaseController {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	// 资源地址
	public static final String RESOURCE_ROOT_PATH = "http://resource.bradypod.com/";
	// 首页模板地址
	public static final String TEMPLATE_HEAD_PATH = "/template/head.html";
	public static final String TEMPLATE_SCRIPT_PATH = "/template/script.html";

	public static final Map<String, Object> INIT_PARAMS = new HashMap<String, Object>() {
		private static final long serialVersionUID = 1L;

		{
			this.put("RESOURCE_ROOT_PATH", RESOURCE_ROOT_PATH);
			this.put("TEMPLATE_HEAD_PATH", TEMPLATE_HEAD_PATH);
			this.put("TEMPLATE_SCRIPT_PATH", TEMPLATE_SCRIPT_PATH);
		}
	};

	@Resource
	private TaskService taskService;

	public TaskService getTaskService() {
		return taskService;
	}
}
