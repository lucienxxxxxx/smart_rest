package com.pitaya.smart_rest.guke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.dianpu.query.StaffQuery;
import com.pitaya.smart_rest.guke.entity.Member;

import java.util.Map;

/**
 * @ClassName IStaffService
 * @author: lucine
 * @Description TODO
 * @date 2022/3/5 10:35
 * @Version 1.0版本
 */
public interface IStaffService extends IService<Member> {
    void switchStatus(Integer id, Integer state);

    void del(Integer id);

    void addOrUpdate(Member member, Integer flag, Integer userId);

    Map<String, Object> queryList(Integer userId, StaffQuery staffQuery);
}
