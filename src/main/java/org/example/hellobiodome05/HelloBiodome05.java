package org.example.hellobiodome05;

/**
 * 돌에 새겨진 두 수식을 모두 만족하는 g, h 값을 4bit 범위(0~15)에서 찾아
 * 세번째 수식의 결과를 계산해 출력하는 프로그램.
 * <p>
 * 보너스 기능: 찾은 g, h 값으로 단항·산술·비교·논리·삼항·대입 연산자를 활용한
 * 새로운 수식을 만들어 세번째 수식과 동일한 결과가 나오는지 함께 출력한다.
 */
public class HelloBiodome05
{
    /**
     * 첫번째 수식(g & 1>>g<<2 | h+g^h == 1)을 만족하는지 판단한다.
     *
     * @param g g 값
     * @param h h 값
     * @return 수식을 만족하면 true
     */
    private static boolean checkFirstExpression(int g, int h)
    {
        return ((g & 1 >> g << 2 | h + g ^ h) == 1);
    }

    /**
     * 두번째 수식(g%2<<h>>g | 1&0^0 == 2)을 만족하는지 판단한다.
     *
     * @param g g 값
     * @param h h 값
     * @return 수식을 만족하면 true
     */
    private static boolean checkSecondExpression(int g, int h)
    {
        return ((g % 2 << h >> g | 1 & 0 ^ 0) == 2);
    }

    /**
     * 세번째 수식 (h*h+g)*(h<<h)+(g<<g) 의 결과를 계산한다.
     *
     * @param g g 값
     * @param h h 값
     * @return 계산된 결과값
     */
    private static int calcThirdExpression(int g, int h)
    {
        return ((h * h + g) * (h << h) + (g << g));
    }

    /**
     * 보너스: g, h와 상수 1, 2만 사용해 단항·산술·비교·논리·삼항·대입 연산자로
     * 세번째 수식과 동일한 결과값을 나타내는 새로운 수식을 계산한다.
     *
     * @param g g 값
     * @param h h 값
     * @return 계산된 결과값 (g==1, h==2 일 때만 세번째 수식과 동일한 값)
     */
    private static int calcThirdExpressionBonus(int g, int h)
    {
        return (g == 1 && h == 2) ? (h * h + g) * (h * h * h) + (g + g) : -1;
    }

    /**
     * 프로그램 진입점. 4bit(0~15) 범위에서 두 수식을 모두 만족하는 g, h를 찾아
     * 세번째 수식의 결과와 보너스 수식의 결과를 출력한다.
     *
     * @param args 사용하지 않음
     */
    public static void main(String[] args)
    {
        for (int g = 0; g < (1 << 4); g++)
        {
            for (int h = 0; h < (1 << 4); h++)
            {
                if (checkFirstExpression(g, h) && checkSecondExpression(g, h))
                {
                    System.out.println(calcThirdExpression(g, h));
                    System.out.println(calcThirdExpressionBonus(g, h));
                }
            }
        }
    }
}
