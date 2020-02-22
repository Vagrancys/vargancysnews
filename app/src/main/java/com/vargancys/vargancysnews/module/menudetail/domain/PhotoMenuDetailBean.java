package com.vargancys.vargancysnews.module.menudetail.domain;

import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/22
 * version:1.0
 */
public class PhotoMenuDetailBean {
    private int retcode;
    private Data data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String title;
        private String countcommenturl;
        private String more;
        private List<?> topic;
        private List<News> news;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMore() {
            return more;
        }

        public void setTopic(List<?> topic) {
            this.topic = topic;
        }

        public String getCountcommenturl() {
            return countcommenturl;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public List<?> getTopic() {
            return topic;
        }

        public void setCountcommenturl(String countcommenturl) {
            this.countcommenturl = countcommenturl;
        }

        public List<News> getNews() {
            return news;
        }

        public void setNews(List<News> news) {
            this.news = news;
        }

        public class News{
            private int id;
            private String title;
            private String url;
            private String listimage;
            private String smallimage;
            private String largeimage;
            private String pubdate;
            private boolean comment;
            private String commenturl;
            private String type;
            private String commentlist;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCommentlist() {
                return commentlist;
            }

            public void setCommentlist(String commentlist) {
                this.commentlist = commentlist;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCommenturl() {
                return commenturl;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPubdate() {
                return pubdate;
            }

            public void setComment(boolean comment) {
                this.comment = comment;
            }

            public String getType() {
                return type;
            }

            public void setCommenturl(String commenturl) {
                this.commenturl = commenturl;
            }

            public String getTitle() {
                return title;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public String getListimage() {
                return listimage;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLargeimage() {
                return largeimage;
            }

            public void setListimage(String listimage) {
                this.listimage = listimage;
            }

            public String getSmallimage() {
                return smallimage;
            }

            public void setLargeimage(String largeimage) {
                this.largeimage = largeimage;
            }

            public void setSmallimage(String smallimage) {
                this.smallimage = smallimage;
            }
        }
    }
}
