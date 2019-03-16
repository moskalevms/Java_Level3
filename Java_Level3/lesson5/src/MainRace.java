import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


/**
 * Все участники должны стартовать одновременно, несмотря на разное время подготовки.
 * В тоннель не может одновременно заехать больше половины участников (условность).
 * Когда все завершат гонку, нужно выдать объявление об окончании.
 */

public class MainRace {
    public static final int CARS_COUNT = 4;
    public static final int HALF_PARTICIPANTS = CARS_COUNT / 2;

    public static void main(String[] args) {

        CyclicBarrier cbCars = new CyclicBarrier(CARS_COUNT+1);
        CountDownLatch cdl = new CountDownLatch(CARS_COUNT);

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];


        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cbCars, cdl);
        }


        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            cdl.await();//пока все не подготовятся, стоим на этой строке
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            cbCars.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        try {
            cbCars.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }


    }

}
