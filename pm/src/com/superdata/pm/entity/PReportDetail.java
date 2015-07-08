package com.superdata.pm.entity;

/**
 * 项目验收栏目实体类
 * @ClassName PReportDetail
 * @author lyyong
 * @date 2014年7月22日 上午11:28:02
 *
 */
public class PReportDetail {
    private Integer id;			// 栏目ID

    private Integer reportId;	// 验收报告ID

    private String name;		// 验收项名称

    private String result;		// 验收结果
    
    private String resultName;	//验收结果中文

    private Integer empId;		// 验收负责人ID
    
    private String empName;		// 验收负责人名称

    private String remark;		// 备注
    

    public PReportDetail() {
		super();
	}
    
	public PReportDetail(String name, String resultName, String remark) {
		super();
		this.name = name;
		this.resultName = resultName;
		this.remark = remark;
	}


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
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

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}