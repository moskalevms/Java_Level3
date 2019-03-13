/**
 * 	Создать три потока, каждый из которых выводит
 *  определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
 *  Используйте wait/notify/notifyAll.
 */
public class WaitNotifyABC {
    private final Object mon = new Object();

    //volatile - чтение/запись - атомарная операция,
    //переменную можно изменять разными потоками, неявная синхронизация
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {
        WaitNotifyABC w = new WaitNotifyABC();
        Thread t1 = new Thread(() ->{
            w.printA();
        });

        Thread t2 = new Thread(() ->{
            w.printB();
        });

        Thread t3 = new Thread(() -> {
            w.printC();
        });

        t1.setName("Thread-A");
        t2.setName("Thread-B");
        t3.setName("Thread-C");

        t1.start();
        t2.start();
        t3.start();
    }

    public void printA(){
        synchronized (mon){
            try {
                for (int i = 0; i < 5; i++){
                    while (currentLetter != 'A'){ //если текущая буква не А
                        mon.wait(); //текущий поток ждет
                    }
                    System.out.print("A"); //т.к. буква - А, поток выполняется и печатает А
                    currentLetter = 'B'; //Присваиваем переменной букву В
                    mon.notify(); //возобновляем работу. Выполнится или поток В или С?
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB(){
        synchronized (mon){
            try {
                for (int i = 0; i < 5; i++){
                    while (currentLetter != 'B'){
                        mon.wait();
                    }
                    System.out.print("B");
                    currentLetter = 'C';
                    mon.notifyAll(); //попробуют выполнится сразу и А и С?
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC(){
        synchronized (mon){
            try {
                for (int i = 0; i < 5; i++){
                    while (currentLetter != 'C'){
                        mon.wait();
                    }
                    System.out.print("C");
                    currentLetter = 'A';
                    mon.notifyAll(); //попробуют выполнится сразу и А и В?
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




}
