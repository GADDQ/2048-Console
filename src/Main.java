import java.util.Random;

public class Main {

    //early init
    static int flag=0;//0 for waiting, 1 for playing, 2 for game over.
    static int keyInsert=0;//0 for nothing, 1 for 'w', 2 for 's', 3 for 'a', 4 for 'd'.
    static int score = 0;
    static int[][] gameData = new int[4][4];
    static int[][] colorTable = new int[4][4];

    public static boolean spawnBlock(int[][] gamaData){//spawn a new number block. Also, if there's no space to spawn new block, means game over.
        Random rand = new Random();
        int number;
        int retry=0;
        boolean canSpawn = false;
        if((rand.nextInt(100 - 1 + 1) + 1) % 10 == 0 ){//90% spawn 2, and 10% spawn 4.
            number = 4;
        }else number = 2;
        while(true){
            int x = rand.nextInt(3 + 1);//(MAX - MIN + 1) + MIN
            int y = rand.nextInt(3 + 1);//(3 - 0 + 1) + 0
            if(retry >= 10){
                flag:
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        if (gamaData[j][i] == 0){
                            x = j;
                            y = i;
                            canSpawn = true;
                            break flag;
                        }
                    }
                }
                if(!canSpawn){
                    if(!checkPossibleMove(gameData)){
                        return false;
                    }
                }
            }
            if(gamaData[y][x] == 0){
                gamaData[y][x] = number;
                break;
            }
            retry++;
        }
        return true;
    }

    public static boolean checkPossibleMove(int[][] gameData){//if there's a hope to continue, return true
        int x;
        int y;
        for (int test = 0; test < 4 ; test++){
            switch (test){
                case 0:
                    for (x = 0; x < 4 ; x++){
                        for (y = 1; y < 4; y++) {
                            if (gameData[y - 1][x] == gameData[y][x] && gameData[y][x] != 0) {
                                return true;
                            }
                        }
                    }
                    break;
                case 1:
                    for (x = 0; x < 4 ; x++){
                        for (y = 2; y > -1; y--) {
                            if (gameData[y + 1][x] == gameData[y][x] && gameData[y][x] != 0) {
                                return true;
                            }
                        }
                    }
                    break;
                case 2:
                    for (y = 0; y < 4; y++){
                        for (x = 1; x < 4 ; x++){
                            if (gameData[y][x - 1] == gameData[y][x] && gameData[y][x] != 0) {
                                return true;
                            }
                        }
                    }
                    break;
                case 3:
                    for (y = 0; y < 4; y++){
                        for (x = 2; x > -1 ; x--){
                            if (gameData[y][x + 1] == gameData[y][x] && gameData[y][x] != 0) {
                                return true;
                            }
                        }
                    }
                    break;
            }
        }
        return false;
    }

    public static boolean moveBlock(int[][] gameData,int direction){//move number block and combine same block. If blocks move, there's a new block spawn and "graphicRender" function run for sure
        int x;
        int y;
        int pos;//only one shot to combine, if combine complete, set this = pos x or y.
        boolean move = false;
        switch (direction){
            case 1://up
                for (x = 0; x < 4 ; x++){
                    pos = -1;
                    for (y = 1; y < 4; y++) {
                        if (gameData[y - 1][x] == 0 && gameData[y][x] != 0){
                            gameData[y - 1][x] = gameData[y][x];
                            gameData[y][x] = 0;
                            if (y != 1) y -= 2;//track back old '0'
                            move = true;
                        } else if (gameData[y - 1][x] == gameData[y][x] && gameData[y][x] != 0) {
                            if (pos != y - 1){
                                gameData[y - 1][x] *= 2;
                                score += gameData[y - 1][x];
                                gameData[y][x] = 0;
                                pos = y - 1;
                                move = true;
                            }
                        }
                    }
                }
                break;
            case 2://down
                for (x = 0; x < 4 ; x++){
                    pos = -1;
                    for (y = 2; y > -1; y--) {
                        if (gameData[y + 1][x] == 0 && gameData[y][x] != 0){
                            gameData[y + 1][x] = gameData[y][x];
                            gameData[y][x] = 0;
                            if (y != 2) y += 2;//track back old '0'
                            move = true;
                        } else if (gameData[y + 1][x] == gameData[y][x] && gameData[y][x] != 0) {
                            if (pos != y + 1){
                                gameData[y + 1][x] *= 2;
                                score += gameData[y + 1][x];
                                gameData[y][x] = 0;
                                pos = y + 1;
                                move = true;
                            }
                        }
                    }
                }
                break;
            case 3://left
                for (y = 0; y < 4; y++){
                    pos = -1;
                    for (x = 1; x < 4 ; x++){
                        if (gameData[y][x - 1] == 0 && gameData[y][x] != 0){
                            gameData[y][x - 1] = gameData[y][x];
                            gameData[y][x] = 0;
                            if (x != 1) x -= 2;//track back old '0'
                            move = true;
                        } else if (gameData[y][x - 1] == gameData[y][x] && gameData[y][x] != 0) {
                            if (pos != x - 1){
                                gameData[y][x - 1] *= 2;
                                score += gameData[y][x - 1];
                                gameData[y][x] = 0;
                                pos = x - 1;
                                move = true;
                            }
                        }
                    }
                }
                break;
            case 4://right
                for (y = 0; y < 4; y++){
                    pos = -1;
                    for (x = 2; x > -1 ; x--){
                        if (gameData[y][x + 1] == 0 && gameData[y][x] != 0){
                            gameData[y][x + 1] = gameData[y][x];
                            gameData[y][x] = 0;
                            if (x != 2) x += 2;//track back old '0'
                            move = true;
                        } else if (gameData[y][x + 1] == gameData[y][x] && gameData[y][x] != 0) {
                            if (pos != x + 1){
                                gameData[y][x + 1] *= 2;
                                score += gameData[y][x + 1];
                                gameData[y][x] = 0;
                                pos = x + 1;
                                move = true;
                            }
                        }
                    }
                }
                break;
        }
        if (move){
            if (!spawnBlock(gameData)){
                return true;
            } else graphicRender(gameData);
        }
        return false;
    }

    public static void drawColor(int[][] gameData){//use a table to draw color on number
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                switch (gameData[i][j]){
                    case 2:
                        colorTable[i][j] = 15;
                    case 4:
                        colorTable[i][j] = 180;
                        break;
                    case 8:
                        colorTable[i][j] = 178;
                        break;
                    case 16:
                        colorTable[i][j] = 172;
                        break;
                    case 32:
                        colorTable[i][j] = 208;
                        break;
                    case 64:
                        colorTable[i][j] = 202;
                        break;
                    case 128:
                        colorTable[i][j] = 228;
                        break;
                    case 256:
                        colorTable[i][j] = 227;
                        break;
                    case 512:
                    case 1024:
                    case 2048:
                        colorTable[i][j] = 226;
                        break;
                    case 4096:
                        colorTable[i][j] = 196;
                        break;
                    case 8192:
                        colorTable[i][j] = 160;
                        break;
                    case 16384:
                        colorTable[i][j] = 124;
                        break;
                    case 32768:
                        colorTable[i][j] = 45;
                        break;
                    case 65536:
                        colorTable[i][j] = 39;
                        break;
                    case 131072:
                        colorTable[i][j] = 33;
                        break;
                    default:
                        colorTable[i][j] = 7;
                        break;
                }
            }
        }
    }

    public static void graphicRender(int[][] gameData){//re-draw graphic when execute this function.
        clearScreen.clean();
        drawColor(gameData);
        StringBuilder text = new StringBuilder();
        System.out.println("  /$$$$$$   /$$$$$$  /$$   /$$  /$$$$$$ ");
        System.out.println(" /$$__  $$ /$$$_  $$| $$  | $$ /$$__  $$");
        System.out.println("|__/  \\ $$| $$$$\\ $$| $$  | $$| $$  \\ $$");
        System.out.println("  /$$$$$$/| $$ $$ $$| $$$$$$$$|  $$$$$$/");
        System.out.println(" /$$____/ | $$\\ $$$$|_____  $$ >$$__  $$");
        System.out.println("| $$      | $$ \\ $$$      | $$| $$  \\ $$");
        System.out.println("| $$$$$$$$|  $$$$$$/      | $$|  $$$$$$/");
        System.out.println("|________/ \\______/       |__/ \\______/ ");
        System.out.println("\n");
        System.out.println("Score: " + score + "    ");
        System.out.println("\n");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //output(gameData[i][j] + "", colorTable[i][j]);
                switch ((gameData[i][j] + "").length()){
                    case 1:
                        text.append("\u001b[38;5;").append(colorTable[i][j]).append("m").append(gameData[i][j]).append("\u001b[0m      ");
                        break;
                    case 2:
                        text.append("\u001b[38;5;").append(colorTable[i][j]).append("m").append(gameData[i][j]).append("\u001b[0m     ");
                        break;
                    case 3:
                        text.append("\u001b[38;5;").append(colorTable[i][j]).append("m").append(gameData[i][j]).append("\u001b[0m    ");
                        break;
                    case 4:
                        text.append("\u001b[38;5;").append(colorTable[i][j]).append("m").append(gameData[i][j]).append("\u001b[0m   ");
                        break;
                    case 5:
                        text.append("\u001b[38;5;").append(colorTable[i][j]).append("m").append(gameData[i][j]).append("\u001b[0m  ");
                        break;
                    case 6:
                        text.append("\u001b[38;5;").append(colorTable[i][j]).append("m").append(gameData[i][j]).append("\u001b[0m ");
                        break;
                }
            }
            text.append("\n\n");
        }
        System.out.println(text);
    }

    public static void main(String[] args) {//Main function.

        readKeyboard rk = new readKeyboard();
        Thread t1 = new Thread(rk);

        //show content
        System.out.println("  /$$$$$$   /$$$$$$  /$$   /$$  /$$$$$$ ");
        System.out.println(" /$$__  $$ /$$$_  $$| $$  | $$ /$$__  $$");
        System.out.println("|__/  \\ $$| $$$$\\ $$| $$  | $$| $$  \\ $$");
        System.out.println("  /$$$$$$/| $$ $$ $$| $$$$$$$$|  $$$$$$/");
        System.out.println(" /$$____/ | $$\\ $$$$|_____  $$ >$$__  $$");
        System.out.println("| $$      | $$ \\ $$$      | $$| $$  \\ $$");
        System.out.println("| $$$$$$$$|  $$$$$$/      | $$|  $$$$$$/");
        System.out.println("|________/ \\______/       |__/ \\______/ ");

        System.out.println("\n-----------------------------------------------");

        System.out.println("This is a 2048 game running in console environment.");
        System.out.println("Use w, s, a and d keys to control your numbers.");
        System.out.println("Press Space key to start when you ready!");
        System.out.println("\n(Sadly, if your console environment has some problem, you will need input enter when you input some key to get it works.) ");

        System.out.println("-----------------------------------------------");

        t1.start();

        //init game data
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameData[i][j] = 0;
            }
        }
        //game logic
        while(true){
            synchronized(rk){
                if (flag == 0){
                    continue;
                    //wait for start...
                }
                if (flag == 2) break;//game over!
                if (flag == 1){ //game start!
                    //read key and do something
                    if(keyInsert != 0){
                        switch (keyInsert){
                            case 1:
                                if(moveBlock(gameData, 1)) flag = 2;
                                break;
                            case 2:
                                if(moveBlock(gameData, 2)) flag = 2;
                                break;
                            case 3:
                                if(moveBlock(gameData, 3)) flag = 2;
                                break;
                            case 4:
                                if(moveBlock(gameData, 4)) flag = 2;
                                break;
                        }
                        keyInsert = 0;
                    }
                }
            }
        }
        System.out.println("Game Over!");
    }
}
