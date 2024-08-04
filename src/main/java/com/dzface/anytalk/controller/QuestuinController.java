package com.dzface.anytalk.controller;

import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.repository.QuestionRepository;
import com.dzface.anytalk.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.zip.DataFormatException;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RequiredArgsConstructor
@Controller
@RequestMapping("/support")
public class QuestuinController {
    private final QuestionService service;
    //게시글 리스트 조회
    @GetMapping("/question-list")
    public String getQuestionList(Model model){
        List<Question> questionList = this.service.getQuestionList();
        //model.addAttribute(String name, Object vlaue) value 객체를 name 이름으로 추가해줌 view 에서 사용
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
    // 상세 게시글 정보 조회
    @GetMapping(value = "/question-detail/{id}")
    public String getQuestionInfo(Model model, @PathVariable("id") Long id) throws DataFormatException {
        Question question = this.service.getQuestionInfo(id);
        model.addAttribute("question", question);
        return "question_detail";

    }
    // 루트 주소를 질문리스트 출력 게시판주소로 설정(localhost:8118/question/list)
    @GetMapping("/")
    public String root(){
        return "redirect:/question/list";
    }
}
