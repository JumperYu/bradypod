package com.yu.sys.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bradypod.common.service.MyBatisBaseService;
import com.yu.sys.constant.TaskState;
import com.yu.sys.mapper.TaskMapper;
import com.yu.sys.po.Task;
import com.yu.util.constant.ResultUtil;
import com.yu.util.validate.AssertUtil;

/**
 * 任务相关业务方法
 * 
 *
 * @author zengxm
 * @date 2015年7月13日
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TaskService extends MyBatisBaseService<Task, TaskMapper> {

	/**
	 * 按固定条件查找符合的数据
	 * 
	 * @param task
	 *            - VO
	 * @return - List<Task>
	 */
	public List<Task> listTasks(Task task) {
		List<Task> tasks = getMapper().listTasks(task);
		return tasks;
	}

	/**
	 * 列出所有的任务
	 * 
	 * @return List<Task>
	 */
	public List<Task> listAllTasks() {
		Task task = new Task();
		return listTasks(task);
	}

	/**
	 * 列出所有正在等待的任务
	 * 
	 * @return List<Task>
	 */
	public List<Task> listAllWatingTasks() {
		Task task = new Task();
		task.setState(TaskState.WAITING);
		return listTasks(task);
	}

	/**
	 * 添加完整Task&Job信息
	 * 
	 * @param task
	 * @param job
	 * @return - true/false
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void addTask(Task task) {
		AssertUtil.assertGreaterThanZero(task.getTaskId(),
				"taskId is not exists");
		
		int task_rows = getMapper().save(task);
		
		AssertUtil.assertEqual(task_rows, ResultUtil.SINGLE_RESULT,
				"task save failed," + task);
	}

	/**
	 * 更新任务状态为正在运行
	 * 
	 * @param taskId
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void setTaskStateToRunning(Task task) {
		AssertUtil.assertGreaterThanZero(task.getTaskId(), "任务Id不能为空");
		AssertUtil.assertNotNull(task.getLastModify(), "lastModify不能为空");

		task.setState(TaskState.RUNNING);
		int effected_rows = update(task);

		AssertUtil.assertEqual(effected_rows, ResultUtil.SINGLE_RESULT,
				"任务运行更新失败, " + task);
	}

	/**
	 * 批量更新任务状态为正在运行
	 * 
	 * @param taskId
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void batchSetTaskStateToRunning(Task[] tasks) {
		AssertUtil.notEmpty(tasks, "tasks不能为空");
		for (Task task : tasks) {
			task.setState(TaskState.RUNNING);
		}
		int effected_rows = batchUpdate(Arrays.asList(tasks));
		AssertUtil.assertGreaterThanZero(effected_rows, "批量运行任务更新失败, " + tasks);
	}

	/**
	 * 修改一个任务到等状态
	 * 
	 * @param taskId
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void setTaskStateToPause(Task task) {

		// 校验
		AssertUtil.assertGreaterThanZero(task.getTaskId(), "任务Id不能为空");
		AssertUtil.assertNotNull(task.getLastModify(), "lastModify不能为空");

		task.setState(TaskState.PAUSE);
		int effected_rows = update(task);

		AssertUtil.assertEqual(effected_rows, ResultUtil.SINGLE_RESULT,
				"任务更新暂停状态失败, " + task);

	}

	/**
	 * 更新任务状态为暂停状态
	 * 
	 * @param taskId
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void batchSetTaskStateToPause(Task[] tasks) {

		AssertUtil.notEmpty(tasks, "tasks不能为空");
		for (Task task : tasks) {
			task.setState(TaskState.PAUSE);
		}
		int effected_rows = batchUpdate(Arrays.asList(tasks));
		AssertUtil.assertGreaterThanZero(effected_rows, "批量运行任务暂停失败, " + tasks);
	}

	/**
	 * 修改一个任务到等状态
	 * 
	 * @param taskId
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void setTaskStateToWaiting(long taskId) {
		AssertUtil.assertGreaterThanZero(taskId, "taskId必须大于0");

		Task task = new Task();
		task.setTaskId(taskId);
		task.setState(TaskState.WAITING);
		int effected_rows = update(task);

		AssertUtil.assertEqual(effected_rows, ResultUtil.SINGLE_RESULT,
				"任务更新等待状态失败, " + task);

	}

	/**
	 * 获取一个任务
	 * 
	 * @param taskId
	 * @return Task
	 */
	public Task getTask(long taskId) {
		Task task = new Task();
		task.setTaskId(taskId);
		task = get(task);
		return task;
	}

	/**
	 * 修改任务信息
	 * 
	 * @param task
	 *            - 任务PO
	 * 
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void editTask(Task task) {

		AssertUtil.assertGreaterThanZero(task.getTaskId(), "taskId必须传入");
		AssertUtil.assertNotNull(task.getLastModify(), "任务的最新修改时间必须传入");

		task.setState(TaskState.MODIFIED);
		int effectdRow = update(task);

		AssertUtil.assertEqual(effectdRow, 1, "未能成功修改任务数据," + task);
	}
}
