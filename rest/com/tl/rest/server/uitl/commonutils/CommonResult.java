package com.tl.rest.server.uitl.commonutils;

import java.io.Serializable;
/**
 * 通用返回信息
 * @author yangcheng
 * @date 2017年12月5日 
 * @version V1.0
 */
public class CommonResult implements Serializable{
	private static final long serialVersionUID = 7888160220044773812L;

   /**
    *  响应业务状态
    */
    private Integer Ret;

    /**
     * 响应消息
     */
    private String Msg;
    /**
     *  响应中的数据
     */
    private Object Data;
    /**
     * 签名（Md5加密之后信息）
     */
    private String Sig;
    /**
     * 接口调用成功
     * @Title: build 
     * @author yangcheng
     * @param Ret 状态码
     * @param msg 状态信息
     * @param data 返回数据
     * @param sig 签名
     * @return
     */
    public static CommonResult build(Integer Ret, String msg, Object data,String sig) {
        return new CommonResult(Ret, msg, data,sig);
    }
    /**
     * 接口调用成功（msg默认为OK）
     * @Title: ok 
     * @author yangcheng
     * @param data 返回数据
     * @param sig 签名信息
     * @return
     */
    public static CommonResult ok(Object data,String sig) {
        return new CommonResult(data,sig);
    }
    /**
     * 用作更改信息的接口调用成功之后返回数据
     * @Title: ok 
     * @author yangcheng
     * @return
     */
    public static CommonResult ok() {
        return new CommonResult(null,null);
    }
    /**
     * 当访问报错失败时返回错误码和错误信息
     * @Title: build 
     * @author yangcheng
     * @param Ret
     * @param msg
     * @return
     */
    public static CommonResult build(Integer Ret, String msg) {
        return new CommonResult(Ret, msg, null,null);
    }
    
    public CommonResult() {

    }


    public CommonResult(Integer Ret, String msg, Object data,String sig) {
        this.Ret = Ret;
        this.Msg = msg;
        this.Data = data;
        this.Sig = sig;
    }

    public CommonResult(Object data,String sig) {
        this.Ret = 0;
        this.Msg = "OK";
        this.Data = data;
        this.Sig = sig;
        
    }


    public Integer getRet() {
        return Ret;
    }

    public void setRet(Integer Ret) {
        this.Ret = Ret;
    }

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

	public Object getData() {
		return Data;
	}

	public void setData(Object data) {
		Data = data;
	}

	public String getSig() {
		return Sig;
	}

	public void setSig(String sig) {
		Sig = sig;
	}



}
