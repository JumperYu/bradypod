package com.seewo.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seewo.trade.bean.ResultEntity;
import com.seewo.trade.service.FileService;

/**
 * 文件上传
 * @author wujianxin
 * @date 2017年4月26日
 *
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {
	@Autowired
	private FileService fileSrv;
	
	@RequestMapping(value = "/upload",method=RequestMethod.POST)
	public ResultEntity uploadPic(MultipartFile file,@RequestParam String feature,@RequestParam String module) throws Exception{
		ResultEntity res = new ResultEntity();
		fileSrv.upload(file, feature,module);
		return res;
	}
}
