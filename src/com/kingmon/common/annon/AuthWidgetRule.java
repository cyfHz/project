package com.kingmon.common.annon;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public  @interface AuthWidgetRule {
	String value();
	String desc();
	String refTable() default "";
	String operateType() default "";
	String crudType() default "";
}
