package com.hululuuuu.ceoying;

import com.hululuuuu.ceoying.domain.memo.Memo;
import com.hululuuuu.ceoying.domain.memo.MemoRepository;
import com.hululuuuu.ceoying.web.dto.memo.MemoSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemoApiController {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemoRepository memoRepository;

    @After
    public void tearDown() throws Exception {
        memoRepository.deleteAll();
    }

    @Test
    public void 메모_등록된다() {

        String link = "aa";
        String content = "aaa";
        String name = "김씨";

        MemoSaveRequestDto requestDto = MemoSaveRequestDto.builder()
                .link(link)
                .content(content)
                .name(name)
                .build();

        String url = "http://localhost:" + port + "/memo";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Memo> list = memoRepository.findAll();

        Memo memo = list.get(0);
        assertThat(memo.getContent()).isEqualTo(content);
        assertThat(memo.getLink()).isEqualTo(link);
        assertThat(memo.getName()).isEqualTo(name);

    }


}
