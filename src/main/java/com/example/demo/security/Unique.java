//package com.example.demo.security;
//
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import java.lang.annotation.Documented;
//import java.lang.annotation.Retention;
//import java.lang.annotation.Target;
//
//import static java.lang.annotation.ElementType.*;
//import static java.lang.annotation.RetentionPolicy.RUNTIME;
//
//@Target({ METHOD, FIELD, ANNOTATION_TYPE, TYPE})
//@Retention(RUNTIME)
//@Constraint(validatedBy = UniqueValidator.class)
//@Documented
//public @interface Unique {
//    String message() default "{com.example.demo.security.Unique.message}";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//    Class<? extends FieldValueExists> service();
//    String serviceQualifier() default "";
//    String fieldName();
//}