package com.picpay.timelineservice.controllers;

import com.picpay.timelineservice.dtos.TimelineListDto;
import com.picpay.timelineservice.service.TimelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timelines")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Log4j2
public class TimelineController {

    private final TimelineService timelineService;

    @GetMapping("/account/{id}")
    public ResponseEntity<Page<TimelineListDto>> getAllByAccount(@PathVariable("id") String accountId,
                                                                 @PageableDefault(sort = "movementDate", direction = Sort.Direction.DESC)
                                                                 Pageable pageable) {
        log.debug("GET request to timeline by account: {}", accountId);

        return ResponseEntity.ok(timelineService.findAllByAccountId(accountId, pageable));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<TimelineListDto>> getAllByUser(@PathVariable("id") String userId,
                                                              @PageableDefault(sort = "movementDate", direction = Sort.Direction.DESC)
                                                              Pageable pageable) {
        log.debug("GET request to timeline by user: {}", userId);
        return ResponseEntity.ok(timelineService.findAllByUserId(userId, pageable));
    }
}
