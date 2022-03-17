package com.pitaya.smart_rest.activity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.activity.entity.MemberActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pitaya.smart_rest.activity.model.MemberActivityModel;
import com.pitaya.smart_rest.activity.query.MemberActivityQuery;

/**
 * <p>
 * 会员活动表 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-03-13
 */
public interface MemberActivityMapper extends BaseMapper<MemberActivity> {

    IPage<MemberActivityModel> selectModelPage(Page<MemberActivityModel> page, MemberActivityQuery query);
}
