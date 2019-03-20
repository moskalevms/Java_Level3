import org.junit.Assert;
import org.junit.Test;

public class ArraysTest {

    @Test
    public void test_firstArr1(){
        JUnitTest firstArr = new JUnitTest();
        int[] result = firstArr.arrTestForTestOne(new int[] {1, 2, 4, 4, 2, 3, 4, 1, 7});
        Assert.assertArrayEquals(new int[] {1,7}, result);
    }


    @Test(expected = RuntimeException.class)
    public void test_firstArr2(){
        JUnitTest firstArr = new JUnitTest();
        int[] result = firstArr.arrTestForTestOne(new int[] {1, 2, 3, 3, 2, 3, 6, 1, 8});
        Assert.assertArrayEquals(new int[] {1,7}, result); //правильно ли это? Не совсем понятно что указывать в ожидаемом результате, если есть expected
    }

}
