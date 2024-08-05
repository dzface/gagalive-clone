package com.dzface.anytalk.controller;

import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/support")
public class QuestionController {
    private final QuestionService questionService;

    //게시글 작성
    @PostMapping("create-question")
    public String createQuestion(@RequestParam(value="title") String title, @RequestParam(value="content") String content){
        this.createQuestion(title, content);
        return "redirect:/question/list";
    }
    //게시글 리스트 조회
    @GetMapping("/question-list")
    public String getQuestionList(Model model){
        List<Question> questionList = this.questionService.getQuestionList();
        //model.addAttribute(String name, Object vlaue) value 객체를 name 이름으로 추가해줌 view 에서 사용
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
    // 페이지 리스트 조회
    @GetMapping("/page-list")
    public String pageList(Model model, @RequestParam(value="page", defaultValue = "0") int page){
        Page<Question> paging = this.questionService.getPageList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }
    // 상세 게시글 정보 조회
    @GetMapping(value = "/question-detail/{id}")
    public String getQuestionInfo(Model model, @PathVariable("id") Long id) throws DataFormatException {
        Question question = this.questionService.getQuestionInfo(id);
        model.addAttribute("question", question);
        return "question_detail";

    }
    // 루트 주소를 질문리스트 출력 게시판주소로 설정(localhost:8118/question/list)
    @GetMapping("/")
    public String root(){
        return "redirect:/question/list";
    }
}
