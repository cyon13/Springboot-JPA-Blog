package com.cyon13.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cyon13.web.dto.ResponseDto;

@ControllerAdvice // 모든 exception이 발생하면 이쪽으로 들어오게 함
@RestController
public class GlobalExceptionHandler {

//	@ExceptionHandler(value = IllegalArgumentException.class) // IllegalArgumentException 이 발생하면 exception에 대한 에러를 e로 전달
//	public String handleArgumentException(IllegalArgumentException e) {
//		return "<h1>"+e.getMessage()+"</h1>";
//	}
	
	@ExceptionHandler(value = Exception.class) //모든 Exception 이 발생하면 exception에 대한 에러를 e로 전달
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
	}
}
