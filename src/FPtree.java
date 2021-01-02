import java.util.Vector;

public class FPtree {

    public FPtree(String item) {
        this.item = item;
        next = null;
        children = new Vector<FPtree>();
        root = false;
    }

    boolean root;
    String item;
    Vector<FPtree> children;
    FPtree parent;
    FPtree next;
    int count;

    boolean isRoot(){
        return root;
    }

}