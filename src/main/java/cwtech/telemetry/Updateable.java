package cwtech.telemetry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
public @interface Updateable {
    public String key();
    public String defaultString() default "";
    public double defaultNumber() default 0.0;
    public boolean defaultBoolean() default false;
    public Level level() default Level.Debug;
    public boolean whenDisabled() default false;
}
