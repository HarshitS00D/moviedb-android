package com.example.moviestabbasic.model;

public class YouTubeVideo {
    private String videoUrlWebView;
    private  String videoUrl;

    public YouTubeVideo(String videoUrl) {
//        "<body style=\"margin: 0; padding: 0\"><iframe  width=\"100% height=\"100% src=\"https://www.youtube.com/embed/K_tLp7T6U1c\" frameborder=\"0\" allowfullscreen></iframe></body>"));
        this.videoUrlWebView = "<body style=\"margin: 0; padding: 0\"><iframe  width=\"100% height=\"100% src=\"https://www.youtube.com/embed/"+ videoUrl +"\" frameborder=\"0\" allowfullscreen></iframe></body>";
        this.videoUrl = "https://www.youtube.com/watch?v="+ videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getVideoUrlWebView() {
        return videoUrlWebView;
    }

    public void setVideoUrlWebView(String videoUrlWebView) {
        this.videoUrlWebView = videoUrlWebView;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
