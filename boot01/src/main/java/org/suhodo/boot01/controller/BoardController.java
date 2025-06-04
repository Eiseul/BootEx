package org.suhodo.boot01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.suhodo.boot01.dto.PageRequestDTO;
import org.suhodo.boot01.service.BoardService;

import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        
    }
}
