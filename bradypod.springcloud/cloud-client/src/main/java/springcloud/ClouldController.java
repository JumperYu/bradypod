package springcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ClouldController {

    // git配置文件里的key
    @Value("${name}")
    String name;

    @RequestMapping("/hi")
    public String hi() {
        return name;
    }

}
