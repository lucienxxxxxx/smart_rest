package com.pitaya.smart_rest.activity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.activity.entity.ChargeActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pitaya.smart_rest.activity.model.ChargeActivityModel;
import com.pitaya.smart_rest.activity.query.ChargeActivityQuery;

/**
 * <p>
 * 充值活动 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-03-13
 */
public interface ChargeActivityMapper extends BaseMapper<ChargeActivity> {
    IPage<ChargeActivityModel> selectOrderModelPage(Page<ChargeActivityModel> page, ChargeActivityQuery query);
}
