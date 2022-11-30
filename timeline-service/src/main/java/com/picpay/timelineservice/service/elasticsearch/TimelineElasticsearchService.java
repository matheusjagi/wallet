package com.picpay.timelineservice.service.elasticsearch;

import com.picpay.timelineservice.dtos.TimelineListDto;
import com.picpay.timelineservice.models.TimelineDocument;
import com.picpay.timelineservice.repositories.TimelineRepositorySearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Log4j2
public class TimelineElasticsearchService implements ElasticsearchReindexService<TimelineDocument, TimelineListDto> {

    private final TimelineRepositorySearch timelineRepositorySearch;
    private final ModelMapper mapper;

    @Override
    @TransactionalEventListener(fallbackExecution = true)
    public void reindex(TimelineDocument document) {
        log.info("Starting index from timeline by id: {}", document.getId());
        timelineRepositorySearch.save(document);
    }

    @Override
    public Page<TimelineListDto> search(String id, Pageable pageable) {
        return timelineRepositorySearch
                .findAllById(id, pageable)
                .map(document -> mapper.map(document, TimelineListDto.class));
    }

    public Page<TimelineListDto> searchByAccountId(String accountId, Pageable pageable) {
        return timelineRepositorySearch
                .findAllBySourceAccountId(accountId, pageable)
                .map(document -> mapper.map(document, TimelineListDto.class));
    }

    public Page<TimelineListDto> searchByUserId(String userId, Pageable pageable) {
        return timelineRepositorySearch
                .findAllByUserId(userId, pageable)
                .map(document -> mapper.map(document, TimelineListDto.class));
    }
}
