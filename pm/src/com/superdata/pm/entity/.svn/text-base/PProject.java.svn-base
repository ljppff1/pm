package com.superdata.pm.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @ClassName: PProject
* @Description: 项目信息
* @author zhuyuesheng
* @date 2014 七月 22 16:29:33
*
*/
public class PProject implements Serializable{

	/**
	 * 
	 */
private static final long serialVersionUID = 1190305973114766471L;
	
	private Integer id;				//主键ID
	
	private Integer companyId;		//企业ID
		
	private String code;			//项目编码
	
	private String name;			//项目名称
	
	private String amount;			//预算总额
	
	private String employeeAmt;		//人力预算
	
	private String productAmt;		//材料预算
		
	private String feeAmt;			//费用预算
	
	private Integer typeId;			//项目类型
	
	private String typeName;		//项目类型名称
	
	private String startDate;			//项目开始时间
	
	private String endDate;			//项目结束时间
	
	private Integer rate;			//完成进度
	
	private Integer attachment;		//附件个数
	
	private String remark;			//项目说明
	
	private Integer empId;			//项目经理ID
	
	private String empName;			//项目经理姓名
	
	private Integer createId;		//立项人
	
	private String createName;		//立项人姓名
	 
	private String createDate;		//立项日期
	
	private Integer closed;			//终止标志(0:正常  1:终止  默认0)
	
	private Integer status;			//项目状态
	
	private String statusName;		//项目状态中文
	
	private Float lastTime;         //持续时间,计算进度用到
	
	private Integer projectStatus;	//项目实际状态
	
	public PProject(String code, String name, String amount, String startDate,
			String endDate, Integer rate, String empName, String createDate,
			String statusName) {
		super();
		this.code = code;
		this.name = name;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rate = rate;
		this.empName = empName;
		this.createDate = createDate;
		this.statusName = statusName;
	}

	public PProject(String code, String name, String amount, String startDate,
			String endDate, Integer rate, String empName, String createDate,
			String statusName,Integer id) {
		super();
		this.code = code;
		this.name = name;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rate = rate;
		this.empName = empName;
		this.createDate = createDate;
		this.statusName = statusName;
		this.id=id;
	}
	
	public PProject(String code, String name, String startDate,
			String endDate, Integer rate, String empName, String createDate,
			Integer id) {
		super();
		this.code = code;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rate = rate;
		this.empName = empName;
		this.createDate = createDate;
		this.id=id;
	}
	
	
	public Float getLastTime() {
		return lastTime;
	}

	public void setLastTime(Float lastTime) {
		this.lastTime = lastTime;
	}

	public PProject() {
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

	public String getEmployeeAmt() {
		return employeeAmt;
	}

	public void setEmployeeAmt(String employeeAmt) {
		this.employeeAmt = employeeAmt;
	}

	public String getProductAmt() {
		return productAmt;
	}

	public void setProductAmt(String productAmt) {
		this.productAmt = productAmt;
	}

	public String getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getClosed() {
		return closed = (closed==null?0:closed);
	}

	public void setClosed(Integer closed) {
		this.closed = (closed==null?0:closed);
	}

	public Integer getAttachment() {
		return attachment;
	}

	public void setAttachment(Integer attachment) {
		this.attachment = attachment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public Integer getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Integer projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}