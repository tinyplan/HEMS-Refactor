package com.tinyplan.exam.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import com.tinyplan.exam.common.properties.QiniuProperties;
import com.tinyplan.exam.entity.po.News;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.entity.vo.NewsVO;
import com.tinyplan.exam.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("imageServiceImpl")
public class ImageServiceImpl implements ImageService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Resource(name = "qiniuProperties")
    private QiniuProperties qiniuProperties;

    @Resource(name = "uploadManager")
    private UploadManager uploadManager;

    @Resource(name = "bucketManager")
    private BucketManager bucketManager;

    @Resource(name = "qiniuAuth")
    private Auth auth;

    /**
     * 保存到本地
     *
     * @param localPath 本地目录的路径
     * @param filename  文件名
     * @param file      文件
     */
    @Override
    public void saveToLocal(String localPath, String filename, MultipartFile file) {
        File root = new File(localPath);
        // 根目录不存在
        if (!root.exists()) {
            if (!root.mkdirs()) {
                throw new BusinessException(ResultStatus.RES_FILE_UPLOAD_FAILED);
            }
        }
        // 获取文件
        File imageFile = new File(localPath + File.separator + filename);
        try {
            // 保存文件
            file.transferTo(imageFile);
            LOGGER.info("上传成功: " + imageFile.getAbsolutePath());
        } catch (IOException e) {
            // e.printStackTrace();
            LOGGER.error("上传失败: " + imageFile.getAbsolutePath());
            throw new BusinessException(ResultStatus.RES_FILE_UPLOAD_FAILED);
        }
    }

    /**
     * 删除本地文件
     *
     * @param localPath 本地路径
     * @param filename  文件名
     */
    @Override
    public void deleteLocal(String localPath, String filename) {
        this.deleteLocal(localPath + File.separator + filename);
    }

    /**
     * 删除本地文件
     *
     * @param filePath 文件全路径
     */
    private void deleteLocal(String filePath){
        this.deleteLocal(new File(filePath));
    }

    /**
     * 删除本地文件
     *
     * @param file 文件
     */
    @Override
    public void deleteLocal(File file) {
        // 若当前指向的不是文件或删除失败，抛异常
        if (!file.exists()) {
            LOGGER.error("要删除的文件不存在: " + file.getAbsolutePath());
            throw new BusinessException(ResultStatus.RES_FILE_DELETE_NOT_EXIST);
        }
        if (!file.isFile() || !file.delete()) {
            LOGGER.error("文件删除失败: " + file.getAbsolutePath());
            throw new BusinessException(ResultStatus.RES_FILE_DELETE_FAILED);
        }
        LOGGER.info("文件删除成功: " + file.getAbsolutePath());
    }


    /**
     * 上传文件到七牛云
     *
     * @param localPath    本地文件路径
     * @param filenameList 文件名列表
     */
    @Override
    public void uploadQiniuYun(String localPath, List<String> filenameList) {
        String upToken = auth.uploadToken(qiniuProperties.getBucket());
        // 记录上传成功的文件名，回滚时会以此为依据
        List<String> uploadFileList = new ArrayList<>();
        for (String filename : filenameList) {
            // 获取当前图片文件
            File file = new File(localPath + File.separator + filename);
            try {
                // 上传图片
                uploadManager.put(file, filename, upToken);
                // 上传成功，记录
                uploadFileList.add(filename);
            } catch (QiniuException e) {
                // 若有一张上传失败，回滚(删除本地所有缓存图片，删除已上传的文件)
                // 删除本地图片
                for (String localFileName : filenameList) {
                    this.deleteLocal(localPath, localFileName);
                }
                // 删除已上传文件
                this.deleteQiniuYun(uploadFileList);
                LOGGER.error(e.getMessage());
                throw new BusinessException(ResultStatus.RES_FILE_UPLOAD_FAILED);
            }
        }
        // 全部上传成功之后，删除缓存图片
        for (String localFileName : filenameList) {
            this.deleteLocal(localPath, localFileName);
        }
    }

    /**
     * 处理新闻信息中的图片信息
     *
     * @param newsList 原始新闻信息列表
     * @return 处理后的新闻信息
     */
    @Override
    public List<NewsVO> handleNewsImage(List<News> newsList) {
        List<NewsVO> result = new ArrayList<>(newsList.size());
        for (News news : newsList) {
            NewsVO newsVO = new NewsVO(news);
            bindCoverImageUrl(newsVO);
            bindContentImageUrl(newsVO, news);
            result.add(newsVO);
        }
        return result;
    }

    /**
     * 批量删除上传的文件
     *
     * @param uploadList 文件名
     */
    public void deleteQiniuYun(List<String> uploadList) {
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        String[] keys = new String[uploadList.size()];
        uploadList.toArray(keys);
        batchOperations.addDeleteOp(qiniuProperties.getBucket(), keys);
        try {
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatuses = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < batchStatuses.length; i++) {
                String key = uploadList.get(i);
                BatchStatus status = batchStatuses[i];
                if (status.code == 200) {
                    LOGGER.info(key + "处理状态: 删除成功");
                } else {
                    LOGGER.info(key + "处理状态: " + status.data.error);
                }
            }
        } catch (QiniuException e) {
            LOGGER.error("批量处理异常");
            throw new BusinessException(ResultStatus.RES_UNKNOWN_ERROR, e.getMessage());
        }
    }

    /**
     * 拼接新闻封面图片URL
     *
     * @param newsVO 待处理的新闻对象
     */
    private void bindCoverImageUrl(NewsVO newsVO){
        newsVO.setCoverImg(qiniuProperties.getDomain() + newsVO.getCoverImg());
    }

    /**
     * 拼接新闻内容图片URL
     *
     * @param newsVO 待处理的新闻对象
     * @param raw 从数据库中获取到的新闻信息
     */
    private void bindContentImageUrl(NewsVO newsVO, News raw){
        String contentImg = raw.getContentImg();
        if (contentImg == null || contentImg.length() == 0) {
            return;
        }
        List<String> contentImgList = newsVO.getContentImgList();
        String[] contentImages = contentImg.split("\\|");
        for (String image : contentImages) {
            contentImgList.add(qiniuProperties.getDomain() + image);
        }
    }
}
