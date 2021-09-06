package com.hululuuuu.ceoying.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hululuuuu.ceoying.domain.memo.Memo;
import com.hululuuuu.ceoying.domain.memo.MemoRepository;
import com.hululuuuu.ceoying.web.dto.memo.MemoSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemoApiControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private MemoRepository memoRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        memoRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void 메모_등록된다() throws Exception {

        String link = "aa";
        String content = "aaa";
        String name = "김씨";

        MemoSaveRequestDto requestDto = MemoSaveRequestDto.builder()
                .link(link)
                .content(content)
                .name(name)
                .build();

        String url = "http://localhost:" + port + "/manage/memo";

        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Memo> list = memoRepository.findAll();

        Memo memo = list.get(0);
        assertThat(memo.getContent()).isEqualTo(content);
        assertThat(memo.getLink()).isEqualTo(link);
        assertThat(memo.getName()).isEqualTo(name);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void memo_삭제된다() throws Exception {
        //save
        String link = "aa";
        String content = "aaa";
        String name = "김씨";

        Memo memo = memoRepository.save(Memo.builder()
                .link(link)
                .content(content)
                .name(name)
                .build());

        Long savedId = memo.getId();

        String url = "http://localhost:" + port + "/manage/memo/" + savedId;

        //when
        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //then
        List<Memo> memoList = memoRepository.findAll();

        assertThat(memoList.size()).isEqualTo(0);
    }
}
