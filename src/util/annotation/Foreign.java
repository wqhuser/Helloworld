package util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Foreign {
	public String foreginKey();
	public boolean immiLoad() default false;
}
