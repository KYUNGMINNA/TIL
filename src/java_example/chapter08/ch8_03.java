package java_example.chapter08;

public class ch8_03 {
    public static void main(String[] args) {
        int number=100;
        int result=0;

        for (int i=0;i<10;i++){
            try {
                result = number / (int) (Math.random() * 10);
            }catch (ArithmeticException e){
                System.out.println("0");
            }catch (Exception e){
                System.out.println("wow");
            }
        }
    }
}