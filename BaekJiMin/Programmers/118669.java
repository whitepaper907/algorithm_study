import java.util.*;

class Solution {
  
    List<Node>[] linked;
    int[] answer;
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        
        linked = new ArrayList[n+1];
        for(int i = 1; i <= n; i++){
            linked[i] = new ArrayList<>();
        }
        
        for(int[] path : paths) {
            linked[path[0]].add(new Node(path[1], path[2]));
            linked[path[1]].add(new Node(path[0], path[2]));
        }
        
        answer = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        getIntensity(n, gates, summits);
        
        return answer;
    }
    
    public void getIntensity(int n, int[] gates, int[] summits){
        int intensity = 0;
        
        boolean[] visited = new boolean[n+1];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        for(int gate : gates){
            for(Node node : linked[gate]){
                if(visited[node.num]) continue;
                pq.add(node);
                //visited[node.num] = true;
            }
        }
        
        boolean[] isSummit = new boolean[n+1];
        for(int summit : summits){
            isSummit[summit] = true;
        }
        
        while(!pq.isEmpty()){
            Node node = pq.poll();
            
            if(visited[node.num]) continue;
            
            visited[node.num] = true;
            intensity = Math.max(intensity, node.w);
            
            if(intensity > answer[1]) return;
            
            if(isSummit[node.num]) {
                answer[0] = Math.min(node.num, answer[0]);
                answer[1] = intensity;
                continue;
            }
            
            
            for(Node next : linked[node.num]){
                //if(visited[next.num]) continue;
                pq.add(next);
            }
        }
        
    }
    
    
    class Node implements Comparable<Node> {
        int num;
        int w;
        
        @Override
        public int compareTo(Node n) {
            return this.w - n.w;
        }
        
        public Node(int num, int w){
            this.num = num;
            this.w = w;
        }
    }
}
