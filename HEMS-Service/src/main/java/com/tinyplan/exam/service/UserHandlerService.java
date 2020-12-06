package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.User;
import com.tinyplan.exam.entity.vo.DetailVO;

/**
 * 此接口将不同用户的相同功能区分开
 */
public interface UserHandlerService {

    /**
     * 根据用户类型, 获取用户对象
     *
     * @param username 用户名(用户ID 或 账户名)
     * @return 用户基础对象
     */
    User getUser(String username);

    /**
     * 根据用户类型, 获取详细信息
     *      调用这个方法之前，需要先调用getUser()方法取得user对象, 否则取得的信息可能不全
     * @param user 用户对象
     * @return 详细信息
     */
    DetailVO getUserDetail(User user);

    /**
     * 把从redis中取出的数据进行格式转换
     *
     * @param rawData 原始数据
     * @return 转换后的类
     */
    DetailVO convertDetail(Object rawData);

    void updatePassword(String userId, String newPassword);

}
