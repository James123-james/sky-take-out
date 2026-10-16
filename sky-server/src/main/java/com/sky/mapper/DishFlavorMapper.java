package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 插入口味
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);
    @Delete("delete from dish_flavor where dish_id=#{dishid}")
    void deleteById(Long dishid);

    /**
     * 根据id删除
     * @param dishIds
     */
    void deleteByIds(List<Long>dishIds);
    @Select("select * from dish_flavor where dish_id=#{dishId}")
    List<DishFlavor> getById(Long dishId);
}
