package com.pitaya.smart_rest.guke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.query.MemberQuery;

import java.util.Map;

/**
 * @ClassName IOnlineMemberService
 * @author: lucine
 * @Description TODO
 * @date 2022/3/3 17:16
 * @Version 1.0版本
 */
public interface IOnlineMemberService  extends IService<Member> {
    Map<String, Object> queryList(Integer userId, MemberQuery memberQuery);

    void del(Integer id);

    void switchStatus(Integer id, Integer state);

    void addOrUpdate(Member member, Integer flag, Integer userId);
}
