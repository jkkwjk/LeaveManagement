package com.jkk.leave.tools;

public enum ApplyStatus {
	UN_SEND(0), WAIT(1), AGREE(2), REJECT(3)
	;

	private final Integer status;
	ApplyStatus(int status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
}
