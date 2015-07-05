package util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import util.db.BaseVO;

@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToMany {
	public String tableName();
	public String foreignKey();//所属类的对应外键字段
	public String dependKey();//该Collection依赖的字段
	public String key();//Collection中的元素对应的字段
	public Class<? extends BaseVO> type();
	public boolean immiLoad() default false;
}
