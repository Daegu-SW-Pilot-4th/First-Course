# 🧑‍💻 HelloBiodome05 터미널 테스트 가이드

---

## 💻 실행 환경 설정

`java_first_course` 디렉토리에서 아래 명령어를 실행한다.

**1단계 — 출력 디렉토리 생성 및 컴파일**
```bash
# Windows (PowerShell / CMD)
mkdir out
javac -d out src\main\java\org\example\hellobiodome05\HelloBiodome05.java

# macOS / Linux
mkdir out
javac -d out src/main/java/org/example/hellobiodome05/HelloBiodome05.java
```

**2단계 — 실행 기본 형태**

이 프로그램은 Command Line Arguments를 사용하지 않는다. g, h 값을 0~15(4bit) 범위에서 직접 탐색해 항상 동일한 결과를 출력한다.

```bash
java -cp out org.example.hellobiodome05.HelloBiodome05
```

---

## 🎯 테스트 케이스

편의를 위해 이하 커맨드에서 클래스패스 부분은 `[CP]`로 표기한다.

```
[CP] = -cp out org.example.hellobiodome05.HelloBiodome05
```

---

### TC-01 기본 실행 (인자 없음)

```bash
java [CP]
```

| 항목 | 내용 |
|---|---|
| 예상 출력 | `42`<br>`42` |
| 확인 사항 | 0~15 범위 전수 탐색으로 두 수식을 모두 만족하는 `g=1, h=2`를 찾아, 세번째 수식 결과(42)와 보너스 수식 결과(42)를 각각 한 줄씩 출력한다 |

---

### TC-02 인자를 전달해도 결과 불변

```bash
java [CP] 1 2 3
```

| 항목 | 내용 |
|---|---|
| 예상 출력 | `42`<br>`42` |
| 확인 사항 | 프로그램이 Command Line Arguments를 전혀 사용하지 않으므로, 임의의 인자를 전달해도 결과나 동작에 영향이 없다 |

---

### TC-03 반복 실행 시 동일 결과 (결정론적 확인)

```bash
java [CP]
java [CP]
```

| 항목 | 내용 |
|---|---|
| 예상 출력 | 두 번 모두 `42`<br>`42` |
| 확인 사항 | g, h를 난수가 아닌 전수 탐색으로 찾으므로 실행할 때마다 항상 같은 결과가 나온다 (`g=1, h=2` 조합이 유일해임을 간접 확인) |

---

## 전체 테스트 결과 체크리스트

| ID | 케이스 | 예상 출력 요약 | 통과 여부 |
|---|---|---|---|
| TC-01 | 기본 실행 (인자 없음) | 42 / 42 | ☐ |
| TC-02 | 인자 전달해도 결과 불변 | 42 / 42 | ☐ |
| TC-03 | 반복 실행 시 동일 결과 | 42 / 42 (양쪽 모두) | ☐ |
