import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
//		String m = "ABCDEFG";
//		String[] musicinfos = { "12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF" };
		// return HELLO

//		String m = "CC#BCC#BCC#BCC#B";
//		String[] musicinfos = {"03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"};
		// return FOO
		
		String m = "ABC";
		String[] musicinfos = {"14:00,14:05,FIRST,ABCDEF","12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF","13:00,13:05,WORLD2,ABCDEF"};
//		// return WORLD

		System.out.println(new Solution().solution(m, musicinfos));
	}

}


class Solution {

	public String solution(String m, String[] musicinfos) {
		String answer = "(None)";
		
		m = m.replace("C#", "c").replace("D#", "d").replace("F#", "f").replace("G#", "g").replace("A#", "a");
		int maxTime = 0;
		
		for (int i = 0; i < musicinfos.length; i++) {
			String[] info = musicinfos[i].split(",");
			
			String[] startInfo = info[0].split(":");
			String[] endInfo = info[1].split(":");
			
			int start = Integer.parseInt(startInfo[0])*60 + Integer.parseInt(startInfo[1]);
			int end = Integer.parseInt(endInfo[0])*60 + Integer.parseInt(endInfo[1]);
			
			int runtime = end - start;
			
			
			if (runtime > maxTime) {
				String code = info[3].replace("C#", "c").replace("D#", "d").replace("F#", "f").replace("G#", "g").replace("A#", "a");
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < runtime; j++) {
					sb.append(code.charAt(j % (code.length())));
				}
				
				if (sb.toString().indexOf(m) >= 0) { // exist
					answer = info[2];
					maxTime = runtime;
				}
			}
		}
		
		
		return answer;
	}

}


// 테스트 30번 오답
class Solution_Wrong {
	class Music {
		String name;
		int runtime;
		int codeLength;
		int order;
		String codes;
	
		@Override
		public String toString() {
			return order + "번 곡 "+name + " " + runtime + " 분, 코드 길이" + codeLength + " 코드: " + codes + "\n";
		}
	}


	public String solution(String m, String[] musicinfos) {
		String answer = "";
		m = m.replace("C#", "c").replace("D#", "d").replace("F#", "f").replace("G#", "g").replace("A#", "a");
		
		Music[] musics = new Music[musicinfos.length];
		
		for (int i = 0; i < musicinfos.length; i++) {
			String[] info = musicinfos[i].split(",");
			musics[i] = new Music();
			
			String[] startInfo = info[0].split(":");
			String[] endInfo = info[1].split(":");
			
			
			int startH = Integer.parseInt(startInfo[0]);
			int startM = Integer.parseInt(startInfo[1]);
			int endH = Integer.parseInt(endInfo[0]);
			int endM = Integer.parseInt(endInfo[1]);
			
			int runtime = 0;
			
			if (endM < startM) {
				endH--;
				runtime += endM - startM + 60;
			} else {
				runtime += endM - startM;
			}
			
			if (endH < startH) {
				runtime += (endH - startH + 24) * 60;
			} else {
				runtime += (endH - startH) * 60;
			}
			
			String codes = info[3];
			codes = codes.replace("C#", "c").replace("D#", "d").replace("F#", "f").replace("G#", "g").replace("A#", "a");
			
			musics[i].runtime = runtime + 1;
			musics[i].name = info[2];
			musics[i].codes = codes;
			musics[i].codeLength = info[3].replace("#", "").length();
			musics[i].order = i;
			int repeat = musics[i].runtime / musics[i].codeLength;
			while (repeat-- > 0) {
				musics[i].codes += codes;
			}
		}
		
		
		
		List<Music> candidates = new LinkedList<>();
		for (int i = 0; i < musics.length; i++) {
			Music music = musics[i];
			if (music.codes.contains(m)) {
				candidates.add(music);
			}
		}
		
		if (candidates.size() == 0) {
			return "(None)";
		} else {
			candidates.sort(compByRuntime);
			return candidates.get(0).name;
		}
		
	}

	private Comparator<Music> compByRuntime = new Comparator<Music>() {
		public int compare(Music m1, Music m2) {
			if (m1.runtime == m2.runtime) {
				return m1.order - m2.order;
			} else {
				return m2.runtime - m1.runtime;
			}
		}
	};
}