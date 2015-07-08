package com.superdata.pm.entity;

import java.util.Date;

/**
 * 项目验收报告实体类
 * @ClassName PReport
 * @author lyyong
 * @date 2014年7月22日 上午11:25:13
 *
 */
public class PReport {
    private Integer id;				// 报告ID

    private Integer projectId;		// 项目ID
    
    private String projectName;		// 项目名称
    
    private String projectStartDate;	// 项目开始时间
    
    private String projectEndDate;	// 项目结束时间

    private String code;			// 报告编号

    private String name;			// 报告名称

    private String billDate;			// 验收时间

    private Integer empId;			// 验收负责人ID
    
    private String empName;			// 验收负责人名称

    private Integer result;			// 验收结果
    
    private String resultName;		//验收结果名称

    private String remark;			// 验收描述

    private Byte attachment;		// 附件数量

    private Integer createId;		// 填报人ID
    
    private String createName;		// 填报人名称

    private String createDate;		// 填报日期

    private Integer auditId;		// 审核人ID
    
    private String auditName;		// 审核人名称

    private String auditDate;			// 审核日期

    private Integer status;			// 审核状态 0:未审 1:已审
    
    private String insertReportDetailsJson; // 添加验收栏目JSON数据
    
    private String updateReportDetailsJson; // 修改验收栏目JSON数据
    
    private String deleteReportDetailsJson; // 删除验收栏目JSON数据
    
    public PReport() {
		super();
	}
    
	public PReport(Integer id, String projectName, String code, String name,
			String billDate, String resultName) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.code = code;
		this.name = name;
		this.billDate = billDate;
		this.resultName = resultName;
	}

	public PReport(Integer id, Integer projectId, Integer result, Integer auditId) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.result = result;
		this.auditId = auditId;
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

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
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

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Byte getAttachment() {
		return attachment;
	}

	public void setAttachment(Byte attachment) {
		this.attachment = attachment;
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

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInsertReportDetailsJson() {
		return insertReportDetailsJson;
	}

	public void setInsertReportDetailsJson(String insertReportDetailsJson) {
		this.insertReportDetailsJson = insertReportDetailsJson;
	}

	public String getUpdateReportDetailsJson() {
		return updateReportDetailsJson;
	}

	public void setUpdateReportDetailsJson(String updateReportDetailsJson) {
		this.updateReportDetailsJson = updateReportDetailsJson;
	}

	public String getDeleteReportDetailsJson() {
		return deleteReportDetailsJson;
	}

	public void setDeleteReportDetailsJson(String deleteReportDetailsJson) {
		this.deleteReportDetailsJson = deleteReportDetailsJson;
	}
    
}