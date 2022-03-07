package com.mega.service.member;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.mega.dao.member.SignupDao;

public class SignupService {
	private static final String SAVE_PATH = "C:\\Users\\USER\\Desktop\\9월\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\project\\resources\\images\\profile\\user";
	
	@Autowired
	private SignupDao dao;

	public int signup(String username, String id, String pw, MultipartFile profileImage, int profileImageType) {
		try {
			// 사용자가 직접 사용할 프로필 이미지를 전달했다면
			// 프로필 이미지 저장(프로필 이미지 업로드 처리)
			String profileImagePath = "img3.png";
			
			if(profileImageType == 1) {
				profileImagePath = "img1.png";
			} else if(profileImageType == 1) {
				profileImagePath = "img2.png";
			} else if(profileImageType == 3 && !profileImage.isEmpty()) {
				String filename = profileImage.getOriginalFilename();
				
				int lastDotIndex = filename.lastIndexOf('.');
				String extension = filename.substring(lastDotIndex);
				
				filename = System.currentTimeMillis() + extension;
				
				profileImage.transferTo(new File(SAVE_PATH + "\\" + filename));
				
				profileImagePath = "user/" + filename;
			}
			
			// 회원정보를 DB에 저장 (회원 가입)
			dao.signup(username, id, pw, profileImageType, profileImagePath);
			
			return HttpStatus.CREATED.value();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return HttpStatus.BAD_REQUEST.value();
	}
}
