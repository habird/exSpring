package com.board.icia.exception;

public class IdCheckException extends RuntimeException {

	public IdCheckException(String msg) {
		super(msg); //getMessage() 호출시 출력됨
	}
}
