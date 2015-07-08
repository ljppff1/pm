package com.superdata.pm.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @ClassName: PContract
* @Description: 项目合同
* @author Administrator
* @date 2014 七月 23 09:42:54
*/
public class PContract implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1190305973114766471L;
	
	private Integer id;				//主键ID
	
	private Integer projectId;		//项目ID
	
	private String projectName;		//项目名称
	
	private String code;			//合同编码
	
	private String name;			//合同名称
	
	private String amount;			//合同金额
	
	private Integer typeId;			//合同类型
	
	private String typeName;		//合同类型名称
	
	private String signedDate;		//签订日期
	
	private String startDate;			//开始日期
	
	private String endDate;			//终止日期
	
	private String otherCompany;	//对方公司
	
	private String otherDeputy;		//对方代表
	
	private String content;			//简要条款
	
	private Integer attachment;		//附件个数
	
	private Integer empId;			//我方代表员工ID
	
	private String empName;			//我方代表员工姓名
	
	private Integer createId;		//创建人
			
	private Date createDate;		//创建时间

	public PContract() {
	}

	public PContract(String projectName, String code, String name,
			String amount, String startDate, String endDate,
			String otherCompany, Integer attachment, Integer id) {
		super();
		this.projectName = projectName;
		this.code = code;
		this.name = name;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.otherCompany = otherCompany;
		this.attachment = attachment;
		this.id = id;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(String signedDate) {
		this.signedDate = signedDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOtherCompany() {
		return otherCompany;
	}

	public void setOtherCompany(String otherCompany) {
		this.otherCompany = otherCompany;
	}

	public String getOtherDeputy() {
		return otherDeputy;
	}

	public void setOtherDeputy(String otherDeputy) {
		this.otherDeputy = otherDeputy;
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

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	

	
}