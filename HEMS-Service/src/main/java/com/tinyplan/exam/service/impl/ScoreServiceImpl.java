package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.common.utils.ExcelUtil;
import com.tinyplan.exam.common.utils.JwtUtil;
import com.tinyplan.exam.common.utils.PaginationUtil;
import com.tinyplan.exam.common.utils.type.StatusUtil;
import com.tinyplan.exam.dao.CandidateMapper;
import com.tinyplan.exam.dao.EnrollMapper;
import com.tinyplan.exam.dao.ExamDetailMapper;
import com.tinyplan.exam.dao.ScoreMapper;
import com.tinyplan.exam.entity.dto.ScoreDTO;
import com.tinyplan.exam.entity.po.Enroll;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.Score;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.JwtDataLoad;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.type.EnrollStatus;
import com.tinyplan.exam.entity.pojo.type.ExamStatus;
import com.tinyplan.exam.entity.pojo.type.ObjectType;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.PortalScoreInfoVO;
import com.tinyplan.exam.entity.vo.ScoreVO;
import com.tinyplan.exam.service.DataInjectService;
import com.tinyplan.exam.service.ImageService;
import com.tinyplan.exam.service.ScoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource(name = "imageServiceImpl")
    private ImageService imageService;

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

    @Resource(name = "enrollMapper")
    private EnrollMapper enrollMapper;

    @Resource(name = "scoreMapper")
    private ScoreMapper scoreMapper;

    @Resource(name = "examDetailMapper")
    private ExamDetailMapper examDetailMapper;

    @Resource(name = "candidateMapper")
    private CandidateMapper candidateMapper;

    @Override
    @Transactional
    public List<ScoreVO> uploadScore(HttpServletRequest request, MultipartFile excelFile) {
        List<ScoreDTO> dtoList;
        String examNo;
        ExamDetail examDetail;
        String filePath = null;
        try {
            String filename = UUID.randomUUID() + "_" + excelFile.getOriginalFilename();
            filePath = imageService.saveToLocal(imageService.getFileTmpPath(request, ObjectType.EXCEL), filename, excelFile);
            dtoList = ExcelUtil.readScoreExcel(new File(filePath));
            List<Score> newScoreList = new ArrayList<>(dtoList.size());
            examNo = dtoList.get(0).getExamNo();
            // 一张表对应一场考试, 查询一次即可
            examDetail = examDetailMapper.getExamDetailByNo(examNo);
            for (ScoreDTO dto : dtoList) {
                // 获取报名信息(准考证号即报名序号)
                Enroll enroll = enrollMapper.getEnrollById(dto.getCandidateNo());
                newScoreList.add(new Score(dto, enroll, examDetail));
            }
            if (newScoreList.size() == 0) {
                throw new BusinessException(ResultStatus.RES_UNKNOWN_ERROR, "成绩表格为空");
            }
            // 插入数据
            scoreMapper.insertScore(newScoreList);
            // 更新报名状态
            enrollMapper.updateEnrollStatusByScore(newScoreList, EnrollStatus.FINISH_EXAM.getCode());
        } finally {
            if (filePath != null && !"".equals(filePath)) {
                imageService.deleteLocal(new File(filePath));
            }
        }
        List<ScoreVO> result = new ArrayList<>(dtoList.size());
        for (Score score : scoreMapper.getScoreByExamNo(examNo)) {
            ScoreVO scoreVO = new ScoreVO(score, examDetail, candidateMapper.getCandidateDetail(score.getCandidateId()));
            scoreVO.setExamName(StatusUtil.getExamLevel(score.getLevel()).getDescription() + scoreVO.getExamName());
            result.add(scoreVO);
        }
        return result;
    }

    @Override
    public Pagination<PortalScoreInfoVO> getScoreForPortal(String token, String candidateNo, Integer pageSize) {
        JwtDataLoad load = new JwtDataLoad(JwtUtil.verify(token));
        List<Score> scoreList = scoreMapper.getScoreByCandidateIdAndNo(load.getUserId(), candidateNo);
        List<PortalScoreInfoVO> result = new ArrayList<>(scoreList.size());
        for (Score score : scoreList) {
            ExamDetail examDetail = examDetailMapper.getExamDetailByNo(score.getExamNo());
            // 当考试状态为成绩发布时, 才能显示
            if (examDetail.getStatus().equals(ExamStatus.SCORE_PUBLISH.getCode())) {
                result.add(dataInjectService.injectPortalScoreInfoVO(score, examDetail));
            }
        }
        Pagination<PortalScoreInfoVO> pagination = new Pagination<>();
        pagination.setTableData(PaginationUtil.getLogicPagination(result, pageSize));
        pagination.setTotal(pagination.getTableData().size());
        return pagination;
    }
}
