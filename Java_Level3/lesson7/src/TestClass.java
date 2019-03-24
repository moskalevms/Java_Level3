

public class TestClass {

    @BeforeSuit
    public void start(){
        System.out.println("start");
    }

    @Test(priority = 10)
    public void test2(){
        System.out.println("test2");
    }

    @Test(priority = 3)
    public void test3(){
        System.out.println("test3");
    }

    @Test(priority = 4)
    public void test4(){
        System.out.println("test4");
    }

    @Test(priority = 7)
    public void test5(){
        System.out.println("test5");
    }

    @AfterSuite
    public void stop(){
        System.out.println("stop");
    }

}
