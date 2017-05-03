package com.seewo.trade.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	void upload(MultipartFile file,String feature,String module);
}
