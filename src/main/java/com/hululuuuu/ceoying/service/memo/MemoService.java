package com.hululuuuu.ceoying.service.memo;


import com.hululuuuu.ceoying.domain.memo.Memo;
import com.hululuuuu.ceoying.domain.memo.MemoRepository;
import com.hululuuuu.ceoying.myComponent.PageableDefault;
import com.hululuuuu.ceoying.web.dto.memo.MemoSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public Long saveMemo(MemoSaveRequestDto requestDto) {
        return memoRepository.save(requestDto.toEntity()).getId();
    }

    public Page<Memo> findMemoList(Pageable pageable) {
        pageable = PageableDefault.setPageableIdDesc(pageable);
        return memoRepository.findAll(pageable);
    }

    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }

}
