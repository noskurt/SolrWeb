package edu.anadolu;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;

@SolrDocument(solrCoreName = "signal")
public class Article {

    private
    @Id
    @Indexed
    @Field
    String id;

    private
    @Field("media-type")
    @Indexed("media-type")
    String type;

    private
    @Field("source")
    @Indexed("source")
    String source;

    private
    @Field("published")
    @Indexed("published")
    Date published;

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    private
    @Field("title")
    @Indexed("title")
    String title;

    private
    @Field("content")
    @Indexed("content")
    String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Article [id=" + id + ", source=" + source + "]";
    }
}