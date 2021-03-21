package top.parak;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import top.parak.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author KHighness
 * @since 2021-03-20
 */

@SpringBootTest
public class ElasticSearchTest {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    // 创建索引
    @Test
    void test1() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("khighness");
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    // 是否存在
    @Test
    void test2() throws IOException {
        GetIndexRequest request = new GetIndexRequest("khighness");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 删除索引
    @Test
    void test3() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("khighness");
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    // 创建文档
    @Test
    void test4() throws IOException {
        User kHighness = User.builder().age(19).name("KHighness").build();
        User flowerK = User.builder().age(19).name("FlowerK").build();
        User rubbishK = User.builder().age(19).name("RubbishK").build();
        User unknownK = User.builder().age(19).name("UnknownK").build();
        IndexRequest request = new IndexRequest()
                .index("khighness").id("1").timeout(TimeValue.timeValueSeconds(1));
        request.source(JSON.toJSONString(kHighness), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    // 是否存在
    @Test
    void test5() throws IOException {
        GetRequest request = new GetRequest("khighness", "1");
        request.fetchSourceContext(new FetchSourceContext(false));
        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 文档信息
    @Test
    void test6() throws IOException {
        GetRequest request = new GetRequest("khighness", "1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        Map<String, Object> map = response.getSourceAsMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // 更新文档
    @Test
    void test7() throws IOException {
        UpdateRequest request = new UpdateRequest("khighness", "1");
        request.timeout("1s");
        User kHighness = User.builder().age(17).name("KHighness").build();
        request.doc(JSON.toJSONString(kHighness), XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    // 删除文档
    @Test
    void test8() throws IOException {
        DeleteRequest request = new DeleteRequest("khighness", "1");
        request.timeout("1s");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    // 批量插入
    @Test
    void test9() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        List<User> userList = new ArrayList<>();
        userList.add(User.builder().age(19).name("KHighness").build());
        userList.add(User.builder().age(19).name("FlowerK").build());
        userList.add(User.builder().age(19).name("RubbishK").build());
        userList.add(User.builder().age(19).name("UnknownK").build());

        for (int i = 0; i < userList.size(); i++) {
            bulkRequest.add(new IndexRequest("khighness")
                    .id("" + (i + 1)).source(JSON.toJSONString(userList.get(i)), XContentType.JSON));
        }
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures()); // 返回false代表成功
    }

    // 查询
    @Test
    void test10() throws IOException {
        // 搜索请求
        SearchRequest request = new SearchRequest("khighness");
        // 查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 构建高亮
        builder.highlighter();
        // term：精确匹配
        TermQueryBuilder tempQuery = QueryBuilders.termQuery("age", "19");
        // match：模糊查询
        MatchAllQueryBuilder matchQuery = QueryBuilders.matchAllQuery();
        // 设置查询和限时
        builder.query(tempQuery).timeout(new TimeValue(60, TimeUnit.SECONDS));
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response.getHits()));
        for (SearchHit documentFields : response.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }
    }

    // 查询
    @Test
    void test11() throws IOException {
        // 搜索请求
        SearchRequest request = new SearchRequest("jd_goods");
        // 查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 构建高亮
        builder.highlighter();
        // term：精确匹配
        TermQueryBuilder tempQuery = QueryBuilders.termQuery("name", "python");
        // match：模糊查询
        MatchAllQueryBuilder matchQuery = QueryBuilders.matchAllQuery();
        // 设置查询和限时
        builder.query(tempQuery).timeout(new TimeValue(60, TimeUnit.SECONDS));
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response.getHits()));
        for (SearchHit documentFields : response.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }
    }

}
