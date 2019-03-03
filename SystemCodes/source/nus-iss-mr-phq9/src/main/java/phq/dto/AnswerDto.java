package phq.dto;

public class AnswerDto {
    private String questionNum;
    private String answer="0";

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public String getAnswer() {
        return answer;
    }

    public int getAnswerAsInt() {
        return Integer.parseInt(answer);
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
