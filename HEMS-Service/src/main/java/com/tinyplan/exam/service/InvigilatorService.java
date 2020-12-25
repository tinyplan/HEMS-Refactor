package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.Invigilator;
import com.tinyplan.exam.entity.vo.InvigilatorVO;
import com.tinyplan.exam.entity.vo.Pagination;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface InvigilatorService {

    Pagination<InvigilatorVO> getAllInvigilator(Integer pageSize);

    void uploadInvigilator(HttpServletRequest request, MultipartFile excelFile);

    void addInvigilator(Invigilator invigilator);

    void deleteInvigilator(String invigilatorId);

    void updateInvigilator(Invigilator invigilator);

}
