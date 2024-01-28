package boj2468;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

	static int N;
	static int[][] map;
	static boolean[][] visited;
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, 1, -1};
	public static boolean safeArea(int x, int y, int h) {
		if(map[x][y] > h && !visited[x][y]) {
			return true;
		}
		return false;
	}
	
	
	//같은 영역 탐색 
 	public static void findArea(int x, int y, int h) {
		visited[x][y] = true;
 		
 		for(int i=0;i<4;i++) {
 			int nx = x+dx[i];
 			int ny = y+dy[i];
 			if(nx>=0 && nx<N && ny>=0 && ny<N) {
 				if(safeArea(nx, ny, h)) {
 					findArea(nx, ny, h);
 				}
 			}
 		}
		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		visited = new boolean[N][N];
		
		Set<Integer> hSet = new HashSet<>(); // 건물 높이 저장 
		for(int i=0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				hSet.add(map[i][j]);
			}
		}
		
		int ans = 1; // 물에 모두 잠기지 않을 경우 : 1
		Iterator<Integer> it = hSet.iterator();
		while(it.hasNext()) { // 건물 높이를 돌며 영역 확인
			int h = it.next();
			int cnt = 0;
			
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					// 잠기지 않고 + 방문하지 않은 영역 탐색
					if(safeArea(i, j, h)) {
						findArea(i, j, h);
						cnt++;
					}
				}
			}
			
			//정답 체크
			ans = Math.max(cnt, ans);
			
			//방문 초기화
			for(int i=0;i<N;i++) Arrays.fill(visited[i], false);
		}
		
		System.out.println(ans);

	}

}
