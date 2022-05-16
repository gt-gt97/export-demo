package com.juliajiang.exportdemo.common;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Lists;
import com.juliajiang.exportdemo.util.DozerUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author jiangfan.julia@gmail.com
 * @description 转换器(Do,Po,Vo转换)
 * @since 2021/2/18 3:09 下午
 */
public class BaseAssembler {

    /**
     * toDTO
     * DO转化为VO
     * @param o
     * @param vClass
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> V toDTO(T o, Class<V> vClass) {
        Mapper mapper = DozerBeanMapperBuilder.create().build();
        V vo = mapper.map(o, vClass);
        return vo;
    }

    /**
     * toDTOList
     * 一组DO转化为一组VO
     * @param oList
     * @param vClass
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> List<V> toDTOList(List<T> oList, Class<V> vClass) {
        if (CollectionUtils.isEmpty(oList)) {
            return Lists.newArrayList();
        }
        Mapper mapper = DozerBeanMapperBuilder.create().build();
        List<V> voList = DozerUtil.mapList(mapper, oList, vClass);
        return voList;
    }
}
