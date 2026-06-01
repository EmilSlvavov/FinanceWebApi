package com.finances.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;
    
    @SerializedName("number")
    private Integer pageNumber;
    
    @SerializedName("size")
    private Integer pageSize;
    
    @SerializedName("totalPages")
    private Integer totalPages;
    
    @SerializedName("totalElements")
    private Long totalElements;
    
    // For compatibility with different response formats
    private Integer page;
    private Long numberOfElements;
    private Boolean last;
    private Boolean first;
}
