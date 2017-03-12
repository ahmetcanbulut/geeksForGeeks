import java.util.LinkedList;
import java.util.Stack;
import java.util.ArrayList;

/** Geeks for Geeks - Find the largest region in a boolean matrix (Amazon interview)
 *  Graph traversal with DFS
 */

class Tuple{
    public int x;
    public int y;

    public Tuple(int px, int py){
        x = px;
        y = py;
    }
}

public class Solution {

    public static boolean isVisited(int[][] visited, Tuple node){
        if(visited[node.x][node.y] == 1){
            return true;
        }else{
            return false;
        }
    }

    public static void setVisited(int[][] visited, Tuple node){
        visited[node.x][node.y] = 1;
    }

    // Check the matrix boundaries
    public static boolean isValidNode(Tuple node, int sizeX, int sizeY){
        if(node.x >= 0 && node.y >=0 && node.x < sizeX && node.y < sizeY){
            return true;
        }else{
            return false;
        }
    }

    // Gets valid children of a given node
    public static LinkedList<Tuple> getChildren(Tuple curNode, int[][] visited, int sizeX, int sizeY){
        LinkedList<Tuple> children = new LinkedList<Tuple>();
        int curX = curNode.x;
        int curY = curNode.y;

        for(int i=curX-1;i<curX+2;i++){
            for(int j=curY-1;j<curY+2;j++){
                Tuple t = new Tuple(i,j);
                // check matrix boundaries && node cannot be its own children.
                if(!(i==curX && j== curY) && isValidNode(t,sizeX,sizeY) ){
                    children.add(t);
                }
            }
        }

        return children;
    }

    // calculates the region value. Works for one region(only puts the nodes with value=1 to the stack)
    public static int dfs(int[][] mat,int[][] visited,Stack<Tuple> stack, int sizeX, int sizeY){
        int count = 0;
        while(!stack.isEmpty()){
            Tuple current = stack.pop();
            if(!isVisited(visited,current)){
                count++;  // if not visited expand the region
                setVisited(visited,current);  // set the node visited
            }

            // Find the valid children of current node
            for (Tuple t : getChildren(current, visited, sizeX, sizeY)) {
                if((mat[t.x][t.y] == 1) && (!isVisited(visited,t))){
                    stack.push(t);  // if the children is full and not visited, push
                }
            }
        }
        return count;
    }

    public static int largestRegion(int[][] mat, int sizeX, int sizeY){
        // Solution

        // For storing all region lengths. (optional)
        ArrayList<Integer> regionList = new ArrayList<Integer>();

        // Set up visited matrix
        int[][] visited = new int[sizeX][sizeY];
        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY;j++){
                visited[i][j] = 0;
            }
        }

        // initialize max region
        int maxRegion = 0;

        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY;j++){
                Tuple start = new Tuple(i,j);
                if(mat[i][j] == 1 && !isVisited(visited, start)){
                    // if current node is full and not visited, calculate its region with dfs
                    Stack<Tuple> stack = new Stack<Tuple>();
                    stack.push(start);
                    int local = dfs(mat,visited,stack,sizeX,sizeY);

                    // add all regions to region list
                    regionList.add(local);
                    if(local>maxRegion){
                        maxRegion = local; // update max region length
                    }
                }
            }
        }
        System.out.println(regionList.size());
        return maxRegion;
    }

    public static void main(String[] args){
        // Question Input

        // Input matrix
        int[][] mat2 =
                {{0, 0, 1, 1, 0},
                 {1, 0, 1, 1, 0},
                 {0, 1, 0, 0, 0},
                 {0, 0, 0, 0, 1}};
        int sizeX2 = 4;
        int sizeY2 = 5;

        int[][] mat =
               {{0,1,1},
                {0,1,0},
                {1,0,1}};

        // matrix size
        int sizeX = 3;
        int sizeY = 3;

        int largestRegion = largestRegion(mat2,sizeX2,sizeY2);
        System.out.println(largestRegion);
    }
}
