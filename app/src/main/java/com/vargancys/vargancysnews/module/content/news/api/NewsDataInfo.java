package com.vargancys.vargancysnews.module.content.news.api;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/17
 * version:1.0
 */
public class NewsDataInfo {
    private String message;
    private int error;
    private ArrayList<News> news;

    public ArrayList<News> getNews() {
        return news;
    }

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    public class News{
        private String title;
        private int type;
        private int id;
        private String url;
        private ArrayList<Children> children;

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

        public ArrayList<News.Children> getChildren() {
            return children;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setChildren(ArrayList<News.Children> children) {
            this.children = children;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public class Children{
            private int id;
            private String title;
            private int type;
            private String uri;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }
        }
    }
}
