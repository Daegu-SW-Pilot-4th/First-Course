package org.example.hellobiodome06;

/**
 * 두 개의 유전자 코드를 Command Line Arguments로 입력받아
 * 두 코드가 동일한지 판별하는 프로그램.
 * <p>
 * 보너스 기능: 첫 번째 유전자 코드가 두 번째 유전자 코드에 부분적으로
 * 포함되는지(부분 문자열 여부)도 함께 판별한다.
 * <p>
 * String.equals(), StringBuilder, for 반복문 사용이 금지되어 있어
 * 문자 단위 비교를 while 반복문으로 직접 구현한다.
 */
public class HelloBiodome06
{
    /**
     * gene1이 gene2에 부분 문자열로 포함되는지 판단한다.
     * gene1이 gene2와 길이가 같거나 더 길면 "부분" 포함이 아니므로 false를 반환한다.
     *
     * @param gene1 포함 여부를 확인할 유전자 코드
     * @param gene2 대상 유전자 코드
     * @return gene1이 gene2에 부분 문자열로 포함되면 true
     */
    private static boolean isSubGene(String gene1, String gene2)
    {
        try
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
        } catch (IndexOutOfBoundsException e) {
            System.out.println("문자열 길이를 벗어나 비교하고 있습니다!!");
            System.exit(1);
        }
        return (false);
    }

    /**
     * 두 유전자 코드가 문자 단위로 완전히 동일한지 판단한다.
     * String.equals()를 사용하지 않고 charAt()으로 순차 비교한다.
     *
     * @param gene1 비교할 첫 번째 유전자 코드
     * @param gene2 비교할 두 번째 유전자 코드
     * @return 두 코드가 동일하면 true
     */
    private static boolean isEqualGenes(String gene1, String gene2)
    {
        try
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
        } catch (IndexOutOfBoundsException e) {
            System.out.println("문자열 길이를 벗어나 비교하고 있습니다!!");
            System.exit(1);
        }
        return (true);
    }

    /**
     * 유전자 코드 형식(숫자·영어 소문자로만 구성, 5자 이상 20자 이하)이 유효한지 판단한다.
     *
     * @param gene 형식을 검사할 유전자 코드
     * @return 형식이 유효하면 true
     */
    private static boolean checkGeneString(String gene)
    {
        if (gene == null || gene.length() < 5 ||  gene.length() > 20)
            return (false);

        try
        {
            int idx = 0;
            while (idx < gene.length())
            {
                if (('0' <= gene.charAt(idx) &&  gene.charAt(idx) <= '9') ||
                        ('a' <= gene.charAt(idx) &&  gene.charAt(idx) <= 'z'))
                    idx++;
                else
                    return (false);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("문자열 길이를 벗어나 비교하고 있습니다!!");
            System.exit(1);
        }
        return (true);
    }

    /**
     * 프로그램 진입점. 2개의 Command Line Arguments(유전자 코드 2개)를 입력받아
     * 동일 여부와 포함 여부(보너스)를 출력한다.
     * 인자가 2개가 아니거나 형식이 유효하지 않으면 안내 문구를 출력하고 종료한다.
     *
     * @param args 사용자가 입력한 유전자 코드 2개
     */
    public static void main(String[] args)
    {
        if (args.length == 2)
        {
            if (checkGeneString(args[0]) &&  checkGeneString(args[1]))
            {
                if (isEqualGenes(args[0], args[1]))
                    System.out.println("-> 동일한 유전자 코드입니다.");
                else
                    System.out.println("-> 일치하지 않습니다.");
                if (isSubGene(args[0], args[1]))
                    System.out.println("-> 부분적으로 포함됩니다.");
                else
                    System.out.println("-> 포함되지 않습니다.");
            }
            else
            {
                System.out.println("-> 올바른 두 개의 유전자 코드를 입력해주세요.");
                System.exit(1);
            }
        }
        else
        {
            System.out.println("-> 두 개의 유전자 코드를 입력해주세요.");
            System.exit(1);
        }
    }
}
