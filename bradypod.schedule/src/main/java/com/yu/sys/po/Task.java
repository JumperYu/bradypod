package com.yu.sys.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;
import java.util.*;

/**
 * 任务记录
 *
 * @author zengxm
 * @date Mon Aug 17 15:47:44 CST 2015
 *
 */
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id; //自增物理主键
	private long taskId; //自定义业务主键
	private String taskDesc; //任务描述
	private int taskType; //任务类型(1,类启动;)
	private Date startTime; //启动时间
	private Date endTime; //结束时间
	private int state; //任务状态(-1,作废;0,暂停;1,运行;2,出错;)
	private String jobClassName; //作业全限定类
	private String jobClassPath; //作业文件路
	private String jobCronTime; //作业cron时间表达式
	private String jobName; //作业名称
	private String jobGroupName; //作业组名
	private String triggerName; //触发器名称
	private String triggerGroupName; //触发器组名
	private Date createTime; //创建时间
	private String createOperator; //创建人
	private Date lastModify; //最后一次修改时间
	private String lastOperator; //最后一次操作人

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassPath(String jobClassPath) {
		this.jobClassPath = jobClassPath;
	}

	public String getJobClassPath() {
		return jobClassPath;
	}

	public void setJobCronTime(String jobCronTime) {
		this.jobCronTime = jobCronTime;
	}

	public String getJobCronTime() {
		return jobCronTime;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}

	public String getTriggerGroupName() {
		return triggerGroupName;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}

	public String getCreateOperator() {
		return createOperator;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	public Date getLastModify() {
		return lastModify;
	}

	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}

	public String getLastOperator() {
		return lastOperator;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}