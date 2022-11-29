package com.picpay.timelineservice.service.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ElasticsearchReindexService<D, DTO> {

    void reindex(D document);

    Page<DTO> search(String id, Pageable pageable);
}
