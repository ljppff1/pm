package com.superdata.pm.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @ClassName: IMsg
* @Description: 发送消息
* @author zhuyuesheng
* @date 2014 七月 31 17:01:08
*/
public class IMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1190305973114766471L;
	
	private Integer id;				//主键ID
	
	private Integer companyId;		//企业ID
	
	private String name;			//消息标题
	
	private String content;			//消息内容
	
	private Integer attachment;		//附件个数
	
	private Integer senderId;		//发送人
	
	private String senderName;		//发送人姓名
	
	private String receiveId;		//接收人 0个人、1部门、2项目组ID集合例如： 0:1,0:2,1:1,2:1
	
	private String receiveName;		//接收人姓名

	private Integer createId;		//创建人
	
	private String createName;		//创建人姓名
			
	private String createDate;		//创建时间
	
	private Integer status;			//发送状态：0-未发送，1-已发送
	
	private String statusName;		//发送状态名称
	
	private String msgName;			//消息标题
	
	private String tempDate;		//显示时间
	
	private boolean canRemove = true;	//标识是否可删除
	

	public IMsg() {
	}


	public IMsg(String content, String receiveName, String createDate,
			String msgName,Integer id, String receiveId) {
		super();
		this.content = content;
		this.receiveName = receiveName;
		this.createDate = createDate;
		this.msgName = msgName;
		this.id = id;
		this.receiveId = receiveId;
	}
	
	
	public IMsg(String content, String receiveName, String createDate,
			String msgName,Integer id) {
		super();
		this.content = content;
		this.receiveName = receiveName;
		this.createDate = createDate;
		this.msgName = msgName;
		this.id = id;
	}


	public IMsg(String content, String receiveName, String createDate,
			String msgName, boolean canRemove) {
		super();
		this.content = content;
		this.receiveName = receiveName;
		this.createDate = createDate;
		this.msgName = msgName;
		this.canRemove = canRemove;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Integer getAttachment() {
		return attachment;
	}


	public void setAttachment(Integer attachment) {
		this.attachment = attachment;
	}


	public Integer getSenderId() {
		return senderId;
	}


	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public String getReceiveId() {
		return receiveId;
	}


	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}


	public String getReceiveName() {
		return receiveName;
	}


	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}


	public Integer getCreateId() {
		return createId;
	}


	public void setCreateId(Integer createId) {
		this.createId = createId;
	}


	public String getCreateName() {
		return createName;
	}


	public void setCreateName(String createName) {
		this.createName = createName;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getTempDate() {
		return tempDate;
	}


	public void setTempDate(String tempDate) {
		this.tempDate = tempDate;
	}
	
	public String getStatusName() {
		return statusName;
	}


	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	public String getMsgName() {
		return msgName;
	}


	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}


	public boolean isCanRemove() {
		return canRemove;
	}


	public void setCanRemove(boolean canRemove) {
		this.canRemove = canRemove;
	}

	
}