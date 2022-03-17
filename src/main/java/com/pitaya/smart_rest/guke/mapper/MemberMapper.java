package com.pitaya.smart_rest.guke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.activity.model.ChargeModel;
import com.pitaya.smart_rest.base.BaseQuery;
import com.pitaya.smart_rest.guke.entity.Member;

/**
 * @ClassName MemberMapper
 * @author: lucine
 * @Description TODO
 * @date 2022/3/5 10:31
 * @Version 1.0版本
 */
public interface MemberMapper extends BaseMapper<Member> {
    IPage<ChargeModel> selectModelPage(Page<ChargeModel> page, BaseQuery query);
}
