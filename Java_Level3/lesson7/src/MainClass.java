import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.	Создать класс, который может выполнять «тесты».
 *  В качестве тестов выступают классы с наборами методов, снабженных аннотациями @Test.
 *  Для этого у них должен быть статический метод start(), которому в качестве параметра передается объект типа Class или имя класса.
 *  Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, если он присутствует.
 *  Далее запускаются методы с аннотациями @Test, а по завершении всех тестов – метод с аннотацией @AfterSuite.
 *
 *  К каждому тесту необходимо добавить приоритеты (int-числа от 1 до 10),
 *  в соответствии с которыми будет выбираться порядок их выполнения.
 *  Если приоритет одинаковый, то порядок не имеет значения.
 *  Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре.
 *  Если это не так – необходимо бросить RuntimeException при запуске «тестирования».
 */

public class MainClass {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        start(TestClass.class);
    }

    public static void start(Class clazz)  {

        Method[] methods = clazz.getDeclaredMethods();
        int bsCount = 0;
        int asCount = 0;
        List<Method> test = new ArrayList<>();


        for (Method method : methods) {
            String name = method.getDeclaredAnnotations()[0].annotationType().getSimpleName();
            if (name.equals("BeforeSuite")){
                bsCount++;
                if(bsCount > 1) throw new RuntimeException("Уже есть такая аннотация");
            }else if (name.equals("AfterSuite")){
                asCount++;
                if(asCount > 1) throw  new RuntimeException("Уже есть такая аннотация");
            } else if (name.equals("Test")){
                test.add(method);
            }

        }

        for (Method m : test) {
            try {
                m.invoke(clazz.newInstance(), null);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException   e) {
                e.printStackTrace();

            }
        }
    }


}

