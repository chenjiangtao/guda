package net.zoneland.ums.common.dal;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsTelDescribe;

import org.springframework.cache.annotation.Cacheable;

public interface UmsTelDescribeMapper {

    @Cacheable(value = "selectByTel")
    List<UmsTelDescribe> selectByTel(String tel);
}