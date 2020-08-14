# mybatis-plus generator 生成代码
# 简介
使用 velocity （ps:看文档现学的，写的稀烂）实现自定义配置，代码拿过去直接可以使用；详细看代码吧，里面我都写了注释 走过路过不要错过，点点star。在此拜谢、
1. 自定义模板
2. 重写批量插入
3. 生成字段注释常亮
4. 字段填充拦截
5. 自定义类型转换
# 注释常亮示例
   log_type TINYINT(1)    COMMENT '日志类型{admin: 1, 管理后台操作日志; business: 0, 业务日志}' 
# 依赖

```xml
		<mybatis-plus.version>3.3.0</mybatis-plus.version>
		<velocity-engine-core.version>2.1</velocity-engine-core.version>
		<!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-generator -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>${mybatis-plus.version}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.velocity/velocity-engine-core -->
        <dependency>
            <groupId>org.apache.velocity</groupId>
            <artifactId>velocity-engine-core</artifactId>
            <version>${velocity-engine-core.version}</version>
        </dependency>
```
# 代码

```java
package com.xxx.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成配置
 *
 * @author Mr.Xi
 * @date 2020/01/13
 */
public class MysqlGenerator {


    /**
     * 主函数
     *
     * @param args
     * @return void
     * @author Mr.Xi
     * @date 2020/8/13
     */
    public static void main(String[] args) {
        // 指定生成的bean的数据库表名
        String[] tables1 = {
        };
        String[] tables2 = {
                "sys_log"
        };

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/generator");
        gc.setAuthor("Mr.Xi");
        gc.setOpen(false);
        // 实体属性 Swagger2 注解
        gc.setSwagger2(true);
        // service 命名方式
        gc.setServiceName("%sService");
        // service impl 命名方式
        gc.setServiceImplName("%sServiceImpl");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setIdType(IdType.AUTO);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.1.110:3306/xxx?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("username");
        dsc.setPassword("pwd");
        dsc.setTypeConvert(new MySqlTypeConvertCustom());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.xxx");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("dao");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        // 公共字段填充功能
        TableFill createField = new TableFill("create_time", FieldFill.INSERT);
        TableFill modifiedField = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        TableFill createByField = new TableFill("create_by", FieldFill.INSERT);
        TableFill modifyByField = new TableFill("update_by", FieldFill.INSERT_UPDATE);
        tableFillList.add(createField);
        tableFillList.add(modifiedField);
        tableFillList.add(createByField);
        tableFillList.add(modifyByField);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        // 设置逻辑删除键
        strategy.setLogicDeleteFieldName("is_del");
        // 自增id
        strategy.setInclude(tables2);
        // uuid
        //strategy.setInclude(tables1);
        strategy.setTableFillList(tableFillList);
        /*  strategy.setSuperEntityColumns("id");*/
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}


/**
 * 自定义类型转换
 */
class MySqlTypeConvertCustom extends MySqlTypeConvert implements ITypeConvert {
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("tinyint(1)")) {
            return DbColumnType.INTEGER;
        }
        return super.processTypeConvert(globalConfig, fieldType);
    }
}


```
# 模板
模板放到 resources/templates 目录下

## entity.java.vm

