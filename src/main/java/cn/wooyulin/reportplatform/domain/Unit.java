package cn.wooyulin.reportplatform.domain;

import lombok.Data;
import javax.persistence.*;
@Data//生成gettersettter方法
@Entity(name = "Unit")//名字
@Table(name = "unit")//对应数据库表中名字
public class Unit {
    @Id//数据库中主键标识
    @GeneratedValue//自增标识
    private int id;
    @Column(name = "name")//name属性为表的字段别名
    private String name;
    @Column(name = "count_type")
    private String countType;

}
