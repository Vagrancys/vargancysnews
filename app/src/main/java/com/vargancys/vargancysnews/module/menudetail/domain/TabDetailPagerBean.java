package com.vargancys.vargancysnews.module.menudetail.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/19
 * version:1.0
 */
public class TabDetailPagerBean {
    private DataEntity data;
    private int retcode;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }



    public static class DataEntity{
        private String countcommenturl;
        private String more;
        private String title;
        private ArrayList<News> news;
        private ArrayList<?> topic;

        private List<Topnews> topicnews;

        public ArrayList<?> getTopic() {
            return topic;
        }

        public void setTopic(ArrayList<?> topic) {
            this.topic = topic;
        }

        public List<Topnews> getTopicnews() {
            return topicnews;
        }

        public void setTopicnews(List<Topnews> topicnews) {
            this.topicnews = topicnews;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<DataEntity.News> getNews() {
            return news;
        }

        public void setNews(ArrayList<DataEntity.News> news) {
            this.news = news;
        }

        public String getCountcommenturl() {
            return countcommenturl;
        }

        public void setCountcommenturl(String countcommenturl) {
            this.countcommenturl = countcommenturl;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public class News{
            private int id;
            private String title;
            private String url;
            private String listimage;
            private String pubdate;
            private boolean comment;
            private String commenturl;
            private String type;
            private String commentList;

            public String getTitle() {
                return title;
            }

            public int getId() {
                return id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public String getCommentList() {
                return commentList;
            }

            public String getCommenturl() {
                return commenturl;
            }

            public String getListimage() {
                return listimage;
            }

            public String getPubdate() {
                return pubdate;
            }

            public String getType() {
                return type;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setComment(boolean comment) {
                this.comment = comment;
            }

            public void setCommentList(String commentList) {
                this.commentList = commentList;
            }

            public void setCommenturl(String commenturl) {
                this.commenturl = commenturl;
            }

            public void setListimage(String listimage) {
                this.listimage = listimage;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }
        }

        public class Topnews{
            private boolean comment;
            private String commentlist;
            private String commenturl;
            private int id;
            private String pubdate;
            private String title;
            private String topimage;
            private String type;
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public String getPubdate() {
                return pubdate;
            }

            public void setCommenturl(String commenturl) {
                this.commenturl = commenturl;
            }

            public String getCommenturl() {
                return commenturl;
            }

            public void setComment(boolean comment) {
                this.comment = comment;
            }

            public int getId() {
                return id;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCommentlist() {
                return commentlist;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTopimage() {
                return topimage;
            }

            public void setCommentlist(String commentlist) {
                this.commentlist = commentlist;
            }

            public void setTopimage(String topimage) {
                this.topimage = topimage;
            }
        }
    }
}