```java
package ${package.Entity};

#foreach($pkg in ${table.importPackages})
import ${pkg};
#end
#if(${swagger2})
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
#end
#if(${entityLombokModel})
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
#end

/**
 * <p>
 * $!{table.comment}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
#if(${entityLombokModel})
@Data
  #if(${superEntityClass})
@EqualsAndHashCode(callSuper = true)
  #else
@EqualsAndHashCode(callSuper = false)
  #end
@Accessors(chain = true)
#end
#if(${table.convert})
@TableName("${table.name}")
#end
#if(${swagger2})
@ApiModel(value="${entity}对象", description="$!{table.comment}")
#end
#if(${superEntityClass})
public class ${entity} extends ${superEntityClass}#if(${activeRecord})<${entity}>#end {
#elseif(${activeRecord})
public class ${entity} extends Model<${entity}> {
#else
public class ${entity} implements Serializable {
#end

#if(${entitySerialVersionUID})
    private static final long serialVersionUID=1L;
#end
#set($arr = [])
## ----------  BEGIN 字段循环遍历  ----------
#foreach($field in ${table.fields})

#if(${field.keyFlag})
#set($keyPropertyName=${field.propertyName})
#end
#if("$!field.comment" != "")
  #if(${swagger2})
    @ApiModelProperty(value = "${field.comment}")
  #else
    /**
     * ${field.comment}
     */
  #end
#end
#if(${field.keyFlag})
## 主键
  #if(${field.keyIdentityFlag})
    @TableId(value = "${field.name}", type = IdType.AUTO)
  #elseif(!$null.isNull(${idType}) && "$!idType" != "")
    @TableId(value = "${field.name}", type = IdType.${idType})
  #elseif(${field.convert})
    @TableId("${field.name}")
  #end
## 普通字段
#elseif(${field.fill})
## -----   存在字段填充设置   -----
  #if(${field.convert})
    @TableField(value = "${field.name}", fill = FieldFill.${field.fill})
  #else
    @TableField(fill = FieldFill.${field.fill})
  #end
#elseif(${field.convert})
    @TableField("${field.name}")
#end
## 乐观锁注解
#if(${versionFieldName}==${field.name})
    @Version
#end
## 逻辑删除注解
#if(${logicDeleteFieldName}==${field.name})
    @TableLogic
#end
    private ${field.propertyType} ${field.propertyName};
  #if(${field.comment.length()} > 0)
    #if($!field.comment.indexOf("{") != -1 && $!field.comment.indexOf("}") != -1 && $!field.comment.indexOf(";") != -1 && $!field.comment.indexOf(",") != -1)
      #set($object_str = $!field.comment.substring($!field.comment.indexOf("{") + 1,$!field.comment.indexOf("}")))
      ## ----------  添加到数组  组合信息 ----------
      #set($tmp = $arr.add($object_str+"~~"+${field.propertyName}+"~"+$field.propertyType+"~"+$field.name))
    #end
  #end
#end
## ----------  生成常量  ----------
#foreach($comment_element_space in $!{arr})
    ## ----------  分离常量 与 配置  ----------
    #set($comment_element_join = $!{comment_element_space.replace(" ","").split("~~")})
    ## ----------  常量  ----------
    #set($comment_element_list = $!comment_element_join.get(0))
    ## ----------  配置  ----------
    #set($comment_element_last = $!comment_element_join.get(1))
    ## ----------  拆分配置  ----------
    #set($comment_element_arr = $!{comment_element_last.split("~")})
    #set($propertyName = $!comment_element_arr.get(0))
    #set($propertyType = $!comment_element_arr.get(1))
    #set($fieldName = $!comment_element_arr.get(2))
    ## ----------  拆分常量  ----------
    #set($element_list = $!comment_element_list.split(";"))
    #foreach($comment_element in $!{element_list})
        #set($firstName = $!comment_element.substring(0,$!comment_element.indexOf(":")))
        #set($firstIndex = $!comment_element.indexOf(":") + 1)
        #set($lastIndex = $!comment_element.indexOf(":") + 2)
        #set($meanIndex = $!comment_element.indexOf(",") + 1)
        ## 常量值
        #set($lastName = $!comment_element.substring($firstIndex,$lastIndex))
        ## 常亮注释
        #set($meanName = $!comment_element.substring($meanIndex,$!comment_element.length()))

    /**
     * ${propertyName}：${meanName}
     */
    public final static ${propertyType} $!fieldName.toUpperCase()_$!firstName.toUpperCase() = ${lastName};
    #end
#end
## ----------  END 字段循环遍历  ----------

#if(!${entityLombokModel})
#foreach($field in ${table.fields})
  #if(${field.propertyType.equals("boolean")})
    #set($getprefix="is")
  #else
    #set($getprefix="get")
  #end

    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

  #if(${entityBuilderModel})
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
  #else
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
  #end
        this.${field.propertyName} = ${field.propertyName};
  #if(${entityBuilderModel})
        return this;
  #end
    }
#end
## --foreach end---
#end
## --end of #if(!${entityLombokModel})--

#if(${entityColumnConstant})
  #foreach($field in ${table.fields})
    public static final String ${field.name.toUpperCase()} = "${field.name}";

  #end
#end
#if(${activeRecord})
    @Override
    protected Serializable pkVal() {
  #if(${keyPropertyName})
        return this.${keyPropertyName};
  #else
        return null;
  #end
    }

#end
#if(!${entityLombokModel})
    @Override
    public String toString() {
        return "${entity}{" +
  #foreach($field in ${table.fields})
    #if($!{foreach.index}==0)
        "${field.propertyName}=" + ${field.propertyName} +
    #else
        ", ${field.propertyName}=" + ${field.propertyName} +
    #end
  #end
        "}";
    }
#end
}

```

