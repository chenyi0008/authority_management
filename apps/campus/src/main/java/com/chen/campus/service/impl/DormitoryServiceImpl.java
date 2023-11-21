package com.chen.campus.service.impl;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.chen.campus.dto.DormitoryDTO;
import com.chen.campus.entity.Dormitory;
import com.chen.campus.dao.DormitoryDao;
import com.chen.campus.service.IDormitoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2023-11-19
 */
@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryDao, Dormitory> implements IDormitoryService {

    @Autowired
    DormitoryDao dormitoryDao;

    @Override
    public Integer chargeFee(Long id,double money) {
        return dormitoryDao.chargeFee(id, money);
    }

    @Override
    public Boolean updateDormitory(DormitoryDTO dto) {
        LambdaUpdateChainWrapper<Dormitory> lcw = new LambdaUpdateChainWrapper<>(dormitoryDao);
        boolean flag = lcw.eq(Dormitory::getId, dto.getId())
                .set(Dormitory::getAddress, dto.getAddress())
                .set(Dormitory::getRoomNumber, dto.getRoomNumber())
                .update();
        return flag;
    }

    @Override
    public Integer bindStuToDormitory(Long stuId, Long dormitoryId) {
        Integer integer = dormitoryDao.bingStuToDormitory(stuId, dormitoryId);
        return integer;
    }


}
