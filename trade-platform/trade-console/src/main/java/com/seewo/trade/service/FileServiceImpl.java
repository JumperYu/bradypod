package com.seewo.trade.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seewo.modules.ItemManagerModule;

@Service
public class FileServiceImpl implements FileService{
	@Override
	public void upload(MultipartFile file,String feature,String module) {
		long currentTimeMillis = System.currentTimeMillis();
		String jarPath = ItemManagerModule.path+"/" + currentTimeMillis+"/plugin.jar";
		String propPath = ItemManagerModule.path+"/" + currentTimeMillis+"/conf.properties";
		
		
		if(!(new File(jarPath).getParentFile().exists())){
			new File(jarPath).getParentFile().mkdirs();
		}
		
		FileOutputStream fos=null;
		
		try {
			Properties pro=new Properties();
			pro.setProperty("feature", feature);
			pro.setProperty("module", module);
			pro.store(new FileOutputStream(propPath), null);
			
			fos=new FileOutputStream(new File(jarPath));
			FileUtil.copyStream(file.getInputStream(), fos);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
