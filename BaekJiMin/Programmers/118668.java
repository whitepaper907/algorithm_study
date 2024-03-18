import java.util.*;

class Solution {
    public int solution(int alp, int cop, int[][] problems) {
        int answer = Integer.MAX_VALUE;
        
        int[][] dp = new int[151][151];
        
        
        int max_alp = 0, max_cop = 0;
        for(int[] problem : problems) {
            max_alp = Math.max(max_alp, problem[0]);
            max_cop = Math.max(max_cop, problem[1]);
        }
        
        for(int i = 0; i <= 150; i++) {
            for(int j = 0; j <= 150; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[alp][cop] = 0;
        
        
        Deque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{alp, cop});
        
        while(!q.isEmpty()) {
            int curr_alp = q.peek()[0];
            int curr_cop = q.poll()[1];
            
            if(curr_alp >= max_alp && curr_cop >= max_cop) continue;
            
            if(curr_alp+1 < 150){
                if(dp[curr_alp+1][curr_cop] > dp[curr_alp][curr_cop]+1){
                    dp[curr_alp+1][curr_cop] = dp[curr_alp][curr_cop]+1;
                    q.add(new int[]{curr_alp+1, curr_cop});
                }
            }
                
            if(curr_cop+1 < 150){
                if(dp[curr_alp][curr_cop+1] > dp[curr_alp][curr_cop]+1){
                    dp[curr_alp][curr_cop+1] = dp[curr_alp][curr_cop]+1;
                    q.add(new int[]{curr_alp, curr_cop+1});
                }
            }
            
            for(int[] problem : problems) {
                if(curr_alp < problem[0] || curr_cop < problem[1]) continue;
                
                int next_alp = curr_alp + problem[2];
                int next_cop = curr_cop + problem[3];
                if(next_alp > 150) next_alp = 150;
                if(next_cop > 150) next_cop = 150;
                
                if(dp[next_alp][next_cop] > dp[curr_alp][curr_cop] + problem[4]){
                    dp[next_alp][next_cop] = dp[curr_alp][curr_cop] + problem[4];
                    q.add(new int[]{next_alp, next_cop});
                }
            }
        }
        
        for(int i = max_alp; i <= 150; i++) {
            for(int j = max_cop; j <= 150; j++) {
                answer = Math.min(dp[i][j], answer);
            }
        }
        
        return answer;
    }
}
