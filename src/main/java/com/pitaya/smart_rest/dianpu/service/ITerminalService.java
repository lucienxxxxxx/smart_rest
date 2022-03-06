package com.pitaya.smart_rest.dianpu.service;

import com.pitaya.smart_rest.dianpu.entity.Terminal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.dianpu.query.TerminalQuery;

import java.util.Map;

/**
 * <p>
 * 终端或者取餐台 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-02-09
 */
public interface ITerminalService extends IService<Terminal> {

    Map<String, Object> queryList(Integer userId, TerminalQuery terminalQuery);

    void switchStatus(Integer terminalId,Integer state);

    void del(Integer terminalId);

    void addOrUpdate(Terminal terminal, Integer flag, Integer userId);

    void setFood(Integer foodId,Integer terminalId);
}
