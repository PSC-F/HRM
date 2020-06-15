package com.dao.provider;

import com.pojo.Job;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.ibatis.jdbc.SQL;

import javax.management.monitor.StringMonitor;
import java.util.Map;

import static com.hrm.util.common.HrmConstants.JOBTABLE;

public class JobDynaSqlProvider {
    //分页动态查询
    public String selectWithParam(Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(JOBTABLE);
                if (params.get("job") != null) {
                    Job job = (Job) params.get("job");
                    if (job.getName() != null && !job.getName().equals("")) {
                        WHERE("name LIKE CONCAT('%',#{job.name},'%')");
                    }
                }
            }
        }.toString();
        if (params.get("pageModel") != null) {
            sql += "limit #{pageModel.fistParam},#{pageModel.pageSize}";
        }
        return sql;
    }

    //动态查询总数量
    public String count(Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("count(*)");
                FROM(JOBTABLE);
                if (params.get("job") != null) {
                    Job job = (Job) params.get("job");
                    if (job.getName() != null && !job.getName().equals("")) {
                        WHERE("name LIKE CONCAT('',#{job.name},'')");
                    }
                }

            }
        }.toString();
    }

    //动态插入
    public String insertJob(Job job) {
        return new SQL() {
            {
                INSERT_INTO(JOBTABLE);
                if (job.getName() != null && !job.getName().equals("")) {
                    VALUES("name", "#{name}");
                }
                if (job.getRemark() != null && !job.getRemark().equals("")) {
                    VALUES("remark", "#{remark}");
                }
            }
        }.toString();
    }

    //动态更新
    public String updateJob(Job job) {
        return new SQL() {
            {
                UPDATE(JOBTABLE);
                if (job.getName() != null) {
                    SET("name=#{name}");
                }
                if (job.getRemark() != null) {
                    SET("remark=#{remark}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }
}