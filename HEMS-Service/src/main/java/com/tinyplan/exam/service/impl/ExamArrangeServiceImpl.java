package com.tinyplan.exam.service.impl;

import com.tinyplan.exam.common.utils.PaginationUtil;
import com.tinyplan.exam.dao.*;
import com.tinyplan.exam.entity.po.*;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.type.EnrollStatus;
import com.tinyplan.exam.entity.pojo.type.ExamStatus;
import com.tinyplan.exam.entity.pojo.type.SiteStatus;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.entity.vo.PortalArrangeVO;
import com.tinyplan.exam.entity.vo.SystemArrangeVO;
import com.tinyplan.exam.service.DataInjectService;
import com.tinyplan.exam.service.ExamArrangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExamArrangeServiceImpl implements ExamArrangeService {

    @Resource(name = "dataInjectServiceImpl")
    private DataInjectService dataInjectService;

    @Resource(name = "enrollMapper")
    private EnrollMapper enrollMapper;

    @Resource(name = "invigilatorMapper")
    private InvigilatorMapper invigilatorMapper;

    @Resource(name = "candidateMapper")
    private CandidateMapper candidateMapper;

    @Resource(name = "siteMapper")
    private SiteMapper siteMapper;

    @Resource(name = "examDetailMapper")
    private ExamDetailMapper examDetailMapper;

    @Resource(name = "candidateArrangeMapper")
    private CandidateArrangeMapper candidateArrangeMapper;

    @Resource(name = "invigilatorArrangeMapper")
    private InvigilatorArrangeMapper invigilatorArrangeMapper;

    @Override
    @Transactional
    public void arrange(String examNo) {
        if (!invigilatorArrangeMapper.getExamSiteByNo(examNo).isEmpty()) {
            throw new BusinessException(ResultStatus.RES_UNKNOWN_ERROR, "请勿重复生成");
        }
        // 取出所有该门考试报名成功的考生
        List<Enroll> enrollList = enrollMapper.getEnrollByExamNoAndStatus(examNo, EnrollStatus.ENROLL_SUCCESS.getCode());
        // 取出所有可用的教师
        List<Invigilator> invigilatorList = invigilatorMapper.getInvigilatorByStatus(1);
        // 取出所有可用的场地
        List<Site> siteList = siteMapper.querySiteByStatus(SiteStatus.AVAILABLE.getCode());

        // 储存分配的信息
        List<InvigilatorArrange> invigilatorArrangeList = new ArrayList<>();
        List<CandidateArrange> candidateArrangeList = new ArrayList<>();
        // 记录当前分配的位置
        int invIndex = 0;
        int candidateIndex = 0;
        for (Site site : siteList) {
            // 教师分配(顺序分配)
            InvigilatorArrange invArrange1 =
                    new InvigilatorArrange(invigilatorList.get(invIndex).getInvigilatorId(), examNo, site.getSiteId());
            InvigilatorArrange invArrange2 =
                    new InvigilatorArrange(invigilatorList.get(invIndex + 1).getInvigilatorId(), examNo, site.getSiteId());
            invigilatorArrangeList.add(invArrange1);
            invigilatorArrangeList.add(invArrange2);
            // 学生分配
            int maxCapacity = site.getCapacity() - 2;
            // 初始化座位号
            int seat = 1;
            while (seat <= maxCapacity && candidateIndex < enrollList.size()) {
                Enroll enroll = enrollList.get(candidateIndex);

                CandidateArrange arrange = new CandidateArrange();
                arrange.setCandidateId(enroll.getCandidateId());
                // 将报考序号作为准考证号
                arrange.setCandidateNo(enroll.getEnrollId());
                arrange.setExamNo(examNo);
                arrange.setSiteId(site.getSiteId());
                arrange.setSeat(seat);

                candidateArrangeList.add(arrange);
                seat++;
                candidateIndex++;
            }
            invIndex += 2;
            // 学生已分配完, 退出循环
            if (candidateIndex >= enrollList.size()) {
                break;
            }
            // 学生未分配完, 教师不够分配, 异常
            if (invIndex >= invigilatorList.size()) {
                throw new BusinessException(ResultStatus.RES_ARRANGE_NEED_MORE_INV);
            }
        }
        // 将信息插入表中
        if (invigilatorArrangeList.isEmpty() || candidateArrangeList.isEmpty()) {
            throw new BusinessException(ResultStatus.RES_UNKNOWN_ERROR, "分配出错");
        }
        candidateArrangeMapper.insertArranges(candidateArrangeList);
        invigilatorArrangeMapper.insertArranges(invigilatorArrangeList);
    }

    @Override
    public Pagination<SystemArrangeVO> getArrangeInfo(String key) {
        List<CandidateArrange> arrangeList = candidateArrangeMapper.queryArrangeByCandidateNoOrRealName(key);
        List<SystemArrangeVO> result = new ArrayList<>();
        arrangeList.forEach(arrange -> {
            ExamDetail examDetail = examDetailMapper.getExamDetailByNo(arrange.getExamNo());
            if (ExamStatus.ARRANGING.getCode().equals(examDetail.getStatus())) {
                result.add(dataInjectService.injectSystemArrangeVO(
                        candidateMapper.getCandidateDetail(arrange.getCandidateId()),
                        arrange,
                        examDetail,
                        siteMapper.querySiteById(arrange.getSiteId())));
            }
        });
        Pagination<SystemArrangeVO> pagination = new Pagination<>();
        pagination.setTotal(result.size());
        pagination.setTableData(PaginationUtil.getLogicPagination(result, 5));
        return pagination;
    }

    @Override
    public List<String> getExamArrangeSite(String examName) {
        // 查找对应在分配中的考试
        List<ExamDetail> examDetailList = examDetailMapper.getExamDetailByCondition(examName, null, ExamStatus.ARRANGING.getCode());
        if (examDetailList.isEmpty()) {
            return new ArrayList<>();
        }
        ExamDetail detail = examDetailList.get(0);
        List<String> siteList = invigilatorArrangeMapper.getExamSiteByNo(detail.getExamNo());
        List<String> result = new ArrayList<>();
        siteList.forEach(site -> {
            result.add(siteMapper.querySiteById(site).getRoom());
        });
        return result;
    }

    @Override
    public Map<String, Object> getExamArrangeInfo(String examName, String siteName) {
        Map<String, Object> resultMap = new HashMap<>();
        List<ExamDetail> examDetailList = examDetailMapper.getExamDetailByCondition(examName, null, ExamStatus.ARRANGING.getCode());
        if (examDetailList.isEmpty()) {
            resultMap.put("total", 0);
            resultMap.put("invigilatorList", new ArrayList<>());
            resultMap.put("tableData", new ArrayList<>());
            return resultMap;
        }

        ExamDetail detail = examDetailList.get(0);
        Site site = siteMapper.getSiteByRoom(siteName);
        List<CandidateArrange> arrangeVOList =
                candidateArrangeMapper.queryArrangeByExamNoAndSite(detail.getExamNo(), site.getSiteId());
        List<SystemArrangeVO> result = new ArrayList<>();
        arrangeVOList.forEach(arrange -> {
            result.add(dataInjectService.injectSystemArrangeVO(
                    candidateMapper.getCandidateDetail(arrange.getCandidateId()), arrange, detail, site));
        });

        List<InvigilatorArrange> invArrangeList = invigilatorArrangeMapper.getArrange(detail.getExamNo(), site.getSiteId());
        List<String> invNameList = new ArrayList<>();
        invArrangeList.forEach(arrange -> {
            invNameList.add(invigilatorMapper.getInvigilator(arrange.getInvigilatorId()).getRealName());
        });

        resultMap.put("total", result.size());
        resultMap.put("invigilatorList", invNameList);
        resultMap.put("tableData", PaginationUtil.getLogicPagination(result, 5));

        return resultMap;
    }

    @Override
    @Transactional
    public void updateCandidateSite(String candidateNo, String siteName) {
        CandidateArrange arrange = candidateArrangeMapper.getArrangeByCandidateNo(candidateNo);
        Site newSite = siteMapper.getSiteByRoom(siteName);
        if (newSite.getSiteId().equals(arrange.getSiteId())) {
            throw new BusinessException(ResultStatus.RES_UNKNOWN_ERROR, "不能修改成当前考点");
        }
        Integer count = candidateArrangeMapper.getSiteCount(arrange.getExamNo(), newSite.getSiteId());
        if (count + 1 > newSite.getCapacity()) {
            throw new BusinessException(ResultStatus.RES_UNKNOWN_ERROR, "新考点容量不足");
        }
        // 更新信息
        candidateArrangeMapper.updateSiteAndSeat(candidateNo, newSite.getSiteId(), count + 1);
    }

    @Override
    public List<PortalArrangeVO> getArrangeForPortal(String realName, String idCard) {
        // TODO 检验身份证号
        CandidateDetail candidateDetail = candidateMapper.getCandidateDetailByIdCard(idCard);
        if (!candidateDetail.getRealName().equals(realName)) {
            throw new BusinessException(ResultStatus.RES_UNKNOWN_ERROR, "身份证或姓名错误");
        }
        List<CandidateArrange> arrangeList = candidateArrangeMapper.queryArrangeByCandidateNoOrRealName(realName);
        List<PortalArrangeVO> result = new ArrayList<>();
        arrangeList.forEach(arrange -> {
            result.add(dataInjectService.injectPortalArrangeVO(
                    candidateDetail, arrange, examDetailMapper.getExamDetailByNo(arrange.getExamNo()),
                    siteMapper.querySiteById(arrange.getSiteId())));
        });
        return result;
    }
}
