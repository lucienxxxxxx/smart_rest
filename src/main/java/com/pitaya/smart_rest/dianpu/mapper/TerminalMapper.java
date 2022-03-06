package com.pitaya.smart_rest.dianpu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dianpu.entity.Terminal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pitaya.smart_rest.dianpu.query.TerminalQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 终端或者取餐台 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-02-09
 */
public interface TerminalMapper extends BaseMapper<Terminal> {

    IPage<Terminal> selectModelPage(Page<Terminal> pages,@Param("terminalQuery") TerminalQuery terminalQuery);
}
