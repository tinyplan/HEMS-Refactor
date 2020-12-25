package com.tinyplan.exam.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.tinyplan.exam.common.utils.*;
import com.tinyplan.exam.dao.InvigilatorMapper;
import com.tinyplan.exam.entity.dto.InvigilatorDTO;
import com.tinyplan.exam.entity.po.Invigilator;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.pojo.type.ObjectType;
import com.tinyplan.exam.entity.pojo.type.UserType;
import com.tinyplan.exam.entity.vo.InvigilatorVO;
import com.tinyplan.exam.entity.vo.Pagination;
import com.tinyplan.exam.service.ImageService;
import com.tinyplan.exam.service.InvigilatorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class InvigilatorServiceImpl implements InvigilatorService {

    @Resource(name = "imageServiceImpl")
    private ImageService imageService;

    @Resource(name = "invigilatorMapper")
    private InvigilatorMapper invigilatorMapper;

    @Override
    public Pagination<InvigilatorVO> getAllInvigilator(Integer pageSize) {
        // 获取全部可用的教师
        List<Invigilator> invigilatorList = invigilatorMapper.getInvigilatorByStatus(1);
        List<InvigilatorVO> result = new ArrayList<>(invigilatorList.size());
        invigilatorList.forEach(invigilator -> result.add(new InvigilatorVO(invigilator)));

        Pagination<InvigilatorVO> pagination = new Pagination<>();
        pagination.setTotal(result.size());
        pagination.setTableData(PaginationUtil.getLogicPagination(result, pageSize));
        return pagination;
    }

    @Override
    @Transactional
    public void addInvigilator(Invigilator invigilator) {
        if (!PhoneCheckUtil.isPhoneLegal(invigilator.getContact())) {
            throw new BusinessException(ResultStatus.RES_ERROR_CONTACT);
        }
        if (!(invigilatorMapper.getInvigilatorCount(invigilator.getContact()) == 0)) {
            throw new BusinessException(ResultStatus.RES_EXISTED_INV);
        }
        // 剩余信息的设置
        String prefix = PrefixUtil.getUserPrefix(UserType.INVIGILATOR);
        String date = DateUtil.format(new Date(), "yyyyMMdd");
        Integer maxId = CommonUtil.checkMaxId(invigilatorMapper.getMaxId()) + 1;
        invigilator.setInvigilatorId(PrefixUtil.generateId(prefix, date, String.valueOf(maxId)));

        invigilator.setAccountName("invigilator" + maxId);
        invigilator.setPassword(SecureUtil.md5("123456"));
        invigilator.setRoleId("r1004");
        invigilator.setStatus(1);
        invigilatorMapper.insertInvigilator(invigilator);
    }

    @Override
    @Transactional
    public void uploadInvigilator(HttpServletRequest request, MultipartFile excelFile) {
        String filePath = null;
        try {
            String filename = UUID.randomUUID() + "_" + excelFile.getOriginalFilename();
            filePath = imageService.saveToLocal(imageService.getFileTmpPath(request, ObjectType.EXCEL), filename, excelFile);
            List<InvigilatorDTO> excelInfoList = ExcelUtil.readInvigilatorExcel(new File(filePath));
            // 这里只选择信息正确的插入(暂时无法回避用户重复的问题, 同一文件重复上传也会导致这种问题)
            List<Invigilator> insertList = new ArrayList<>(excelInfoList.size());
            String prefix = PrefixUtil.getUserPrefix(UserType.INVIGILATOR);
            String date = DateUtil.format(new Date(), "yyyyMMdd");
            AtomicReference<Integer> maxId = new AtomicReference<>(CommonUtil.checkMaxId(invigilatorMapper.getMaxId()));
            excelInfoList.forEach(dto -> {
                if (PhoneCheckUtil.isPhoneLegal(dto.getContact()) &&
                        invigilatorMapper.getInvigilatorCount(dto.getContact()) == 0) {
                    maxId.getAndSet(maxId.get() + 1);
                    String id = PrefixUtil.generateId(prefix, date, String.valueOf(maxId));
                    Invigilator inv = new Invigilator();
                    inv.setInvigilatorId(id);
                    inv.setAccountName("invigilator" + maxId);
                    inv.setRealName(dto.getRealName());
                    inv.setPassword(SecureUtil.md5("123456"));
                    inv.setContact(dto.getContact());
                    inv.setRoleId("r1004");
                    inv.setStatus(1);
                    insertList.add(inv);
                }
            });
            if (!insertList.isEmpty()) {
                invigilatorMapper.insertInvigilators(insertList);
            }
        }finally {
            if (filePath != null && !"".equals(filePath)){
                imageService.deleteLocal(new File(filePath));
            }
        }
    }

    @Override
    @Transactional
    public void updateInvigilator(Invigilator invigilator) {
        if (!PhoneCheckUtil.isPhoneLegal(invigilator.getContact())) {
            throw new BusinessException(ResultStatus.RES_ERROR_CONTACT);
        }
        if (!(invigilatorMapper.getInvigilatorCountExceptSelf(invigilator.getContact(), invigilator.getInvigilatorId()) == 0)) {
            throw new BusinessException(ResultStatus.RES_EXISTED_INV);
        }
        invigilatorMapper.updateInvigilator(invigilator);
    }

    @Override
    @Transactional
    public void deleteInvigilator(String invigilatorId) {
        invigilatorMapper.updateInvigilatorStatus(invigilatorId, -1);
    }
}
