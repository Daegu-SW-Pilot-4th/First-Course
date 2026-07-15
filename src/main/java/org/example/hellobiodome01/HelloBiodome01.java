package org.example.hellobiodome01;

/**
 * 사용자 이름을 Command Line Arguments로 입력받아 맞춤 환영 인사를 출력하는 프로그램.
 * <p>
 * 보너스 기능: 이름을 따옴표로 감싸 출력하며, 10글자를 초과하는 이름은 절단한다.
 * length() · substring() · 반복문을 사용하지 않고 정규식으로 구현한다.
 */
public class HelloBiodome01
{
    /**
     * 입력 문자열을 보안 정제 후 환영 인사말 문자열을 반환한다.
     * null·ANSI 이스케이프·C0·C1 제어 문자를 제거하고, 공백 전용 입력을 거부한다.
     *
     * @param input 사용자로부터 전달받은 원시 이름 문자열 (null 허용)
     * @return 환영 인사말 또는 재입력 안내 문구
     */
    private static void buildGreeting(String input)
    {
        if (input == null)
        {
            System.out.println("입력한 이름을 확인해주세요!");
            System.exit(1);
        }

        // ANSI CSI 시퀀스 제거 (터미널 인젝션 방지): ESC[ ... m 패턴
        String sanitized = input.replaceAll("\033\\[[0-9;]*[A-Za-z]", "");
        // Unicode 제어 문자 제거 (C0·C1·DEL — 로그 인젝션·터미널 조작·null 바이트 방지)
        sanitized = sanitized.replaceAll("[\\p{Cc}]", "").trim();

        if (sanitized.isEmpty())
        {
            System.out.println("입력한 이름을 확인해주세요!");
            System.exit(1);
        }

        String name = sanitized.replaceAll("^(.{1,10}).*$", "$1");
        System.out.println("\"" + name + "\"님, 스프링와트에 오신 걸 환영합니다!");
    }

    /**
     * 프로그램 진입점. Command Line Arguments에서 이름을 읽어 환영 인사를 출력한다.
     * 인자가 없을 경우 재입력 안내 문구를 출력하고 종료한다.
     *
     * @param args 사용자가 입력한 이름 토큰 (공백 포함 이름은 복수 인자로 전달됨)
     */
    public static void main(String[] args)
    {
        if (args.length > 0)
        {
            String name = String.join(" ", args);
            buildGreeting(name);
        }
        else
        {
            System.out.println("입력한 이름을 확인해주세요!");
            System.exit(1);
        }
    }
}
