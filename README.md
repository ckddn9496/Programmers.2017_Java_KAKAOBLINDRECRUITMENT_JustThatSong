# Programmers.2017_Java_KAKAOBLINDRECRUITMENT_JustThatSong

## 프로그래머스 > (2017) KAKAO BLIND RECRUITMENT > [3차] 방금그곡

### 1. 문제설명

문제: https://programmers.co.kr/learn/courses/30/lessons/17683

input으로 문자열 `m`과 문자열 배열 `musicinfos`가 들어온다. `m`은 음계를 문자열로 저장하고있으며 이와 유사한 곡을 `musicinfos`에서 찾아서 음악의 이름을 return하는 문제이다.

음악은 반복재생 될 수 있으며 `musicinfos`의 각 문자열은 `시작시각:끝난시각:음악제목:악보정보`로 구성된다. 만약 일치하는 곡이 없다면 `(None)`을 return하면 되며, 일치하는 곡이 여러개라면 가장 재생시간이 긴 음악을, 재생시간이 같은 곡이 많다면 가장 먼저 input된 곡을 return하면 된다.

### 2. 풀이

음계의 종류로 **C, C#, D, D#, E, F, F#, G, G#, A, A#, B** 가 존재한다. 이때 음계의 개수를 편하게 카운트 해주기 위하여 **#** 이 붙은 음계는 한글자의 소문자로 `replace`함수를 써서 대체하였다. 이후 `musicinfos`의 곡들을 순서대로 탐색한다. 변수 `maxTime`을 두어 `m`과 일치한곡 중 가장 재생시간이 긴 곡의 재생시간을 저장한다. 매 검사 마다 재생시간을 계산, `maxTime` 보다 긴 재생시간을 가진 곡이라면 검사해볼 가치가 있으므로 그때 `m`과의 일치하는 음계를 가지고 있는지 검사한다. 만약 그렇다면 `maxTime`을 갱신하고 곡명을 저장한다. 이렇게 모든곡을 탐색하면 가장 마지막으로 갱신된 `maxTime`과 함께 갱신된 곡명을 return하면 해결 할 수 있다.

```java

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
```

### 3. 어려웠던 점

처음 문제를 접근할 때는 모든 음악에 대한 정보를 받은 후, Music class를 별도로 만들어 저장. 이후에 Music을 담은 리스트를 모두 탐색하며 검사하였다. `list.sort()`를 이용하여 정렬 시 재생시간이 같은 경우 입력 순서를 우선순위로 return한다고 하는것이 stable sort를 따라야 한다는 것을 알게되었다. merge sort를 추가로 구현하는것보다 위의 방법대로 순서대로 해당곡의 가능성에따라 검사와 갱신을 해준다면 별도의 정렬없이 가능하여 위의 방법을 참고하여 해결하였다.

모든 데이터를 활용하지 않고도 문제를 해결할 수 있다면, 굳이 모든것을 저장하지 않는것이 더 빠르고 효율적으로 공간을 이용하는 방법일 수 있다.
