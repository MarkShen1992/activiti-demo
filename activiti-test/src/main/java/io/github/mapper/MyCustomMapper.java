package io.github.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 自定义SQL
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/31 16:41
 */
public interface MyCustomMapper {

    @Select("SELECT * FROM ACT_RU_TASK")
    List<Map<String, Object>> findAll();
}
