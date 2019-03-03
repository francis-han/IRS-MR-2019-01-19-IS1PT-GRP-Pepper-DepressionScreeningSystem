package phq;

import com.phq9_final.phq9final.*;
import converter.AnswerConverter;
import converter.PatientConverter;
import org.springframework.beans.factory.annotation.Autowired;
import phq.dto.AnswerDto;
import org.springframework.stereotype.Service;
import phq.dto.PersonDto;

import java.util.List;

@Service
public class PhqCheckService {

    //@Autowired
    private Phq9Integration phq9Integration = new Phq9Integration();

    public boolean isLowRisk(PersonDto person) {

        PreScreenR result = phq9Integration.computePreScreenResult( PatientConverter.convertFromPersonDto(person) );
        return result == null;
    }

    public boolean isPhq2Stopped(List<AnswerDto> answerDtoList) {

        AnswerDto firstAnswer = answerDtoList.get(0);
        AnswerDto secondAnswer = answerDtoList.get(1);


        PHQ2Q phq2q = AnswerConverter.convertAnswer(firstAnswer, secondAnswer);

        PHQ2R result = phq9Integration.computePHQ2Result(phq2q);

        //return Integer.parseInt(firstAnswer.getAnswer()) > 1 || Integer.parseInt(secondAnswer.getAnswer()) > 1;
        return result == null;
    }



    public int getFinalScore(List<AnswerDto> answerDtoList) {
        int finalScore = 0;
        for(AnswerDto dto : answerDtoList ) {
            int answer = Integer.parseInt(dto.getAnswer());
            finalScore+=answer;
        }
        return finalScore;
    }

    public boolean isAlertFromPhq9(List<AnswerDto> answerDtoList) {

        PHQ2Q phq2q = AnswerConverter.convertAnswer(answerDtoList.get(0), answerDtoList.get(1));
        PHQ9Q phq9q = AnswerConverter.convertAnswerToPhq9q(
                answerDtoList.get(2),
                answerDtoList.get(3),
                answerDtoList.get(4),
                answerDtoList.get(5),
                answerDtoList.get(6),
                answerDtoList.get(7),
                answerDtoList.get(8)
        );

        PHQ9R phq9Result = phq9Integration.computePHQ9Result(phq2q, phq9q);

        return phq9Result != null;
    }

    public boolean isAlert(List<AnswerDto> answerDtoList) {

        AnswerDto lastAnswerDto = answerDtoList.get(answerDtoList.size()-1);
        int lastAnswer = lastAnswerDto.getAnswerAsInt();
        if( lastAnswer >= 1 ) {
            int alertNum = 0;
            for(int i=0; i<answerDtoList.size()-1; i++ ) {
                if(answerDtoList.get(i).getAnswerAsInt() >= 2) {
                    ++alertNum;
                }
            }
            return alertNum >= 4;
        }

        int alertNum = 0;
        for(int i=0; i<answerDtoList.size(); i++ ) {
            if(answerDtoList.get(i).getAnswerAsInt() >= 2) {
                ++alertNum;
            }
        }
        return alertNum >= 5;
    }
}
