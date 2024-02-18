package datastructers;

public class Triplet <A,B,C>{
    A first;
    B second;
    C third;
    public Triplet(A a, B b, C c){
        first = a;
        second = b;
        third = c;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }
}
