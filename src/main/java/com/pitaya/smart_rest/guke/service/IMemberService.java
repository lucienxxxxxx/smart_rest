package com.pitaya.smart_rest.guke.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.query.MemberQuery;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IMemberService
 * @author: lucine
 * @Description TODO
 * @date 2022/3/27 22:03
 * @Version 1.0版本
 */
public interface IMemberService extends IService<Member> {

    Map<String, Object> queryNewMemberOrderByDate(Integer userId, MemberQuery memberQuery);

    List<Map<String, Object>> addInfoByExcel(JSONArray list);

    Map<String, List> chargeByExcel(JSONArray list);
}
