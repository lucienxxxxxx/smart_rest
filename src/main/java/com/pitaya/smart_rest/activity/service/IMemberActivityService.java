package com.pitaya.smart_rest.activity.service;

import com.pitaya.smart_rest.activity.entity.MemberActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.activity.query.MemberActivityQuery;

import java.util.Map;

/**
 * <p>
 * 会员活动表 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-03-13
 */
public interface IMemberActivityService extends IService<MemberActivity> {

    void verify(Integer id);

    Map<String, Object> queryList(Integer userId, MemberActivityQuery query);
}
