package com.juliajiang.exportdemo.student.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.juliajiang.exportdemo.ExportDemoApplication;
import com.juliajiang.exportdemo.asyn.EventModel;
import com.juliajiang.exportdemo.asyn.EventType;
import com.juliajiang.exportdemo.asyn.QueueNameEnum;
import com.juliajiang.exportdemo.asyn.export.ExportEnum;
import com.juliajiang.exportdemo.common.BaseAssembler;
import com.juliajiang.exportdemo.common.JsonResult;
import com.juliajiang.exportdemo.student.dto.StudentDTO;
import com.juliajiang.exportdemo.student.dto.StudentReq;
import com.juliajiang.exportdemo.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/3/7 3:51 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExportDemoApplication.class)
@Slf4j
public class StudentControllerTest {

    @Resource
    private StudentController studentController;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        StudentReq req = new StudentReq();
        req.setName("张三");
        req.setSize(10);
//        JsonResult query = studentController.export(req);
//        System.out.println(query);

        Map<String, Object> exts = new HashMap<>();
        exts.put("query", BaseAssembler.toDTO(req, StudentDTO.class));
        exts.put("exportCode", ExportEnum.STUDENT.getCode());

                // @Builder,初始化实例对象，实例化了一个事件模板对象。
        EventModel model = EventModel.builder()
                .type(EventType.EXPORT)
                .exts(exts)
                .key(QueueNameEnum.EXPORT.getCode())
                .build();
        try{
            // 对象转化为json类型的字符串。
            String json = GsonUtil.toJsonString(model);
            String key = model.getKey();
            log.info("key:{},json:{}", key, json);
            //用key作为键，对象的json类型字符串作为值，右插入的方式，添加进redis
//            stringRedisTemplate.boundListOps(key).rightPush(json);
            stringRedisTemplate.opsForList().rightPush(key,json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}