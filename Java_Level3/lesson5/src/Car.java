import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable{

    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
        //для того, чтобы все участники стартовали одновременно

    }
        private Race race;
        private int speed;
        private String name;

        CyclicBarrier cb;
        CountDownLatch cdl;

        public String getName () {
            return name;
        }
        public int getSpeed () {
            return speed;
        }
    public Car(Race race, int speed, CyclicBarrier cb, CountDownLatch cdl){
            this.race = race;
            this.speed = speed;
            CARS_COUNT++;
            this.name = "Участник #" + CARS_COUNT;
            this.cb = cb;
            this.cdl = cdl;
        }
        @Override
        public void run () {
            try {
                System.out.println(this.name + " готовится");
                //подготовительная часть задачи, после которой ВСЕ потоки будут выполнять свою задачу
                Thread.sleep(500 + (int) (Math.random() * 800));
                System.out.println(this.name + " готов");

                cdl.countDown(); //чтобы сообщение о начале гонки выводилось после готовности участников
                cb.await();

                for (int i = 0; i < race.getStages().size(); i++) {
                    race.getStages().get(i).go(this);
                }

                cb.await(); //ждем всех, чтобы вывести сообщение о завершении гонки
                //cdl.countDown(); //хочу запустить еще один счетчик для вывода сообщения о завершении гонки в конце
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

}
