package com.superdata.pm.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @ClassName ProjectInfo
 * @author 朱岳胜
 * @date 2014 六月 27 16:39:43
 *
 */
public class PProjectLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1190305973114766471L;
	
	public static final String[] opTypes =  {"修改","调整项目周期","调整项目预算","调整立项人","新增"};
	
	private Integer id;				//主键ID
	
	private Integer projectId;		//项目ID
	
	private String projectName;		//项目名称
	
	private String opType;			//操作类型
	
	private String opRemark;		//操作说明
	
	private Integer createId;		//创建人
	
	private String createName;		//创建人名称
	
	private String createDate;		//创建时间
	
	public PProjectLog() {
	}
	
	public PProjectLog(Integer projectId,String opType,String opRemark,Integer createId,String createDate) {
		this.projectId = projectId;
		this.opType = opType;
		this.opRemark = opRemark;
		this.createId = createId;
		this.createDate = createDate;
	}

	public PProjectLog(String opType, String opRemark, String createName,
			String createDate) {
		super();
		this.opType = opType;
		this.opRemark = opRemark;
		this.createName = createName;
		this.createDate = createDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getOpRemark() {
		return opRemark;
	}

	public void setOpRemark(String opRemark) {
		this.opRemark = opRemark;
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

	
}