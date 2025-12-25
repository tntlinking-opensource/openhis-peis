#### Java中执行`xxx.bat`脚本

```java
import java.io.IOException;

public class PortKiller {
    public static void killPort() {
        String cmd = "cmd /c start /B xxx.bat";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```