import java.util.LinkedList;

public class Test{

    public static void main(String[] args){
        LinkedList<Integer> ints = new LinkedList<>();

        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);
        ints.add(4);

        System.out.println(ints.size());

        ints.remove(3);
        ints.remove(3);

        System.out.println(ints.size());
    }

}
