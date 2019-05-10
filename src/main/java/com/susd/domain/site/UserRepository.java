package com.susd.domain.site;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserRepository {

	/**
	 * 新增驾驶员/客户
	 * @param user
	 * @return 返回受影响行
	 */
	int add(User user);
	
	/**
	 * 根据关键字查询客户列表 TODO sql查询语句需要修改，统计记录数不对
	 * @param keyword 关键字
	 * @return 返回获取结果集
	 */
	List<User> findClientByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	/**
	 * 根据关键字查询驾驶员列表 TODO sql查询语句需要修改，统计记录数不对
	 * @param keyword 关键字
	 * @return 返回获取结果集
	 */
	List<User> findDriverByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	/**
	 * 修改驾驶员/客户
	 * @param user
	 * @return 返回受影响行
	 */
	int edit(User user);
	
	/**
	 * 根据主键Id查询 驾驶员/客户 信息
	 * @param userId
	 * @return
	 */
	User findUserById(int userId);

	/**
	 * 查找驾驶员
	 * @return
	 */
	List<User> findDriver(@Param("q") String q, @Param("tenantId") int tenantId);
	
	/**
	 * 获取客户
	 * @param q
	 * @return
	 */
	List<User> findClient(@Param("q") String q, @Param("tenantId") int tenantId);
}
