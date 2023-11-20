package com.chen.campus.service;

import com.chen.campus.dto.DormitoryDto;
import com.chen.campus.entity.Dormitory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2023-11-19
 */
public interface IDormitoryService extends IService<Dormitory> {

    public Integer chargeFee(Long id ,double money);

    public Boolean updateDormitory(DormitoryDto dto);
}
