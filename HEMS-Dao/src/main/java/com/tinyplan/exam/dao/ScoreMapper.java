package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.Score;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreMapper {

    List<Score> getScoreByCandidateId(@Param("candidateId") String candidateId, @Param("pass") Integer pass);

    List<Score> getScoreByExamNo(@Param("examNo") String examNo);

    Integer insertScore(@Param("scoreList") List<Score> scoreList);

}
