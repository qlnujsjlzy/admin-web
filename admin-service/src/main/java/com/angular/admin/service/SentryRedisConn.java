package com.angular.admin.service;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SentryRedisConn {

    String CHARSET = "UTF-8";

    public void setex(String key, int seconds, String value);

    public void set(String key, String value);

    public Set<String> keys(String key);

    public String get(String key);

    public Boolean hset(String key, String field, String value);


    public String hget(String key, String field);


    public void hmset(String s, Map<String, String> map);


    public Long hdel(String key, String... fields);


    public Long hlen(String key);


    public Set<String> hkeys(String key);


    public List<String> hvals(String key);


    public Map<String, String> hgetAll(String key);


    public Long rpush(String s, String... as);


    public Long lpush(String s, String... as);


    public Long llen(String s);


    public List<String> lrange(String s, long l, long l1);


    public String ltrim(String s, long l, long l1);


    public String lindex(String s, long l);


    public String lset(String s, long l, String s1);


    public Long lrem(String s, long l, String s1);


    public String lpop(String s);


    public String rpop(String s);


    public Long sadd(String s, String... as);


    public Set<String> smembers(String s);


    public Long srem(String s, String... as);


    public String spop(String s);


    public Long scard(String s);


    public Boolean sismember(String s, String s1);


    public String srandmember(String s);


    public List<String> srandmember(String s, int i);


    public Long strlen(String s);


    public Long zadd(String s, double d, String s1);


    public Long zadd(String s, Map<String, Double> map);


    public Set<String> zrange(String s, long l, long l1);


    public Long zrem(String s, String... as);


    public Double zincrby(String s, double d, String s1);


    public Long zrank(String s, String s1);


    public Long zrevrank(String s, String s1);


    public Set<String> zrevrange(String s, long l, long l1);


    public Set<Tuple> zrangeWithScores(String s, long l, long l1);


    public Set<Tuple> zrevrangeWithScores(String s, long l, long l1);


    public Long zcard(String s);


    public Double zscore(String s, String s1);


    public List<String> sort(String s);


    public List<String> sort(String s, SortingParams sortingparams);


    public Long zcount(String s, double d, double d1);


    public Long zcount(String s, String s1, String s2);


    public Set<String> zrangeByScore(String s, double d, double d1);


    public Set<String> zrangeByScore(String s, String s1, String s2);


    public Set<String> zrevrangeByScore(String s, double d, double d1);


    public Set<String> zrangeByScore(String s, double d, double d1, int i, int j);


    public Set<String> zrevrangeByScore(String s, String s1, String s2);


    public Set<String> zrangeByScore(String s, String s1, String s2, int i, int j);


    public Set<String> zrevrangeByScore(String s, double d, double d1, int i, int j);


    public Set<Tuple> zrangeByScoreWithScores(String s, double d, double d1);


    public Set<Tuple> zrevrangeByScoreWithScores(String s, double d, double d1);


    public Set<Tuple> zrangeByScoreWithScores(String s, double d, double d1, int i, int j);


    public Set<String> zrevrangeByScore(String s, String s1, String s2, int i, int j);


    public Set<Tuple> zrangeByScoreWithScores(String s, String s1, String s2);


    public Set<Tuple> zrevrangeByScoreWithScores(String s, String s1, String s2);


    public Set<Tuple> zrangeByScoreWithScores(String s, String s1, String s2, int i, int j);


    public Set<Tuple> zrevrangeByScoreWithScores(String s, double d, double d1, int i, int j);


    public Set<Tuple> zrevrangeByScoreWithScores(String s, String s1, String s2, int i, int j);


    public Long zremrangeByRank(String s, long l, long l1);


    public Long zremrangeByScore(String s, double d, double d1);


    public Long zremrangeByScore(String s, String s1, String s2);


    public Long zlexcount(String s, String s1, String s2);


    public Set<String> zrangeByLex(String s, String s1, String s2);


    public Set<String> zrangeByLex(String s, String s1, String s2, int i, int j);


    public Long zremrangeByLex(String s, String s1, String s2);


    public Long linsert(String s, LIST_POSITION list_position, String s1, String s2);


    public Long lpushx(String s, String... as);


    public Long rpushx(String s, String... as);


    public List<String> blpop(String s);


    public List<String> blpop(int i, String s);


    public List<String> brpop(String s);


    public List<String> brpop(int i, String s);


    public Long del(String keys);


    public String echo(String s);


    public Long move(String s, int i);


    public Long bitcount(String s);


    public Long bitcount(String s, long l, long l1);

    /*
        public ScanResult<Map.Entry<String, String>> hscan(String s, int i) ;


        public ScanResult<String> sscan(String s, int i) ;


        public ScanResult<Tuple> zscan(String s, int i) ;


        public ScanResult<Map.Entry<String, String>> hscan(String s, String s1) ;


        public ScanResult<String> sscan(String s, String s1) ;


        public ScanResult<Tuple> zscan(String s, String s1) ;

    */
    public Long pfadd(String s, String... as);


    public long pfcount(String s);


    public List<String> hmget(String keys, String[] fields);

    public boolean expire(String key, int seconds);

    public void rename(String oldkey, String newkey);

    /**
     * @param key
     * @param number
     * @return
     * @Description:原子递增计数器 这里用来防止黄牛
     * @author yuzj7@lenovo.com
     * @date 2015年6月11日 下午4:47:44
     */
    public long incrBy(String key, long number);


    long ttl(String key);
}
