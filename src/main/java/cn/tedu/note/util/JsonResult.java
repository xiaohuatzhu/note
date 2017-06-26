package cn.tedu.note.util;

import java.io.Serializable;

public class JsonResult implements Serializable {
	private static final long serialVersionUID = -3644950655568598241L;

	public static final int SUCCESS = 0;
	public static final int ERROR = 1;

	// 返回的状态,0成功,1出错
	private int state;
	// 发生错误的时候,赋值错误信息
	private String message;
	// 正常的时候,赋值返回数据
	private Object data;

	public JsonResult() {
		this.state = SUCCESS;
	}

	public JsonResult(Throwable e) {
		state = ERROR;
		message = e.getMessage();
	}

	public JsonResult(Object obj) {
		state = SUCCESS;
		data = obj;
	}

	public JsonResult(int state, Throwable e) {
		this.state = state;
		this.message = e.getMessage();
	}

	public JsonResult(int state, String message) {
		this.state = state;
		this.message = message;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message + "]";
	}

}
