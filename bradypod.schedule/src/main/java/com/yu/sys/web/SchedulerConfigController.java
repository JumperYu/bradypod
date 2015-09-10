package com.yu.sys.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.yu.common.po.Result;
import com.yu.common.po.ResultCode;
import com.yu.sys.po.Task;
import com.yu.util.date.DateUtils;
import com.yu.util.gzip.GzipUtil;
import com.yu.util.validate.AssertUtil;

/**
 * 调度后台管理
 *
 * @author zengxm
 * @date 2015年7月13日
 *
 */
@Controller
public class SchedulerConfigController extends ScheduleBaseController {

	// -> home-page
	@RequestMapping("/")
	public String root() {
		return "redirect:index.html";
	}

	@RequestMapping("/index.html")
	public String index(Map<String, Object> context) {
		context.putAll(INIT_PARAMS);
		return "index";
	}

	@RequestMapping("/task/listAllTasks.json")
	@ResponseBody
	public Result<List<Task>> listAllTasks() {

		String msg = "查询全部任务信息";

		log.info(msg);

		Result<List<Task>> result = new Result<List<Task>>();

		result.setCode(ResultCode.SUCCESS);

		result.setMessage(msg);

		result.setResult(getTaskService().getAll(new Task()));

		return result;

	}

	/**
	 * 暂停任务
	 * 
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/task/pauseTask.do")
	@ResponseBody
	public Result<String> pauseTask(Task task) {

		// 校验
		AssertUtil.assertGreaterThanZero(task.getTaskId(), "任务Id不能为空");
		AssertUtil.assertNotNull(task.getLastModify(), "lastModify不能为空");

		Result<String> result = new Result<String>();
		try {
			// 暂停任务
			getTaskService().setTaskStateToPause(task);
			// 如果都没有异常产生则正常
			result.setMessage("成功暂停任务");
			result.setResult("成功");
			result.setCode(ResultCode.SUCCESS);
		} catch (Exception e) {
			log.error("暂停任务发生错误", e);
			result.setMessage("暂停失败");
			result.setResult(e.getMessage());
			result.setCode(ResultCode.FAILURE);
		}
		return result;
	}

	/**
	 * 启动任务
	 * 
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/task/startTask.do")
	@ResponseBody
	public Result<String> startTask(Task task) {

		// 校验
		AssertUtil.assertGreaterThanZero(task.getTaskId(), "任务Id不能为空");
		AssertUtil.assertNotNull(task.getLastModify(), "lastModify不能为空");

		Result<String> result = new Result<String>();
		try {
			// 数据库操作
			getTaskService().setTaskStateToRunning(task);
			result.setMessage("成功启动任务");
			result.setResult("成功");
			result.setCode(ResultCode.SUCCESS);
		} catch (Exception e) {
			log.error("启动任务发生错误", e);
			result.setMessage("启动失败");
			result.setResult(e.getMessage());
			result.setCode(ResultCode.FAILURE);
		}
		return result;
	}

	/**
	 * 批量启动任务
	 * 
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/task/batchStartTask.do")
	@ResponseBody
	public Result<String> batchStartTask(
			@RequestParam(value = "taskId[]") Long[] taskId,
			@RequestParam(value = "lastModify[]") String[] lastModify) {

		// 校验
		AssertUtil.notEmpty(taskId, "taskId没有传入数据");
		AssertUtil.notEmpty(lastModify, "lastModify没有传入数据");
		AssertUtil.assertEqual(taskId.length, lastModify.length, "传入的数据不成对");

		Result<String> result = new Result<String>();
		try {
			Task[] tasks = new Task[taskId.length];
			for (int i = 0, len = taskId.length; i < len; i++) {
				Task task = new Task();
				task.setTaskId(taskId[i]);
				task.setLastModify(DateUtils.strToDate(lastModify[i]));
				tasks[i] = task;
			}
			// 数据库操作
			getTaskService().batchSetTaskStateToRunning(tasks);
			result.setMessage("批量启动任务成功");
			result.setResult("成功");
			result.setCode(ResultCode.SUCCESS);
		} catch (Exception e) {
			log.error("批量启动任务发生错误", e);
			result.setMessage("启动失败");
			result.setResult(e.getMessage());
			result.setCode(ResultCode.FAILURE);
		}
		return result;
	}

	/**
	 * 批量暫停任务
	 * 
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/task/batchPauseTask.do")
	@ResponseBody
	public Result<String> batchPauseTask(
			@RequestParam(value = "taskId[]") Long[] taskId,
			@RequestParam(value = "lastModify[]") String[] lastModify) {

		// 校验
		AssertUtil.notEmpty(taskId, "taskId没有传入数据");
		AssertUtil.notEmpty(lastModify, "lastModify没有传入数据");
		AssertUtil.assertEqual(taskId.length, lastModify.length, "传入的数据不成对");

		Result<String> result = new Result<String>();
		try {
			Task[] tasks = new Task[taskId.length];
			for (int i = 0, len = taskId.length; i < len; i++) {
				Task task = new Task();
				task.setTaskId(taskId[i]);
				task.setLastModify(DateUtils.strToDate(lastModify[i]));
				tasks[i] = task;
			}
			// 数据库操作
			getTaskService().batchSetTaskStateToPause(tasks);
			result.setMessage("批量暂停任务成功");
			result.setResult("成功");
			result.setCode(ResultCode.SUCCESS);
		} catch (Exception e) {
			log.error("批量暂停任务发生错误", e);
			result.setMessage("启动失败");
			result.setResult(e.getMessage());
			result.setCode(ResultCode.FAILURE);
		}
		return result;
	}

	/**
	 * 修改任务
	 * 
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/task/editTask.do")
	@ResponseBody
	public Result<String> editTask(Task task) {

		// 校验
		AssertUtil.assertGreaterThanZero(task.getTaskId(), "任务Id不合法");

		Result<String> result = new Result<String>();
		try {
			// 数据库操作
			getTaskService().editTask(task);
			result.setMessage("修改任务成功");
			result.setResult("成功");
			result.setCode(ResultCode.SUCCESS);
		} catch (Exception e) {
			log.error("修改任务失败", e);
			result.setMessage("启动失败");
			result.setResult(e.getMessage());
			result.setCode(ResultCode.FAILURE);
		}
		return result;
	}

	@RequestMapping("/download")
	public void output(HttpServletResponse response,
			String filePathAndFileName, String mimeType) throws IOException {

		File file = new File(filePathAndFileName);

		// set response headers
		response.setContentType((mimeType != null) ? mimeType
				: "application/octet-stream");
		response.setContentLength((int) file.length());

		// read and write file
		ServletOutputStream op = response.getOutputStream();
		// 128 KB buffer
		int bufferSize = 131072;
		FileInputStream fileInputStream = new FileInputStream(file);
		FileChannel fileChannel = fileInputStream.getChannel();
		// 6x128 KB = 768KB byte buffer
		ByteBuffer bb = ByteBuffer.allocateDirect(786432);
		byte[] barray = new byte[bufferSize];
		int nRead, nGet;

		try {
			while ((nRead = fileChannel.read(bb)) != -1) {
				if (nRead == 0)
					continue;
				bb.position(0);
				bb.limit(nRead);
				while (bb.hasRemaining()) {
					nGet = Math.min(bb.remaining(), bufferSize);
					// read bytes from disk
					bb.get(barray, 0, nGet);
					// write bytes to output
					op.write(barray);
				}
				bb.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bb.clear();
			fileChannel.close();
			fileInputStream.close();
		}
	}

	@RequestMapping(value = "/getClientIp")
	// @ResponseBody
	public void jsonPCallback(WebRequest webRequest,
			HttpServletRequest request, String callback,
			HttpServletResponse resp) throws Exception {
		/*
		 * long lastModifiedTimestamp = 0;
		 * if(webRequest.checkNotModified(lastModifiedTimestamp)){ return; }
		 */
		String ip = request.getRemoteHost();
		String result = String.format("%s({ip:'%s'});", callback, ip);
		resp.setHeader("Content-Encoding", "gzip");
		// resp.setContentType("text/html;charset=utf-8");
		byte[] output = GzipUtil.compress(result.getBytes());
		// resp.setContentLength(output.length);
		OutputStream out = resp.getOutputStream();
		out.write(output);
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/test.html")
	public String returnURL() {
		return "/test";
	}
}
