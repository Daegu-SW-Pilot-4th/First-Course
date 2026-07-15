‼️ (문제 6) "언제나 새롭고 또 새로운"
---

## 📢 개요
두 개의 유전자 코드를 입력받아 문자 단위로 비교해 동일 여부를 판별하고, 첫 번째 코드가 두 번째 코드에 부분적으로 포함되는지까지 함께 출력하는 프로그램이다.

## 💻 코드 평가(테스팅) 방법

---

### 📁 코드 구조 및 작업 과정 요약

#### 0. 전체 코드 구현 구조

```
HelloBiodome06.java
├── isSubGene(String, String)                          ← gene1이 gene2에 부분 포함되는지 판단, 보너스 (static 메서드)
├── isEqualGenes(String, String)                       ← 두 유전자 코드가 동일한지 판단 (static 메서드)
├── checkGeneString(String)                            ← 유전자 코드 형식(길이/문자 구성) 검증 (static 메서드)
└── main(String[] args)                                ← 진입점, 입력 검증 및 결과 출력
```

제약사항(`StringBuilder`/`String.equals()` 사용 금지, 반복문은 `while`만 사용)에 따라 모든 문자열 비교를 `charAt()` 기반 `while` 루프로 직접 구현했다.

#### 1. isEqualGenes() — 문자 단위 동일 여부 판단

```java
private static boolean isEqualGenes(String gene1, String gene2)
{
    int idx = 0;
    while (idx < gene1.length() && idx < gene2.length())
    {
        if (gene1.charAt(idx) != gene2.charAt(idx))
            return (false);
        idx++;
    }
    if (idx < gene1.length() || idx < gene2.length())
        return (false);
    return (true);
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 순차 비교 | 두 문자열의 앞에서부터 `charAt()`으로 한 글자씩 비교 | "각 문자를 순차적으로 비교" |
| ② 길이 검사 | 루프 종료 후 아직 남은 문자가 있으면(길이가 다르면) 불일치 | 정확한 동일성 판단 |

#### 2. isSubGene() — 부분 문자열 포함 여부 판단 (보너스)

```java
private static boolean isSubGene(String gene1, String gene2)
{
    if (gene1.length() >= gene2.length())
        return (false);

    int start = 0;
    while (start <= gene2.length() - gene1.length())
    {
        int idx = 0;
        while (idx < gene1.length() && gene1.charAt(idx) == gene2.charAt(start + idx))
            idx++;
        if (idx == gene1.length())
            return (true);
        start++;
    }
    return (false);
}
```

gene2의 모든 시작 위치(`start`)에서 gene1과의 일치 여부를 확인해, gene1이 gene2의 어느 위치에 있든 포함 여부를 판단한다. gene1이 gene2와 길이가 같거나 더 길면(즉 "부분"이 아니면) 곧바로 false를 반환한다.

> **구현 중 확인한 점:** 첫 구현은 두 문자열을 인덱스 0부터 나란히 비교하는 방식이라 gene1이 gene2의 접두사(맨 앞부분)일 때만 포함으로 판단했다. 하지만 gene1이 gene2 중간에 위치하는 경우(예: `dna12`가 `xxdna12yy`의 중간에 포함)도 있어, gene2의 모든 시작 위치를 순회하도록 수정했다.

#### 3. checkGeneString() — 유전자 코드 형식 검증

```java
private static boolean checkGeneString(String gene)
{
    if (gene == null || gene.length() < 5 || gene.length() > 20)
        return (false);

    int idx = 0;
    while (idx < gene.length())
    {
        if (('0' <= gene.charAt(idx) && gene.charAt(idx) <= '9') ||
                ('a' <= gene.charAt(idx) && gene.charAt(idx) <= 'z'))
            idx++;
        else
            return (false);
    }
    return (true);
}
```

요구사항 설명("유전자 코드는 숫자와 영어 소문자로 이루어져있다", "최소 5개 이상, 최대 20개")을 검증 로직으로 구현했다. 형식에 맞지 않으면 인자 개수 부족과는 다른 안내 문구(`"올바른 두 개의 유전자 코드를 입력해주세요."`)를 출력한다.

#### 4. main() — 입력 검증 및 결과 출력

| 단계 | 처리 내용 |
|---|---|
| ① 인자 개수 검사 | `args.length == 2`가 아니면 `"두 개의 유전자 코드를 입력해주세요."` 출력 후 종료 |
| ② 형식 검사 | 두 유전자 코드 모두 `checkGeneString()`을 통과해야 다음 단계 진행 |
| ③ 결과 출력 | 동일 여부(`isEqualGenes`)와 포함 여부(`isSubGene`, 보너스)를 각각 한 줄씩 출력 |

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
javac -d out src\main\java\org\example\hellobiodome06\HelloBiodome06.java

# macOS / Linux
mkdir out
javac -d out src/main/java/org/example/hellobiodome06/HelloBiodome06.java
```

컴파일 성공 시 `out/org/example/hellobiodome06/` 경로에 `.class` 파일이 생성된다.

#### 3단계 — 프로그램 실행

```bash
# Windows / macOS / Linux 공통
java -cp out org.example.hellobiodome06.HelloBiodome06 <유전자코드1> <유전자코드2>
```

**실행 예시**

```bash
# 동일한 유전자 코드
java -cp out org.example.hellobiodome06.HelloBiodome06 sfd215j sfd215j
# → 동일한 유전자 코드입니다.
# → 포함되지 않습니다.

# 일치하지 않는 유전자 코드
java -cp out org.example.hellobiodome06.HelloBiodome06 sww29 spp29
# → 일치하지 않습니다.
# → 포함되지 않습니다.

# 부분적으로 포함되는 경우 (보너스)
java -cp out org.example.hellobiodome06.HelloBiodome06 dna123 dna123456
# → 일치하지 않습니다.
# → 부분적으로 포함됩니다.

# 인자 1개만 입력
java -cp out org.example.hellobiodome06.HelloBiodome06 askkk
# → 두 개의 유전자 코드를 입력해주세요.
```

> ⚠️ `Requirements.md`의 결과 예시 중 `sfd215j asfd215j`는 문서상 `"동일한 유전자 코드입니다."`로 안내되어 있으나, 실제로는 서로 다른 문자열(두 번째에 `a`가 하나 더 붙음)이라 `"일치하지 않습니다."`가 정확한 결과다. 다만 이 조합은 흥미롭게도 첫 번째 코드가 두 번째 코드에 부분적으로 포함되는 케이스라, 보너스 기능의 예시로는 정확히 들어맞는다. 자세한 내용과 재현 방법은 `TerminalTest.md`의 TC-14를 참고한다.

더 다양한 테스트 케이스(형식 오류, 부분 포함 회귀 테스트 포함)는 `TerminalTest.md`에서 확인할 수 있다.

## 🧑‍💻 문제 해결 과정에서 느낀 점
- (직접 작성해주세요)
