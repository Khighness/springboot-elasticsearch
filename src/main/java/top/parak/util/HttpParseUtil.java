package top.parak.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import top.parak.entity.Content;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KHighness
 * @since 2021-03-20
 */

public class HttpParseUtil {

    public static List<Content> parseJD(String keywords) throws IOException {
        List<Content> list = new ArrayList<>();
        String url = "https://search.jd.com/Search?keyword=" + keywords + "&enc=utf-8";
        Document document = Jsoup.parse(new URL(url), 30000);
        Element element = document.getElementById("J_goodsList");
        Elements elements = element.getElementsByTag("li");
        for (Element ele : elements) {
            String name = ele.getElementsByClass("p-name").eq(0).text();
            // 关于图片特别多的网站，所有的图片都是延迟加载的
            String img = ele.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = ele.getElementsByClass("p-price").eq(0).text();
            Content content = new Content(name, img, price);
            list.add(content);
        }
        return list;
    }

}
