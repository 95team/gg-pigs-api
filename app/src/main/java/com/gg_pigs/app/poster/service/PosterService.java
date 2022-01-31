package com.gg_pigs.app.poster.service;

import com.gg_pigs.app.poster.dto.PosterDto;
import com.gg_pigs.app.poster.entity.Poster;
import com.gg_pigs.app.poster.entity.PosterNewEvent;
import com.gg_pigs.app.poster.repository.PosterRepository;
import com.gg_pigs.app.posterType.entity.PosterType;
import com.gg_pigs.app.posterType.repository.PosterTypeRepository;
import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PosterService {

    private final UserRepository userRepository;
    private final PosterRepository posterRepository;
    private final PosterTypeRepository posterTypeRepository;

    private final PosterEventHandler posterEventHandler;

    /** CREATE */
    @Transactional
    public PosterDto.Create.ResponseDto create(PosterDto.Create.RequestDto requestDto) {
        User user = userRepository.findUserByEmail(requestDto.getUserEmail()).orElse(null);
        PosterType posterType = posterTypeRepository.findPosterTypeByType(requestDto.getPosterType()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        Poster poster = requestDto.toEntity(user, posterType);
        poster.changeReviewStatusToNew();
        poster.changeIsActivatedToDeactivated();

        poster = posterRepository.save(poster);

        posterEventHandler.handle(PosterNewEvent.of(poster));

        return PosterDto.Create.ResponseDto.of(poster);
    }

    /** READ */
    public PosterDto.Read.ResponseDto read(Long posterId) {
        Poster poster = posterRepository.findByIdWithFetch(posterId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        return PosterDto.Read.ResponseDto.of(poster);
    }

    public List<PosterDto.Read.ResponseDto> readAll(PosterDto.Read.SearchConditionDto searchCondition) {
        List<Poster> posters = posterRepository.findAllByCondition(searchCondition);

        return posters.stream().map(PosterDto.Read.ResponseDto::of).collect(Collectors.toList());
    }

    /** UPDATE */
    @Transactional
    public PosterDto.Update.ResponseDto update(Long posterId, PosterDto.Update.RequestDto requestDto) {
        User user = userRepository.findUserByEmail(requestDto.getUserEmail()).orElse(null);
        PosterType posterType = posterTypeRepository.findPosterTypeByType(requestDto.getPosterType()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        Poster poster = posterRepository.findById(posterId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));
        Poster posterToUpdate = requestDto.toEntity(user, posterType);

        poster.changePoster(posterToUpdate);

        return PosterDto.Update.ResponseDto.of(poster);
    }


    /** DELETE */
    public void delete(Long posterId) {
        posterRepository.deleteById(posterId);
    }
}
