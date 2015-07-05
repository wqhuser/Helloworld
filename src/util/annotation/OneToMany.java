package util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import util.db.BaseVO;

@Retention(RetentionPolicy.RUNTIME)
public @interface OneToMany {
	public String foreginKey();
	public Class<? extends BaseVO> Type();
	public boolean immiLoad() default false;
}
