package Annotation;
/* AccessAnnotations.java */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


//@Target({ElementType.LOCAL_VARIABLE})
//@Retention(RetentionPolicy.RUNTIME)
public class DBAnnotation {
	public static void annoate(String variable, String table, String column, boolean isSource){};
	
}



//@Target(ElementType.LOCAL_VARIABLE)
//@Retention(RetentionPolicy.RUNTIME)
//public @interface DBAnnotation {
//     String[]  variable () default {};
//     String[] table () default {};
//     String[] attribute () default {};
//     boolean[] isSource () default {};
//}
