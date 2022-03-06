package com.pitaya.smart_rest.guke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dingdan.model.OrderModel;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.query.MemberQuery;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName OfflineMemberMapper
 * @author: lucine
 * @Description TODO
 * @date 2022/3/3 17:20
 * @Version 1.0版本
 */
public interface OfflineMemberMapper extends BaseMapper<Member> {
    IPage<Member> selectMemberPage(Page<Member> page, MemberQuery memberQuery);
}
