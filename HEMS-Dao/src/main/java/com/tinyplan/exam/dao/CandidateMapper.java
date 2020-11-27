package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateMapper {

    /**
     * 获取表中的最大ID
     */
    Integer getMaxId();

    /**
     * 增加一个考生
     * @param user 基础信息
     */
    Integer insertCandidate(User user);

    /**
     * 获取考生基础信息
     * @param username 用户名(用户ID 或 账户名)
     */
    User getCandidateByUsername(@Param("username") String username);

    /**
     * 新增考生的详细信息
     * @param detail 新增的详细信息
     */
    Integer insertCandidateDetail(CandidateDetail detail);

    /**
     * 获取考生详细信息
     * @param userId 用户ID
     */
    CandidateDetail getCandidateDetail(@Param("userId") String userId);


}
