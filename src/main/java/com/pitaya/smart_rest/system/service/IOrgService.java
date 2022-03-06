package com.pitaya.smart_rest.system.service;

import com.pitaya.smart_rest.system.entity.Org;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.system.query.OrgQuery;

import java.util.Map;

/**
 * <p>
 * 机构会员 可以有上级机构 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-01-26
 */
public interface IOrgService extends IService<Org> {

    Map<String, Object> queryAllOrgByParams(OrgQuery orgQuery);

    void add(Org org);


    void update(Org org);

    void del(Integer orgId);
}