## mapper.java.vm

```java
package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * $!{table.comment} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
#if(${kotlin})
interface ${table.mapperName} : ${superMapperClass}<${entity}>
#else
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    /**
     * 批量添加
     * @param list 数组
     * @return
     * @author Mr.Xi
     * @date ${date}
     */
    int insertBatch(@Param("list") List<${entity}> list);
}
#end

```

##  mapper.xml.vm

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

#if(${enableCache})
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

#end
#if(${baseResultMap})
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
#foreach($field in ${table.fields})
#if(${field.keyFlag})##生成主键排在第一位
        <id column="${field.name}" property="${field.propertyName}" />
#end
#end
#foreach($field in ${table.commonFields})##生成公共字段
    <result column="${field.name}" property="${field.propertyName}" />
#end
#foreach($field in ${table.fields})
#if(!${field.keyFlag})##生成普通字段
        <result column="${field.name}" property="${field.propertyName}" />
#end
#end
    </resultMap>

#end
#if(${baseColumnList})
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
#foreach($field in ${table.commonFields})
        12312312${field.name},
#end
        ${table.fieldNames}
    </sql>

    <!-- 批量添加 -->
    <insert id="insertBatch" parameterType="${package.Entity}.${entity}">
        INSERT INTO  ${table.name}
        (
        #foreach($field in ${table.commonFields})
            `${field.name}`,
        #end
        ${table.fieldNames}
        )
        VALUES
        <foreach collection="list" item="item" index="index" separator=",">
            (
            #foreach($field in ${table.commonFields})
            #{item.${field.name}},
            #end
            #set($index = 1)
            #set($length = $table.fields.size())
            #foreach($field in ${table.fields})
                #if($index != $length)
            #{item.${field.propertyName}},
                #else
            #{item.${field.propertyName}}
                #end
                #set($index = $index + 1)
            #end
            )
        </foreach>
    </insert>
#end
</mapper>

```

## service.java.vm

```java
package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import java.util.List;

/**
 * <p>
 * $!{table.comment} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
#if(${kotlin})
interface ${table.serviceName} : ${superServiceClass}<${entity}>
#else
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
     * 批量添加
     * @param list
     * @return
     * @author Mr.Xi
     * @date 2020/8/14 下午8:35
     */
    int addList(List<${entity}> list);

}
#end

```
## serviceImpl.java.vm

```java
package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * $!{table.comment} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
#if(${kotlin})
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
#else
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    /**
     * 批量添加
     * @param list
     * @return
     * @author Mr.Xi
     * @date 2020/8/14 下午8:35
     */
    @Override
    public int addList(List<${entity}> list){
        return baseMapper.insertBatch(list);
    }
}
#end

```

## 填充拦截器 MybatisPlusMetaObjectHandler

```java
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

```
