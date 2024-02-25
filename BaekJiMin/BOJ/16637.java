import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	static int N, fN, answer;
	static boolean[] isFirst;
	static String formula;
	static Deque<Integer> numbers; // 숫자
	static Deque<Character> operators; // 연산자
	
	public static void main(String[] args) throws Exception {
		
		N = Integer.parseInt(br.readLine());
		formula = br.readLine(); // 수식 저장 

		// 괄호는 중첩, 연산자 하나만 됨
		// 따라서, 괄호로 묶을 수 있는 그룹은 N/2개임 (마지막 숫자를 제외한 숫자들을 기준으로 하면 됨)
		// ex) 3+8*7-9*2는 3, 8, 7, 9를 시작으로 괄호로 묶을 수 다
		fN = N/2; 
		isFirst = new boolean[fN]; // 괄호 여부
		
		answer = Integer.MIN_VALUE;
		orderFirst(0);
		
		bw.write(Integer.toString(answer));
		bw.flush();
		bw.close();
	}

	// 괄호 조합 구하기 
	public static void orderFirst(int idx) {
		calFirst(); // 해당 조합으로 연산하기 
		
		for(int i = idx; i < fN; i++) {
			if(i != 0 && isFirst[i-1]) continue; // 이전이 괄호 였으면 괄호 불가능 
			
			isFirst[i] = true;
			orderFirst(i+1);
			isFirst[i] = false;
		}
	}

	// 괄호를 기준으로 해서 먼저 연산 처리 
	public static void calFirst() { 
		int idx = 0;
		int groupCnt = 0; 
		
		numbers = new ArrayDeque<>(); // 숫자 저장
		operators = new ArrayDeque<>(); // 연산자 저장 
		
		while(idx < N) {
			//연산자면 
			if(!isNumber(formula.charAt(idx))) {
				operators.add(formula.charAt(idx++));
				continue;
			}
			
			if(groupCnt < fN && isFirst[groupCnt++]) { // 만약 해당 숫자가 
				numbers.add(calString(formula.substring(idx, idx+3))); // 괄호 계산해서 숫자로 넣기 
				groupCnt++; // 다음 숫자까지 연산해버렸으니 + 해줘야함
				idx += 3; // 3개를 한번에 바꿔버렸으니 +3 해줌
			}
			else { // 숫자 넣기 
				numbers.add((formula.charAt(idx++) - '0'));
			}
		}
		
		findAnswer(); // 저장된 숫자, 연산자 기준으로 답 구하기 
	}

	// 맨 앞에서부터 하나씩 계산하는 함수 
	public static void findAnswer() {
		while(numbers.size() > 1) { // 1이되면 정답
			int result = 0;
			int a = numbers.poll();
			int b = numbers.poll();
			
			char op = operators.poll();
			switch(op) {
			case '+':
				result = a + b;
				break;
			case '-':
				result = a - b;
				break;
			case '*':
				result = a * b;
				break;
			}
			// 더한거 맨 앞으로 넣기
			numbers.addFirst(result);
		}
		
		answer = Math.max(numbers.poll(), answer);
	}

	// a연산자b 순서의 문자열을 계산하는 함수 
	public static int calString(String f) {
		int a = f.charAt(0) - '0';
		int b = f.charAt(2) - '0';
		
		switch(f.charAt(1)) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		}
		
		return 0;
	}
	
	
	public static boolean isNumber(char c) {
		if(c >= '0' && c <= '9') return true;
		return false;
	}
}
