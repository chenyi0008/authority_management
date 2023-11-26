package com.chen.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.authority.biz.dao.auth.RoleAuthorityMapper;
import com.chen.authority.biz.service.auth.ResourceService;
import com.chen.authority.biz.service.auth.RoleAuthorityService;
import com.chen.authority.biz.service.auth.UserRoleService;
import com.chen.authority.dto.auth.RoleAuthoritySaveDTO;
import com.chen.authority.dto.auth.UserRoleSaveDTO;
import com.chen.authority.entity.auth.RoleAuthority;
import com.chen.authority.entity.auth.UserRole;
import com.chen.authority.enumeration.auth.AuthorizeType;
import com.chen.common.constant.CacheKey;
import com.chen.database.mybatis.conditions.Wraps;
import com.chen.utils.NumberHelper;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 业务实现类
 * 角色的资源
 */
@Slf4j
@Service
public class RoleAuthorityServiceImpl extends ServiceImpl<RoleAuthorityMapper, RoleAuthority> implements RoleAuthorityService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CacheChannel cache;

    @Override
    public boolean saveUserRole(UserRoleSaveDTO userRole) {
        userRoleService.remove(Wraps.<UserRole>lbQ().eq(UserRole::getRoleId, userRole.getRoleId()));
        List<UserRole> list = userRole.getUserIdList()
                .stream()
                .map((userId) -> UserRole.builder()
                        .userId(userId)
                        .roleId(userRole.getRoleId())
                        .build())
                .collect(Collectors.toList());
        userRoleService.saveBatch(list);

        //清除 用户拥有的资源列表
        userRole.getUserIdList().forEach((userId) -> {
            String key = CacheKey.buildKey(userId);
            cache.evict(CacheKey.USER_RESOURCE, key);
        });
        return true;
    }


    /**
     * 保存角色和资源/菜单的关联关系，支持同时关联多个资源和菜单。
     *
     * @param dto 包含角色ID、资源ID列表和菜单ID列表的数据传输对象
     * @return 保存成功返回 true，否则返回 false
     */
    @Override
    public boolean saveRoleAuthority(RoleAuthoritySaveDTO dto) {
        // 删除角色和资源的关联关系
        super.remove(Wraps.<RoleAuthority>lbQ().eq(RoleAuthority::getRoleId, dto.getRoleId()));

        List<RoleAuthority> list = new ArrayList<>();

        // 如果资源ID列表不为空，处理资源关联
        if (dto.getResourceIdList() != null && !dto.getResourceIdList().isEmpty()) {
            // 获取与资源关联的菜单ID列表
            List<Long> menuIdList = resourceService.findMenuIdByResourceId(dto.getResourceIdList());

            // 如果传入的菜单ID列表为空，将资源关联的菜单ID列表加入到传入的菜单ID列表中
            if (dto.getMenuIdList() == null || dto.getMenuIdList().isEmpty()) {
                dto.setMenuIdList(menuIdList);
            } else {
                // 否则，将资源关联的菜单ID列表添加到传入的菜单ID列表中
                dto.getMenuIdList().addAll(menuIdList);
            }

            // 保存授予的资源
            List<RoleAuthority> resourceList = new HashSet<>(dto.getResourceIdList())
                    .stream()
                    .map((resourceId) -> RoleAuthority.builder()
                            .authorityType(AuthorizeType.RESOURCE)
                            .authorityId(resourceId)
                            .roleId(dto.getRoleId())
                            .build())
                    .collect(Collectors.toList());
            list.addAll(resourceList);
        }

        // 如果菜单ID列表不为空，处理菜单关联
        if (dto.getMenuIdList() != null && !dto.getMenuIdList().isEmpty()) {
            // 保存授予的菜单
            List<RoleAuthority> menuList = new HashSet<>(dto.getMenuIdList())
                    .stream()
                    .map((menuId) -> RoleAuthority.builder()
                            .authorityType(AuthorizeType.MENU)
                            .authorityId(menuId)
                            .roleId(dto.getRoleId())
                            .build())
                    .collect(Collectors.toList());
            list.addAll(menuList);
        }

        // 批量保存关联关系
        super.saveBatch(list);

        // 清理与角色关联的用户的缓存
        List<Long> userIdList = userRoleService.listObjs(Wraps.<UserRole>lbQ().select(UserRole::getUserId).eq(UserRole::getRoleId, dto.getRoleId()),
                (userId) -> NumberHelper.longValueOf0(userId));
        userIdList.stream().collect(Collectors.toSet()).forEach((userId) -> {
            log.info("清理了 {} 的菜单/资源", userId);
            cache.evict(CacheKey.USER_RESOURCE, String.valueOf(userId));
        });

        return true;
    }

}