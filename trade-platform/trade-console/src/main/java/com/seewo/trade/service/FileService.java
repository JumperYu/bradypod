package com.seewo.trade.service;

import org.springframework.web.multipart.MultipartFile;

import com.seewo.trade.bean.ResultEntity;

public interface FileService {
	ResultEntity upload(MultipartFile file,String feature,String module);
}
