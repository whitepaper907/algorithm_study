import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;

	static int N, rotateN, answer;
	static int[][] map;
	static boolean[][] visited;
	static Group[][] groupMap;
	static List<Group> groups;
	
	public static void main(String[] args) throws Exception{
		N = Integer.parseInt(br.readLine());
		rotateN = N/2;
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int round = 0;
		while(true) {
			groups = new ArrayList<>();
			visited = new boolean[N][N];
			groupMap = new Group[N][N];
			
			// 그룹 묶어주기 -> groupMap에 해당 정보 저장 
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(visited[i][j]) continue;
					findGroup(i, j, new Group(map[i][j]));				
				}
			}
			
			// 각 칸마다 인접한 그룹이 있는지 확인 (해당 칸 상하좌우의 다른 그룹이 있으면)
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					findAdjGroup(i, j);				
				}
			}
			
			
			int artisry = 0;
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(groupMap[i][j].isSearch) continue; // 이미 탐색했으면 패스
					groupMap[i][j].isSearch = true;
					
					Group group = groupMap[i][j];
					// 인접한 그룹 돌면서 예술성 구하기 
					for(Group adjGroup : group.adj) {
						artisry += (group.count + adjGroup.count) * group.num * adjGroup.num * group.adjCount.get(adjGroup.hashCode());
			
					}
				}
			}
			
			answer += artisry;
			if(++round == 4) break; // 4라운드면 돌리지 않고 끝냄 
			rotateMap();
		}
		
		bw.write(Integer.toString(answer));
		bw.flush();
		bw.close();

	}
	
	static int[] dx = {0, 0, 1, -1};
	static int[] dy = {1, -1, 0, 0};
	public static void findGroup(int x, int y, Group group) {
		visited[x][y] = true;
		groupMap[x][y] = group; // 해당 좌표에 해당하는 그룹 저장 
		group.count++; // 칸수 증가
		
		for(int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			if(nx < 0 || nx >= N || ny < 0 || ny >= N) continue;
			
			if(visited[nx][ny]) continue;
			
			if(map[nx][ny] != group.num) continue;
			
			findGroup(nx, ny, group);
			
		}
	}
	
	public static void findAdjGroup(int x, int y) {
		Group group = groupMap[x][y];
		
		for(int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			if(nx < 0 || nx >= N || ny < 0 || ny >= N) continue;
			
			// 다른 그룹 발견 (맞닿은 변이라서 중복체크 안해줘도 됨)
			if(group.num < map[nx][ny]) { // 나보다 큰 경우만 더해줌 - 중복방지 
				Group adjGroup = groupMap[nx][ny];
				// 처음 보는 그룹이면 (해당 그룹의 해시코드로 검색)
				// 맞닿은 변 개수 0으로 초기화, 인접한 그룹리스트에 해당 그룹 넣어줌
				if(!group.adjCount.containsKey(adjGroup.hashCode())) {
					group.adjCount.put(adjGroup.hashCode(), 0);
					group.adj.add(adjGroup);
				}
			
				// 맞닿은 변 개수 업데이트 
				int preCount = group.adjCount.get(adjGroup.hashCode());
				group.adjCount.replace(adjGroup.hashCode(), preCount+1);
				
			}
			
		}
	}
	
	static int[][] changeMap; 
	public static void rotateMap() {
		changeMap = new int[N][N];
		
		// 4개의 작은 사각형 회전 
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				rotate(i*(rotateN+1), j*(rotateN+1));
			}
		}
		
		//십자가 회전
		for(int i = 0; i < N; i++) {
			changeMap[rotateN][i] = map[i][rotateN];
			changeMap[i][rotateN] = map[rotateN][N-1-i];
		}
		
		map = changeMap;
	}
	
	// 작은 사각형 회전 
	public static void rotate(int x, int y) {
		for(int i = 0; i < rotateN; i++) {
			for(int j = 0; j < rotateN; j++) {
				changeMap[x+j][y+rotateN-i-1] = map[x+i][y+j];
			}
		}
	}
	
	static class Group {
		int num; // 그룹을 구성하는 숫자
		int count; // 그룹의 칸 수 
		List<Group> adj = new ArrayList<>(); // 인접한 그룹 리스트 
		// 인접한 그룹의 변수 (인접한 그룹의 해시코드로 얻어냄)
		HashMap<Integer, Integer> adjCount = new HashMap<>(); 
		boolean isSearch;
		
		public Group(int num) {
			this.num = num;
		}
	}

}
