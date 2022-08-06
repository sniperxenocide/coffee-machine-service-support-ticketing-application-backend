package com.cgd.cvm_technical_support.tmp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class IssueNextStatus {
    private Long issueId;
    private Long nextStatusId;
    private List<StatusData> dataList;

    public Long getFieldId(int i){
        return dataList.get(i).getFieldId();
    }
    public Long getFieldDataId(int i){
        return dataList.get(i).getFieldDataId();
    }
    public String getFieldData(int i){
        return dataList.get(i).getFieldData();
    }
    public String getFieldData2(int i){
        return dataList.get(i).getFieldData2();
    }
    public String getFieldData3(int i){
        return dataList.get(i).getFieldData3();
    }
}

@Data @NoArgsConstructor @AllArgsConstructor @ToString
class StatusData{
    private Long fieldId;
    private Long fieldDataId;
    private String fieldData;
    private String fieldData2;   // for Spare Parts Quantity
    private String fieldData3;   // for Future Additional Data
}