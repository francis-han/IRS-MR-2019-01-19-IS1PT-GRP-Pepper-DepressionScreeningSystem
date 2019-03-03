package phq;

import phq.dto.AnswerDto;
import phq.dto.LastAnswerDto;
import phq.dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RequestController {

    PersonDto currentPerson;

    List<AnswerDto> answerDtoList = new ArrayList();

    @Autowired
    PhqCheckService phqCheckService;


    @RequestMapping("/")
    public String welcome() {
        //return "static/index.html";
        return "index";
    }

    @GetMapping("/start")
    public String start(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        model.addAttribute("name", name);
        PersonDto personDto = new PersonDto();
        model.addAttribute("personDto", personDto);
        return "personal_info";
    }

    /*@GetMapping("/next")
    public String nextQuestion(@ModelAttribute AnswerDto currentAnswer, @RequestParam(name="currentQ", defaultValue="1") int currentQ, Model model) {
        model.addAttribute("currentQ", currentQ);

        System.out.println("--------------------" + currentAnswer.getAnswer());
        AnswerDto answerDto = new AnswerDto();
        model.addAttribute("answerDto", answerDto);

        return "q"+(currentQ+1);
    }*/

    @PostMapping("/next")
    public String nextQuestion(@ModelAttribute AnswerDto answerDto, @RequestParam("currentSeq") String currentSeq, Model model) {

        System.out.println("currentSeq========" + currentSeq);
        System.out.println("--------------------" + answerDto.getAnswer());

        answerDtoList.add(answerDto);

        if(isPhq2CheckEligible(currentSeq)) {
            if ( phqCheckService.isPhq2Stopped(answerDtoList) ) {
                return "ph2_complete";
            }
        }
        AnswerDto nextAnswerDto = new AnswerDto();

        int nextSeq = Integer.parseInt(currentSeq) + 1;

        if( nextSeq == 10 ) {
            LastAnswerDto lastAnswerDto = new LastAnswerDto();
            model.addAttribute("lastAnswerDto", lastAnswerDto);
        }

        model.addAttribute("answerDto", nextAnswerDto);

        return "q"+ nextSeq;
    }

    private boolean isPhq2CheckEligible(String currentSeq) {
        return Integer.parseInt(currentSeq) == 2;
    }


    @PostMapping("/personal_info_submit")
    public String submitPersonalInfo(@ModelAttribute PersonDto personDto, Model model) {

        currentPerson = personDto;
        answerDtoList.clear();

        AnswerDto answerDto = new AnswerDto();
        answerDto.setQuestionNum("1");
        model.addAttribute("answerDto", answerDto);

        boolean isLowRisk = phqCheckService.isLowRisk(personDto);
        System.out.println("isLowRisk=======" + isLowRisk);

        return isLowRisk ? "low_risk_profile" : "q1";
    }

    @GetMapping("/submit")
    public String submit(@ModelAttribute AnswerDto answerDto, Model model) {

        answerDtoList.add(answerDto);

        int finalScore = phqCheckService.getFinalScore(answerDtoList);

        boolean isAlert = phqCheckService.isAlertFromPhq9(answerDtoList);
        //boolean isAlert = phqCheckService.isAlert(answerDtoList);

        model.addAttribute("finalScore", finalScore);
        model.addAttribute("isAlert", isAlert);

        return isAlert ? "complete_blue" : "complete";
    }

    @PostMapping("/submit")
    public String submit11(Model model) {
        return "complete";
    }

/*
    @GetMapping("/genericAnswer")
    public String getGenericAnswer() {
        return "genericAnswer";
    }

    @GetMapping("/q10Answer")
    public String getQ10Answer() {
        return "q10Answer";
    }

    @GetMapping("/finalAnswer")
    public String getFinalAnswer() {
        return "finalAnswer";
    }*/
}
