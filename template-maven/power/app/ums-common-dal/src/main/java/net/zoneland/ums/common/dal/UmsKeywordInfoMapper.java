package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsKeywordInfo;
import net.zoneland.ums.common.util.annotation.Log;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface UmsKeywordInfoMapper {

    @Log(name = "关键字维护", comments = "删除关键字")
    @CacheEvict(value = "keyWordCache", allEntries = true)
    int deleteByPrimaryKey(String id);

    @Log(name = "关键字维护", comments = "增加关键字")
    @CacheEvict(value = "keyWordCache", allEntries = true)
    int insert(UmsKeywordInfo record);

    @Log(name = "关键字维护", comments = "增加关键字")
    @CacheEvict(value = "keyWordCache", allEntries = true)
    int insertSelective(UmsKeywordInfo record);

    @Log(name = "关键字维护", comments = "查询关键字")
    UmsKeywordInfo selectByPrimaryKey(String id);

    /**
     * 分页查询关键字
     *
     * @param params
     * @return
     */
    @Log(name = "关键字维护", comments = "分页查询关键字")
    List<UmsKeywordInfo> searchKeywordByPage(Map<String, Object> params);

    /**
     * 查询分页记录条数
     *
     * @param params
     * @return
     */
    @Log(name = "关键字维护", comments = "分页查询关键字")
    int searchAllNum(Map<String, Object> params);

    /**
     * 查看是否有重名的关键字
     *
     * @param keyWord
     * @return
     */
    @Log(name = "关键字维护", comments = "查询重复关键字")
    List<UmsKeywordInfo> getKeyword(UmsKeywordInfo umsKeywordInfo);

    /**
     * 根据应用和关键字判断数据库中该应用是否已经有相同关键字
     *
     * @param umsKeywordInfo
     * @return
     */
    @Log(name = "关键字维护", comments = "查询重复关键字")
    List<UmsKeywordInfo> getKeywordInfo(UmsKeywordInfo umsKeywordInfo);

    /**
     * 根据APPID查询关键字
     * @param appId
     * @return
     */
    @Log(name = "关键字维护", comments = "查询关键字")
    @Cacheable(value = "keyWordCache")
    List<String> selectByAppId(String appId);

    /**
     * 根据AppId删除关键字
     *
     * @param appId
     * @return
     */
    @Log(name = "关键字维护", comments = "删除关键字")
    @CacheEvict(value = "keyWordCache", allEntries = true)
    int deleteKeywordByAppId(String appId);

    /**
     * 删除关键字
     *
     * @param appId
     * @return
     */
    @Log(name = "关键字维护", comments = "删除关键字")
    @CacheEvict(value = "keyWordCache", allEntries = true)
    int deleteByKeyWord(String keyWord);

}