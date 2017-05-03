package com.seewo.trade.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seewo.modules.ItemManagerModule;
import com.seewo.trade.bean.ResultEntity;
import com.seewo.trade.commons.ErrorcodeType;

@Service
public class FileServiceImpl implements FileService {
	
	private Map<String,String> pathMap=new HashMap<>();
	
	@Override
	public ResultEntity upload(MultipartFile file, String feature, String module) {
		ResultEntity resp=new ResultEntity();
		String parentDir;
		
		if (pathMap.containsKey(feature.concat(module))){
			parentDir=pathMap.get(feature.concat(module));
			resp.setMessage("该活动模块对应jar包之前已存在，本次上传已覆盖之前jar包");
		}else{
			parentDir = String.valueOf(System.currentTimeMillis());
			pathMap.put(feature.concat(module), parentDir);
		}
		
		String jarPath = ItemManagerModule.PATH + "/" + parentDir + "/" + ItemManagerModule.MODULE_NAME;
		String propPath = ItemManagerModule.PATH + "/" + parentDir + "/" + ItemManagerModule.CONIFG_NAME;

		if (!(new File(jarPath).getParentFile().exists())) {
			new File(jarPath).getParentFile().mkdirs();
		}

		FileOutputStream fos = null;

		try {
			Properties pro = new Properties();
			pro.setProperty("feature", feature);
			pro.setProperty("module", module);
			pro.store(new FileOutputStream(propPath), null);

			fos = new FileOutputStream(new File(jarPath));
			FileUtil.copyStream(file.getInputStream(), fos);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorcode(ErrorcodeType.FAIL);
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return resp;
	}
}
