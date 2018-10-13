package misisonaries;

import TreeGraphSearch.*;
import TreeGraphSearch.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joseph
 */
class Border {

    private int c;
    private int m;
    private int b;

    public Border(int c, int m, int b) {
        this.c = c;
        this.m = m;
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public int getM() {
        return m;
    }

    public int getB() {
        return b;
    }

    @Override
    public boolean equals(Object obj) {
        Border tmp = (Border) obj;
        if (this.b == tmp.getB()
                && this.c == tmp.getC()
                && this.m == tmp.getM()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "" + this.c + " " + this.m + " " + this.b;
    }

    @Override
    public Object clone() {
        return new Border(c, m, b);
    }
}

class OrderSensitiveComparableArrayList extends ArrayList<Border> {

    @Override
    public boolean equals(Object o) {
        ArrayList<Border> tmp = (ArrayList<Border>) o;
        for (int i = 0; i < tmp.size(); ++i) {
            if (!(this.get(i).equals(tmp.get(i)))) {
                return false;
            }
        }
        return true;
    }
}

class State<T extends OrderSensitiveComparableArrayList> extends Node {

    private Border b1;
    private Border b2;
    private Class<?> subClass;

    public State(T value) {
        super(value);
        b1 = value.get(0);
        b2 = value.get(1);
        subClass = value.getClass();

    }

    @Override
    public ArrayList<Node> getNeighbours() {

        OrderSensitiveComparableArrayList v_f;
        State s;

        if (b1.getB() > 0) {
            for (int i = 1; i < 3; ++i) {

                try {
                    v_f = (OrderSensitiveComparableArrayList) subClass.newInstance();
                    v_f.add(new Border(b1.getC() - i, b1.getM(), 0));
                    v_f.add(new Border(b2.getC() + i, b2.getM(), 1));
                    s = new State(v_f);
                    if (isValidAction(s)) {
                        addNeighbour(s);
                    }

                    v_f = (OrderSensitiveComparableArrayList) subClass.newInstance();
                    v_f.add(new Border(b1.getC(), b1.getM() - i, 0));
                    v_f.add(new Border(b2.getC(), b2.getM() + i, 1));
                    s = new State(v_f);
                    if (isValidAction(s)) {
                        addNeighbour(s);
                    }

                    v_f = (OrderSensitiveComparableArrayList) subClass.newInstance();
                    v_f.add(new Border(b1.getC() - i + 1, b1.getM() - i + 1, 0));
                    v_f.add(new Border(b2.getC() + i - 1, b2.getM() + i - 1, 1));
                    s = new State(v_f);
                    if (isValidAction(s)) {
                        addNeighbour(s);
                    }
                } catch (InstantiationException ex) {
                    Logger.getLogger(State.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(State.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {

            for (int i = 1; i < 3; ++i) {
                try {
                    v_f = (OrderSensitiveComparableArrayList) subClass.newInstance();
                    v_f.add(new Border(b1.getC() + i, b1.getM(), 1));
                    v_f.add(new Border(b2.getC() - i, b2.getM(), 0));
                    s = new State(v_f);
                    if (isValidAction(s)) {
                        addNeighbour(s);
                    }

                    v_f = (OrderSensitiveComparableArrayList) subClass.newInstance();
                    v_f.add(new Border(b1.getC(), b1.getM() + i, 1));
                    v_f.add(new Border(b2.getC(), b2.getM() - i, 0));
                    s = new State(v_f);
                    if (isValidAction(s)) {
                        addNeighbour(s);
                    }

                    v_f = (OrderSensitiveComparableArrayList) subClass.newInstance();
                    v_f.add(new Border(b1.getC() + i - 1, b1.getM() + i - 1, 1));
                    v_f.add(new Border(b2.getC() - i + 1, b2.getM() - i + 1, 0));
                    s = new State(v_f);
                    if (isValidAction(s)) {
                        addNeighbour(s);
                    }
                } catch (InstantiationException ex) {
                    Logger.getLogger(State.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(State.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return super.getNeighbours();
    }

    public boolean isValidAction(State s) {
        if (s.getB1().getC() > s.getB1().getM() && s.getB1().getM() > 0) {
            return false;
        }
        if (s.getB2().getC() > s.getB2().getM() && s.getB2().getM() > 0) {
            return false;
        }
        if (s.getB1().getC() < 0
                || s.getB1().getM() < 0
                || s.getB2().getC() < 0
                || s.getB2().getM() < 0) {
            return false;
        }
        return true;
    }

    public Border getB1() {
        return b1;
    }

    public void setB1(Border b1) {
        this.b1 = b1;
    }

    public Border getB2() {
        return b2;
    }

    public void setB2(Border b2) {
        this.b2 = b2;
    }

    @Override
    public boolean equals(Object obj) {
        State tmp = (State) obj;
        return b1.equals(tmp.getB1()) && b2.equals(tmp.getB2());
    }

    @Override
    public String toString() {
        return b1.toString() + " " + b2.toString();
    }

    @Override
    public Object clone() {
        try {
            OrderSensitiveComparableArrayList v_f = (OrderSensitiveComparableArrayList) subClass.newInstance();
            v_f.add((Border) b1.clone());
            v_f.add((Border) b2.clone());
            return new State(v_f);
        } catch (InstantiationException ex) {
            Logger.getLogger(State.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(State.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}

public class Misisonaries {

    public static void main(String[] args) {

        Border b1 = new Border(3, 3, 1);
        Border b2 = new Border(0, 0, 0);
        OrderSensitiveComparableArrayList v = new OrderSensitiveComparableArrayList();
        v.add(b1);
        v.add(b2);
        State is = new State(v);

        Border b1_f = new Border(0, 0, 0);
        Border b2_f = new Border(3, 3, 1);
        OrderSensitiveComparableArrayList v_f = new OrderSensitiveComparableArrayList();
        v_f.add(b1_f);
        v_f.add(b2_f);
        UninformedSearch ts_dfs = new BFS();
        List<Node> path = ts_dfs.search(v_f,is);
        if (path == null) {
            System.out.println("Path is null");
            return;
        }
        System.out.println("C M B ---- C M B");
        for (Node n : path) {
            System.out.println(((List<Border>) n.getValue()).get(0) + " ---- " + ((List<Border>) n.getValue()).get(1));
        }
        System.out.println("");
    }
}
