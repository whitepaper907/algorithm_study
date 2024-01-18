import java.util.*;

class Solution {
    
    public int findDollIdx(int[][] board, int idx){
        // 1초 기준 입력의 최대 크기 O(N) : 약 1억
        for(int i=0;i<board.length;i++){
            if(board[i][idx] != 0) {
                return i;
            }
        }
        return -1;
    }
    
    public int solution(int[][] board, int[] moves) {
        int answer = 0;
        int n = board.length;
        
        List<Integer> stack = new ArrayList<>();
        
        for(int move : moves){
            move--;
            int idx = findDollIdx(board, move);
            
            if(idx == -1) continue;
            
            int doll = board[idx][move];
            board[idx][move] = 0;
            
            boolean flag = false;
            if(!stack.isEmpty()){
               if(stack.get(stack.size()-1) == doll) {
                    stack.remove(stack.size()-1);
                    flag = true;
                    answer+=2;
                } 
            }
            
            if(!flag) stack.add(doll);
        }
        
        return answer;
    }
}
