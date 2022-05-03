package com.aptech.javaFx;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.aptech.dao.LinkDataDao;
import com.aptech.dao.LinkDataDaoImpl;
import com.aptech.models.LinkData;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class MyController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private VBox linkTable;

    @FXML
    private ScrollPane container;

    @FXML
    private Button searchButton;

    @FXML
    private TextField myTextField;

    @FXML
    private Hyperlink hyperlink;

    @FXML
    private Pagination pagination;

    public HostServices getHostServices() {
        return hostServices;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    private HostServices hostServices;


    public void showLinks(){
        linkTable.getChildren().clear();
        LinkDataDao linkDataDao = new LinkDataDaoImpl();
        List<Hyperlink> links = new ArrayList<>();
        List<LinkData> linkDataList = linkDataDao.findContent(myTextField.getText());
        //List<LinkData> linkDataList = linkDataDao.findContent();
        linkDataList.forEach(t-> System.out.println(t.getTitle()));

        for(LinkData linkData : linkDataList){
            String link1 = linkData.getLink();
            Hyperlink hyperlink = new Hyperlink(linkData.getLink());
            Hyperlink title = new Hyperlink(linkData.getTitle());
            title.setOnAction(event -> {
                hostServices.showDocument(hyperlink.getText());
            });
            links.add(title);
        }

        linkTable.getChildren().addAll(links);
    }
}
