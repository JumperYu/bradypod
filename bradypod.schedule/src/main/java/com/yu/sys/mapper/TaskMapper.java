package com.yu.sys.mapper;

import java.util.List;

import com.bradypod.common.mapper.BaseMapper;
import com.yu.sys.po.Task;

public interface TaskMapper extends BaseMapper<Task> {
	
	/**
	 * 查找固定条件的所有值
	 * 
	 * @param task - 任务vo
	 * @return List<Task> - 数组
	 */
	public List<Task> listTasks(Task task);
}
