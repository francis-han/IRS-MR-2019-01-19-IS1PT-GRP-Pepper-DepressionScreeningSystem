package converter;

import com.phq9_final.phq9final.PHQ2Q;
import com.phq9_final.phq9final.PHQ9Q;
import phq.dto.AnswerDto;

public class AnswerConverter {
    public static PHQ2Q convertAnswer(AnswerDto answer1, AnswerDto answer2) {
        PHQ2Q phq2Question = new PHQ2Q();
        phq2Question.setPHQQ1( convertAnswerToDouble(answer1) );
        phq2Question.setPHQQ2( convertAnswerToDouble(answer2) );

        return phq2Question;
    }

    public static PHQ9Q convertAnswerToPhq9q(AnswerDto answer3, AnswerDto answer4, AnswerDto answer5,
                                             AnswerDto answer6, AnswerDto answer7, AnswerDto answer8, AnswerDto answer9) {
        PHQ9Q phq9Question = new PHQ9Q();
        phq9Question.setPHQQ3( convertAnswerToDouble(answer3) );
        phq9Question.setPHQQ4( convertAnswerToDouble(answer4) );
        phq9Question.setPHQQ5( convertAnswerToDouble(answer5) );
        phq9Question.setPHQQ6( convertAnswerToDouble(answer6) );
        phq9Question.setPHQQ7( convertAnswerToDouble(answer7) );
        phq9Question.setPHQQ8( convertAnswerToDouble(answer8) );
        phq9Question.setPHQQ9( convertAnswerToDouble(answer9) );

        return phq9Question;
    }

    public static double convertAnswerToDouble(AnswerDto answer) {
        return answer.getAnswerAsInt();
    }
}
