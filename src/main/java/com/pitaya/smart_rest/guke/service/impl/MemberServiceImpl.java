package com.pitaya.smart_rest.guke.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.mapper.MemberMapper;
import com.pitaya.smart_rest.guke.query.MemberQuery;
import com.pitaya.smart_rest.guke.service.IMemberService;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MemberSerivceImpl
 * @author: lucine
 * @Description TODO
 * @date 2022/3/27 22:03
 * @Version 1.0版本
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Resource
    private MemberMapper memberMapper;

    /**
     * 按照时间查询每天新增的用户
     *
     * @param userId
     * @param memberQuery
     * @return
     */
    @Override
    public Map<String, Object> queryNewMemberOrderByDate(Integer userId, MemberQuery memberQuery) {
        return null;
    }

    /**
     * 批量上传，信息完善
     *
     * @param list
     * @return
     */
    public List<Map<String, Object>> addInfoByExcel(JSONArray list) {
        List<Map<String, Object>> resultInfo = new ArrayList<>();
        //判断该电话号码是否存在，存在-》修改真实姓名 不存在->添加到返回列表
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = list.getJSONObject(i);
            String trueName = jsonObject.getString("trueName");
            String phone = jsonObject.getString("phone");
            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
            memberQueryWrapper.eq("mobile", phone);
            Member member = memberMapper.selectOne(memberQueryWrapper);
            if (member == null) {
                resultInfo.add(jsonObject);
                continue;
            } else {
                member.setTrueName(trueName);
                AssertUtil.isTrue(memberMapper.updateById(member) != 1, "上传失败");
            }

        }

        return resultInfo;
    }

    @Override
    /**
     * 模板批量充值
     */
    public Map<String, List> chargeByExcel(JSONArray list) {
        Map<String,List> map=checkParam(list);
        return map;
    }

    private Map<String, List> checkParam(JSONArray list) {
        Map<String,List> resultMap = new HashMap<>();
        List errorList = new ArrayList<>();

        //参数校验
        for (int i = 0; i < list.size(); i++) {
            JSONObject chargeObject = list.getJSONObject(i);
            String trueName = chargeObject.getString("trueName");
            String phone = chargeObject.getString("phone");
            String virtualAcc = chargeObject.getString("virtualAcc");
            String giftAcc = chargeObject.getString("giftAcc");
            String allowanceAcc = chargeObject.getString("allowanceAcc");
            String cashAcc = chargeObject.getString("cashAcc");
            String chargeAcc = chargeObject.getString("chargeAcc");

            //手机号码为空
            if (StringUtils.isBlank(phone)){
                chargeObject.put("errorReason","手机号码不能为空");
                errorList.add(chargeObject);
                list.remove(chargeObject);
                continue;
            }else {

                if (!StringUtils.isNumeric(virtualAcc)){
                    chargeObject.put("errorReason","金额必须为数字");
                    errorList.add(chargeObject);
                    list.remove(chargeObject);
                    continue;
                }
                if (!StringUtils.isNumeric(giftAcc)){
                    chargeObject.put("errorReason","金额必须为数字");
                    errorList.add(chargeObject);
                    list.remove(chargeObject);
                    continue;
                }
                if (!StringUtils.isNumeric(allowanceAcc)){
                    chargeObject.put("errorReason","金额必须为数字");
                    errorList.add(chargeObject);
                    list.remove(chargeObject);
                    continue;
                }
                if (!StringUtils.isNumeric(cashAcc)){
                    chargeObject.put("errorReason","金额必须为数字");
                    errorList.add(chargeObject);
                    list.remove(chargeObject);
                    continue;
                }
                if (!StringUtils.isNumeric(chargeAcc)){
                    chargeObject.put("errorReason","金额必须为数字");
                    errorList.add(chargeObject);
                    list.remove(chargeObject);
                    continue;
                }

            }

            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
            memberQueryWrapper.eq("mobile",phone);
            Member member = memberMapper.selectOne(memberQueryWrapper);
            if (member == null) {
                chargeObject.put("errorReason","该用户不存在");
                errorList.add(chargeObject);
                list.remove(chargeObject);
                continue;
            }

        }

        resultMap.put("errorList",errorList);
        resultMap.put("chargeList",list);
        return resultMap;
    }


}
