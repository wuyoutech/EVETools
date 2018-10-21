package main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Window;


public class Controller {
    @FXML private TextField searchField;

    private Popup popShowList = new Popup();
    private ListView<String> autoTipList = new ListView<String>();

    private ChangeListener<String> searchFieldChanged = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if(!newValue.equals("")){
                //设置大小和长度
                autoTipList.setPrefWidth(searchField.getWidth());
                autoTipList.setPrefHeight(100); //TODO:自动匹配长度
                //获取显示位置
                Point2D fieldPosition = searchField.localToScene(0,0);
                Window window = searchField.getScene().getWindow();
                Scene scene = searchField.getScene();
                //显示窗口
                popShowList.show(window,window.getX()+fieldPosition.getX()+scene.getX(),
                        window.getY()+fieldPosition.getY()+scene.getY()+searchField.getHeight());
                autoTipList.getSelectionModel().clearSelection();
                autoTipList.getFocusModel().focus(-1);
            }else {
                popShowList.hide();
            }
        }
    };


    void Init(){
        searchField.textProperty().addListener(searchFieldChanged);
        //TODO:初始化代码
        popShowList.getContent().add(autoTipList);
        //自动修改搜索框大小
        searchField.widthProperty().addListener((observable, oldValue, newValue) ->
                autoTipList.setPrefWidth(searchField.getWidth()));

    }



}
