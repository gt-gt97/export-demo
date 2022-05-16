package com.juliajiang.exportdemo.asyn;

import com.juliajiang.exportdemo.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jiangfan.julia@gmail.com
 * @description 生产者
 * @since 2021/2/10 5:25 下午
 */
@Component
@Slf4j
public class EventProducer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean fireEvent(EventModel model) {
        try{
            // 对象转化为json类型的字符串。
            String json = GsonUtil.toJsonString(model);
            String key = model.getKey();
            log.info("key:{},json:{}", key, json);
            //用key作为键，对象的json类型字符串作为值，右插入的方式，添加进redis
            stringRedisTemplate.boundListOps(key).rightPush(json);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
