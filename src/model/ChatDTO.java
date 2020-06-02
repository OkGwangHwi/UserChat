package model;

import java.sql.Date;

public class ChatDTO {

	//멤버변수
	private int num; //메시지에 순서 매기기
	private String id; //채팅 접속자 아이디
	private String msg; //보낸 메시지
	private String chatTime; //메시지 작성일
	
	//getter/setter
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getChatTime() {
		return chatTime;
	}
	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
	
}
