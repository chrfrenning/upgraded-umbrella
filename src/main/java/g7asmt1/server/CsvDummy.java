package g7asmt1.server;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CsvDummy {
    // public static void main(String[] args) {
    //     try {
    //         Reader reader = Files.newBufferedReader(Paths.get("data.csv"));
    //         CsvToBean<MyBean> csvToBean = new CsvToBeanBuilder<MyBean>(reader)
    //                 .withType(MyBean.class)
    //                 .build();

    //         List<MyBean> beans = csvToBean.parse();

    //         // Query using Java streams
    //         List<MyBean> filteredBeans = beans.stream()
    //                 .filter(bean -> "someValue".equals(bean.getSomeField()))
    //                 .collect(Collectors.toList());

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}