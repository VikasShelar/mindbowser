package com.mind.bowser.utility;

import org.springframework.stereotype.Component;

import com.mind.bowser.dto.ResponseDto;

@Component
public class CommonUtils {

	public ResponseDto<?> setData(int code, String msg, Object data) {

		return new ResponseDto<Object>(code, msg, data);
	}

}
