package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.CandidateArrange;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateArrangeMapper {

    Integer insertArranges(@Param("list") List<CandidateArrange> arrangeList);

    List<CandidateArrange> queryArrangeByCandidateNoOrRealName(@Param("param") String param);

    List<CandidateArrange> queryArrangeByExamNoAndSite(@Param("examNo") String examNo, @Param("siteId") String siteId);

    CandidateArrange getArrangeByCandidateNo(@Param("candidateNo") String candidateNo);

    Integer getSiteCount(@Param("examNo") String examNo, @Param("siteId") String siteId);

    Integer updateSiteAndSeat(@Param("candidateNo") String candidateNo, @Param("siteId") String siteId, @Param("seat") Integer seat);

}
