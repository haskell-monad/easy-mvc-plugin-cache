package easy.framework.plugin.cache.model;

/**
 * @author limengyu
 * @create 2017/11/2
 */
public class Duration {
    /**
     * 存入缓存的时间
     * 单位：毫秒
     */
    private long currentTime;
    /**
     * 多久之后过期
     * 单位：毫秒
     */
    private long millis;
    public Duration(long millis){
        this.currentTime = System.currentTimeMillis();
        this.millis = millis;
    }
    public long expireTime(){
        return currentTime + millis;
    }
    public boolean isExpire(){
        return System.currentTimeMillis() > expireTime() ? true : false;
    }
}
