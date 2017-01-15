package com.youthlin.jlu.drcom.util;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Created by lin on 2017-01-14-014.
 * 简易浏览器
 * new/show 需要在 JavaFx 线程中
 */
@SuppressWarnings("WeakerAccess")
public class Browser {
    public static final double prefWidth = 800;
    public static final double prefHeight = 600;
    private final TextField urlBar = new TextField();
    private final WebView webView = new WebView();
    private final BorderPane pane = new BorderPane(webView, urlBar, null, null, null);
    private final Stage stage = new Stage();

    public Browser(String url, Double width, Double height) {
        urlBar.setText(url);
        //urlBar.setEditable(false);
        stage.setTitle(Constants.TITLE);
        WebEngine engine = webView.getEngine();
        engine.load(url);
        //http://blog.csdn.net/oppo117/article/details/17354453
        engine.locationProperty().addListener((observable, oldValue, newValue) -> urlBar.setText(newValue));
        engine.titleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                stage.setTitle(newValue + " - " + Constants.TITLE);
            }
        });
        stage.getIcons().add(FxUtil.icon);
        webView.setPrefWidth(width == null ? prefWidth : width);
        webView.setPrefHeight(height == null ? prefHeight : height);
        stage.setScene(new Scene(pane));
        stage.sizeToScene();

        urlBar.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                String newUrl = urlBar.getText().trim();
                if (!newUrl.startsWith("http") && !newUrl.startsWith("file:")) {
                    newUrl = "http://" + newUrl;
                }
                engine.load(newUrl);
                webView.requestFocus();
            }
        });
        webView.setOnKeyReleased(event -> {
            if (event.isControlDown() && KeyCode.L.equals(event.getCode())) {
                urlBar.requestFocus();
            }
        });
        pane.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if (event.isControlDown() && (KeyCode.W.equals(code)) || KeyCode.Q.equals(code)) {
                stage.hide();
            }
        });
    }

    public void show() {
        stage.show();
    }

    public TextField getUrlBar() {
        return urlBar;
    }


    public WebView getWebView() {
        return webView;
    }


    public BorderPane getPane() {
        return pane;
    }

    public Stage getStage() {
        return stage;
    }
}
