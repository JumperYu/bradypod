package com.seewo.trade.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seewo.trade.bean.JarRecord;
import com.seewo.trade.commons.DB;
import com.seewo.trade.utils.IdGeneration;

@Service
public class FileServiceImpl implements FileService{
	@Autowired
	private DB db;
	
	@Override
	public void upload(MultipartFile file, String name, HttpServletRequest request) {
		String path = "upload/" + System.currentTimeMillis()+"/"+file.getOriginalFilename();
		String savePath=request.getServletContext().getRealPath("/")+path;
		
		if(!(new File(savePath).getParentFile().exists())){
			new File(savePath).getParentFile().mkdirs();
		}
		
		//保存在服务器
		FileOutputStream fos=null;
		try {
			fos=new FileOutputStream(new File(savePath));
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
		
		//add record
		JarRecord record=new JarRecord();
		record.setId(IdGeneration.getId());
		record.setName(name);
		record.setPath(savePath);
		
		db.jarMap.put(record.getId(), record);
	}

}
