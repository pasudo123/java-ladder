package ladder.view;

import java.util.Scanner;

public class InputView {
    private static Scanner scan = new Scanner(System.in);

    private InputView() {
    }  // prevent making instance

    public static String getPeopleNames() {
        System.out.println("참여할 사람 이름을 입력하세요. (이름은 쉼표(,)로 구분하세요)");
        return scan.next();
    }

    public static String getGameResult() {
        System.out.println();
        System.out.println("실행 결과를 입력하세요. (결과는 쉼표(,)로 구분하세요)");
        return scan.next();
    }

    public static int getLadderHeight() {
        System.out.println();
        System.out.println("최대 사다리 높이는 몇 개인가요?");
        return scan.nextInt();
    }

    public static String getAsk() {
        System.out.println();
        System.out.println("결과를 보고 싶은 사람은?");
        return scan.next();
    }

}