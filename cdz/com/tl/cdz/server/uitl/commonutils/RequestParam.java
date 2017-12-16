package com.tl.cdz.server.uitl.commonutils;

import java.io.Serializable;

/**
 * 用于获取客户端传递过来的数据
 * @author yangcheng
 * @date 2017年12月13日 
 * @version V1.0
 */
public class RequestParam implements Serializable{
	private static final long serialVersionUID = 771024108423024801L;
	private String operatorID;
	private String data;
	private String timeStamp;
	private String seq;
	private String sig;
	public String getOperatorID() {
		return operatorID;
	}
	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}
	public String getData() {
		
		return data.replace(' ', '+');
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSig() {
		return sig;
	}
	public void setSig(String sig) {
		this.sig = sig;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
