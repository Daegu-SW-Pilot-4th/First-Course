package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * HelloBiodome01 통합 테스트
 *
 * [테스트 방식]
 *  buildGreeting()은 private이며 System.exit()을 직접 호출하는 부수효과 함수라
 *  같은 JVM 안에서 단위 테스트로 호출할 수 없다 (private 접근 불가 + exit()이 테스트
 *  러너 자체를 종료시킴). 대신 ProcessBuilder로 컴파일된 프로그램을 별도 프로세스로
 *  실행하고, 표준 출력과 종료 코드를 검증하는 통합 테스트로 구성한다.
 *
 * [테스트에서 제외한 케이스]
 *  - null 입력: main()의 args 배열 원소는 커맨드라인에서 절대 null이 될 수 없어
 *    CLI로는 재현 불가능한 시나리오다.
 *  - null 바이트(\0) 포함 입력: OS 프로세스 인자로 null 바이트를 전달할 수 없어
 *    ProcessBuilder로 재현 불가능하다.
 *  - Unicode C1 제어 문자(U+009B 등) 포함 입력: Windows에서 프로세스 인자는
 *    JVM의 sun.jnu.encoding(시스템 코드페이지, 예: MS949)으로 인코딩되어 전달되는데
 *    이 인코딩에 U+009B가 존재하지 않아 자식 프로세스에 도달하기 전에 '?'로
 *    깨진다. sun.jnu.encoding은 JVM 실행 중 -D 옵션으로 바꿀 수 없어 해결 불가능한
 *    플랫폼 제약이다. (buildGreeting()의 `\p{Cc}` 정규식 자체는 U+0080-U+009F
 *    범위를 정상적으로 제거하도록 구현되어 있음 — 코드 리뷰로 확인)
 */
class HelloBiodome01Test
{
    private record ProcessResult(String output, int exitCode) {}

    private static ProcessResult runProgram(String... args) throws IOException, InterruptedException
    {
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

        List<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-Dfile.encoding=UTF-8");
        command.add("-Dstdout.encoding=UTF-8");
        command.add("-cp");
        command.add(System.getProperty("java.class.path"));
        command.add("org.example.hellobiodome01.HelloBiodome01");
        command.addAll(List.of(args));

        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).strip();
        int exitCode = process.waitFor();
        return new ProcessResult(output, exitCode);
    }

    // ====================================================================
    // 기능 테스트 - 정상 입력
    // ====================================================================

    @Test
    @DisplayName("단일 이름 입력 - 인사말 정상 생성")
    void 단일_이름_인사말_정상_생성() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("홍길동");
        assertEquals("\"홍길동\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
        assertEquals(0, result.exitCode());
    }

    @Test
    @DisplayName("영문 이름 입력")
    void 영문_이름_입력() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("Alex");
        assertEquals("\"Alex\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
        assertEquals(0, result.exitCode());
    }

    @Test
    @DisplayName("띄어쓰기 있는 이름 - 여러 인자로 전달되어도 전체 이름 출력")
    void 띄어쓰기_있는_이름_전체_출력() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("Alex", "Lee");
        assertEquals("\"Alex Lee\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
        assertEquals(0, result.exitCode());
    }

    @Test
    @DisplayName("한글+영문 혼합 이름")
    void 한글_영문_혼합_이름() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("Kim", "철수");
        assertEquals("\"Kim 철수\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
        assertEquals(0, result.exitCode());
    }

    @Test
    @DisplayName("이름이 따옴표로 감싸져서 출력됨 (보너스)")
    void 이름_따옴표_감싸기_보너스() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("제리");
        assertEquals("\"제리\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
    }

    // ====================================================================
    // 경계값 테스트 - 10글자 절단 (보너스 기능)
    // ====================================================================

    @Test
    @DisplayName("1글자 이름 - 절단 없이 그대로 출력")
    void 한_글자_이름_절단_없음() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("김");
        assertEquals("\"김\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
    }

    @Test
    @DisplayName("정확히 10글자 이름 - 절단 없이 출력 (경계값)")
    void 정확히_10글자_이름_절단_없음() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("1234567890");
        assertEquals("\"1234567890\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
    }

    @Test
    @DisplayName("11글자 이름 - 10글자까지만 출력 (경계값)")
    void 열한_글자_이름_10글자로_절단() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("12345678901");
        assertEquals("\"1234567890\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
    }

    @Test
    @DisplayName("긴 영문 이름 - 절단 후 정확히 10글자")
    void 긴_이름_절단_후_10글자_확인() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("aaaaabbbbbccccc");
        assertEquals("\"aaaaabbbbb\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
    }

    @Test
    @DisplayName("README 예시: 긴 한글 이름이 10글자에서 절단됨")
    void README_예시_긴_한글_이름_절단() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("아돌프 블레인 찰스 데이비드 얼 프레더릭");
        assertEquals("\"아돌프 블레인 찰스\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
    }

    // ====================================================================
    // 유효성 검사
    // ====================================================================

    @Test
    @DisplayName("인자 없이 실행 - 안내 문구 출력 후 종료")
    void 인자_없이_실행() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram();
        assertEquals("입력한 이름을 확인해주세요!", result.output());
        assertEquals(1, result.exitCode());
    }

    @Test
    @DisplayName("공백 전용 입력 - 유효하지 않은 이름으로 거부")
    void 공백_전용_입력_거부() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram(" ");
        assertEquals("입력한 이름을 확인해주세요!", result.output());
        assertEquals(1, result.exitCode());
    }

    // ====================================================================
    // [보안] 입력 검증
    // ====================================================================

    @Test
    @DisplayName("[보안] 개행 문자 포함 - 로그 인젝션 방지")
    void 보안_개행_문자_로그_인젝션() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("홍길동\n[ADMIN] 권한 부여");
        assertEquals("\"홍길동[ADMIN]\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
    }

    @Test
    @DisplayName("[보안] ANSI 이스케이프 코드 포함 - 터미널 출력 조작 방지")
    void 보안_ANSI_이스케이프_코드_터미널_인젝션() throws IOException, InterruptedException
    {
        ProcessResult result = runProgram("[31m빨간글씨[0m");
        assertEquals("\"빨간글씨\"님, 스프링와트에 오신 걸 환영합니다!", result.output());
    }
}
