import java.util.*;
import java.util.Map.Entry;

class Solution {
    final static int DONUT = 1; // 도넛
    final static int STICK = 2; // 막대
    final static int EIGHT = 3; // 8자
    
    // 노드 배열 
    HashMap<Integer, Node> nodeList = new HashMap<>();
    public int[] solution(int[][] edges) {
        int[] answer = new int[4];
        
        for(int[] edge : edges) { // 정점 방향 저장 
            Node a = nodeList.get(edge[0]);
            if(a == null) { // 저장된 적 없으면
                a = new Node(edge[0]);
                nodeList.put(a.num, a);
            }
            
            Node b = nodeList.get(edge[1]);
            if(b == null) {
                b = new Node(edge[1]);
                nodeList.put(b.num, b);
            }
            
            a.outNode.add(b);
            b.inNode = a;
            
        }
        
        // 생성한 정점 찾기
        int addNum = -1;
        for(Entry<Integer, Node> entry : nodeList.entrySet()) {
            Node node = entry.getValue();
            
            // 생성된 정점은 -> 방향은 있고 <- 는 없음을 활용 
            if(node.outNode.size() > 1 && node.inNode == null) {
                addNum = node.num;
                break;
            }
        }
        
        answer[0] = addNum;
        
        
        
        for(Node node : nodeList.get(addNum).outNode) {
            answer[findGraph(node)]++;
        }
        
        return answer;
    }
    
    public int findGraph(Node startNode) {
        HashSet<Integer> visited = new HashSet<>();
        Deque<Node> nodes = new ArrayDeque<>();
        nodes.add(startNode);
        visited.add(startNode.num);
        
        boolean isCycle = false;
        int edge = 0;
        
        // 그래프 순회
        while(!nodes.isEmpty()){
            Node currNode = nodes.poll();
            
            for(Node nextNode : currNode.outNode){
                edge++;
                if(visited.contains(nextNode.num)) {
                    isCycle = true; // 방문한 노드 재방문이면 사이클 있는 것 
                    continue;
                }
                
                visited.add(nextNode.num);
                nodes.add(nextNode);
            }
        }
        
        if(isCycle){
            // 사이클인데 정점의 개수와 간선의 개수가 같으면 도넛
            if(visited.size() == edge) return DONUT;
            // 다르면 8자 
            return EIGHT;
        }
        return STICK; // 사이클 없으면 막대
    }
    
    class Node {
        int num;
        // 현재 정점 기준 밖으로 나가는 방향으로 연결된 정점
        List<Node> outNode = new ArrayList<>(); 
        Node inNode; // inNode는 여러개 저장할 필요없음
        
        public Node(int num) {
            this.num = num;
        }
    }
}
