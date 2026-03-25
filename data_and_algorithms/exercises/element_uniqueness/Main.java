package element_uniqueness;

public class Main {

    public static boolean unique(int[] data, int low, int high) {
        if (low >= high) return true;
        else if (!unique(data, low, high-1)) return false;
        else if (!unique(data, low+1, high)) return false;
        else return (data[low] != data[high]);
    }

    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,1};
        System.out.println(unique(array, 0, array.length-1)); // Output: false
    }
}