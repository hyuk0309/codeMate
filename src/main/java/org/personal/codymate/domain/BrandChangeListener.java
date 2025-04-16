package org.personal.codymate.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrandChangeListener {

	private final CodyService codyService;

	@Async
	@EventListener
	void onBrandChange(BrandChangeEvent event) {
		codyService.handleEvent();
	}

}
