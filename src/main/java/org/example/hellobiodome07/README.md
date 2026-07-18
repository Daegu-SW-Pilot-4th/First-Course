‼️ (문제 7) "다른 줄기에서 나온 비슷한 초록"
---

## 📢 개요
사용자로부터 커맨드 라인 인자로 DNA 염기서열을 입력받아, 반복되는 뉴클레오타이드를 문자와 개수로 압축해 출력하는 프로그램이다.

## 💻 코드 평가(테스팅) 방법

---

### 📁 코드 구조 및 작업 과정 요약

#### 0. 전체 코드 구현 구조

```
HelloBiodome07.java
├── isInBase(char)                     ← 문자가 5가지 뉴클레오타이드(C,Y,J,E,H) 또는 공백인지 확인 (static 메서드)
├── normalize(String)                  ← 대문자 변환 + 연속된 공백을 1개로 축소 (static 메서드)
├── compressDNA(String)                ← 정규화된 염기서열을 반복 문자 수로 압축, 공백은 보너스 규칙대로 유지 (static 메서드)
└── main(String[] args)                ← 진입점, 입력 정규화·검증 및 결과 출력
```

제약사항(`StringBuilder` 사용 금지, 반복문은 `for` 구문만 사용, 입력은 Command Line Arguments)에 따라 문자열 압축을 `String` 연결과 `for` 루프로 직접 구현했다.

#### 1. normalize() — 대문자 변환 및 공백 정리

