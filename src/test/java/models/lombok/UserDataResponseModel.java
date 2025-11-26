package models.lombok;


import lombok.Data;

import java.util.List;

@Data
public class UserDataResponseModel {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<UserDataResponseModel> data;
    private SupportDataModel support;
    private MetaResponseModel meta;
}
