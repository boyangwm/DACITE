package DAL;

import java.lang.annotation.*;

@Target(ElementType.LOCAL_VARIABLE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBAnnotation {
	String[]   variable  ()  default {};
	String[]   table     ()  default {};
	String[]   attribute ()  default {};
	boolean[]  isSource  ()  default {};
}
