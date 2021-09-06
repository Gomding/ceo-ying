package com.hululuuuu.ceoying.service.memo;


import com.hululuuuu.ceoying.domain.memo.Memo;
import com.hululuuuu.ceoying.domain.memo.MemoRepository;
import com.hululuuuu.ceoying.myComponent.PageableDefault;
import com.hululuuuu.ceoying.web.dto.memo.MemoResponseDto;
import com.hululuuuu.ceoying.web.dto.memo.MemoSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemoService {

    private final MemoRepository memoRepository;

    public Long saveMemo(MemoSaveRequestDto requestDto) {
        return memoRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public Page<MemoResponseDto> findMemoList(Pageable pageable) {
        pageable = PageableDefault.setPageableIdDesc(pageable);
        Page<Memo> list = memoRepository.findAll(pageable);
        return list.map(MemoResponseDto::new);
    }

    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }
}
