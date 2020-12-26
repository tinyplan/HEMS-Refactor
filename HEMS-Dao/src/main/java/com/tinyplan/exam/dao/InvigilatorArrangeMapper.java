package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.InvigilatorArrange;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvigilatorArrangeMapper {

    Integer insertArranges(@Param("list") List<InvigilatorArrange> arrangeList);

    List<String> getExamSiteByNo(@Param("examNo") String examNo);

    List<InvigilatorArrange> getArrange(@Param("examNo") String examNo, @Param("siteId") String siteId);

}
