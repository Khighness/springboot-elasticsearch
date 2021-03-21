package top.parak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.parak.service.ContentService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author KHighness
 * @since 2021-03-20
 */

@RestController
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/parse/{keywords}")
    public boolean parse(@PathVariable("keywords") String keywords) throws IOException {
        return contentService.parseContent(keywords);
    }

    @GetMapping("/search/{keywords}/{pageNo}/{pageSize}")
    public List<Map<String, Object>> search(@PathVariable("keywords") String keywords,
                                            @PathVariable("pageNo") Integer pageNo,
                                            @PathVariable("pageSize") Integer pageSize) throws IOException {
        if (pageNo == null)
            throw new IllegalArgumentException("pageNo is null");
        if (pageSize == null)
            throw new IllegalArgumentException("pageSize is illegal");
        return contentService.searchPage(keywords, pageNo, pageSize);
    }

}
