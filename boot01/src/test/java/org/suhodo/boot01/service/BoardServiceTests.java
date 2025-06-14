package org.suhodo.boot01.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.suhodo.boot01.dto.BoardDTO;
import org.suhodo.boot01.dto.BoardImageDTO;
import org.suhodo.boot01.dto.BoardListAllDTO;
import org.suhodo.boot01.dto.PageRequestDTO;
import org.suhodo.boot01.dto.PageResponseDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardServiceTests {
    
    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user00")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("bno: " + bno);
    }

    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                    .bno(101L)
                    .title("Updated...101")
                    .content("Updated content 101...")
                    .build();
                
        boardService.modify(boardDTO);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                        .type("tcw")
                        .keyword("1")
                        .page(1)
                        .size(10)
                        .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
    }

    @Test
    public void testRegisterWithImages(){
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("File...Sample Title...")
                .content("Sample Content...")
                .writer("user00")
                .build();

        boardDTO.setFileNames(Arrays.asList(UUID.randomUUID() + "_aaa.jpg", 
                                            UUID.randomUUID() + "_bbb.jpg", 
                                            UUID.randomUUID() + "_ccc.jpg"));
        Long bno = boardService.register(boardDTO);

        log.info("bno: " + bno);
    }

    @Test
    public void testReadAll(){
        Long bno = 101L;

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        for(String fileName : boardDTO.getFileNames()){
            log.info(fileName);
        }
    }

    @Test
    public void testModify1(){
        BoardDTO boardDTO = BoardDTO.builder()
                    .bno(309L)
                    .title("Updated.....309")
                    .content("Updated content 309......")
                    .build();

        boardDTO.setFileNames(Arrays.asList(UUID.randomUUID() + "_zzz.jpg"));

        boardService.modify(boardDTO);
    }

    @Test
    public void testRemoveAll(){
        Long bno = 309L;

        boardService.remove(bno);
    }

    @Test
    public void testListWithAll(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                    .page(1)
                    .size(10)
                    .build();

        PageResponseDTO<BoardListAllDTO> responseDTO = boardService.listWithAll(pageRequestDTO);

        List<BoardListAllDTO> dtoList = responseDTO.getDtoList();

        dtoList.forEach(boardListAllDTO -> {
            log.info(boardListAllDTO.getBno() + ":" + boardListAllDTO.getTitle());

            if(boardListAllDTO.getBoardImages() != null){
                for(BoardImageDTO boardImage : boardListAllDTO.getBoardImages()){
                    log.info(boardImage);
                }
            }

            log.info("-----------------------------------");
        });
    }
}
