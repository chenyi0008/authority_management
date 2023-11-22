package com.chen.campus.service;

import com.chen.campus.dto.ApplyStuDTO;
import com.chen.campus.entity.Apply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2023-11-22
 */
public interface IApplyService extends IService<Apply> {

    public List<ApplyStuDTO> getApplyAndStu();

    public Boolean examineAndApprove(Long id, String status);

    public List<Apply> getByStuId(Long stuId);

}
