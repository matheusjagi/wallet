package com.picpay.timelineservice.service;

import com.picpay.timelineservice.dtos.TimelineListDto;
import com.picpay.timelineservice.service.elasticsearch.TimelineElasticsearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TimelineService {

    private final TimelineElasticsearchService timelineElasticsearchService;

    public Page<TimelineListDto> findAllByAccountId(String accountId, Pageable pageable) {
        return timelineElasticsearchService.searchByAccountId(accountId, pageable);
    }

    public Page<TimelineListDto> findAllByUserId(String userId, Pageable pageable) {
        return timelineElasticsearchService.searchByUserId(userId, pageable);
    }
}
