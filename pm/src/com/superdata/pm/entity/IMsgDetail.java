package com.superdata.pm.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @ClassName: IMsgDetail
* @Description: 接收消息
* @author zhuyuesheng
* @date 2014 七月 31 17:01:08
*/
public class IMsgDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1190305973114766471L;
	
	private Integer id;				//消息明细ID
	
	private Integer msgId;			//信息发送ID
	
	private String msgName;			//消息标题
	
	private String msgContent;		//消息内容
	
	private String senderName;		//发送人姓名
	
	private Integer receiveId;		//接收者ID
	
	private String receiveDate;		//接收时间
	
	private Integer status;			//阅读状态：0-未读，1-已读
	
	private String statusName;		//阅读状态中文
	
	private Integer attachment;	//附件个数
	
	private String tempDate;		//显示时间
	
	private Integer senderId;		//发送人id
	
	private boolean canRemove = true;	//标识是否可删除
	

	public IMsgDetail(String msgName, String msgContent, String senderName,
			String receiveDate,	Integer status, Integer id, boolean canRemove) {
		super();
		this.msgName = msgName;
		this.msgContent = msgContent;
		this.senderName = senderName;
		this.receiveDate = receiveDate;
		this.status = status;
		this.id = id;
		this.canRemove = canRemove;
	}
	

	public IMsgDetail(String msgName, String msgContent, String senderName,
			String receiveDate, Integer status, Integer id) {
		super();
		this.msgName = msgName;
		this.msgContent = msgContent;
		this.senderName = senderName;
		this.receiveDate = receiveDate;
		this.status = status;
		this.id = id;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public Integer getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public IMsgDetail() {
	}

	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getTempDate() {
		return tempDate;
	}

	public void setTempDate(String tempDate) {
		this.tempDate = tempDate;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Integer getAttachment() {
		return attachment;
	}

	public void setAttachment(Integer attachment) {
		this.attachment = attachment;
	}

	public boolean isCanRemove() {
		return canRemove;
	}

	public void setCanRemove(boolean canRemove) {
		this.canRemove = canRemove;
	}
	
}