```java
private static String normalize(String sourceDNA)
{
    String  normalized = "";
    boolean prevIsSpace = false;

    for (int idx = 0; idx < sourceDNA.length(); idx++)
    {
        char ch = Character.toUpperCase(sourceDNA.charAt(idx));

        if (ch == ' ' && prevIsSpace)
            continue;
        normalized += ch;
        prevIsSpace = (ch == ' ');
    }
    return (normalized);
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 대문자 변환 | 각 문자를 `Character.toUpperCase()`로 변환 | "결과는 대문자로만 출력한다" |
| ② 공백 축소 | 직전 문자가 공백이면 이어지는 공백은 건너뜀 | 보너스: "공백이 여러개 반복 입력되는 경우 1개의 공백만 남긴다" |

#### 2. compressDNA() — 반복 문자 수 압축

```java
private static String compressDNA(String sourceDNA)
{
    String  compress = "-> ";
    int     countCurrentDNA = 0;
    char    currentDNA = '\0';
    int     idx = 0;

    for (; idx < sourceDNA.length() && isInBase(sourceDNA.charAt(idx)); idx++)
    {
        if (idx == 0)
        {
            currentDNA = sourceDNA.charAt(idx);
            countCurrentDNA++;
        }
        else if (currentDNA == sourceDNA.charAt(idx))
            countCurrentDNA++;
        else
        {
            compress += currentDNA;
            if (countCurrentDNA > 1)
                compress += Integer.toString(countCurrentDNA);
            currentDNA = sourceDNA.charAt(idx);
            countCurrentDNA = 1;
        }
    }
    if (idx != sourceDNA.length())
    {
        System.out.println("-> 염기서열은 C, J, H, E, Y 다섯가지로만 입력됩니다. 확인하고 다시 입력해주세요");
        System.exit(1);
    }
    compress += currentDNA;
    if (countCurrentDNA > 1)
        compress += Integer.toString(countCurrentDNA);
    return (compress);
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 순차 비교 | 같은 문자가 이어지는 동안 개수를 누적 | "반복되는 뉴클레오타이드를 숫자로 압축" |
| ② 문자 전환 시 flush | 다른 문자를 만나면 지금까지 누적한 문자+개수를 결과에 붙임 (개수가 1이면 숫자 생략) | 압축 결과를 문자로 반환 |
| ③ 유효성 검사 | 루프 도중 `isInBase()`를 통과하지 못하면 루프가 즉시 멈추고, 마지막 인덱스와 전체 길이가 다르면 잘못된 뉴클레오타이드로 판단 | "5가지 염기서열이 아닌 뉴클레오타이드를 입력하는 경우" 안내 |
| ④ 공백 처리 | 공백도 `isInBase()`를 통과하므로 압축 대상 문자에 포함되지만, `normalize()`에서 이미 연속 공백을 1개로 줄여두었기 때문에 공백에는 숫자가 붙지 않고 그대로 유지된다 | 보너스: "공백을 유지한 상태로 출력한다" |

#### 3. main() — 입력 정규화·검증 및 결과 출력

| 단계 | 처리 내용 |
|---|---|
| ① 정규화 | 커맨드 라인 인자를 공백으로 이어 붙인 뒤 `normalize()`로 대문자 변환·공백 정리 |
| ② 빈 값 검사 | 정규화된 결과가 빈 문자열이면(인자가 없거나, 인자가 모두 빈 문자열인 경우 포함) 안내 문구 출력 후 종료 |
| ③ 결과 출력 | `compressDNA()`의 반환값을 그대로 출력 |

> **구현 중 확인한 점:** 초기 구현은 `args.length >= 1`만으로 입력 여부를 판단해, 빈 문자열 인자(`""`)가 들어오면 초기화되지 않은 문자(`\0`)가 그대로 출력되는 버그가 있었다. 정규화된 문자열 자체가 비어 있는지로 판단 기준을 바꿔 해결했다. 재현 방법은 `TerminalTest.md`의 TC-06을 참고한다.

---

### 🚀 GitHub Clone 후 실행 방법

#### ✨ 사전 요구 사항

| 항목 | 작업 환경 | 최소 요구 | 비고 |
|---|---|---|---|
| JDK | Amazon Corretto 17.0.19 | 11 이상 | `java -version`으로 확인 |

```bash
# Java 버전 확인
java -version
# 출력 예시: openjdk version "17.0.19" 2025-04-15 LTS (Amazon Corretto)
```

#### 1단계 — 저장소 Clone 및 이동

```bash
git clone <repository-url>
cd java_first_course
```

#### 2단계 — 출력 디렉토리 생성 및 컴파일

```bash
# Windows (PowerShell / CMD)
mkdir out
javac -d out src\main\java\org\example\hellobiodome07\HelloBiodome07.java

# macOS / Linux
mkdir out
javac -d out src/main/java/org/example/hellobiodome07/HelloBiodome07.java
```

컴파일 성공 시 `out/org/example/hellobiodome07/` 경로에 `.class` 파일이 생성된다.

#### 3단계 — 프로그램 실행

```bash
# Windows / macOS / Linux 공통
java -cp out org.example.hellobiodome07.HelloBiodome07 <DNA 염기서열...>
```

**실행 예시**

```bash
# 기본 압축
java -cp out org.example.hellobiodome07.HelloBiodome07 JJJCCCEEHHYYYEEEEE
# -> J3C3E2H2Y3E5

# 소문자 입력 (대문자로 변환되어 압축)
java -cp out org.example.hellobiodome07.HelloBiodome07 CCCCjjjYYEEHHCCYYY
# -> C4J3Y2E2H2C2Y3

# 잘못된 뉴클레오타이드
java -cp out org.example.hellobiodome07.HelloBiodome07 KKKYYYHHWWMMM
# -> 염기서열은 C, J, H, E, Y 다섯가지로만 입력됩니다. 확인하고 다시 입력해주세요

# 공백 포함 (보너스: 공백 1개 유지)
java -cp out org.example.hellobiodome07.HelloBiodome07 CCCCHHHH JJ EEEEJJ
# -> C4H4 J2 E4J2

# 인자 없이 실행
java -cp out org.example.hellobiodome07.HelloBiodome07
# -> 염기서열이 입력되지 않았습니다. 다시 입력해주세요
```

> ⚠️ `Requirements.md`의 기본 결과 예시 중 공백이 포함된 케이스(`EEE EECCCCYYY` → `E5C4Y3`)는 공백을 완전히 제거하고 앞뒤 같은 문자를 병합하는 동작을 보여준다. 반면 이 구현은 보너스 과제(공백을 1개만 남기고 그 경계에서는 병합하지 않음)를 선택했기 때문에, 같은 입력이 `E3 E2C4Y3`으로 출력된다. 같은 이유로 `CodeValidationGuideline.md`의 기본 확인 예시(`CCJJhhJJJJJ EEEEEHHHH jjjCCCCC` → `C2J2H2J5E5H4J3C5`)도 공백이 남은 형태(`C2J2H2J5 E5H4 J3C5`)로 출력된다. 자세한 내용과 재현 방법은 `TerminalTest.md`의 TC-04, TC-12를 참고한다.

더 다양한 테스트 케이스(경계 케이스, 회귀 테스트 포함)는 `TerminalTest.md`에서 확인할 수 있다.

## 🧑‍💻 문제 해결 과정에서 느낀 점
- (직접 작성해주세요)
