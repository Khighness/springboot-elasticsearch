package top.parak.service;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.parak.util.HttpParseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author KHighness
 * @since 2021-03-20
 */

@Service
public class ContentService {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient restHighLevelClient;

    public Boolean parseContent(String keywords) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        HttpParseUtil.parseJD(keywords).stream().forEach(
            content -> {
               bulkRequest.add(new IndexRequest("jd_goods")
                       .source(JSON.toJSONString(content), XContentType.JSON));
            }
        );
        BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulkItemResponses.hasFailures();
    }


    public List<Map<String, Object>> searchPage(String keywords, int pageNo, int pageSize) throws IOException {
        // 返回结果
        List<Map<String, Object>> list = new ArrayList<>();

        // 构建条件
        // term：精确匹配
        TermQueryBuilder tempQuery = QueryBuilders.termQuery("name", keywords);
        // match：模糊查询
        MatchAllQueryBuilder matchQuery = QueryBuilders.matchAllQuery();

        // 构建高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");

        // 构造查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(tempQuery)                                           // 查询条件
                .from(pageNo).size(pageSize)                                // 设置分页
                .timeout(new TimeValue(60, TimeUnit.SECONDS))      // 设置时间
                .highlighter(highlightBuilder);                            // 设置高亮

        // 构造请求
        SearchRequest searchRequest = new SearchRequest()
                .indices("jd_goods")                                        // 查询索引
                .source(sourceBuilder);                                     // 设置条件

        // 发出请求
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 结果处理
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField name = highlightFields.get("name");
            Map<String, Object> map = hit.getSourceAsMap();
            if (name != null) {
                // 将高亮字段替换为没有高亮的字段
                Text[] fragments = name.fragments();
                StringBuilder stringBuilder = new StringBuilder();
                String newName = "";
                for (Text text : fragments) {
                    stringBuilder.append(text);
                    newName += text;
                }
                map.put("name", newName);
                System.out.println(name);
                System.out.println(newName);
            }
            list.add(map);
        }
        return list;
    }

}
