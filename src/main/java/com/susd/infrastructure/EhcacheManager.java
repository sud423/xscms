package com.susd.infrastructure;

import java.io.InputStream;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public final class EhcacheManager {

	private static final String path = "ehcache-default.xml";

	private CacheManager cacheManager;

	private static EhcacheManager manager;

	public EhcacheManager(String path) {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
		cacheManager = CacheManager.create(inputStream);
	}

	/**
	 * 创建ehcache管理 默认配置文件ehcache-default.xml
	 * 
	 * @return
	 */
	public static EhcacheManager getInstance() {

		if (manager == null) {
			manager = new EhcacheManager(path);
		}
		return manager;
	}

	/**
	 * 创建ehcache管理
	 * 
	 * @param configPath ehcache配置文件
	 * @return
	 */
	public static EhcacheManager getInstance(String configPath) {

		if (manager == null) {
			manager = new EhcacheManager(configPath);
		}
		return manager;
	}

	/**
	 * 获取缓存
	 * 
	 * @param config
	 * @param name
	 * @return
	 */
	public Cache getCache(String name) {
		return cacheManager.getCache(name);

	}

	/**
	 * 设置缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public void put(String cacheName, String key, Object value) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}

	/**
	 * 获取缓存值
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public Object get(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	}

	/**
	 * 移除缓存
	 * 
	 * @param cacheName
	 * @param key
	 */
	public void remove(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		cache.remove(key);
	}
}
