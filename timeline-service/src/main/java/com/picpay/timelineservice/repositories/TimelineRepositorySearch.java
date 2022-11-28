package com.picpay.timelineservice.repositories;

import com.picpay.timelineservice.models.TimelineDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TimelineRepositorySearch extends ElasticsearchRepository<TimelineDocument, UUID> {

    Page<TimelineDocument> findAllById(String id, Pageable pageable);

    Page<TimelineDocument> findAllBySourceAccountId(String accountId, Pageable pageable);

    Page<TimelineDocument> findAllByUserId(String userId, Pageable pageable);
}
