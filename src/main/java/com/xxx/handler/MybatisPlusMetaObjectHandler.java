package com.xxx.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xxx.common.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * mybatis plus 自动填充设置
 *
 * @author Mr.Xi
 * @date 2020/08/13
 */
@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入自动填充
     *
     * @param metaObject
     * @return void
     * @author Mr.Xi
     * @date 2020/08/13
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String[] names = metaObject.getGetterNames();
        if (attributes != null) {
            # 此处逻辑自己修改
            HttpServletRequest request = attributes.getRequest();
            String token = JwtTokenUtils.getToken(request);
            String username = StringUtils.isNotBlank(token) ? JwtTokenUtils.getIdStringFromToken(token) : null;
            if (names != null && names.length > 0) {
                for (String name : names) {
                    if ("createBy".equals(name)) {
                        this.setFieldValByName("createBy", username, metaObject);
                    } else if ("createTime".equals(name)) {
                        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
                    } else if ("updateBy".equals(name)) {
                        this.setFieldValByName("updateBy", username, metaObject);
                    } else if ("updateTime".equals(name)) {
                        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
                    }
                }
            } else {
                log.warn("mybatis-plus 自动更新 获取属性失败");
            }
        } else {
            log.info("任务调度默认值处理");
            for (String name : names) {
                if ("createTime".equals(name)) {
                    this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
                } else if ("updateTime".equals(name)) {
                    this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
                }
            }
        }
    }

    /**
     * 更新自动填充
     *
     * @param metaObject
     * @return void
     * @author Mr.Xi
     * @date 2020/08/13
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            # 此处逻辑自己修改
            String token = JwtTokenUtils.getToken(request);
            String username = StringUtils.isNotBlank(token) ? JwtTokenUtils.getIdStringFromToken(token) : null;
            if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(username)) {
                String[] names = metaObject.getGetterNames();
                if (names != null && names.length > 0) {
                    for (String name : names) {
                        if ("updateBy".equals(name)) {
                            this.setFieldValByName("updateBy", username, metaObject);
                        } else if ("updateTime".equals(name)) {
                            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
                        }
                    }
                } else {
                    log.warn("mybatis-plus 自动更新 获取属性失败");
                }
            }else{
                this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            }
        }else{
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
