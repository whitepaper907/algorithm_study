package boj12865;

import java.io.*;
import java.util.*;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, K;
	static Item[] items;
	static HashMap<Integer, Integer> values = new HashMap<>(); // dp 값 저장 
	
	public static void main(String args[]) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		items = new Item[N];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int w = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			
			items[i] = new Item(w, v);
		}
		
		Arrays.sort(items); // 오름차순 정렬
		values.put(0, 0);
		for(Item item : items) {
			if(item.w > K) continue;
			
			ArrayList<Item> addItems = new ArrayList<>(); // 돌면서 추가할 수 없어서 map 순회 끝나고 추가해야됨 
			for(Entry<Integer, Integer> set : values.entrySet()) {
				int nw = set.getKey() + item.w;
				int nv = set.getValue() + item.v;
				
				if(nw > K) continue;
				
				if(!values.containsKey(nw)) { // 추가 
					addItems.add(new Item(nw, nv));
					continue;
				}
				
				if(values.get(nw) < nv) { // 업데이트
					addItems.add(new Item(nw, nv));
				}
			}
			
			for(Item addItem : addItems) {
				values.put(addItem.w, addItem.v);
			}
		}
		
		int answer = 0;
		for(Entry<Integer, Integer> set : values.entrySet()) { // max 값 찾기 
			answer = Math.max(answer, set.getValue());
		}
		
		
		bw.write(Integer.toString(answer));
		bw.flush();
		bw.close();
		
	}
	
	static class Item implements Comparable<Item> {
		int w;
		int v;
		
		public Item(int w, int v) {
			this.w = w;
			this.v = v;
		}

		@Override
		public int compareTo(Item o) {
			return w - o.w;
		}
	}

}
