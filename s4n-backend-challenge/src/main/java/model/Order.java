package model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    @Getter
    @Setter
    public String name;

    @Getter
    @Setter
    public Boolean state;


    @Getter
    @Setter
    private List<String> orders;


}
