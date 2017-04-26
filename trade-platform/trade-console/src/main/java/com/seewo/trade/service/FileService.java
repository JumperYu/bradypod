package com.seewo.trade.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	void upload(MultipartFile file, String name,HttpServletRequest request);
}
