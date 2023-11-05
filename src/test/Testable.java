package test;

import java.lang.reflect.Method;

public class Testable {
    static public void test() {

        Class<Testable> testsClass = Testable.class;
        for(Method method: testsClass.getDeclaredMethods()){
            TestToRun annotation = method.getDeclaredAnnotation(TestToRun.class);
            if(annotation != null){
                try{
                    System.out.println(method.getName() + ": ");
                    method.invoke(testsClass.getDeclaredConstructors()[0].newInstance());
                }
                catch (Throwable ex){
                    System.out.println(ex.getCause());
                }
            }
        }
    }


}
