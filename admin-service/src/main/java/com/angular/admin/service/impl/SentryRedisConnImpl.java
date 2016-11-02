package com.angular.admin.service.impl;


import com.angular.admin.dao.cache.redis.RedisObjectDao;
import com.angular.admin.service.SentryRedisConn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 哨兵adaptor
 * @author wangrq1 
 *
 */
@Service
public class SentryRedisConnImpl implements SentryRedisConn {
	
	private static final Logger logger = LogManager.getLogger(SentryRedisConnImpl.class.getName());

	@Autowired
	private RedisObjectDao redisObjectDao;
	
	private int selectedDB;
	
	public SentryRedisConnImpl() {
	}
	public SentryRedisConnImpl(int db) {
		this.selectedDB = db;
	}

    @Override
    public Set<String> keys(String key) {
        return redisObjectDao.keys(key);
    }

    @Override
	public void setex(String key, int seconds, String value) {
		redisObjectDao.setString(key, value, seconds);
	}
	
	@Override
	public void set(String key, String value) {
		redisObjectDao.setString(key, value);
	}
	@Override
	public String get(String key) {
		return redisObjectDao.getString(key);
	}
	@Override
	public Boolean hset(String key, String field, String value) {
		if(1 ==redisObjectDao.hashSet(key, field, value)){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
		
	}
	@Override
	public String hget(String key, String field) {
         return redisObjectDao.hashGet(key, field);
    }
	@Override
	public void hmset(String key, Map<String, String> tuple) {
		redisObjectDao.hashMultipleSet(key, tuple);
	}
	@Override
	public List<String> hmget(String key, String fields[]) {
		return redisObjectDao.hashMultipleGet(key, fields);
	}
	@Override
	public Long hdel(String key, String... fields) {
		return redisObjectDao.hashDel(key, fields);
	}
	@Override
	public Long hlen(String key) {
		return redisObjectDao.hlen(key);
	}
	@Override
	public Set<String> hkeys(String key) {
		return redisObjectDao.hkeys(key);
	}
	@Override
	public List<String> hvals(String key) {
		return redisObjectDao.hvals(key);
	}
	@Override
	public Map<String, String> hgetAll(String key) {
		return redisObjectDao.hashGetAll(key);
		
	}
	@Override
	public boolean expire(String key, int seconds) {
		if(Long.valueOf(1L).equals(redisObjectDao.expire(key, seconds))){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public Long rpush(String s, String... as) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long lpush(String s, String... as) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long llen(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> lrange(String s, long l, long l1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String ltrim(String s, long l, long l1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String lindex(String s, long l) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String lset(String s, long l, String s1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long lrem(String s, long l, String s1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String lpop(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String rpop(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long sadd(String s, String... as) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> smembers(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long srem(String s, String... as) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String spop(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long scard(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean sismember(String s, String s1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String srandmember(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> srandmember(String s, int i) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long strlen(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zadd(String s, double d, String s1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zadd(String s, Map<String, Double> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrange(String s, long l, long l1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zrem(String s, String... as) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Double zincrby(String s, double d, String s1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zrank(String s, String s1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zrevrank(String s, String s1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrevrange(String s, long l, long l1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Tuple> zrangeWithScores(String s, long l, long l1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Tuple> zrevrangeWithScores(String s, long l, long l1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zcard(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Double zscore(String s, String s1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> sort(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> sort(String s, SortingParams sortingparams) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zcount(String s, double d, double d1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zcount(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrangeByScore(String s, double d, double d1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrangeByScore(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrevrangeByScore(String s, double d, double d1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrangeByScore(String s, double d, double d1, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrevrangeByScore(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrangeByScore(String s, String s1, String s2, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrevrangeByScore(String s, double d, double d1, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String s, double d, double d1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String s, double d, double d1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String s, double d, double d1, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrevrangeByScore(String s, String s1, String s2, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String s, String s1, String s2, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String s, double d, double d1, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String s, String s1, String s2, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zremrangeByRank(String s, long l, long l1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zremrangeByScore(String s, double d, double d1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zremrangeByScore(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zlexcount(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrangeByLex(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> zrangeByLex(String s, String s1, String s2, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long zremrangeByLex(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long linsert(String s, LIST_POSITION list_position, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long lpushx(String s, String... as) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long rpushx(String s, String... as) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> blpop(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> blpop(int i, String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> brpop(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> brpop(int i, String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long del(String keys) {
		return redisObjectDao.delKey(keys);
	}
	@Override
	public String echo(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long move(String s, int i) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long bitcount(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long bitcount(String s, long l, long l1) {
		// TODO Auto-generated method stub
		return null;
	}
//	@Override
//	public ScanResult<Entry<String, String>> hscan(String s, int i) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public ScanResult<String> sscan(String s, int i) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public ScanResult<Tuple> zscan(String s, int i) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public ScanResult<Entry<String, String>> hscan(String s, String s1) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public ScanResult<String> sscan(String s, String s1) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public ScanResult<Tuple> zscan(String s, String s1) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	@Override
	public Long pfadd(String s, String... as) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long pfcount(String s) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void rename(String oldkey, String newkey) {
		
	}
	/*
	 * (非 Javadoc)
	* <p>Title: incrBy</p>
	* <p>Description: redis 原子递增，利用redis 原子递增来防止黄牛并发请求 </p>
	* @param key
	* @param number
	* @return
	* @see com.redis.lenovo.dao.redisEncapsulation.MyRedisConn#incrBy(java.lang.String, long)
	 */
	@Override
	public long incrBy(String key, long number) {
		return redisObjectDao.incrBy(key, (int)number);
	}

    @Override
    public long ttl(String key) {
        return redisObjectDao.ttl(key);
    }

}
