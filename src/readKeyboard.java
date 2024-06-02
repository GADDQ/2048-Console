import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

public class readKeyboard implements Runnable {

    @Override
    public void run() {

        //read key real-time
        //set terminal mode
        Terminal terminal = null;
        try {
            terminal = TerminalBuilder.builder()
                    .jna(true)
                    .system(true)
                    .build();

            terminal.enterRawMode();
            NonBlockingReader reader = terminal.reader();

            //read key
            int key;
            while ((key = reader.read()) != -1) {
                if (Main.flag == 2){
                    break;
                }
                char c = (char) key;
                //System.out.println(key);
                switch (c){
                    case 'W':
                    case 'w':
                        Main.keyInsert = 1;
                        break;
                    case 'S':
                    case 's':
                        Main.keyInsert = 2;
                        break;
                    case 'A':
                    case 'a':
                        Main.keyInsert = 3;
                        break;
                    case 'D':
                    case 'd':
                        Main.keyInsert = 4;
                        break;
                    case ' ':
                        if(Main.flag != 1){
                            Main.flag = 1;
                            Main.spawnBlock(Main.gameData);
                            Main.spawnBlock(Main.gameData);
                            Main.graphicRender(Main.gameData);
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
