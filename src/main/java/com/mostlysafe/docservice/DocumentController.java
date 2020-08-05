package com.mostlysafe.docservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

@RestController
@RequestMapping("/api/doc")
public class DocumentController {
    static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentManager manager;

    @Autowired
    DocumentController(@Nonnull final DocumentManager manager){
        this.manager = manager;
    }

    @GetMapping()
    public String index() {
        logger.debug("index method called.");

        return "index";
    }
}
