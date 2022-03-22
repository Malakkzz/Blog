package com.example.blog;

public class Blog {
    private String name;
    private String blog;

    Blog(String n, String b){
        name = n;
        blog = b;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ""+name+"\n"+
                blog;
    }
}
