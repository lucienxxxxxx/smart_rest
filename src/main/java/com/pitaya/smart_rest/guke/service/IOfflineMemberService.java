package com.pitaya.smart_rest.guke.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.dianpu.entity.RfidUser;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.query.MemberQuery;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IOfflineMemberService
 * @author: lucine
 * @Description TODO
 * @date 2022/3/3 17:19
 * @Version 1.0版本
 */
public interface IOfflineMemberService extends IService<Member> {
    void del(Integer id);

    void switchStatus(Integer id, Integer state);

    Map<String, Object> queryList(Integer userId, MemberQuery memberQuery);

    void addOrUpdate(Member member, RfidUser rfidUer, Integer flag, Integer userId);

    List<Map<String, Object>> addBatch(JSONArray list,Integer userId);

    List<Map<String, Object>> chargeByExcel(JSONArray list,Integer userId);
}
