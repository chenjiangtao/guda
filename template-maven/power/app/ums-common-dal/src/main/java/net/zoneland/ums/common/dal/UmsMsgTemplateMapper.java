package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate;
import net.zoneland.ums.common.util.annotation.Log;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface UmsMsgTemplateMapper {

	@Log(name = "短信模版维护", comments = "删除短信模版")
	@CacheEvict(value = "findByTempId", allEntries = true)
	int deleteByPrimaryKey(String id);

	@Log(name = "短信模版维护", comments = "添加短信模版")
	@CacheEvict(value = "findByTempId", allEntries = true)
	int insert(UmsMsgTemplate record);

	UmsMsgTemplate selectByPrimaryKey(String id);

	@Log(name = "短信模版维护", comments = "更新短信模版")
	@CacheEvict(value = "findByTempId", allEntries = true)
	int updateByPrimaryKey(UmsMsgTemplate record);

	@Cacheable(value = "findByTempId")
	UmsMsgTemplate findByTempId(String tempId);

	/**
	 * 根据查询条件获得个数。
	 * 
	 * @param map
	 * @return
	 */
	int getCountByRequirement(Map<String, Object> map);

	/**
	 * 分页查询短信模版
	 * 
	 * @param map
	 * @return
	 */
	List<UmsMsgTemplate> searchByPage(Map<String, Object> map);

	List<UmsMsgTemplate> searchByAppId(Map<String, Object> map);

	UmsMsgTemplate selectByTemplateId(String templateId);

	/**
	 * 通过appId删除与该应用相关的短信模版
	 * 
	 * @param appId
	 */
	int deleteByAppId(String appId);

	/**
	 * 通过subAppId删除与该子应用相关的短信模版
	 * 
	 * @param subAppId
	 */
	int deleteBySubAppId(String subAppId);

}